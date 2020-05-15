import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public  final class ChatServlet extends DatabaseServlet {
    
    //retrieve data from db
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            //check if we are logged in
            if (!IndexServlet.check_login(request))
                throw new IOException("You need to be signed in to access this page");

        }

        //catch exception if we are not logged in
        catch (IOException e) {

            request.setAttribute("error_message", e.getMessage());
            request.setAttribute("appname", request.getContextPath());

            try{
                request.getRequestDispatcher("errorpage.jsp").forward(request, response);
            }

            catch(Exception ignored){}

        }

    }   
    
}