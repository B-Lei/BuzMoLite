package BuzMo.GUI;

import BuzMo.Database.ChatGroups;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/27/2016.
 * This is a simple form allowing you to create a new ChatGroup.
 */
public class NewChatGroup extends View {

    public NewChatGroup (Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);
        
        writeOpeningText();
        while(!handleInput()) {
            log.Log("Invalid ChatGroup creation");
        }

        log.Log("GUI -- NewChatGroup properly loaded");
    }

    // Outputs for the opening text
    public void writeOpeningText() {
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Create a New ChatGroup");
        o.writeLine();
    }

    public boolean handleInput() {
        Vector<String> newMemberList = new Vector<>();

        System.out.print("ENTER NAME OF NEW CHATGROUP: ");
        String chatGroupName = scanner.next();

        System.out.print("ENTER A LIST OF NEW MEMBERS, SEPARATED BY COMMA: ");
        chatGroupName = scanner.next();
        String temp[] = chatGroupName.split(",");
        newMemberList.copyInto(temp);

        System.out.print("ENTER MESSAGE DURATION IN DAYS: ");
        int messageDuration = scanner.nextInt();

        try {    
            boolean exists = ChatGroups.exists(log, connection, chatGroupName);

            if (exists) {
                System.out.println("NewChatGroup -- Name already exists!");
                log.Log("NewChatGroup -- Name already exists!");
                return false;
            }
        
            //ChatGroups.insertGroupAndUsers(yourUsername, chatGroupName, messageDuration, newMemberList);
        } catch(Exception except) {
            System.out.println("NewChatGroup -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("NewChatGroup -- Error: "+except.getMessage());
        }
    
        return true;
    }
}
