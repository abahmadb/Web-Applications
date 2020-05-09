import java.sql.*;

/*
 * Search Person by ID database access object
 *
 * @author Xianwen Jin
 */
 
 public final class SearchPersonByIdDAO {
	 
		private static final String STATEMENT = "SELECT Name, Surname, Gender, DoB, Email, Passwd, Phone, City, Description FROM Person WHERE IDUser=?";
		private final Connection con;
		private final int idUser;
		
		public SearchPersonByIdDAO(final Connection con, final int idUser){
		
			this.con = con;
			this.idUser = idUser;
		
		}
		
		public Person searchPersonById() throws SQLException{
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			// the result of the query
			Person person = null;
			
			try{
				pstmt = con.prepareStatement(STATEMENT);
				pstmt.setInt(1, idUser);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {					
					person = new Person(idUser, rs.getString("Name"), rs.getString("Surname"),
							rs.getString("Gender"), rs.getDate("Dob"), rs.getString("Email"),
							rs.getString("Passwd"), rs.getString("Phone"), rs.getString("City"), 
							rs.getString("Description"));	
				}
			} finally {
				if (rs != null){
					rs.close();
				}
					
				if (pstmt != null){
					pstmt.close();
				}
				
			}
			return person;
			
		}
 }