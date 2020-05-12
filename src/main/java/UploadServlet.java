import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class UploadServlet
 */

public class UploadServlet extends HttpServlet {
     
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    protected void doPost(HttpServletRequest req,
		HttpServletResponse res) throws ServletException, IOException {
       
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
                        req.setAttribute("fileMessage",
                            "uploaded");
						req.getRequestDispatcher("profile.jsp").forward(req, res);
                    }
                }
            }
        } catch (Exception ex) {
            req.setAttribute("fileMessage", "upload failed");
        }
        
    }
}