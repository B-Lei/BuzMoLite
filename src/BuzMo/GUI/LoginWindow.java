package BuzMo.GUI;

import BuzMo.Database.DatabaseException;
import BuzMo.Database.User;
import BuzMo.Logger.Logger;
import java.sql.Connection;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Created by Ben on 11/26/2016.
 * This is what the GUI calls - the initial login window that leads to all other windows 
 */
public class LoginWindow {
    private Logger log;
    private Connection connection;

    public LoginWindow(Logger log, Connection connection) {
        // Hook up logger to GUI
        this.log = log;
        this.connection = connection;

        Scanner userInput = new Scanner(System.in);

        String usernameInput;
        System.out.print("ENTER USERNAME (EMAIL): ");
        usernameInput = userInput.next();
        System.out.println("Detected input: "+usernameInput);

        String passwordInput;
        System.out.print("ENTER PASSWORD: ");
        passwordInput = userInput.next();
        System.out.println("Detected input: "+passwordInput);

        try{
            Boolean exists = User.exists(connection, usernameInput);

            //Check if user exists
            if(!exists){
                System.out.println("LoginWindow -- Invalid username entered!");
                log.Log("LoginWindow -- Invalid username entered!");
            }

            // If login successful, bring up a new Main Menu and dispose of the current window
            String pass = User.getPassword(log, connection, usernameInput);

            if (passwordInput.equals(pass)) {
                new MainMenu(log, usernameInput);
            }
            else {
                System.out.println("LoginWindow -- Invalid password entered!");
                log.Log("LoginWindow -- Invalid password entered!");
            }
        } catch(Exception except){
            System.out.println("LoginWindow -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("LoginWindow -- Error: "+except.getMessage());
        }

        System.out.println("GUI -- LoginWindow properly loaded");
        log.Log("GUI -- LoginWindow properly loaded");
    }
}
