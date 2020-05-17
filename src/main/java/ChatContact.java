public class ChatContact {

    public ChatContact(int uid, String n, String s, String lmd, String lm, boolean is_teacher, boolean cr){
        
        userID = uid;
        name = n;
        surname = s;
        lastMessageDate = lmd;
        lastMessage = lm;
        teacher = is_teacher;
        requestConfirmed = cr;
    }//ChatContact

    public final String getName() {return name;}
    public final String getSurname() {return surname;}
    public final int getUserID() {return userID;}
    public final String getLastMessageDate() {return lastMessageDate;}
    public final String getLastMessage() {return lastMessage;}
    public final boolean isTeacher() {return teacher;}
    public final boolean isRequestConfirmed() {return requestConfirmed;}

    private final String name, surname, lastMessage, lastMessageDate;
    private final int userID;
    private final boolean teacher, requestConfirmed;

}//ChatContact
