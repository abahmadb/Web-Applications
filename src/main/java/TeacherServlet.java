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
        
        try {
           
            //req.getSession().setAttribute("userid", 2);

            //int userid = (int) req.getSession().getAttribute("userid");
            
            //Retrive the name of the teacher
            int userid = Integer.parseInt(req.getParameter("teacher_id"));
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Name FROM person WHERE IDUser = " + userid);
            
            while (rs.next()) {
                teacher_name.append(rs.getString("Name"));
            }
            
            //Retrieve the average teacher score
            st = con.createStatement();
            rs = st.executeQuery("SELECT AVG(Score) FROM feedback WHERE TeacherID = " + userid);
            
            while (rs.next()) {
                teacher_score.append(rs.getFloat("AVG(Score)"));
            }
            
            //Retrieve the teacher city
            st = con.createStatement();
            rs = st.executeQuery("SELECT City FROM person WHERE IDUser = " + userid);
            
            while (rs.next()) {
                teacher_city.append(rs.getString("City"));
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
        
        req.setAttribute("teacher_name", teacher_name);        
        req.setAttribute("teacher_avgscore", teacher_score);
        req.setAttribute("teacher_city", teacher_city);
        req.setAttribute("teacher_price", teacher_tariff);
        
        req.getRequestDispatcher("teacher.jsp").forward(req, res);

    }
}
