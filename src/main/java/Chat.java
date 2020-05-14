public class Chat {
   
    public  Chat (String tname, String sname, Boolean confirmed, String messages, Date lastMessage){

        this.tname = tname;
        this.sname = sname;
	    this.confirmed = confirmed;
        this.messages = messages;
	    this.lastMessage = lastMessage;
        
    }

    public final String getTname() {return Tname;}
    public final String getSname() {return Sname;}
    public final Boolean getConfirmed() {return Confirmed;}
    public final String getMessages() {return Messages;}
    public final Date getLastMessage() {return LastMessage;}

    private final String tname, sname, message;
    private final Boolean confirmed;
    private final Date LastMessage;

    public class Chat extends ArrayList<Chat.messages> {
    

        public Messages(String sename, String rename,String message, Date ts){
    
            this.sename = sename;
            this.rename = rename;
            this.message = message;
            this.ts = date;
            
        }

    public final String getSename() {return Sename;}
    public final String getRename() {return Rename;}
    public final String getMessage() {return Message;}
    public final Date getTs() {return Ts;}

    }
     
    private final String sename, rename, message;
    private final Date ts;
}