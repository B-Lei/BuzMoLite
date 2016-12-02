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

        o.setAlignment(GUIOutput.ALIGN.LEFT);
        o.write("MyCircle: View Your MyCircle Feed"); //Views the MyCircle Feed
        o.write("ChatGroups: View Your Chatgroups"); //Handles all chatgroup operations
        o.write("Conversations: View All Current Conversations");
        o.write("Messages: View All Of Your Messages"); //Could be the same as above
        o.write("Conversations");
    }


}
