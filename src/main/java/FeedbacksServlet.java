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

        try {

            //check if we are logged in
            if (!IndexServlet.check_login(request))
                throw new IOException("You need to be signed in to access this page");

        }

        //catch exception if we are not logged in
        catch (IOException e) {

            request.setAttribute("error_message", e.getMessage());
            request.setAttribute("appname", request.getContextPath());

            try{
                request.getRequestDispatcher("errorpage.jsp").forward(request, response);
            }

            catch(Exception ignored){}

        }

        //if we are logged in, we can go on and retrieve the userid attribute from the session
        int userid = Integer.parseInt(String.valueOf(request.getSession().getAttribute("userid")));

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Feedback> feedbacks = new ArrayList<>();
        int[] counters = new int[5];
        String countersString = "";
        String avg = "";
        ArrayList<String> userfeedlist = new ArrayList<>();

        try {

            st = con.prepareStatement("SELECT * FROM feedback F INNER JOIN person P ON P.IDUser = " +
                    "F.StudentID WHERE F.TeacherID = ? ORDER BY F.ReviewDate DESC");
            st.setInt(1, userid);
            rs = st.executeQuery();

            while(rs.next()) {

                feedbacks.add(new Feedback(rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getInt("Score"),
                        rs.getDate("ReviewDate"),
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

            //find all teachers associated that have at least a lesson with the user
            st = con.prepareStatement("SELECT TeacherID from " +
                    "lesson AS L INNER JOIN person AS P ON L.StudentID = P.IDUser WHERE L.StudentID = ?");
            st.setInt(1, userid);
            rs = st.executeQuery();

            //verify if rs is an empty set
            if (rs.next()){

                //if it's not, go on, and fill this arraylist
                ArrayList<Integer> teachers = new ArrayList<>();

                do {
                    teachers.add(rs.getInt("TeacherID"));
                } while (rs.next());

                //now we see if the user gave already a feedback to a teacher
                //we check this for every teacher that has id in the arraylist
                for(int i: teachers) {

                    st = con.prepareStatement("SELECT CONCAT(Name, ' ', Surname) FROM " +
                            "feedback AS F INNER JOIN person AS P ON F.TeacherID = P.IDUser " +
                            "WHERE F.TeacherID = ? AND F.StudentID = ? LIMIT 1");
                    st.setInt(1, i);
                    st.setInt(2, userid);
                    rs = st.executeQuery();

                    //if there isn't a feedback, we allow the user to give one
                    if (!rs.next()){

                        st = con.prepareStatement("SELECT CONCAT(Name, ' ', Surname) FROM person WHERE IDUser = ? LIMIT 1");
                        st.setInt(1, i);
                        rs = st.executeQuery();

                        while(rs.next())
                            userfeedlist.add("\""+ rs.getString("CONCAT(Name, ' ', Surname)") + "\"");

                    }


                }

            }


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
        request.setAttribute("userfeedlist", userfeedlist);

        request.getRequestDispatcher("feedbacks.jsp").forward(request, response);

    }

    //write feedbacks in the db
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        PreparedStatement st = null;
        ResultSet rs = null;

        //get userid from session
        int studentID = Integer.parseInt(String.valueOf(request.getSession().getAttribute("userid")));

        //get form parameters
        String teacherUsername = request.getParameter("teacher");
        int score = Integer.parseInt(request.getParameter("score"));
        String comment = request.getParameter("comment");


        try {

            //take the teacherid knowing the username given by the form
            st = con.prepareStatement("SELECT IDUser FROM feedback F INNER JOIN person P ON P.IDUser = F.TeacherID " +
                    "WHERE CONCAT(P.Name, ' ', P.Surname) = ? LIMIT 1");
            st.setString(1, teacherUsername);
            rs = st.executeQuery();

            int teacherID = 0;
            while(rs.next())
                teacherID = rs.getInt("IDUser");

            //get current date in yyyy-MM-dd format
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

        //reload page in order to include db updates
        doGet(request,response);


    }

}