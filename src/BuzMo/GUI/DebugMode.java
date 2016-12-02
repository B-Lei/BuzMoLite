package BuzMo.GUI;

import BuzMo.Logger.Logger;
import BuzMo.Database.Timestamp;

import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;

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
                System.out.println("input: "+input);

                // Unconfirmed if this works or not
                String inputNoWhitespace = input.replace(" ", "");
                System.out.println("stripped input: "+input);

                // 1(Day) 2(Month) 3(Year) 4(Hour) 5(MinuteAM/PM)
                String[] tempTimeStamp = inputNoWhitespace.split("[.|:]");
                for (int i=0; i<tempTimeStamp.length; i++) {
                    System.out.println("Component found: "+tempTimeStamp[i]);
                }
                Vector<String> timeStamp = new Vector<>();
                timeStamp.copyInto(tempTimeStamp);

                // Fix minuteAMPM issue
                String str = timeStamp.elementAt(4);
                String ampm = str.substring(Math.max(str.length() - 2, 0));
                str = str.substring(0, 1);

                timeStamp.setElementAt(str, 4);
                System.out.println("New minute: "+timeStamp.elementAt(4));

                timeStamp.add(ampm);
                System.out.println("New ampm: "+timeStamp.elementAt(5));

                for (int i=0; i<timeStamp.size()-1; i++) {
                    System.out.println("Found final component: "+timeStamp.elementAt(i));
                }
            } catch (Exception except) {
                System.out.println("DebugMode -- EXCEPTION CAUGHT: " + except.getMessage());
                log.Log("DebugMode -- Error: " + except.getMessage());
            }
        }
    }
}
