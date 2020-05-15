public class Chat {
   
    public  Chat (String tname, String sname, Boolean confirmed, String messages, Date lastMessage){

        this.tname = tname;
        this.sname = sname;
	    this.isconfirmed = confirmed;
        this.messages = messages;
	    this.lastMessage = lastMessage;
        
    }

    public final String getTname() {return tname;}
    public final String getSname() {return sname;}
    public final Boolean isConfirmed() {return isConfirmed;}
    public final String getMessages() {return messages;}
    public final Date getLastMessage() {return lastMessage;}

    private final String tname, sname, message;
    private final Boolean isconfirmed;
    private final Date LastMessage;

    public class Chat extends ArrayList<Chat.messages> {
    

        public Messages(String sename, String rename,String message, Date ts){
    
            this.sename = sename;
            this.rename = rename;
            this.message = message;
            this.ts = date;
            
        }

    public final String getSename() {return sename;}
    public final String getRename() {return rename;}
    public final String getMessage() {return message;}
    public final Date getTs() {return ts;}

    }
     
    private final String sename, rename, message;
    private final Date ts;
}