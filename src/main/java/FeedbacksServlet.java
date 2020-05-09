import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class FeedbacksServlet extends DatabaseServlet {

    //retrieve data from db
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //check if we are logged in
        if(!IndexServlet.check_login(request))
            throw new IOException("You did not sign in before opening the dashboard, sign in and retry");

        //if we are, we can go on and retrieve the userid attribute from the session
        int userid = Integer.parseInt(String.valueOf(request.getSession().getAttribute("userid")));

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Feedback> feedbacks = new ArrayList<>();
        int[] counters = new int[5];
        String countersString = "";
        String avg = "";

        try {

            st = con.prepareStatement("SELECT * FROM feedback F INNER JOIN person P ON P.IDUser = " +
                    "F.StudentID WHERE F.TeacherID = ?");
            st.setInt(1, userid);
            rs = st.executeQuery();

            while(rs.next()) {

                feedbacks.add(new Feedback(rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getInt("Score"),
                        rs.getString("F.Description")));

            }

            //fill counters array and compute the average score
            st = con.prepareStatement("SELECT Score, COUNT(*) FROM feedback WHERE TeacherID = ? GROUP BY Score");
            st.setInt(1, userid);
            rs = st.executeQuery();

            int sum = 0;
            while(rs.next()){

                int score = rs.getInt("Score");
                int times = rs.getInt("COUNT(*)");

                sum += score * times;
                counters[score - 1] = times;

            }

            countersString += Arrays.toString(counters);
            avg += (double)sum/(Arrays.stream(counters).sum());

        }

        //catch exceptions
        catch (SQLException e) {

            request.setAttribute("error_message", e.getMessage());
            request.setAttribute("appname", request.getContextPath());

            try{
                request.getRequestDispatcher("errorpage.jsp").forward(request, response);
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
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("counters", countersString);
        request.setAttribute("avg", avg);

        request.getRequestDispatcher("feedbacks.jsp").forward(request, response);

    }

    //write feedbacks in the db
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        PreparedStatement st = null;
        ResultSet rs = null;

        int studentID = Integer.parseInt(String.valueOf(request.getSession().getAttribute("userid")));

        String teacherUsername = request.getParameter("teacher");
        int score = Integer.parseInt(request.getParameter("score"));
        String comment = request.getParameter("comment");


        try {

            //take the teacherid knowing the username given by the form
            st = con.prepareStatement("SELECT IDUser FROM feedback F INNER JOIN person P ON P.IDUser = F.TeacherID " +
                    "WHERE CONCAT(P.Name, ' ', P.Surname) = ? LIMIT 1");
            st.setString(1, teacherUsername);
            rs = st.executeQuery();
            int teacherID = rs.getInt("IDUser");

            //get current date in yyyy-MM-dd HH:mm:ss format
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            java.sql.Date date = java.sql.Date.valueOf(simpleDateFormat.format(new Date()));

            //insert the feedback into the db
            st = con.prepareStatement("INSERT INTO feedback VALUES (?,?,?,?,?)");
            st.setInt(1, teacherID);
            st.setInt(2, studentID);
            st.setInt(3, score);
            st.setString(4, comment);
            st.setDate(5, date);
            st.executeUpdate();


        }

        //catch exceptions
        catch (SQLException e) {

            request.setAttribute("error_message", e.getMessage());
            request.setAttribute("appname", request.getContextPath());

            try{
                request.getRequestDispatcher("errorpage.jsp").forward(request, response);
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


    }

}