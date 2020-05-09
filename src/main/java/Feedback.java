public class Feedback {

    public Feedback(String name, String surname, int score, String description){

        this.name = name;
        this.surname = surname;
        this.score = score;
        this.description = description;

    }

    public final String getName() {return name;}
    public final String getSurname() {return surname;}
    public final int getScore() {return score;}
    public final String getDescription() {return description;}

    private final String name, surname, description;
    private final int score;


}
