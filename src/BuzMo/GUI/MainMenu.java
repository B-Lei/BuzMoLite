package BuzMo.GUI;

import BuzMo.Database.ChatGroupInvites;
import BuzMo.Database.ChatGroups;
import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Ben on 11/26/2016.
 * This is created after a successful login. The portal for all other options.
 */
public class MainMenu extends View {

    MainMenu(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername); 

        writeMenu();
        takeInput();

        log.Log("GUI -- MainMenu properly loaded");
    }

    //outputs for the main menu
    private void writeMenu(){
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("BuzMo Main Menu");
        o.writeLine();

        o.write("Enter the Number of Your Selection");
        o.write("Enter exit to exit");


        o.setAlignment(GUIOutput.ALIGN.LEFT);
        o.write("1: View Your MyCircle Feed"); //Views the MyCircle Feed
        o.write("2: View Your Chatgroups"); //Handles all chatgroup operations
        o.write("3: View All Current Conversations");
        o.write("4: Create Conversation");
        o.write("5: Create a ChatGroup");
        o.write("6: Search All Messages");
        o.write("7: Use Manager Functions");
        o.write("8: Log in or Log out as Manager");
        o.write("9: Edit BuzMo Time");
        o.write("10: Find all private messages from someone");
        o.write("11: Send a ChatGroup invitation from someone");
    }

    public void takeInput() {
        String in="";
        while(!in.contentEquals("exit")){
            in = scanner.next();
            if(in.length() == 1){
                Integer selection = new Integer(in);
                switch(selection){
                    //Existing Conversations
                    case 1:
                        new MyCircle(scanner,log,connection,yourUsername);
                        break;
                    //Chatgroups
                    case 2:
                        break;
                    //Private Conversations
                    case 3:
                        new ExistingConvos2(scanner, log, connection, yourUsername);
                        break;
                    //Create Conversation
                    case 4:
                        new CreateNewConvo2(scanner,log,connection,yourUsername);
                        break;
                    // Create a ChatGroup
                    case 5:
                        new NewChatGroup(scanner, log, connection, yourUsername);
                        break;
                    // Search Messages
                    case 6:
                        new BrowseMessages(scanner, log, connection, yourUsername);
                        break;
                    // Manager Mode
                    case 7:
                        new ManagerMenu(scanner, log, connection, yourUsername);
                        break;
                    // Manager login/logout
                    case 8:
                        new ManagerToggle(scanner, log, connection, yourUsername);
                        break;
                    // Edit BuzMo Time
                    case 9:
                        new DebugMode(scanner, log, connection, yourUsername);
                        break;
                    // Find all PMs for an email -- NOT IMPLEMENTED
                    case 10:
                        o.write("Enter an email:");
                        String input = scanner.next();

                        break;
                    // Send a ChatGroup invite
                    case 11:
                        o.write("Enter a user email:");
                        String email = scanner.next();
                        o.write("Enter a GroupChat name:");
                        String groupname = scanner.next();
                        try {
                            ChatGroups chatGroups = new ChatGroups(log, connection);
                            ChatGroupInvites invites = new ChatGroupInvites(log, connection, chatGroups);
                            invites.newInvite(yourUsername, email, groupname);
                            System.out.println("Invite successful.");
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    // Change duration of ChatGroup messages
                    case 12:
                        o.write("Enter a GroupChat name:");
                        String name = scanner.next();
                        o.write("Enter a duration:");
                        int duration = Integer.parseInt(scanner.next());
                        try {
                            ChatGroups.changeDuration(log, connection, name, duration);
                            System.out.println("Invite successful.");
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                }

            }
            writeMenu();
        }
    }
}
