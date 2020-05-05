import java.sql.*;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class FeedbacksServlet extends DatabaseServlet {

    //retrieve data from db
    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        Statement st = null;
        ResultSet rs = null;

        StringBuilder data = new StringBuilder("");
        int[] counters = new int[5];
        String countersString = "";
        String avg = "";

        //we will get the userid attribute from somewhere else...for now let's set it here
        request.getSession().setAttribute("userid", 2);

        //and here is where we get the attribute from the session
        int userid = (int) request.getSession().getAttribute("userid");

        try {

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM feedback F INNER JOIN person P ON P.IDUser = F.StudentID " +
                    "WHERE F.TeacherID = " + userid);

            while(rs.next()) {

                //single feedback structure
                data.append("<div class=\"rev\">" + "<img src=\"images/user-photo2.png\" alt=\"\">")
                        .append("<div>")
                            .append(rs.getString("Name")).append(" ").append(rs.getString("Surname"))
                        .append("</div>")
                        .append("<div>Score: ")
                            .append(rs.getInt("Score"))
                        .append("</div>")
                        .append("<br><div>")
                            .append(rs.getString("F.Description"))
                        .append("</div>")
                .append("</div>");

                //data will contain all the concatenated feedbacks

            }

            //fill counters array and compute the average score
            st = con.createStatement();
            rs = st.executeQuery("SELECT Score FROM feedback WHERE TeacherID = 2");

            int sum = 0;
            while(rs.next()){

                int temp = rs.getInt("Score");
                sum += temp;
                counters[temp - 1]++;

            }

            countersString += Arrays.toString(counters);
            avg += (double)sum/(Arrays.stream(counters).sum());

        }

        //catch exceptions
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

        //place everything on jsp page
        request.setAttribute("feedbacks", data);
        request.setAttribute("counters", countersString);
        request.setAttribute("avg", avg);

    }

    //write feedbacks in the db
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Connection con = getConnection(); //use DatabaseServlet method to get connection
        Statement st = null;
        ResultSet rs = null;

        request.getSession().setAttribute("userid", 2);

        int studentID = (int) request.getSession().getAttribute("userid");

        String teacherUsername = request.getParameter("teacher");
        int score = Integer.parseInt(request.getParameter("score"));
        String comment = request.getParameter("comment");


        try {

            //take the teacherid knowing the username given by the form
            st = con.createStatement();
            rs = st.executeQuery("SELECT IDUser FROM feedback F INNER JOIN person P ON P.IDUser = F.TeacherID " +
                    "WHERE CONCAT(P.Name, ' ', P.Surname) = "+ teacherUsername + " LIMIT 1");
            int teacherID = rs.getInt("IDUser");

            //get current date in yyyy-MM-dd HH:mm:ss format
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());

            //insert the feedback into the db
            st = con.createStatement();
            rs = st.executeQuery("INSERT INTO feedback VALUES" +
                    "('"+ teacherID + "','" + studentID + "','" + score + "','" + comment + "','" + date + "'");

        }

        //catch exceptions
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


    }

}