package BuzMo.GUI;

import BuzMo.Database.MessageHandler;
import BuzMo.Logger.Logger;


import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;


/**
 * Created by Ben on 11/30/2016.
 * This displays a list of all friends [who have at least one message to you].
 * You can click each friend element to send a message to them.
 */
public class ExistingConvos2 extends View{
    private MessageHandler handler;
    private Vector<String> friends;


    public ExistingConvos2(Scanner scanner, Logger log, Connection connection, String user) {
        super(scanner, log, connection, user);
        try {
            this.handler = new MessageHandler(log, connection);
            this.friends = handler.getPrivateUserMessages(user);

            o.setAlignment(GUIOutput.ALIGN.CENTER);
            o.writeLine();
            o.empty();
            o.write("Your Messages");
            o.empty();
            o.writeLine();

            o.setAlignment(GUIOutput.ALIGN.LEFT);

            for (String s : friends) {
                o.write(s);
            }

        } catch (Exception e) {
            log.Log("ERROR couldnt init Message handler " + e.getMessage());
        }

        String s = "";
        while(!s.contentEquals("exit")){
            s = scanner.next();
            if(friends.contains(s)){
                new FriendConvo(scanner,log, connection,user, s);
            }
        }
    }
}