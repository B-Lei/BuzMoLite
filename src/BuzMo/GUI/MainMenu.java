package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

/**
 * Created by Ben on 11/26/2016.
 * This is created after a successful login. The portal for all other options.
 */
public class MainMenu extends View {

    MainMenu(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(log, connection); 

        writeMenu();

        log.Log("GUI -- MainMenu properly loaded");
    }

    //outputs for the main menu
    private void writeMenu(){
        write.setAlignment(GUIOutput.ALIGN.CENTER);
        write.write("BuzMo Main Menu");
        write.writeLine();

        write.setAlignment(GUIOutput.ALIGN.LEFT);
        write.write("MyCircle: View Your MyCircle Feed"); //Views the MyCircle Feed
        write.write("ChatGroups: View Your Chatgroups"); //Handles all chatgroup operations
        write.write("Conversations: View All Current Conversations");
        write.write("Messages: View All Of Your Messages"); //Could be the same as above
        write.write("Conversations");
    }


}
