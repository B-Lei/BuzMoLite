package BuzMo.GUI;

import BuzMo.Logger.Logger;
import BuzMo.Database.Timestamp;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Ben on 11/26/2016.
 */
public class DebugMode extends View {

    public DebugMode(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);

        writeOpeningText();
        handleInput();

        log.Log("GUI -- DebugMode properly loaded");
    }

    // Outputs for the opening text
    public void writeOpeningText() {
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Enter a new Timestamp for BuzMo");
        o.writeLine();

        o.write("Enter a new Timestamp - format: MONTH.DAY_OF_WEEK.YEAR, HOUR:MINUTEPM");
        o.write("Example: ");
    }

    public void handleInput() {
        int input = scanner.nextInt();

        try {
            // INCOMPLETE - add SQL to log in as Manager
            if (input == 1) {

            }
            // INCOMPLETE - add SQL to log out as Manager
            else {

            }
        } catch(Exception except) {
            System.out.println("ManagerToggle -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("ManagerToggle -- Error: "+except.getMessage());
        }
    }
}
