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

        o.write("Enter a new Timestamp with format DAY.MONTH.YEAR|HOUR:MINUTE (PM or AM).");
        o.write("Format example: 11.1.2016| 5:00 PM");
    }

    // ADD SQL QUERY TO ADD NEW TIMESTAMP INSIDE!
    public void handleInput() {
        String input = scanner.next();
        String regex = "^([1-9]|[12][0-9]|3[01]).([1-9]|1[012]).(20\\d\\d)[ ]*\\|[ ]*([1-9]|1[012])\\:([0-5][0-9])[ ]*(PM|AM)$";
        if (!input.matches(regex)) {
            System.out.println("Invalid Timestamp format entered");
            log.Log("Invalid Timestamp format entered");
        }
        else {
            try {
                // Unconfirmed if this works or not
                String inputNoWhitespace = input.replace(" ", "");
                // 1(Day) 2(Month) 3(Year) 4(Hour) 5(Minute) 6(first letter of AM/PM) 7(second letter of AM/PM)
                String[] timeStamp = inputNoWhitespace.split("[.|:A-Z]");
                // Combine to form AM and PM in index 6
                timeStamp[6] = timeStamp[6]+timeStamp[7];
                System.out.println("Detected Timestamp: "+timeStamp);
            } catch (Exception except) {
                System.out.println("DebugMode -- EXCEPTION CAUGHT: " + except.getMessage());
                log.Log("DebugMode -- Error: " + except.getMessage());
            }
        }
    }
}
