import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import java.sql.*;


public abstract class DatabaseServlet extends HttpServlet {

    private Connection con;

    public void init(ServletConfig config) {

        con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        }//try
        catch (ClassNotFoundException ignored) {}
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/remytutor", "root", "root");
        }//try
        catch(SQLException ignored){}
        
    }//init
    
    public void destroy() {
        con = null;
    }//destroy
    
    protected final Connection getConnection() {
        return con;
    }//getConnection
    
    
}//DatabaseServlet