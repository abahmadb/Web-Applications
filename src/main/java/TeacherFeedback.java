import java.util.Date;

public class TeacherFeedback {

    public TeacherFeedback(int studentid, String name, String description, int score){

        this.studentid = studentid;
        this.name = name;
        this.description = description;
        this.score = score;

    }

    private final String name, description;
    private final int studentid, score;
    
    public final int getStudentid() {return studentid;}
    public final String getName() {return name;}
    public final int getScore() {return score;}
    public final String getDescription() {return description;}

}
