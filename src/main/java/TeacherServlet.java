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
        
        IndexServlet.check_login(req);
        
        Connection con = getConnection();
        Statement st = null;
        ResultSet rs = null;
        
        String teacher_name = "";
        String teacher_score = "";
        String teacher_numfeed = "";
        String teacher_city = "";
        String teacher_tariff = "";
        String teacher_subject = "";
        String teacher_profile = "";
        
        // GET THE TEACHER ID
        String userid = req.getParameter("teacher_id");
        
        // GET THE TOPIC ID THE USER REQUESTED FROM THE SEARCH PAGE
        String topicid = req.getParameter("topic_id");
        
        
        // LIST OF FEEDBACKS FOR THE REQUESTED TEACHER
        List<Feedback> student_feedbacks = new ArrayList<>();
        
        // LIST OF OTHER TOPICS THIS TEACHER OFFERS
        List<String> teacher_other_subjects = new ArrayList<String>();
                
        // CHECK IF THE TEACHER IDENTITY AND CERTIFICATE ARE VERIFIED
        
        String home = System.getProperty("user.dir");
        File images;
        boolean identity_flag, certificate_flag;
                            
        // IDENTITY VERIFICATION
        images = new File(home + "./webapps/imageset/identity/" + userid + ".jpg");
            
        identity_flag = false;
        if(images.isFile())
            identity_flag = true;
                
        // CERTIFICATE VERIFICATION
        images = new File(home + "./webapps/imageset/certificate/" + userid + ".jpg");
                
        certificate_flag = false;
        if(images.isFile())
            certificate_flag = true;

        
        try {
            
            // RETRIEVE THE NAME, CITY AND DESCRIPTION OF THE TEACHER         
            st = con.createStatement();
            rs = st.executeQuery("SELECT Name, City, JSON_UNQUOTE(Description) as teacher_desc FROM person WHERE IDUser = " + userid);
            
            if (!rs.next()) throw new IOException("The requested teacher could not be found");
            teacher_name = rs.getString("Name");
            teacher_city = rs.getString("City");
            teacher_profile = rs.getString("teacher_desc");
            
            
            // RETRIEVE THE AVERAGE TEACHER SCORE
            rs = st.executeQuery("SELECT ROUND(IFNULL(AVG(Score), '0'), 1) as avg_score, COUNT(TeacherID) as num_feed FROM feedback WHERE TeacherID = " + userid);
            
            if (!rs.next()) throw new IOException("Score in feedback table could not be found.");
            teacher_score = rs.getString("avg_score");
            teacher_numfeed = rs.getString("num_feed");
            
            
            // RETRIEVE THE TEACHER TARIFF FOR THIS TOPIC AND THE TOPIC LABEL
            rs = st.executeQuery("SELECT Tariff, T.Label FROM teacher_topic as TT JOIN topic as T ON TT.TopicID=T.IDTopic WHERE TeacherID = " + userid + " AND TopicID = " + topicid);
            
            if (!rs.next()) throw new IOException("Tariff in teacher_topic table could not be found.");
            teacher_tariff = rs.getString("Tariff");
            teacher_subject = rs.getString("Label");
            
            
            // RETRIEVE THE OTHER TOPICS THIS TEACHER OFFERS
            rs = st.executeQuery("SELECT O.Label FROM teacher_topic T INNER JOIN topic O ON T.TopicID = O.IDTopic WHERE T.TeacherID = " + userid + " AND T.TopicID <> " + topicid);
            
            while (rs.next()) 
                teacher_other_subjects.add(rs.getString("O.Label"));
                        
            
            // RETRIEVE THE DETAILED FEEDBACK VIEW WITH STUDENT ID, NAME, DESCRIPTION AND SCORE
            rs = st.executeQuery("SELECT IDUser, Name, F.Description, Score FROM person P INNER JOIN feedback F ON F.StudentID = P.IDUser WHERE TeacherID = " + userid);
            
            while (rs.next())
                student_feedbacks.add(new Feedback(rs.getInt("IDUser"), rs.getString("Name"), null, rs.getInt("Score"), null, rs.getString("F.Description")));
        
            
        }
        
        catch (Exception ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);} 
            catch(Exception e){}
        }
        
        // SEND THE VALUES TO THE JSP PAGE
        req.setAttribute("teacher_id", userid);
        req.setAttribute("teacher_name", teacher_name);        
        req.setAttribute("teacher_avgscore", teacher_score);
        req.setAttribute("teacher_numfeed", teacher_numfeed);
        req.setAttribute("teacher_city", teacher_city);
        req.setAttribute("teacher_price", teacher_tariff);
        req.setAttribute("teacher_description", teacher_profile);
        req.setAttribute("teacher_identity", identity_flag);
        req.setAttribute("teacher_certificate", certificate_flag);
        req.setAttribute("teacher_subject", teacher_subject);
        
        req.setAttribute("teacher_other_subjects", teacher_other_subjects);
        
        req.setAttribute("student_feedbacks", student_feedbacks);                                   
        
        req.getRequestDispatcher("teacher.jsp").forward(req, res);

    }//doGet
    
    
    //doPost is used when a student clicks on the "book a lesson" button in order to book the lesson of the teacher he is viewing
    public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        PrintWriter out = res.getWriter();
        
        // IF THE USER IS NOT LOGGED IN STOP THE REQUEST AND WRITE BACK TO BROWSER
        if (!IndexServlet.check_login(req)){
               out.printf("0");
               return;
        }
        
        Connection con = getConnection();
        
        // GET USERID FROM SESSION
        String studentID = req.getSession().getAttribute("userid")+"";
        
        // GET VALUES FROM AJAX REQUEST
        String teacherID = req.getParameter("teacherID");
        String comment = req.getParameter("comment");

        //try-with-resources syntax, does not need a finally block to close the statement resource
        //on duplicate key is needed if the student books more than a lesson
        try (PreparedStatement st = con.prepareStatement("INSERT INTO chat VALUES (?, ?, FALSE, JSON_ARRAY(JSON_OBJECT('SenderID', ?, 'Message', ?, 'TS', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i'))), NOW()) ON DUPLICATE KEY UPDATE Messages = JSON_ARRAY_APPEND(Messages, '$', JSON_OBJECT('SenderID', ?, 'Message', ?, 'TS', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i'))), LastMessage = NOW()")) {

            //insert the feedback into the db
            st.setString(1, teacherID);
            st.setString(2, studentID);
            st.setString(3, studentID);
            st.setString(4, comment);
            st.setString(5, studentID);
            st.setString(6, comment);
            st.executeUpdate();
            out.printf("1");

        }//try

        catch (SQLException e) {
            out.printf("0");
        }//catch

    }//dopost
}
