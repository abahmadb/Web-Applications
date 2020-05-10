<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import = "javax.servlet.http.*" %>
<%@ page import = "org.apache.commons.fileupload.*" %>
<%@ page import = "org.apache.commons.fileupload.disk.*" %>
<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
<%@ page import = "org.apache.commons.io.output.*" %>

<%
   File file;
   int maxFileSize = 20000 * 1024;
   int maxMemSize = 20000 * 1024;
   ServletContext context = pageContext.getServletContext();
   //String name = fileItem.getFieldName();
   String home = System.getProperty("user.dir");
   String filePath = context.getInitParameter(home);

   // Verify the content type
   String contentType = request.getContentType();
   
   if ((contentType.indexOf("multipart/form-data") >= 0)) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
      
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\data"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
      
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
      
        try { 
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
    
            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>JSP File upload</title>");  
            out.println("</head>");
            out.println("<body>");
         
            while ( i.hasNext () ) {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () ) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName;
                    // make distinction between files uploaded
                    if (fieldName.equals("document_card")){
                        fileName = "ID" + fi.getName();
                    }
                    else if (fieldName.equals("photo")){
                        fileName = "ProfileImg" + fi.getName();
                    }
                    else if (fieldName.equals("qualification")){
                        fileName = "Qualification" + fi.getName();
                    }
                    else{
                        fileName = fi.getName();
                    }
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
            
                    // Write the file
                    if( fileName.lastIndexOf("\\") >= 0 ) {
                        file = new File( "TestFile " + 
                        fileName.substring( fileName.lastIndexOf("\\"))) ;
                    } else {
                        file = new File( "TestFile " + 
                        fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                    }
                    fi.write( file ) ;
                    out.println("Uploaded Filename: " + 
                    fileName + "<br>");
                }
            }  
            out.println("</body>");
            out.println("</html>");
        } catch(Exception ex) {
            System.out.println(ex);
        }
   } else {
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      out.println("<p>No file uploaded</p>"); 
      out.println("</body>");
      out.println("</html>");
   }
%>