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
		char gender = 0;
		Date dob = null;
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
			System.out.println("test" + person.getName());
			surname = req.getParameter("lastname");
			temp = req.getParameter("gender");
			if (temp.equals("Male")){
				gender = 'M';
			} else if (temp.equals("Female")){
				gender = 'F';
			}
			temp = req.getParameter("birth");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			dob = new java.sql.Date(format.parse(temp).getTime());
			email = req.getParameter("email");
			phone = req.getParameter("phone_nr");	
				
				// create person from the request parameters
			person = new Person(idUser, name, surname, gender, dob, email, passwd, phone, description);
			
			new UpdatePersonDAO(getConnection(), person).updatePerson();
			
		} catch (Exception e){
				
				// error page
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
		out.printf("<title>HelloWorld Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>HelloWorld Servlet Response</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Hello, world!%n");
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");

		// flush the output stream buffer
		out.flush();

		// close the output stream
		out.close();
	}
}