import java.util.Date;

public class Feedback {

    public Feedback(int studentID, String name, String surname, int score, Date date, String description){

        this.studentID = studentID;
        this.name = name;
        this.surname = surname;
        this.score = score;
        this.date = date;
        this.description = description;

    }

    public final int getStudentID() {return studentID;}
    public final String getName() {return name;}
    public final String getSurname() {return surname;}
    public final int getScore() {return score;}
    public final Date getDate() {return date;}
    public final String getDescription() {return description;}

    private final String name, surname, description;
    private final Date date;
    private final int studentID, score;

}
