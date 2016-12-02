package BuzMo.GUI;

import BuzMo.Database.Message;
import BuzMo.Database.MessageHandler;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/27/2016.
 * This is a ConvoView for personal MyCircle messages.
 */
public class FriendConvo extends View{
    String friend;
    Vector<Message> messages;
    // UNIMPLEMENTED FOR NOW
    FriendConvo(Scanner scanner, Logger log, Connection connection, String yourUsername, String friendUsername) {
        super(scanner, log, connection,yourUsername);
        this.friend = friendUsername;
        try {
            MessageHandler msg = new MessageHandler(log, connection);
            this.messages = msg.getPrivateMessagesBetween(yourUsername, friendUsername);
        }
        catch (Exception e) {
            log.Log("ERROR couldnt init Message handler " + e.getMessage());
        }

        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.writeLine();
        o.write("Current conversations with "+friendUsername);
        o.writeLine();
        o.setAlignment(GUIOutput.ALIGN.LEFT);
        for(Message m: messages){
            o.write("ID: "+m.id );
            o.write(m.sender+ " to "+m.receiver+":"+m.message );
        }
        o.writeLine();
        o.writeLine();


        String in = "";
        while(!in.contentEquals("exit")){
            log.Log("friend convo");
        }
    }
}
