import javax.servlet.http.*;
import java.sql.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public final class PaymentsServlet extends DatabaseServlet {

	//retrieve data from db
	public void doGet(HttpServletRequest request, HttpServletResponse response){

		try {

			//check if we are logged in
			if (!IndexServlet.check_login(request))
				throw new IOException("You need to be signed in to access this page");

			HttpSession session = request.getSession();
			Connection c = getConnection();

			Statement st = c.createStatement();

			DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
			symbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat( "#.##", symbols);
			ResultSet rs = st.executeQuery("SELECT DATE_FORMAT(L.Payment, '%d/%m/%Y %H:%i') as payment_date, " +
					"IF(L.TeacherID = " + session.getAttribute("userid") + ", CONCAT('Incoming deposit from ' , S.Name), CONCAT('Outgoing payment to ', T.Name)) as payment_desc, " +
					"ROUND(TIME_TO_SEC(LessonDuration)*L.LessonTariff/3600, 2) as money_amount, " +
					"IF(L.TeacherID = " + session.getAttribute("userid") + ", '+', '-') as payment_direction " +
					"FROM (lesson as L JOIN person as T ON L.TeacherID=T.IDUser) JOIN person as S ON L.StudentID=S.IDUser " +
					"WHERE L.Payment IS NOT NULL AND (TeacherID = " + session.getAttribute("userid") + " OR StudentID = " + session.getAttribute("userid") + ") " +
					"ORDER BY L.Payment DESC");

			double earned = 0;
			double spent = 0;
			ArrayList<String> payments = new ArrayList<>();
			while(rs.next()){
				payments.add(rs.getString("payment_date") + "," +
						rs.getString("payment_desc") + "," +
						rs.getString("payment_direction") + df.format(rs.getDouble("money_amount")) + " &euro;");
				
				if (rs.getString("payment_direction").equals("+"))
					earned += rs.getDouble("money_amount");
				else if (rs.getString("payment_direction").equals("-"))
					spent += rs.getDouble("money_amount");
			}
			request.setAttribute("payments", payments);
			request.setAttribute("earned", earned);
			request.setAttribute("spent", spent);
			request.setAttribute("currentpage", request.getServletPath());
			request.getRequestDispatcher("payments.jsp").forward(request, response);

		}//try

		//catch exception if we are not logged in
		catch (Exception e) {

			request.setAttribute("error_message", e.getMessage());
			request.setAttribute("appname", request.getContextPath());

			try {
				request.getRequestDispatcher("errorpage.jsp").forward(request, response);
			} catch (Exception ignored) {
			}

		}//catch
	}//doGet

}//PaymentsServlet