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

        String in="";
        while(!in.contentEquals("exit")){
            in = scanner.next();
            if(in.length() == 1){
                Integer selection = new Integer(in);
                switch(selection){
                    //Existing Conversations
                    case 1:
                        new ExistingConvos2(scanner, log, connection, yourUsername);
                        break;
                    //Chatgroups
                    case 2:
                        break;
                    //Private Conversations
                    case 3:
                        break;
                    case 4:
                        break;

                }

            }
        }
    }


}
