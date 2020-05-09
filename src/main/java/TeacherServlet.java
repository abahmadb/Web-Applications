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
        
        try {
           
            //req.getSession().setAttribute("userid", 2);

            //int userid = (int) req.getSession().getAttribute("userid");
            
            //Retrive the name of the teacher
            int userid = Integer.parseInt(req.getParameter("teacher_id"));
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Name FROM person WHERE person.IDUser = " + userid);
            
            while (rs.next()) {
                teacher_name.append(rs.getString("Name"));
            }
            
            //Retrieve the average teacher score
            st = con.createStatement();
            rs = st.executeQuery("SELECT AVG(Score) FROM feedback WHERE TeacherID = " + userid);
            
            while (rs.next()) {
                teacher_score.append(rs.getFloat("AVG(Score)"));
            }
            
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        //release resources in the end anyway
        finally {

            //connection destroy method from DatabaseServlet
            //destroy();

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
        
        req.getRequestDispatcher("teacher.jsp").forward(req, res);

    }
}
