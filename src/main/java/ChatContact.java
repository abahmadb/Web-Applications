public class ChatContact {

    public ChatContact(int uid, String n, String s, String lmd, String lm){
        
        userID = uid;
        name = n;
        surname = s;
        lastMessageDate = lmd;
        lastMessage = lm;

    }//ChatContact

    public final String getName() {return name;}
    public final String getSurname() {return surname;}
    public final int getUserID() {return userID;}
    public final String getLastMessageDate() {return lastMessageDate;}
    public final String getLastMessage() {return lastMessage;}

    private final String name, surname, lastMessage, lastMessageDate;
    private final int userID;

}//ChatContact
