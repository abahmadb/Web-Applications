import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.IOException;
import java.io.*;
import java.util.*;

public final class PaymentsServlet extends DatabaseServlet {

	//retrieve data from db
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//check if we are logged in
			if (!IndexServlet.check_login(request))
				throw new IOException("You need to be signed in to access this page");

		}

		//catch exception if we are not logged in
		catch (IOException e) {

			request.setAttribute("error_message", e.getMessage());
			request.setAttribute("appname", request.getContextPath());

			try {
				request.getRequestDispatcher("errorpage.jsp").forward(request, response);
			} catch (Exception ignored) {
			}

		}


	/*

	 */


	}//try

        /*catch(Exception ex)

	{
		req.setAttribute("error_message", ex.getMessage());
		req.setAttribute("appname", req.getContextPath());
		try {
			req.getRequestDispatcher("errorpage.jsp").forward(req, res);
		} catch (Exception e) {
		}
	}//catch*/
}