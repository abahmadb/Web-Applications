import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class FeedbacksServlet extends DatabaseServlet {

    //retrieve data from db
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {

            //check if we are logged in
            if (!IndexServlet.check_login(req))
                throw new IOException("You need to be signed in to access this page");

        }

        //catch exception if we are not logged in
        catch (IOException e) {

            req.setAttribute("error_message", e.getMessage());
            req.setAttribute("appname", req.getContextPath());

            try{
                req.getRequestDispatcher("errorpage.jsp").forward(req, res);
            }

            catch(Exception ignored){}

        }

        //if we are logged in, we can go on and retrieve the userid attribute from the session
        int userid = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Feedback> feedbacks = new ArrayList<>();
        int[] counters = new int[5];
        String countersString = "";
        String avg = "";
        ArrayList<String> userfeedlist = new ArrayList<>();

        try {

            st = con.prepareStatement("SELECT StudentID, Name, Surname, Score, ReviewDate, F.Description AS Description " +
                    "FROM feedback F INNER JOIN person P ON P.IDUser = F.StudentID WHERE F.TeacherID = ? " +
                    "ORDER BY ReviewDate DESC");
            st.setInt(1, userid);
            rs = st.executeQuery();

            while(rs.next()) {

                feedbacks.add(new Feedback(rs.getInt("StudentID"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getInt("Score"),
                        rs.getDate("ReviewDate"),
                        rs.getString("Description")));

            }

            //fill counters array and compute the average score
            st = con.prepareStatement("SELECT Score, COUNT(*) AS Count FROM feedback WHERE TeacherID = ? GROUP BY Score");
            st.setInt(1, userid);
            rs = st.executeQuery();

            int sum = 0;
            while(rs.next()){

                int score = rs.getInt("Score");
                int count = rs.getInt("Count");

                sum += score * count;
                counters[score - 1] = count;

            }

            countersString += Arrays.toString(counters);
            avg += (double)sum/(Arrays.stream(counters).sum());

            //find all teachers associated that have at least a lesson with the current user
            //and retrieve only the ones that didn't receive a feedback from him
            st = con.prepareStatement("SELECT L.TeacherID, CONCAT(P.Name, ' ', P.Surname) AS Fullname " +
                    "FROM lesson AS L INNER JOIN person AS P ON L.TeacherID = P.IDUser " +
                    "WHERE L.StudentID = ? " +
                    "AND DATE(L.LessonDate) < CURDATE() " +
                    "AND L.TeacherID NOT IN (SELECT TeacherID FROM feedback WHERE StudentID = ?)");
            st.setInt(1, userid);
            st.setInt(2, userid);
            rs = st.executeQuery();

            while (rs.next())
                userfeedlist.add(rs.getString("Fullname") + "," + rs.getInt("L.TeacherID"));


        }

        //catch exceptions
        catch (SQLException e) {

            req.setAttribute("error_message", e.getMessage());
            req.setAttribute("appname", req.getContextPath());

            try{
                req.getRequestDispatcher("errorpage.jsp").forward(req, res);
            }

            catch(Exception ignored){}

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

        //place everything on jsp page
        req.setAttribute("feedbacks", feedbacks);
        req.setAttribute("userfeedlist", userfeedlist);
        req.setAttribute("counters", countersString);
        req.setAttribute("avg", avg);

        req.setAttribute("currentpage", req.getServletPath());
        req.getRequestDispatcher("feedbacks.jsp").forward(req, res);

    }

    //write feedbacks in the db
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Connection con = getConnection(); //use DatabaseServlet method to get connection

        //get userid from session
        int studentID = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));

        //get form parameters
        int teacherID = Integer.parseInt(req.getParameter("teacher"));
        int score = Integer.parseInt(req.getParameter("score"));
        String comment = req.getParameter("comment");

        //try-with-resources syntax, does not need a finally block to close the statement resource
        try (PreparedStatement st = con.prepareStatement("INSERT INTO feedback VALUES (?,?,?,?, CURDATE())")) {

            //insert the feedback into the db
            st.setInt(1, teacherID);
            st.setInt(2, studentID);
            st.setInt(3, score);
            st.setString(4, comment);
            st.executeUpdate();

        }

        catch (SQLException e) {

            req.setAttribute("error_message", e.getMessage());
            req.setAttribute("appname", req.getContextPath());

            try {
                req.getRequestDispatcher("errorpage.jsp").forward(req, res);
            }

            catch (Exception ignored) {}

        }

        //reload page in order to include db updates
        res.sendRedirect("feedbacks");


    }

}