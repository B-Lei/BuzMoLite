package BuzMo.GUI;

import BuzMo.Database.User;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/30/2016.
 * This displays the four options available to managers:
 * 1) Find active users (top 3 message counts in last 7 days)
 * 2) Find top messages (top read counts in last 7 days)
 * 3) Find inactive users (3 or less messages sent)
 * 4) generate a report of all 3
 */
public class ManagerMenu extends View {

    ManagerMenu(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);

        boolean isManager = checkManagerStatus();
        if (isManager) {
            writeMenu();
            handleInput();
        }

        else
            nonManagerMessage();

        log.Log("GUI -- ManagerMenu properly loaded");
    }

    // THIS IS INCOMPLETE! ADD SQL QUERIES TO CHECK IF MANAGER
    private boolean checkManagerStatus() {
        return false;
    }

    private void writeMenu(){
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Manager Menu");
        o.writeLine();

        o.write("Enter the Number of Your Selection");

        o.setAlignment(GUIOutput.ALIGN.LEFT);
        o.write("1: Search for Users via Email, Topic Word(s), Message Timestamp, and/or No. Messages in past 7 days");
        o.write("2: Add new User to BuzMo");
        o.write("3: Generate a usage report");
    }

    private void nonManagerMessage() {
        o.setAlignment(GUIOutput.ALIGN.LEFT);
        o.write("You need to be logged in as a Manager to access the Manager Menu!");
        log.Log("Attempted to access ManagerMenu as a non-manager");
    }

    private void handleInput() {
        String in="";
        if(in.length() == 1){
            Integer selection = new Integer(in);
            switch(selection){
                // Search for Users
                case 1:
                    searchUsers();
                    break;
                // Add new User
                case 2:
                    addUser();
                    break;
                // Generate a Usage report
                case 3:
                    generateReport();
                    break;
            }
        }
    }

    // THIS IS INCOMPLETE! NEED TO ADD SQL QUERIES IN HELPER FUNCTIONS
    private void searchUsers() {
        // FORMATS ACCEPTED
        // email:        contains @ and .com
        // timestamp:    has "AM" or "PM" at the end
        // no. msgs:     integer
        // topic word:   any string

        // 1) Take input (string separated by commas), then split into a String array "input"
        System.out.print("Enter a list of search terms, separated by comma: ");
        String inputString = scanner.next();
        String input[] = inputString.split("\\s*,\\s*");

        // 2) Go through "input" and check the corresponding types for each index, then call the appropriate SQL
        //      query for each and save into a vector of String vectors "userResults"; print results
        try {
            Vector<Vector<String>> userResults = new Vector<Vector<String>>();
            for (int i = 0; i < input.length; i++) {
                int inputType = getStringType(input[i]); // Get the string type
                userResults.add(i, handleQuery(inputType, input[i])); // Find matches for it and add to results
                if (!userResults.elementAt(i).isEmpty()) { // If there are matches, print it
                    String printMsg = userResults.elementAt(i).toString();
                    System.out.println(printMsg);
                }
            }
        } catch (Exception except) {
            System.out.println("Exception occurred while finding Users!");
            log.Log("ManagerMenu -- Exception occurred while finding Users!");
        }
    }

    private void addUser() {
        System.out.print("Enter the User's name: ");
        String name = scanner.next();
        System.out.print("Enter the User's phone number: ");
        String phone = scanner.next();
        System.out.print("Enter the User's email address: ");
        String email = scanner.next();
        System.out.print("Enter the User's password: ");
        String password = scanner.next();
        System.out.print("Enter the User's screen name: ");
        String screenName = scanner.next();
        System.out.print("Is the User a manager? 1 (for yes, 0 for no): ");
        int temp = scanner.nextInt();
        boolean isManager = (temp == 1);

        try {
            User newUser = new User(log, connection, name, email, password, phone, screenName);
            User.insert(connection, newUser, isManager);
        } catch (Exception except) {
            System.out.println("Tried to create an invalid user!");
            log.Log("Tried to create an invalid user!");
        }
    }

    // INCOMPLETE
    private void generateReport() {
        // Generates a systemwide report of: 1) # new messages 2) # message reads 3) average # new message reads
        //      4) average # reads for msgs in last report 5) top 3 msgs by read count 6) top 3 users by msg count
        //      7) # users who sent < 3 msgs 8) most read msgs for each topic word


    }

    // Helper function used in searchUsers to get the string type
    private int getStringType(String input) {
        // 0 = topic word, 1 = email, 2 = timestamp, 3 = no. msgs
        int result = 0;
        if (input.contains("@") && input.contains(".com"))
            result = 1;
        else if (input.endsWith("AM") || input.endsWith("PM"))
            result = 2;
        else if (input.matches("^-?\\d+$"))
            result = 3;
        return result;
    }

    // THIS IS A STUB -- ADD APPROPRIATE SQL QUERIES
    // Helper function used in searchUsers to call the correct SQL query
    private Vector<String> handleQuery(int inputType, String inputString) {
        Vector<String> result = new Vector<String>();
        try {
            switch (inputType) {
                case 0:
                    // SQL query for Users with matching topicWords
                    break;
                case 1:
                    // SQL query for Users with matching email
                    break;
                case 2:
                    // SQL query for Users with matching message timestamps
                    break;
                case 3:
                    // SQL query for Users with a matching # of messages in the past 7 days
                    break;
                default:
                    System.out.println("This shouldn't even be possible");
            }
        } catch (Exception except) {
            System.out.println("Exception occurred while performing a single handleQuery");
            log.Log("ManagerMenu -- Exception occurred while performing a single handleQuery");
        }
        return result;
    }
}