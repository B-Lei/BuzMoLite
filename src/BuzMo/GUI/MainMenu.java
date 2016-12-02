package BuzMo.GUI;

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
                        break;
                    // Create a ChatGroup
                    case 5:
                        break;
                    // Search Messages
                    case 6:
                        break;
                    // Manager Mode
                    case 7:
                        break;
                    // Manager login/logout
                    case 8:
                        break;
                    // Edit BuzMo Time
                    case 9:
                        break;
                }

            }
        }
    }
}
