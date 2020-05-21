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
		
		Connection con = getConnection();
		
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try{
			//check if we are logged in
			if(!IndexServlet.check_login(req))
            throw new IOException("You did not sign in before opening the dashboard, sign in and retry");

			//if we are, we can go on and retrieve the userid attribute from the session
			int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));		
	
			// set the field values of person
			Person person = new SearchPersonByIdDAO(con, idUser).searchPersonById();
			req.setAttribute("person", person);
			
			// check if there exist the certificate and identity file for this userid
			String identityFilePath = System.getProperty("user.dir") + "\\..\\webapps\\imageset\\identity\\" + idUser + ".jpg";
            String certificateFilePath = System.getProperty("user.dir") + "\\..\\webapps\\imageset\\cerificate\\" + idUser + ".jpg";
			if (new File(identityFilePath).isFile())
				req.setAttribute("identitytExists", true);
			else 
				req.setAttribute("identityExists", false);
		
			if (new File(certificateFilePath).isFile())
				req.setAttribute("certificateExists", true);
			else 
				req.setAttribute("certificateExists", false);
			
			
			// topic info
			List<Topic> lista = new ArrayList<Topic>();
            st1 = con.prepareStatement("SELECT * FROM topic");
            rs1 = st1.executeQuery();

            while(rs1.next())
                lista.add(new Topic(rs1.getInt("IDTopic"), rs1.getString("Label")));

            req.setAttribute("topics_list", lista);
			
			// topic form info
			ArrayList<TeacherTopic> subjects = new ArrayList<TeacherTopic>();		
			
			final String STATEMENT_SELECT_TEACHER_TOPIC = "SELECT * FROM teacher_topic WHERE TeacherID=?";
			final String STATEMENT_SELECT_TOPIC_NAME = "SELECT Label FROM Topic WHERE IDTopic=?";
            st1 = con.prepareStatement(STATEMENT_SELECT_TEACHER_TOPIC);
			st1.setInt(1, idUser);
            rs1 = st1.executeQuery();
			
			st2 = con.prepareStatement(STATEMENT_SELECT_TOPIC_NAME);
            while(rs1.next()){				
				st2.setInt(1, rs1.getInt("TopicID"));
				rs2 = st2.executeQuery();
				rs2.next();
                subjects.add(new TeacherTopic(rs1.getInt("TeacherID"), rs1.getInt("TopicID"), rs1.getInt("Tariff"), rs2.getString("Label")));
			}
			req.setAttribute("first_subject", subjects.get(0));
			//subjects.remove(0);
            req.setAttribute("subject_list", subjects);			
			
			req.setAttribute("currentpage", req.getServletPath());
			req.getRequestDispatcher("profile.jsp").forward(req, res);	
			
		} catch (SQLException ex){
				
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		} finally {
         		
			if (st1 != null) {
                try {
                    st1.close();
                } catch (SQLException ignored) { }
            }
			
			if (st2 != null) {
                try {
                    st2.close();
                } catch (SQLException ignored) { }
            }
			
			if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException ignored) { }
            }
			
			if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException ignored) { }
            }

        }	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		// Check the type of request

        if (req.getParameter("personForm") != null)
            doUpdatePerson(req, res);
        else if (req.getParameter("passForm") != null)
            doUpdatePass(req, res);
		else if (req.getParameter("formTopic") != null)
            doUpdateTopic(req, res);
		else if (req.getParameter("descriptionForm") != null)
            doUpdateDescription(req, res);
		else 
			doUploadFile(req, res);
		
		res.sendRedirect("profile");

		
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
			// update the information about person
			new UpdatePersonDAO(getConnection(), person).updatePerson();
			
			
		} catch (SQLException ex){
				
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		} 
	}
	
	public void doUpdatePass(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));		
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
			
		} catch (SQLException ex){
			
			req.setAttribute("passMessage", "Failed");			
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		} 
	}
	
	public void doUpdateTopic(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		/*final String STATEMENT_INSERT_TOPIC = "INSERT INTO Topic(Label) " + 
													"SELECT ? " +
													"FROM Dual " +
													"WHERE NOT EXISTS (SELECT * FROM Topic WHERE Label=?)";*/
		final String STATEMENT_DELETE_TEACHER_TOPIC = "DELETE FROM teacher_topic WHERE teacherID=?";
		final String STATEMENT_INSERT_TEACHER_TOPIC = "INSERT INTO teacher_topic VALUES (?, (SELECT IDTopic FROM Topic WHERE Label=?), ?) " +
													"ON DUPLICATE KEY UPDATE tariff=?";	
													
		int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));
		String[] topics = req.getParameterValues("subject");
		String[] tariffs = req.getParameterValues("tariff");
		Connection con = getConnection();
		PreparedStatement st = null;
		
		try{
			st = con.prepareStatement(STATEMENT_DELETE_TEACHER_TOPIC);
			st.setInt(1, idUser);
			st.execute();
			
			st = con.prepareStatement(STATEMENT_INSERT_TEACHER_TOPIC);
			for (int i = 0; i < topics.length; i++){
				st.setInt(1, idUser);
				st.setString(2, topics[i]);
				int tariff = Integer.parseInt(tariffs[i]);
				st.setInt(3, tariff);	
				st.setInt(4, tariff);
			
				st.execute();	
			}
			
			

			req.setAttribute("topicMessage", "Updated");
			
		} catch (SQLException ex){
			
			req.setAttribute("topicMessage", "Failed");			
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		} finally {
         
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignored) { }
            }

        }			
	}
	
	public void doUpdateDescription(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
				
		final String STATEMENT = "UPDATE Remytutor.Person SET Description=? WHERE IDUser=?";		
		Connection con = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			
			int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));
			String text = req.getParameter("text");
			// update the description
			st = con.prepareStatement(STATEMENT);
            st.setString(1, text);
			st.setInt(2, idUser);
			
			st.execute();	
			
		} catch (SQLException ex){
			
			req.setAttribute("topicMessage", "Failed");			
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
			
		} finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException ignored) { }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignored) { }
            }
        }
	}
	
	public void doUploadFile(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
				
		// upload settings
		final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
		final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
		final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
       
		int idUser = Integer.parseInt(String.valueOf(req.getSession().getAttribute("userid")));
	
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
		String upload_dir = null;
		String uploadPath = null;
        String filePath = null;
		String fieldName = null;
        try {
            // retrieve the file
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(req);
 
            if (formItems != null && formItems.size() > 0) {
               
                for (FileItem item : formItems) {
                    
                    if (!item.isFormField()) {
                        // the file name depends on idUser
						String fileName = idUser + ".jpg";
						// get the info about from which form is sent the request
						fieldName = item.getFieldName();
						if (fieldName.equals("photo")){
							upload_dir = "profile";
						}
						else if (fieldName.equals("document_card")){
							upload_dir = "identity";
						}
						else if (fieldName.equals("qualification")){
							upload_dir = "certificate";
						}
						// set the upload path 
						uploadPath = System.getProperty("user.dir") + "\\..\\webapps\\imageset\\" + upload_dir;
                        filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                  
                        item.write(storeFile);
						
                        req.setAttribute("fileMessage", "uploaded");
						
                    }
                }
            }
        } catch (Exception ex) {
            req.setAttribute("fileMessage", "upload failed");
			req.setAttribute("error_message", ex.getMessage());
            req.setAttribute("appname", req.getContextPath());
            try{req.getRequestDispatcher("errorpage.jsp").forward(req, res);}	catch(Exception e){}
        }
        		
	}
	
}