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
        
        try {
            
            /*
            // CHECK IF THERE IS A COOKIE AND NO LOGIN SESSION, IF SO, SET THE SESSION
            HttpSession session = req.getSession();
            Cookie[] cs = req.getCookies();

            if(cs != null && session.getAttribute("userid") == null){

                // LOOP THROUGH THE COOKIES TO FIND THE LOGIN ONE
                boolean found_cookie = false;
                for(int i = 0; i < cs.length && !found_cookie; i++){

                    if(cs[i].getName().equals("userid")){

                        found_cookie = true;

                        session.setAttribute("userid", cs[i].getValue());

                    }

                }

            }*/  
           
            //req.getSession().setAttribute("userid", 2);

            //int userid = (int) req.getSession().getAttribute("userid");
            
            int userid = Integer.parseInt(req.getParameter("teacher_id"));
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT Name FROM person WHERE person.IDUser = " + userid);
            
            teacher_name.append(rs.getString("Name"));
            
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        //release resources in the end anyway
        finally {

            //connection destroy method from DatabaseServlet
            destroy();

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
        
        req.getRequestDispatcher("teacher.jsp").forward(req, res);

    }
}
