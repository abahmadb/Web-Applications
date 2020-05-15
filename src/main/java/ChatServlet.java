import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;
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
                                 "@full_length := (JSON_UNQUOTE(JSON_EXTRACT(JSON_EXTRACT(Messages, '$[last]'), '$.Message'))) as full_message, " + 
                                 "CONCAT(LEFT(@full_length, 100), IF(LENGTH(@full_length)>100, '...', '')) as cropped_message, " + 
                                 "DATE_FORMAT(LastMessage, '%d/%m/%Y %H:%i') as last_message " +
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
                                          rs.getString("cropped_message")));
            
            // PUSH THE LIST TO THE JSP
            req.setAttribute("contactlist", contactlist);
            
            // THIS IS NEEDED FOR THE MENU TO SHOW THE "CHAT" ITEM IN BLUETTE
            req.setAttribute("currentpage", req.getServletPath());
            
            // PASS THE CONTROL OVER TO THE 
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
        
    }//doPost


}//ChatServlet