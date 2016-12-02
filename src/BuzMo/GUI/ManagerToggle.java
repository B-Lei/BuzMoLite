package BuzMo.GUI;

import BuzMo.Logger.Logger;
import BuzMo.Database.User;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Ben on 11/30/2016.
 */
public class ManagerToggle extends View {

    ManagerToggle(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);

        o.empty();
        writeOpeningText();
        while(!handleInput()) {
            log.Log("Invalid ManagerToggle operation");
        }
        o.empty();

        log.Log("GUI -- ManagerToggle properly loaded");
    }

    // Outputs for the opening text
    public void writeOpeningText() {
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Log in or Log out as a Manager");
        o.writeLine();

        o.write("Enter the Number of Your Selection");
        o.setAlignment(GUIOutput.ALIGN.LEFT);
        o.write("1: Log in as Manager");
        o.write("2: Log out as Manager");
    }

    public boolean handleInput() {
        int input = scanner.nextInt();

        if (input!=1 && input !=2)
            return false;

        try {
            // INCOMPLETE - add SQL to log in as Manager
            if (input == 1) {
                o.write("Logged in as Manager!");
            }
            // INCOMPLETE - add SQL to log out as Manager
            else {
                o.write("Logged out as Manager!");
            }
        } catch(Exception except) {
            System.out.println("ManagerToggle -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("ManagerToggle -- Error: "+except.getMessage());
        }
        return true;
    }
}
