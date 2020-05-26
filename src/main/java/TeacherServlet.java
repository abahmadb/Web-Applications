import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;

public final class TeacherServlet extends DatabaseServlet {
    
    //doGet is used to retrieve data from database in order to "populate" a specific teacher page with the current teacher info
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        Connection con = getConnection();
        Statement st = null;
        ResultSet rs = null;
        
        String teacher_name = "";
        String teacher_score = "";
        String teacher_city = "";
        String teacher_tariff = "";
        String teacher_subject = "";
        String teacher_description = "";
        
        //need to retrieve teacher_id from search_jsp in order to know what teacher to visualize on the teacher page
        String userid = req.getParameter("teacher_id");
        //need to retrieve topic_id from search.jsp in order to retrieve the teacher price per hour
        String topicid = req.getParameter("topic_id");
        String teacher_profile = null;
        
        //feedback from student
        List<TeacherFeedback> student_feedbacks = new ArrayList<>();
        
        //array of strings for other subjects taught by teacher
        List<String> teacher_other_subjects = new ArrayList<String>();
        
        //check if teacher has other subjects that he teaches
        boolean other_subjects = true;
        
        // CHECK IF THE TEACHER IDENTITY AND CERTIFICATE ARE VERIFIED
        
        String home = System.getProperty("user.dir");
        File images;
        boolean identity_flag, certificate_flag;
                            
        // IDENTITY VERIFICATION
        images = new File(home + "./webapps/imageset/identity/" + userid + ".jpg");
            
        identity_flag = false;
        if(images.isFile()) {
            identity_flag = true;
        }
                
        // CERTIFICATE VERIFICATION
        images = new File(home + "./webapps/imageset/certificate/" + userid + ".jpg");
                
        certificate_flag = false;
        if(images.isFile()) {
            certificate_flag = true;
        }
        
        try {
            
            //Retrive the name, city and description of the teacher            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Name, City, Description FROM person WHERE IDUser = " + userid);
            
            if (!rs.next()) throw new IOException("Name, City or Description in table person could not be found.");
            teacher_name = rs.getString("Name");
            teacher_city = rs.getString("City");
            teacher_description = rs.getString("Description");
            
            //need to skip first " character and last " character since I don't need them in the description
            teacher_profile = teacher_description.substring(1, teacher_description.length() - 1);
            
            //Retrieve the average teacher score
            st = con.createStatement();
            rs = st.executeQuery("SELECT AVG(Score) FROM feedback WHERE TeacherID = " + userid);
            
            if (!rs.next()) throw new IOException("Score in feedback table could not be found.");
            teacher_score = String.valueOf(rs.getFloat("AVG(Score)"));
            
            //Retrieve the teacher price per hour
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Tariff FROM teacher_topic WHERE TeacherID = " + userid + " AND TopicID = " + topicid);
            
            if (!rs.next()) throw new IOException("Tariff in teacher_topic table could not be found.");
            teacher_tariff = String.valueOf(rs.getInt("Tariff"));
            
            //Retrieve the teacher topic name to put in the subject section
            st = con.createStatement();
            rs = st.executeQuery("SELECT Label FROM topic WHERE IDTopic = " + topicid);
            
            while (rs.next()) {
                teacher_subject = teacher_subject + (rs.getString("Label"));
            }
            
            //Retrieve the other subjects the teacher offers
            st = con.createStatement();
            rs = st.executeQuery("SELECT O.Label FROM teacher_topic T INNER JOIN topic O ON T.TopicID = O.IDTopic WHERE T.TeacherID = " + userid);
            
            while (rs.next()) {
                teacher_other_subjects.add(new String(rs.getString("O.Label")));
            }
            
            //make sure you delete the current teacher subject from the other subjects
            if (teacher_other_subjects.contains(teacher_subject)) {
                teacher_other_subjects.remove(teacher_subject);
            }
            
            //check if teacher has other subjects
            if (teacher_other_subjects.isEmpty()) {
                other_subjects = false;
            }
            
            //Retrieve the student id, name, description and score for the feedback
            st = con.createStatement();
            rs = st.executeQuery("SELECT IDUser, Name, F.Description, Score FROM person P INNER JOIN feedback F ON F.StudentID = P.IDUser WHERE TeacherID = " + userid);
            
            while (rs.next()) {
                student_feedbacks.add(new TeacherFeedback(rs.getInt("IDUser"), rs.getString("Name"), rs.getString("F.Description"), rs.getInt("Score")));
            }
            
        }
        
        catch (Exception ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);} 
            catch(Exception e){}
        }
        
        //release resources in the end anyway
        finally {

            if (rs != null) {

                try {
                    rs.close();
                }

                catch (SQLException ignored) { }

            }

            if (st != null) {

                try {
                    st.close();
                }

                catch (SQLException ignored) { }

            }

        }
        
        req.setAttribute("teacher_id", userid);
        req.setAttribute("teacher_name", teacher_name);        
        req.setAttribute("teacher_avgscore", teacher_score);
        req.setAttribute("teacher_city", teacher_city);
        req.setAttribute("teacher_price", teacher_tariff);
        req.setAttribute("teacher_description", teacher_profile);
        req.setAttribute("teacher_identity", identity_flag);
        req.setAttribute("teacher_certificate", certificate_flag);
        req.setAttribute("teacher_subject", teacher_subject);
        req.setAttribute("other_subjects", other_subjects);
        
        req.setAttribute("teacher_other_subjects", teacher_other_subjects);
        
        req.setAttribute("student_feedbacks", student_feedbacks);                                   
        
        req.getRequestDispatcher("teacher.jsp").forward(req, res);

    }
    
    
    //doPost is used when a student clicks on the "book a lesson" button in order to book the lesson of the teacher he is viewing
    public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        //if user is not logged in set status to 500
        if (!IndexServlet.check_login(req)){
               res.setStatus(500);
               return;
        }
        
        Connection con = getConnection(); //use DatabaseServlet method to get connection
        
        //get userid from session
        int studentID = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));
        
        //get parameters sent by ajax function
        int teacherID = Integer.parseInt(req.getParameter("teacherID"));
        String comment = req.getParameter("comment");

        //try-with-resources syntax, does not need a finally block to close the statement resource
        //on duplicate key is needed if the student books more than a lesson
        try (PreparedStatement st = con.prepareStatement("INSERT INTO chat VALUES (?, ?, FALSE, JSON_ARRAY(JSON_OBJECT('SenderID', ?, 'Message', ?, 'TS', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i'))), NOW()) ON DUPLICATE KEY UPDATE Messages = JSON_ARRAY_APPEND(Messages, '$', JSON_OBJECT('SenderID', ?, 'Message', ?, 'TS', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i'))), LastMessage = NOW()")) {

            //insert the feedback into the db
            st.setInt(1, teacherID);
            st.setInt(2, studentID);
            st.setInt(3, studentID);
            st.setString(4, comment);
            st.setInt(5, studentID);
            st.setString(6, comment);
            st.executeUpdate();

        }

        catch (SQLException e) {

        }

    }
}
