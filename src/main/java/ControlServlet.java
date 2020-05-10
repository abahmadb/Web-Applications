import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;
import java.text.*;

public final class ControlServlet extends DatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try{

            HttpSession session = req.getSession();

            //  A SIGN-OUT REQUEST HAS BEEN ISSUED
            if(Boolean.valueOf(req.getParameter("signout"))){

                // UNSET THE SESSION LOGIN VALUE
                session.setAttribute("userid", null);

                // SEARCH FOR A POSSIBLE LOGIN COOKIE
                Cookie[] cs = req.getCookies();

                if(cs != null){

                    // IF YOU FIND IT, UNSET IT
                    boolean found = false;
                    for(int i = 0; i < cs.length && !found; i++){

                        if(cs[i].getName().equals("userid")){
                            cs[i].setValue("");
                            cs[i].setPath("/");
                            cs[i].setMaxAge(0);
                            res.addCookie(cs[i]);
                            found = true;
                        }//if

                    }//for

                }//if

                res.sendRedirect(req.getContextPath());
                return;
            }//if

            // THIS PAGE CAN ONLY BE ACCESSED IF THE USER IS LOGGED IN SO IF IT'S NOT
            if(!IndexServlet.check_login(req))

                // POINT HIM TO THE ERROR PAGE
                throw new IOException("You did not sign in before opening the dashboard, sign in and retry");


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

            // ============== CHAT BOX ==============
            ArrayList<ArrayList<String>> recent_chats = new ArrayList<ArrayList<String>>();
            rs = st.executeQuery("SELECT IF(C.TeacherID = " + session.getAttribute("userid") + ", S.Name, T.Name) as name, " + 
                                 "@full_length := (JSON_UNQUOTE(JSON_EXTRACT(JSON_EXTRACT(Messages, '$[last]'), '$.Message'))) as full_message, " + 
                                 "CONCAT(LEFT(@full_length, 100), IF(LENGTH(@full_length)>100, '...', '')) as cropped_message, " + 
                                 "DATE_FORMAT(LastMessage, '%d/%m/%Y %H:%i') as last_message " +
                                 "FROM (chat as C JOIN person as T ON C.TeacherID=T.IDUser) JOIN person as S ON C.StudentID=S.IDUser "+
                                 "WHERE TeacherID = " + session.getAttribute("userid") + " OR StudentID = " + session.getAttribute("userid") +
                                 " ORDER BY LastMessage LIMIT 4");

            while(rs.next()){
                recent_chats.add(new ArrayList<String>());

                recent_chats.get(recent_chats.size() - 1).add(rs.getString("name"));
                recent_chats.get(recent_chats.size() - 1).add(rs.getString("cropped_message"));
                recent_chats.get(recent_chats.size() - 1).add(rs.getString("last_message"));
            }//while

            req.setAttribute("recent_chats", recent_chats);

            // ============== FEEBACK BOX ==============
            ArrayList<Feedback> recent_feed = new ArrayList<Feedback>();
            rs = st.executeQuery("SELECT S.Name, " + 
                                 "@full_length := (F.Description) as full_desc, " + 
                                 "CONCAT(LEFT(@full_length, 100), IF(CHAR_LENGTH(@full_length)>100, '...', '')) as cropped_desc, " + 
                                 "F.Score " + 
                                 "FROM feedback as F JOIN person as S ON F.StudentID=S.IDUser " +
                                 "WHERE TeacherID = " + session.getAttribute("userid") + 
                                 " ORDER BY ReviewDate DESC " + 
                                 "LIMIT 4");

            while(rs.next())
                recent_feed.add(new Feedback(rs.getString("Name"), null, rs.getInt("Score"), rs.getString("cropped_desc")));

            req.setAttribute("recent_feed", recent_feed);

            // ============== PAYMENT BOX ==============
            ArrayList<ArrayList<String>> recent_payment = new ArrayList<ArrayList<String>>();

            DecimalFormat df = new DecimalFormat( "#.##" );

            rs = st.executeQuery("SELECT DATE_FORMAT(L.Payment, '%d/%m/%Y %H:%i') as payment_date, " + 
                                 "IF(L.TeacherID = " + session.getAttribute("userid") + ", CONCAT('Incoming deposit from ' , S.Name), CONCAT('Outgoing payment to ', T.Name)) as payment_desc, " + 
                                 "ROUND(TIME_TO_SEC(LessonDuration)*L.LessonTariff/3600, 2) as money_amount, " + 
                                 "IF(L.TeacherID = " + session.getAttribute("userid") + ", '+', '-') as payment_direction " + 
                                 "FROM (lesson as L JOIN person as T ON L.TeacherID=T.IDUser) JOIN person as S ON L.StudentID=S.IDUser " + 
                                 "WHERE L.Payment IS NOT NULL AND (TeacherID = " + session.getAttribute("userid") + " OR StudentID = " + session.getAttribute("userid") + ") " + 
                                 "ORDER BY L.Payment DESC LIMIT 4");

            while(rs.next()){
                recent_payment.add(new ArrayList<String>());
                recent_payment.get(recent_payment.size() - 1).add(rs.getString("payment_date"));
                recent_payment.get(recent_payment.size() - 1).add(rs.getString("payment_desc"));
                recent_payment.get(recent_payment.size() - 1).add(rs.getString("payment_direction") + df.format(rs.getDouble("money_amount")) + " &euro;");
            }//while

            req.setAttribute("recent_payment", recent_payment);


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