
import java.sql.Date;
import com.fasterxml.jackson.core.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/*
 * Person class
 *
 * @author Xianwen Jin
*/

public class Person extends Resource{
    
    private final int idUser;
    private final String name;
	private final String surname;
	private final String gender;
	private final java.sql.Date dob;
	private final String email;
	private final String passwd;
	private final String phone;
	private final String city;
	private final String description;

	// constructor
	public Person(int idUser, String name, String surname, String gender,
			java.sql.Date dob, String email, String passwd, String phone, String city, String description) {
			
		this.idUser = idUser;		
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.passwd = passwd;
		this.phone = phone;
		this.description = description;
		this.city = city;
		
    }
	
	// get methods
	
    public final int getID() {
        return idUser;
    }
    
    public final String getName() {
        return name;
    }
	
	public final String getSurname() {
        return surname;
    }
	
	public final String getGender() {
        return gender;
    }
	
	public final Date getDob() {
        return dob;
    }
	
	public final String getEmail() {
        return email;
    }
	
	public final String getPassword() {
        return passwd;
    }
	
	public final String getPhone() {
        return phone;
    }

	public final String getDescription() {
        return description;
    }
	
	public final String getCity() {
		return city;	
	}
	
	@Override
	public final void toJSON(final OutputStream out) throws IOException {
		
		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);	
		jg.writeStartObject();
		
		jg.writeFieldName("Person");
		
		jg.writeStartObject();
		
		jg.writeNumberField("idUser", idUser);
		jg.writeStringField("name", name);
		jg.writeStringField("surname", surname);
		jg.writeStringField("gender", gender);
		jg.writeStringField("birthday", new SimpleDateFormat("dd/MM/yyyy").format(dob));
		jg.writeStringField("email", email);
		jg.writeStringField("password", passwd);
		jg.writeStringField("phone", phone);
		jg.writeStringField("description", description);
		jg.writeStringField("city", city);
		
		jg.writeEndObject();
		jg.writeEndObject();
		
		jg.flush();
	}
	
	public static Person fromJSON(final InputStream in) throws IOException, ParseException {
		
		int jIdUser = -1;
		String jName = null;
		String jSurname = null;
		String jGender = null;
		java.sql.Date jDob = null;
		String jEmail = null;
		String jPasswd = null;
		String jPhone = null;
		String jDescription = null;
		String jCity = null;
		
		final JsonParser jp = JSON_FACTORY.createParser(in);
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "employee".equals(jp.getCurrentName()) == false) {
			if (jp.nextToken() == null)
				throw new IOException("Unable to parse JSON: no person object found.");
		}
		
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
				
				switch(jp.getCurrentName()) {
					case "idUser":
						jp.nextToken();
						jIdUser = jp.getIntValue();
						break;
					case "name":
						jp.nextToken();
						jName = jp.getText();
						break;
					case "surname":
						jp.nextToken();
						jSurname = jp.getText();
						break;
					case "gender":
						jp.nextToken();
						jGender = jp.getText();
						break;
					case "birthday":
						jp.nextToken();
						SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy"); // New Pattern
						java.util.Date date = sdf1.parse(jp.getText()); // Returns a Date format object with the pattern
						jDob = new java.sql.Date(date.getTime());
						break;
					case "email":
						jp.nextToken();
						jEmail = jp.getText();
						break;
					case "password":
						jp.nextToken();
						jPasswd = jp.getText();
						break;
					case "phone":
						jp.nextToken();
						jPhone = jp.getText();
						break;
					case "description":
						jp.nextToken();
						jDescription = jp.getText();
						break;
					case "city":
						jp.nextToken();
						jCity = jp.getText();
						break;
				}
			}		
		}
		
		return new Person(jIdUser, jName, jSurname, jGender, jDob, jEmail, jPasswd, jPhone, jDescription, jCity);
		
	}
}