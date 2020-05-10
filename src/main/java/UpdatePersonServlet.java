import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

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
        String city = null;
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
			} else if (temp.equals("Other")){
				gender = "O";
			}
			temp = req.getParameter("birth").toString();
			/*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			dob = new java.sql.Date(format.parse(temp).getTime());*/
			try{
				dob = Date.valueOf(req.getParameter("birth").toString());
			} catch (Exception e){}
			email = req.getParameter("email");
			phone = req.getParameter("phone_nr");
				
			// create person from the request parameters
			person = new Person(idUser, name, surname, gender, dob, email, passwd, phone, city, description);
		
			new UpdatePersonDAO(getConnection(), person).updatePerson();
			
			// send back new field values of person
			Person personUpdated = new SearchPersonByIdDAO(getConnection(), idUser).searchPersonById();
			req.setAttribute("firstName", personUpdated.getName());
			req.setAttribute("lastName", personUpdated.getSurname());
			String newGender = personUpdated.getGender();
			if (newGender.equals("M")){
				req.setAttribute("gender", "Male");
			} else if (newGender.equals("F")){
				req.setAttribute("gender", "Female");
			} else if (newGender.equals("O")){
				req.setAttribute("gender", "Other");	
			}
			req.setAttribute("birth", personUpdated.getDob());
			req.setAttribute("email", personUpdated.getEmail());
			req.setAttribute("phone_nr", personUpdated.getPhone());
			
			req.getRequestDispatcher("profile.jsp").forward(req, res);
			
		} catch (SQLException ex){
				
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		}

	}

}