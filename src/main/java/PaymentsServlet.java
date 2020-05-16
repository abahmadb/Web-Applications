import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.IOException;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public final class PaymentsServlet extends DatabaseServlet {

	//retrieve data from db
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//check if we are logged in
			if (!IndexServlet.check_login(request))
				throw new IOException("You need to be signed in to access this page");

			HttpSession session = request.getSession();
			Connection c = getConnection();
			Statement st = c.createStatement();
			DecimalFormat df = new DecimalFormat( "#.##" );
			ResultSet rs = st.executeQuery("SELECT DATE_FORMAT(L.Payment, '%d/%m/%Y %H:%i') as payment_date, " +
					"IF(L.TeacherID = " + session.getAttribute("userid") + ", CONCAT('Incoming deposit from ' , S.Name), CONCAT('Outgoing payment to ', T.Name)) as payment_desc, " +
					"ROUND(TIME_TO_SEC(LessonDuration)*L.LessonTariff/3600, 2) as money_amount, " +
					"IF(L.TeacherID = " + session.getAttribute("userid") + ", '+', '-') as payment_direction " +
					"FROM (lesson as L JOIN person as T ON L.TeacherID=T.IDUser) JOIN person as S ON L.StudentID=S.IDUser " +
					"WHERE L.Payment IS NOT NULL AND (TeacherID = " + session.getAttribute("userid") + " OR StudentID = " + session.getAttribute("userid") + ") " +
					"ORDER BY L.Payment DESC LIMIT 4");


			ArrayList<String> recent_payment = new ArrayList<String>();
			while(rs.next())
				recent_payment.add(new String(rs.getString("payment_date"),
											rs.getString("payment_desc"),
											rs.getString("payment_direction") + df.format(rs.getDouble("money_amount")) + " &euro;"));

			request.setAttribute("recent_payment", String);
			request.setAttribute("currentpage", request.getServletPath());
			request.getRequestDispatcher("payments.jsp").forward(request, response);

		}//try

		//catch exception if we are not logged in
		catch (IOException e) {

			request.setAttribute("error_message", e.getMessage());
			request.setAttribute("appname", request.getContextPath());

			try {
				request.getRequestDispatcher("errorpage.jsp").forward(request, response);
			} catch (Exception ignored) {
			}

		}//catch
	}//doGet

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		}//doPost
}//PaymentsServlet
