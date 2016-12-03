package BuzMo.GUI;

import BuzMo.Database.ChatGroupInvites;
import BuzMo.Database.ChatGroups;
import BuzMo.Database.AdminFile;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/27/2016.
 * This is a simple form allowing you to create a new ChatGroup.
 */
public class NewChatGroup extends View {
    private ChatGroups chatGroups;
    private ChatGroupInvites chatGroupInvites;
    private AdminFile adminFile;

    public NewChatGroup (Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);
        try {
            chatGroups = new ChatGroups(log, connection);
            chatGroupInvites = new ChatGroupInvites(log, connection, chatGroups);
            adminFile = new AdminFile(log, connection);
        } catch(Exception except) {
            System.out.println("NewChatGroup -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("NewChatGroup -- Error: "+except.getMessage());
        }

        o.empty();
        writeOpeningText();
        while(!handleInput()) {
            log.Log("Invalid ChatGroup creation");
        }
        o.empty();

        log.Log("GUI -- NewChatGroup properly loaded");
    }

    // Outputs for the opening text
    public void writeOpeningText() {
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Create a New ChatGroup");
        o.writeLine();
    }

    public boolean handleInput() {
        System.out.print("ENTER NAME OF NEW CHATGROUP: ");
        String chatGroupName = scanner.next();

        System.out.print("ENTER A LIST OF NEW MEMBERS, SEPARATED BY COMMA: ");
        String newMembers = scanner.next();
        String temp[] = newMembers.split("\\s*,\\s*");
        Vector<String> newMemberList = new Vector(Arrays.asList(temp));

        System.out.print("ENTER MESSAGE DURATION IN DAYS: ");
        String messageDurationString = scanner.next();
        int messageDuration;
        if (messageDurationString.equals(""))
            messageDuration = 7;
        else messageDuration = Integer.parseInt(messageDurationString);

        try {    
            boolean exists = ChatGroups.exists(log, connection, chatGroupName);
            if (exists) {
                System.out.println("NewChatGroup -- Name already exists!");
                log.Log("NewChatGroup -- Name already exists!");
                return false;
            }

            // Create the ChatGroup
            chatGroups.insertGroup(yourUsername, chatGroupName, adminFile.getNextMessage(), messageDuration);

            // Send an invite to all the members in newMemberList
            for(int i=0; i<newMemberList.size(); i++){
                chatGroupInvites.newInvite(yourUsername, newMemberList.elementAt(i), chatGroupName);
            }

        } catch(Exception except) {
            System.out.println("NewChatGroup -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("NewChatGroup -- Error: "+except.getMessage());
        }
    
        return true;
    }
}
