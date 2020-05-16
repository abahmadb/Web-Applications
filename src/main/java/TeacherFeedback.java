import java.util.Date;

public class TeacherFeedback {

    public TeacherFeedback(String name, String description, int score){

        this.name = name;
        this.description = description;
        this.score = score;

    }

    private final String name, description;
    private final int score;
    
    public final String getName() {return name;}
    public final int getScore() {return score;}
    public final String getDescription() {return description;}

}
