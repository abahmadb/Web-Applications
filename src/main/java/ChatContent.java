public class ChatContent {
    
    public ChatContent(int uid, String m, String ts){
            
        userID = uid;
        Message = m;
        TS = ts;

    }//ChatContent


    public final int getUserID() {return userID;}
    public final String getMessage() {return Message;}
    public final String getTS() {return TS;}

    private final String Message, TS;
    private final int userID;

}//ChatContent