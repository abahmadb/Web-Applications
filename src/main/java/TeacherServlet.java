import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;

public final class TeacherServlet extends DatabaseServlet {
    
    //retrieve data from database
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        Connection con = getConnection();
        Statement st = null;
        ResultSet rs = null;
        
        StringBuilder teacher_name = new StringBuilder("");
        StringBuilder teacher_score = new StringBuilder("");
        StringBuilder teacher_city = new StringBuilder("");
        StringBuilder teacher_tariff = new StringBuilder("");
        StringBuilder teacher_description = new StringBuilder("");
        
        int userid = Integer.parseInt(req.getParameter("teacher_id"));
        String teacher_profile = null;
        
        try {
            
            //Retrive the name, city and description of the teacher            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Name, City, Description FROM person WHERE IDUser = " + userid);
            
            while (rs.next()) {
                teacher_name.append(rs.getString("Name"));
                teacher_city.append(rs.getString("City"));
                teacher_description.append(rs.getString("Description"));
            }
            
            //need to skip first and last characters since I don't need them in the description
            teacher_profile = teacher_description.substring(1, teacher_description.length() - 1);
            
            //Retrieve the average teacher score
            st = con.createStatement();
            rs = st.executeQuery("SELECT AVG(Score) FROM feedback WHERE TeacherID = " + userid);
            
            while (rs.next()) {
                teacher_score.append(rs.getFloat("AVG(Score)"));
            }
            
            //Retrieve the teacher price per hour
            //need to retrieve topic_id for that from search.jsp
            int topicid = Integer.parseInt(req.getParameter("topic_id"));
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Tariff FROM teacher_topic WHERE TeacherID = " + userid + " AND TopicID = " + topicid);
            
            while (rs.next()) {
                teacher_tariff.append(rs.getInt("Tariff"));
            }         
            
        }
        
        catch (SQLException e) {
            e.printStackTrace();
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
        
        req.getRequestDispatcher("teacher.jsp").forward(req, res);

    }
}
