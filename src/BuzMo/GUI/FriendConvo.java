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
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Enter number for action you want to use or type exit");
        o.write("1: Create New Post");
        o.write("2: Delete a Post");
        o.write("3: See More Messages");
        o.write("4: Send ChatGroup Invite to "+friendUsername);
        o.write("5: View my pending ChatGroup Invites");
        o.write("6: View my pending MyCircle Invites");


        String in = "";
        while(!in.contentEquals("exit")){
            in = scanner.next();
            log.Log("Read "+in+" in scanner");
            if(in.length() == 1){
                Integer response = new Integer(in);
                switch(response){
                    case(1):
                        o.write("");
                        break;
                    case(2):
                        break;
                    case(3):
                        break;
                    case(4):
                        break;
                    case(5):
                        break;
                    case(6):
                        break;
                }
            }
        }
    }
}
