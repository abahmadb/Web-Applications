import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.io.PrintWriter;


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
                                           " ORDER BY Confirmed ASC, LastMessage DESC");

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

        try{

            // GET CONNECTION OBJECT FROM SUPERCLASS DatabaseServlet
            Connection c = getConnection();

            // DISPATCH THE REQUESTO TO THE CORRESPONDING HANDLER FUNCTION
            if(req.getParameter("load_messages") != null)
                download_messages(c, req, res);
            else if(req.getParameter("send_message") != null)
                send_message(c, req, res);
            else if(req.getParameter("confirm_request") != null)
                confirm_request(c, req, res);
            else if(req.getParameter("offer_lesson") != null)
                offer_lesson(c, req, res);
            else if(req.getParameter("confirm_lesson") != null)
                confirm_lesson(c, req, res);

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

        // MAYBE HERE WE CAN DECIDE TO SEND AN EMAIL NOTIFICATION???
        
        // GET READY AND PRINT THE CONVERSATION BACK TO THE BROWSER
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();

        out.printf(esito+"");
    }//send_message

    private void confirm_request(Connection c, HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        
        // SEE WHAT THE TEACHER DECIDED
        boolean decision = Boolean.valueOf(req.getParameter("confirm_request"));
        
        // BASED ON WHAT HE DECIDED, WE PERFORM A DIFFERENT OPERATION
        String query;
        
        if(decision)
            query = "UPDATE chat SET Confirmed = TRUE WHERE TeacherID = ? AND StudentID = ?";
        else
            query = "DELETE FROM chat WHERE TeacherID = ? AND StudentID = ?";

        // MAYBE HERE WE CAN DECIDE TO SEND AN EMAIL NOTIFICATION???
        
        PreparedStatement pst = c.prepareStatement(query);
        pst.setString(1, req.getParameter("teacher_id"));
        pst.setString(2, req.getParameter("student_id"));

        int esito = pst.executeUpdate();

        // GET READY AND PRINT THE QUERY OUTCOME BACK TO THE BROWSER
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();

        out.printf(esito+"");
    }//confirm_request

    private void offer_lesson(Connection c, HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        
        // INSERT A NEW (YET UNCONFIRMED) LESSON INTO THE DB
        PreparedStatement pst = c.prepareStatement("INSERT INTO lesson VALUES (NULL, ?, ?, ?, ?, ?, ?, NULL)", Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, req.getParameter("teacher_id"));
        pst.setString(2, req.getParameter("student_id"));
        pst.setString(3, req.getParameter("lesson_date"));
        pst.setString(4, req.getParameter("lesson_time"));
        pst.setString(5, req.getParameter("lesson_duration"));
        pst.setString(6, req.getParameter("lesson_tariff"));

        // GET THE ID OF THE LESSON WE JUST INSERTED
        int lesson_id = pst.executeUpdate();
        ResultSet k = pst.getGeneratedKeys();
        
        if(!k.next()) throw new IOException("An error occurred in the lesson ID retrieval");
        
        lesson_id = k.getInt(1);
        
        // THIS QUERY JUST DOES 3 BASIC CONVERSIONS, RE-FORMATS THE DATE AND COMPUTES THE LESSON TOTAL COST
        pst = c.prepareStatement("SELECT DATE_FORMAT(?, '%d/%m/%Y') as lesson_date_ok, ROUND(TIME_TO_SEC(?)*?/3600, 2) as lesson_cost, TIME_FORMAT(?, '%Hh%im') as lesson_duration_ok");
        pst.setString(1, req.getParameter("lesson_date"));
        pst.setString(2, req.getParameter("lesson_duration"));
        pst.setString(3, req.getParameter("lesson_tariff"));
        pst.setString(4, req.getParameter("lesson_duration"));
        
        k = pst.executeQuery();
        
        if(!k.next()) throw new IOException("An error occurred in the execution a query to the DB");
            
        
        // NOW IT'S TIME TO LET THE USERS NOW A LESSON HAS BEEN REGISTERED THOUGH A SPECIAL MESSAGE
        String message_body = "<h2>You have a new lesson proposal!</h2><table><tr><td>Date</td><td>Time</td><td>Duration</td><td>Cost</td></tr>";
        message_body = String.format(message_body + "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s&euro;</td></tr></table>", k.getString("lesson_date_ok"),
                                                                                                                   req.getParameter("lesson_time"),
                                                                                                                   k.getString("lesson_duration_ok"),
                                                                                                                   k.getString("lesson_cost"));
        
        // APPEND A SPECIAL MESSAGE TO THE CHAT THROUGH WHICH THE STUDENT CAN ACCEPT/REJECT
        pst = c.prepareStatement("UPDATE chat SET " + 
                                 "Messages = JSON_ARRAY_APPEND(Messages, '$', JSON_OBJECT('SenderID', ?, 'Message', ?, 'TS', DATE_FORMAT(NOW(), '%d-%m-%Y %H:%i'), 'LessonID', ?)), " + 
                                 "LastMessage = NOW() WHERE TeacherID = ? AND StudentID = ?");
        
        pst.setString(1, req.getParameter("teacher_id"));
        pst.setString(2, message_body);
        pst.setString(3, lesson_id+"");
        pst.setString(4, req.getParameter("teacher_id"));
        pst.setString(5, req.getParameter("student_id"));
        
        pst.executeUpdate();
        
        
        // MAYBE HERE WE CAN DECIDE TO SEND AN EMAIL NOTIFICATION???
        
        // GET READY AND PRINT THE CONVERSATION BACK TO THE BROWSER
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();

        out.printf(message_body);
    }//offer_lesson
    
    private void confirm_lesson(Connection c, HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        
        boolean action = Boolean.valueOf(req.getParameter("confirm_lesson"));
        
        String title, query;
        
        // BASED ON WHAT THE STUDENT DECIDED WE EITHER
        if(action){
            
            // CHANGE THE MESSAGE IN THE CHAT TO SAY "OK" AND CONFIRM THE LESSON IN THE CORRESPONDING TABLE
            title = "<h2 class=\"accepted\">This lesson proposal has been accepted</h2>";
            query = "UPDATE lesson SET Payment = NOW() WHERE IDLesson = ?";
            
        }//if
        
        else{
            
            // CHANGE THE MESSAGE IN THE CHAT TO SAY "CANCELED" AND DELETE THE LESSON FROM THE CORRESPONDING TABLE
            title = "<h2 class=\"rejected\">This lesson proposal has been rejected</h2>";
            query = "DELETE FROM lesson WHERE IDLesson = ?";
            
        }//else
        
        
        // CONFIRM THE LESSON OR DELETE IT FROM THE DB
        PreparedStatement pst = c.prepareStatement(query);
        
        pst.setString(1, req.getParameter("lesson_id"));
        
        int esito = pst.executeUpdate();
        if(esito != 1) throw new IOException("An error occurred in confirming/deleting the lesson in the DB");
        
        
        // CHANGE THE "SPECIAL" MESSAGE IN THE CHAT TO THE NEW TITLE VALUE WE SET ERLIER BY
        
        // FINDING THE RIGHT MESSAGE INDEX THE ARRAY OF MESSAGES (e.g. @position = '$[5]')
        pst = c.prepareStatement("SELECT JSON_UNQUOTE(REPLACE(JSON_SEARCH(Messages, 'one', ?, null, '$[*].LessonID'), '.LessonID', '')) as position FROM chat WHERE TeacherID = ? and StudentID = ?");
        
        pst.setString(1, req.getParameter("lesson_id"));
        pst.setString(2, req.getParameter("teacher_id"));
        pst.setString(3, req.getParameter("student_id"));
        
        ResultSet rs = pst.executeQuery();
        if(!rs.next()) throw new IOException("An error occurred in accessing the DB 1");

        // REPLACING THE OLD TITLE WITH THE NEW TITLE
        pst = c.prepareStatement("UPDATE chat SET " + 
                                    "Messages = JSON_SET(" + 
                                    "Messages, "+
                                    "CONCAT(?, '.Message'), "+
                                    "CONCAT(?, SUBSTRING(JSON_UNQUOTE(JSON_EXTRACT(Messages, CONCAT(?, '.Message'))) FROM 41))"+
                                 ") WHERE TeacherID = ? and StudentID = ?");
        
        pst.setString(1, rs.getString("position"));
        pst.setString(2, title);
        pst.setString(3, rs.getString("position"));
        pst.setString(4, req.getParameter("teacher_id"));
        pst.setString(5, req.getParameter("student_id"));
        
        esito = pst.executeUpdate();
        if(esito != 1) throw new IOException("An error occurred in accessing the DB 2");
        
        // REMOVING THE ATTRIBUTE LESSONID FROM THE CHAT OBJECT TO TURN IT INTO A "NORMAL" MESSAGE
        pst = c.prepareStatement("UPDATE chat SET " + 
                                    "Messages = JSON_REMOVE(" + 
                                    "Messages, "+
                                    "CONCAT(?, '.LessonID') "+
                                 ") WHERE TeacherID = ? and StudentID = ?");
        
        pst.setString(1, rs.getString("position"));
        pst.setString(2, req.getParameter("teacher_id"));
        pst.setString(3, req.getParameter("student_id"));
        
        esito = pst.executeUpdate();
        if(esito != 1) throw new IOException("An error occurred in accessing the DB 3");
                
        // MAYBE HERE WE CAN DECIDE TO SEND AN EMAIL NOTIFICATION???
        
        // GET READY AND PRINT THE NEW TITLE BACK TO THE BROWSER
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();

        out.printf(title);
    }//confirm_lesson
    
}//ChatServlet