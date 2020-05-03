import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.sql.*;

public abstract class DatabaseServlet extends HttpServlet {

    private Connection con;

    public void init(ServletConfig config) throws ServletException {

        con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        }//try
        catch (ClassNotFoundException e) {}
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/remytutor", "root", "root");
        }//try
        catch(SQLException e){}
        
    }//init
    
    public void destroy() {
        con = null;
    }//destroy
    
    protected final Connection getConnection() {
        return con;
    }//getConnection
    
    
}//DatabaseServlet