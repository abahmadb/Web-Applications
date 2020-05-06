import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import javax.servlet.ServletException;
import java.io.*;
import java.nio.file.*;
import java.text.ParseException;

/* submit data into database
 *
 * @author Xianwen Jin
 */
public final class UpdatePersonServlet extends DatabaseServlet{
		
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
				
		int idUser = 1;
		String name = null;
		String surname = null;
		String gender = null;
		java.sql.Date dob = null;
		String email = null;
		String passwd = null;
		String phone = null;
		String description = null;
		String temp = null;
		Person person = null;
		
        req.getSession().setAttribute("idUser", 1);
        idUser = (int) req.getSession().getAttribute("idUser");
			
		try{
			// retrieve the request parameters
			name = req.getParameter("firstname");
			surname = req.getParameter("lastname");
			temp = req.getParameter("gender");
			if (temp.equals("Male")){
				gender = "M";
			} else if (temp.equals("Female")){
				gender = "F";
			}
			temp = req.getParameter("birth").toString();
			/*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			dob = new java.sql.Date(format.parse(temp).getTime());*/
			if (!temp.equals("gg/mm/aaaa")){
				dob = Date.valueOf(req.getParameter("birth").toString());
			}
			email = req.getParameter("email");
			phone = req.getParameter("phone_nr");	
				
			// create person from the request parameters
			person = new Person(idUser, name, surname, gender, dob, email, passwd, phone, description);
		
			new UpdatePersonDAO(getConnection(), person).updatePerson();
			
			// send back new field values of person
			Person personUpdated = new SearchPersonByIdDAO(getConnection(), idUser).searchPersonById();
			req.setAttribute("firstname", personUpdated.getName());
			req.setAttribute("lastname", personUpdated.getSurname());
			String newGender = personUpdated.getGender();
			if (newGender.equals("M")){
				req.setAttribute("gender", "Male");
			} else if (newGender.equals("M")){
				req.setAttribute("gender", "Female");
			} else {
				req.setAttribute("gender", "Other");	
			}
			//req.setAttribute("birth", personUpdated.getDob());
			req.setAttribute("email", personUpdated.getEmail());
			req.setAttribute("phone_nr", personUpdated.getPhone());
			
			req.getRequestDispatcher("profile.jsp").forward(req, res);
			
		} catch (SQLException e){
				
			// set the MIME media type of the response
			res.setContentType("text/html; charset=utf-8");

			// get a stream to write the response
			PrintWriter out = res.getWriter();

			// write the HTML page
			out.printf("<!DOCTYPE html>%n");
		
			out.printf("<html lang=\"en\">%n");
			out.printf("<head>%n");
			out.printf("<meta charset=\"utf-8\">%n");
			out.printf("<title>Error</title>%n");
			out.printf("</head>%n");

			out.printf("<body>%n");
			out.printf("<h1>SQLException</h1>%n");
			out.printf("<hr/>%n");
			out.printf("<p>%n");
			out.printf("Hello, world!%n");
			out.printf("</p>%n");
			out.printf("</body>%n");
		
			out.printf("</html>%n");
		}
		
		// set the MIME media type of the response
		res.setContentType("text/html; charset=utf-8");

		// get a stream to write the response
		PrintWriter out = res.getWriter();

		// write the HTML page
		out.printf("<!DOCTYPE html>%n");
		
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("<meta charset=\"utf-8\">%n");
		out.printf("<title>Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>Servlet Response</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Submitted, check the database%n");
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");

		// flush the output stream buffer
		out.flush();

		// close the output stream
		out.close();
	}
}