import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public final class IndexServlet extends DatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try{

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

                    }//if

                }//for

            }//if

            String lista = "";

            Connection c = getConnection();

            Statement st = c.createStatement();

            ResultSet topics = st.executeQuery("SELECT * FROM topic");

            while(topics.next())
                lista += "{id: '" + topics.getInt("IDTopic") + "', value: '" + topics.getString("Label") + "'},";

            if(lista.length() > 0)
                lista = lista.substring(0, lista.length()-1);

            req.setAttribute("topics_list", lista);

            req.getRequestDispatcher("index.jsp").forward(req, res);


        }//try

        catch (SQLException ex) {


        }//catch

    }//doGet

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // CHECK IF THE REQUEST IF OF SIGN IN OR SIGN UP TYPE AND DISPATCH TO CORRECT HANDLER

        if(req.getParameter("register") == null)
            doSignIN(req, res);
        else
            doSignUP(req, res);
    }//doPost


    private void doSignIN(HttpServletRequest req, HttpServletResponse res){

        try{
            Connection c = getConnection();

            PreparedStatement pst = c.prepareStatement("SELECT IDUser, COUNT(IDUser) as c FROM person WHERE Email = ? AND Passwd = SHA2(?, 256)");

            pst.setString(1, req.getParameter("uname"));
            pst.setString(2, req.getParameter("pword"));
            
            ResultSet rs = pst.executeQuery();

            
            res.setContentType("application/json; charset=utf-8");
            PrintWriter out = res.getWriter();

            if(rs.next() && rs.getInt("c") == 1){

                HttpSession session = req.getSession();
                session.setAttribute("userid", rs.getInt("IDUser"));

                out.printf("{\"response\": 1, \"userid\": " + rs.getInt("IDUser") + "}");
            }
            else
                out.printf("{\"response\": 0, \"userid\": null}");

        }//try

        catch (SQLException ex) {


        }//catch
        catch (IOException ex) {


        }//catch
            
    }//doSignIN

    private void doSignUP(HttpServletRequest req, HttpServletResponse res){
        try{
            
            // VALIDATION MISSING
            
            Connection c = getConnection();

            PreparedStatement pst = c.prepareStatement("INSERT INTO person VALUES (NULL, ?, ?, NULL, NULL, ?, SHA2(?,256), NULL, NULL)");

            pst.setString(1, req.getParameter("firstname"));
            pst.setString(2, req.getParameter("lastname"));
            pst.setString(3, req.getParameter("email"));
            pst.setString(4, req.getParameter("passwd"));

            int esito = pst.executeUpdate();

            if(esito == 1){

                // GET THE ID OF THE INSERTED USER
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() as last_person");

                // SING HIM IN
                if(rs.next()){
                    HttpSession session = req.getSession();
                    session.setAttribute("userid", rs.getInt("last_person"));
                    res.sendRedirect(req.getContextPath());
                }
                
                // CREATE A PLACEHOLDER USER PICTURE
                //Files.copy(new File("images/imageset/profile/nopic.jpg").toPath(), new File("images/imageset/profile/" + rs.getInt("last_person") + ".jpg").toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            else{
                //ERRORPAGE
            }

        }//try

        catch (SQLException ex) {


        }//catch
        catch (IOException ex) {


        }//catch
    }//doSignUP


}//IndexServlet