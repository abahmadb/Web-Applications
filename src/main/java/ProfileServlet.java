import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
import java.util.*;

// package needed for upload file
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import java.io.IOException;
import java.io.PrintWriter;

/* submit data into database
 *
 * @author Xianwen Jin
 */
public final class ProfileServlet extends DatabaseServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		try{
			
			//check if we are logged in
			if(!IndexServlet.check_login(req))
            throw new IOException("You did not sign in before opening the dashboard, sign in and retry");

			//if we are, we can go on and retrieve the userid attribute from the session
			int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));		
	
			// send back new field values of person
			Person person = new SearchPersonByIdDAO(getConnection(), idUser).searchPersonById();
			req.setAttribute("person", person);
		
			req.getRequestDispatcher("profile.jsp").forward(req, res);	
			
		} catch (SQLException ex){
				
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		// Check the type of request

        if(req.getParameter("personForm") != null)
            doUpdatePerson(req, res);
        else if(req.getParameter("passForm") != null)
            doUpdatePass(req, res);
		else if(req.getParameter("profileImgForm") != null){
            
			
			doUpdateProfileImg(req, res);
			
		}
		else if(req.getParameter("idImgForm") != null)
            doUpdateIdImg(req, res);
		else if(req.getParameter("qualificationImgForm") != null)
            doUpdateQualificationImg(req, res);
		else if(req.getParameter("topicForm") != null)
            doUpdateTopic(req, res);
		else if(req.getParameter("descriptionForm") != null)
            doUpdateDescription(req, res);
	}

	public void doUpdatePerson(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
				
        //req.getSession().setAttribute("idUser", 1);
		int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));	
        //idUser = (int) req.getSession().getAttribute("idUser");
	
		try{
			// retrieve the request parameters
			String name = req.getParameter("firstname");
			String surname = req.getParameter("lastname");
			String gender = req.getParameter("gender");
			if (gender.equals("M")){
				gender = "M";
			} else if (gender.equals("F")){
				gender = "F";
			} else {
				gender = "";
			}
			String temp = req.getParameter("birth").toString();
			/*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			dob = new java.sql.Date(format.parse(temp).getTime());*/
			java.sql.Date dob = null;
			try{
				dob = java.sql.Date.valueOf(req.getParameter("birth").toString());
			} catch (Exception e){}
			String email = req.getParameter("email");
			String phone = req.getParameter("phone_nr");
			String city = req.getParameter("city");
			String passwd = null, description = null;
			// create person from the request parameters
			Person person = new Person(idUser, name, surname, gender, dob, email, passwd, phone, city, description);
		
			new UpdatePersonDAO(getConnection(), person).updatePerson();
			req.setAttribute("person", person);
			req.getRequestDispatcher("profile.jsp").forward(req, res);
			
			
		} catch (SQLException ex){
				
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		}

	}
	
	public void doUpdatePass(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		//req.getSession().setAttribute("idUser", 1);
		int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));
		//int idUser = 1;			
		try{
			
			String newPassword = req.getParameter("new_pw");
			String oldPassword = req.getParameter("old_pw");
			Person person = new SearchPersonByIdDAO(getConnection(), idUser).searchPersonById();
			String oldPassSHA = person.getPassword();
			new UpdatePersonDAO(getConnection(), person).updatePassword(newPassword, oldPassword);
			Person personUpdated = new SearchPersonByIdDAO(getConnection(), idUser).searchPersonById();
		
			// check the password is changed
			if (!oldPassSHA.equals(personUpdated.getPassword())){
				req.setAttribute("passMessage", "Updated Successfully");	
			} else {
				req.setAttribute("passMessage", "Update Failed.");	
			}
			req.getRequestDispatcher("profile.jsp").forward(req, res);
			
		} catch (SQLException ex){
			
			req.setAttribute("passMessage", "Failed");			
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		}
	}
	
	public void doUpdateProfileImg(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		
		//int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));
		
		// upload settings
		final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
		final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
		final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
 
        
		String upload_dir = "identity";
		if(req.getParameter("profileImgForm") != null)
            upload_dir = "profile";
		else if(req.getParameter("idImgForm") != null)
            upload_dir = "identity";
		else if(req.getParameter("qualificationImgForm") != null)
            upload_dir = "certificate";
			
		// check if is a multipart content
        if (!ServletFileUpload.isMultipartContent(req)) {
			req.setAttribute("error_message", "Not multipart content");
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
        }
 
        // Initialize settings for upload
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // relative path
        //String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
		String uploadPath = System.getProperty("user.dir") + "\\..\\webapps\\imageset\\" + upload_dir;
        String filePath = null;
 
        try {
            // retrieve the file
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(req);
 
            if (formItems != null && formItems.size() > 0) {
               
                for (FileItem item : formItems) {
                    
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        
                        System.out.println(filePath);
                        item.write(storeFile);
                        req.setAttribute("fileMessage",
                            "uploaded");
                    }
                }
            }
        } catch (Exception ex) {
            req.setAttribute("fileMessage", "upload failed");
        }
        // 跳转到 message.jsp
        //request.getServletContext().getRequestDispatcher("/message.jsp").forward(
         //       request, response);
		// write the HTML page
		PrintWriter out = res.getWriter();
		out.printf("<!DOCTYPE html>%n");
		
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("<meta charset=\"utf-8\">%n");
		out.printf("<title>HelloWorld Form Get Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>Profile Form</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Hello%s", filePath);
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");
    
	}
	
	public void doUpdateIdImg(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		// write the HTML page
		PrintWriter out = res.getWriter();
		out.printf("<!DOCTYPE html>%n");
		
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("<meta charset=\"utf-8\">%n");
		out.printf("<title>HelloWorld Form Get Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>ID Form</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Hello");
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");
	}
	
	public void doUpdateQualificationImg(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		// write the HTML page
		PrintWriter out = res.getWriter();
		out.printf("<!DOCTYPE html>%n");
		
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("<meta charset=\"utf-8\">%n");
		out.printf("<title>HelloWorld Form Get Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>Qualification Form</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Hello");
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");
	}
	
	public void doUpdateTopic(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
				
		// write the HTML page
		PrintWriter out = res.getWriter();
		out.printf("<!DOCTYPE html>%n");
		
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("<meta charset=\"utf-8\">%n");
		out.printf("<title>HelloWorld Form Get Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>Topic Form</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Hello");
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");		
				
				
	}
	
	
	public void doUpdateDescription(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		// write the HTML page
		PrintWriter out = res.getWriter();
		out.printf("<!DOCTYPE html>%n");
		
		out.printf("<html lang=\"en\">%n");
		out.printf("<head>%n");
		out.printf("<meta charset=\"utf-8\">%n");
		out.printf("<title>HelloWorld Form Get Servlet Response</title>%n");
		out.printf("</head>%n");

		out.printf("<body>%n");
		out.printf("<h1>Description Form</h1>%n");
		out.printf("<hr/>%n");
		out.printf("<p>%n");
		out.printf("Hello");
		out.printf("</p>%n");
		out.printf("</body>%n");
		
		out.printf("</html>%n");
	}
}