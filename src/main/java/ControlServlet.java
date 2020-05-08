import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;

public final class ControlServlet extends DatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try{

            // THIS PAGE CAN ONLY BE ACCESSED IF THE USER IS LOGGED IN SO IF IT'S NOT
            if(!IndexServlet.check_login(req))
                
                // POINT HIM TO THE ERROR PAGE
                throw new IOException("You did not sign in before opening the dashboard, sign in and retry");
                
            
            // IF HE IS LOGGED IN, GET THE SESSION TO RETRIEVE ITS USER ID
            HttpSession session = req.getSession();
            
            // GET THE DB CONNECTION FROM THE FATHER CLASS
            Connection c = getConnection();

            // ============== PERSONAL INFO BOX ==============
            
            // QUERY FOR NAME, SURNAME, E-MAIL AND PHONE NR
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT Name, Surname, Email, Phone FROM person WHERE IDUser = " + session.getAttribute("userid"));
            if(rs.next()){
                req.setAttribute("user_fullname", rs.getString("Name") + " " + rs.getString("Surname"));
                req.setAttribute("user_email", rs.getString("Email"));
                req.setAttribute("user_phone", rs.getString("Phone"));
            }//if
            
            // QUERY FOR FEEDBACK AVERAGE AND TOTAL AMOUNT OF FEEDBACKS
            rs = st.executeQuery("SELECT COUNT(TeacherID) as amount, ROUND(IFNULL(AVG(Score), 0), 1) as avg_score FROM feedback WHERE TeacherID = " + session.getAttribute("userid"));
            if(rs.next()){
                req.setAttribute("user_feedamount", rs.getString("amount"));
                req.setAttribute("user_feedaverage", rs.getString("avg_score"));
            }//if

            
            // QUERY FOR TOTAL LECTURING TIME
            rs = st.executeQuery("SELECT IFNULL(TIME_FORMAT(SUM(LessonDuration),'%Hh %im'), '0h 0m') as tot_time FROM lesson WHERE TeacherID = " + session.getAttribute("userid"));
            if(rs.next())
                req.setAttribute("total_time", rs.getString("tot_time"));
            

            req.setAttribute("currentpage", req.getServletPath());
            req.getRequestDispatcher("control.jsp").forward(req, res);
            
            
        }//try

        catch (Exception ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}catch(Exception e){}
        }//catch

    }//doGet

}//ControlServlet