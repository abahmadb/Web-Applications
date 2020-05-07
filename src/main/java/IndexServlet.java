import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
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

            List<Topic> lista = new ArrayList<Topic>();

            Connection c = getConnection();

            Statement st = c.createStatement();

            ResultSet topics = st.executeQuery("SELECT * FROM topic");

            while(topics.next())
                lista.add(new Topic(topics.getInt("IDTopic"), topics.getString("Label")));

            req.setAttribute("topics_list", lista);

            req.getRequestDispatcher("index.jsp").forward(req, res);


        }//try

        catch (SQLException ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}catch(Exception e){}
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

        catch (Exception ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}catch(Exception e){}
        }//catch

    }//doSignIN

    private void doSignUP(HttpServletRequest req, HttpServletResponse res) throws ServletException{
        try{

            if(!isValid(req.getParameter("email"))) throw new IOException("The e-mail is invalid");


            // + FURTHER SIMILAR VALIDATIONS ON INPUT DATA

            Connection c = getConnection();

            PreparedStatement pst = c.prepareStatement("INSERT INTO person VALUES (NULL, ?, ?, NULL, NULL, ?, SHA2(?,256), NULL, NULL)");

            pst.setString(1, req.getParameter("firstname"));
            pst.setString(2, req.getParameter("lastname"));
            pst.setString(3, req.getParameter("email"));
            pst.setString(4, req.getParameter("passwd"));

            int esito = pst.executeUpdate();

            if(esito == 0) throw new IOException("An error occurred in the creation of the new user");

            // GET THE ID OF THE INSERTED USER
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() as last_person");
            if(rs.next()){

                // CREATE PLACEHOLDER PICTURES
                createPlaceholderImages(rs.getInt("last_person"));

                // SIGN HIM IN
                HttpSession session = req.getSession();
                session.setAttribute("userid", rs.getInt("last_person"));

                // REDIRECT HIM TO HOMEPAGE
                res.sendRedirect(req.getContextPath());

            }

        }//try

        catch (Exception ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}catch(Exception e){}
        }//catch

    }//doSignUP

    private boolean isValid(String email) { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
            "[a-zA-Z0-9_+&*-]+)*@" + 
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
            "A-Z]{2,7}$"; 

        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 


    private void createPlaceholderImages(int uid) throws IOException{

        String home = System.getProperty("user.dir");

        File p_dir = new File(home + "./webapps/imageset/profile/profile.jpg");
        File p_dir_new = new File(home + "./webapps/imageset/profile/" + uid + ".jpg");

        File i_dir = new File(home + "./webapps/imageset/identity/identity.jpg");
        File i_dir_new = new File(home + "./webapps/imageset/identity/" + uid + ".jpg");

        File c_dir = new File(home + "./webapps/imageset/certificate/certificate.jpg");
        File c_dir_new = new File(home + "./webapps/imageset/certificate/" + uid + ".jpg");

        Files.copy(p_dir.toPath(), p_dir_new.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(i_dir.toPath(), i_dir_new.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(c_dir.toPath(), c_dir_new.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }

}//IndexServlet