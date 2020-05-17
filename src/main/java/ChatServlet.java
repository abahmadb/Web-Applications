import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public  final class ChatServlet extends DatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {

            // THIS PAGE CAN ONLY BE ACCESSED IF THE USER IS LOGGED IN SO IF IT'S NOT
            if(!IndexServlet.check_login(req))

                // POINT HIM TO THE ERROR PAGE
                throw new IOException("You did not sign in before opening the dashboard, sign in and retry");

            // IF HE IS LOGGED IN, LET'S GET THE SESSION TO GET IT'S ID
            HttpSession session = req.getSession();

            // GET CONNECTION OBJECT FROM SUPERCLASS DatabaseServlet
            Connection c = getConnection();
            Statement st = c.createStatement();

            // EXECUTE QUERY TO RETREIVE THE CONTACTS LIST (ID, NAME, SURNAME) AND THEIR LAST MESSAGES (+ THE DATETIME OF THEM)
            ResultSet rs = st.executeQuery("SELECT IF(C.TeacherID = " + session.getAttribute("userid") + ", S.IDUser, T.IDUser) as userid, " + 
                                           "IF(C.TeacherID = " + session.getAttribute("userid") + ", S.Name, T.Name) as name, " + 
                                           "IF(C.TeacherID = " + session.getAttribute("userid") + ", S.Surname, T.Surname) as surname, " + 
                                           "IF(C.TeacherID = " + session.getAttribute("userid") + ", TRUE, FALSE) as am_i_teacher, " + 
                                           "@full_length := (JSON_UNQUOTE(JSON_EXTRACT(JSON_EXTRACT(Messages, '$[last]'), '$.Message'))) as full_message, " + 
                                           "CONCAT(LEFT(@full_length, 100), IF(LENGTH(@full_length)>100, '...', '')) as cropped_message, " + 
                                           "DATE_FORMAT(LastMessage, '%d/%m/%Y %H:%i') as last_message, " +
                                           "Confirmed as okay " +
                                           "FROM (chat as C JOIN person as T ON C.TeacherID=T.IDUser) JOIN person as S ON C.StudentID=S.IDUser "+
                                           "WHERE TeacherID = " + session.getAttribute("userid") + " OR StudentID = " + session.getAttribute("userid") +
                                           " ORDER BY LastMessage DESC");

            // GET READY WITH A LIST OF OBJECT TO PUSH TO THE JSP PAGE
            ArrayList<ChatContact> contactlist = new ArrayList<ChatContact>();

            while(rs.next())
                contactlist.add(new ChatContact(rs.getInt("userid"),
                                                rs.getString("name"),
                                                rs.getString("surname"),
                                                rs.getString("last_message"),
                                                rs.getString("cropped_message"),
                                                rs.getBoolean("am_i_teacher"),
                                                rs.getBoolean("okay")));

            // PUSH THE LIST TO THE JSP
            req.setAttribute("contactlist", contactlist);

            // THIS IS NEEDED FOR THE MENU TO SHOW THE "CHAT" ITEM IN BLUETTE
            req.setAttribute("currentpage", req.getServletPath());

            // PASS THE CONTROL OVER TO THE JSP
            req.getRequestDispatcher("chat.jsp").forward(req, res);

        }//try

        catch (Exception e) {

            req.setAttribute("error_message", e.getMessage());
            req.setAttribute("appname", req.getContextPath());

            try{
                req.getRequestDispatcher("errorpage.jsp").forward(req, res);
            }catch(Exception ignored){}

        }//catch

    }//doGet

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        // YOU CAN USE THIS TO LISTEN TO AN AJAX POST REQUEST AND RESPOND WITH THE WHOLE CHAT CONTENT
        // THIS GOES TO REPLACE THE "DOWNLOAD EVERYTHING AT THE BEGINNING" WHICH COULD SLOW DOWN THE PAGE

        try{

            // GET CONNECTION OBJECT FROM SUPERCLASS DatabaseServlet
            Connection c = getConnection();

            // DISPATCH THE REQUESTO TO THE CORRESPONDING HANDLER FUNCTION
            if(req.getParameter("load_messages") != null)
                download_messages(c, req, res);
            else if(req.getParameter("send_message") != null)
                send_message(c, req, res);

        }//try

        catch (Exception ex) {
            req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}catch(Exception e){}
        }//catch
    }//doPost

    private void download_messages(Connection c, HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        // PREPARE AND EXECUTE THE QUERY TO RETREIVE THE WHOLE CONVERSATION
        PreparedStatement pst = c.prepareStatement("SELECT Messages as ms FROM chat WHERE TeacherID = ? AND StudentID = ?");

        pst.setString(1, req.getParameter("teacher_id"));
        pst.setString(2, req.getParameter("student_id"));

        ResultSet rs = pst.executeQuery();

        // GET READY AND PRINT THE CONVERSATION BACK TO THE BROWSER
        res.setContentType("application/json; charset=utf-8");
        PrintWriter out = res.getWriter();

        if(rs.next())
            out.printf(rs.getString("ms"));
    }//download_messages
    
    private void send_message(Connection c, HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        // PREPARE AND EXECUTE THE QUERY TO SEND A MESSAGE
        PreparedStatement pst = c.prepareStatement("UPDATE chat SET " + 
                                                   "Messages = JSON_ARRAY_APPEND(Messages, '$', JSON_OBJECT('SenderID', ?, 'Message', ?, 'TS', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i'))), " + 
                                                   "LastMessage = NOW() WHERE TeacherID = ? AND StudentID = ?");

        pst.setString(1, req.getParameter("sender"));
        pst.setString(2, req.getParameter("text_message"));
        pst.setString(3, req.getParameter("teacher_id"));
        pst.setString(4, req.getParameter("student_id"));

        int esito = pst.executeUpdate();

        // GET READY AND PRINT THE CONVERSATION BACK TO THE BROWSER
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();

        out.printf(esito+"");
    }//send_message


}//ChatServlet