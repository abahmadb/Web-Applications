import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersonRestResource extends RestResource{

	public PersonRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}

	public void readPerson() throws IOException {

		Person person  = null;

		try{
			// parse the URI path to extract the idUser
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("person") + 6);

			final int idUser = Integer.parseInt(path.substring(1));

			// creates a new object for accessing the database and reads the Person
			person = new SearchPersonByIdDAO(con, idUser).searchPersonById();

			if(person != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				person.toJSON(res.getOutputStream());
			} else {
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				RestServlet.JSONMessage(res.getOutputStream(), String.format("Person %d not found.", idUser), "E5A3", null);
			}
		} catch (Throwable t) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			RestServlet.JSONMessage(res.getOutputStream(), "Cannot read Person: unexpected error.", "E5A1", t.getMessage());
		}
	}
}