import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Upadate person database access object
 * 
 * @author Xianwen Jin
*/

public final class UpdatePersonDAO {
	private static final String STATEMENT = "UPDATE Remytutor.Person SET Name=?, Surname=?, Gender=?, DoB=?, Email=?, Phone=?, City=? WHERE IDUser=?";
	private static final String STATEMENT1 = "UPDATE Remytutor.Person SET Passwd=SHA2(?,256) WHERE IDUser=? AND Passwd = SHA2(?, 256)";
	
	private final Connection con;
	private final Person person;
	
	public UpdatePersonDAO(final Connection con, final Person person) {
		this.con = con;
		this.person = person;
	}
	
	public void updatePerson() throws SQLException {
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, person.getName());
			pstmt.setString(2, person.getSurname());
			pstmt.setString(3, person.getGender());
			pstmt.setDate(4, person.getDob());
			pstmt.setString(5, person.getEmail());
			pstmt.setString(6, person.getPhone());
			pstmt.setString(7, person.getCity());
			pstmt.setInt(8, person.getID());
			
			pstmt.execute();
		} finally {
			if (pstmt != null)
				pstmt.close();
			
		}
	}
	
	public void updatePassword(String newPassword) throws SQLException{
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(STATEMENT1);
			pstmt.setString(1, newPassword);
			pstmt.setInt(2, person.getID());
			pstmt.setString(3, person.getPassword()); 
			
			pstmt.execute();
		} finally {
			if (pstmt != null)
				pstmt.close();
			
		}
	}
}