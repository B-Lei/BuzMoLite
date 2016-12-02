package BuzMo.Database;

/**
 * Created by lucas on 12/1/2016.
 */
public class Message {
    public int id;
    public String message;
    public String sender;
    public String receiver;
    //public String owner;
    //public String timestamp;

    public Message(int id, String message, String sender, String receiver){
        this.id = id;
        this.message = message;
        this.receiver = receiver;
        this.sender= sender;
    }
}
