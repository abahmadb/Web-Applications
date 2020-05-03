import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeedbacksServlet extends HttpServlet {

    //retrieve data from db
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        String connectionURL = "jdbc:mysql://localhost:3306/remytutor";
        Connection con;
        Statement st;
        ResultSet rs;
        //all the retrieved data will be forwarded to js, so here we directly build the js statements
        StringBuilder data = new StringBuilder("let data = \"");
        int[] counters = new int[5];
        String countersString = "let counters = ";
        String avg = "let avg = ";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(connectionURL, "root", "root");
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM feedback F INNER JOIN person P ON P.IDUser = F.StudentID " +
                    "WHERE F.TeacherID = 2");

            while(rs.next()) {

                //single feedback structure
                data.append("<div class=\\\"rev\\\">" + "<img src=\\\"images/user-photo2.png\\\" alt=\\\"\\\">")
                        .append("<div>")
                            .append(rs.getString("Name") + " " + rs.getString("Surname"))
                        .append("</div>")
                        .append("<div>Score:")
                            .append(rs.getInt("Score"))
                        .append("</div>")
                        .append("<br><div>")
                            .append(rs.getString("F.Description"))
                        .append("</div>")
                .append("</div>");

                //data will contain all the concatenated feedbacks

            }
            //end js statement
            data.append("\";");

            //fill counters array and compute the average score
            st = con.createStatement();
            rs = st.executeQuery("SELECT Score FROM feedback " +
                    "WHERE TeacherID = 2");

            int sum = 0;
            while(rs.next()){

                int temp = rs.getInt("Score");
                sum += temp;
                counters[temp - 1]++;

            }

            countersString += Arrays.toString(counters) + ";";
            avg += (double)sum/(Arrays.stream(counters).sum()) + ";";

        }

        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        //print everything on jsp page
        PrintWriter writer = response.getWriter();
        writer.println(data);
        writer.println(avg);
        writer.println(countersString);

    }

    //write feedbacks in the db
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) {






    }

}