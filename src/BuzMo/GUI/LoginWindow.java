package BuzMo.GUI;

import BuzMo.Database.DatabaseException;
import BuzMo.Database.User;
import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;

import static java.lang.System.exit;

/**
 * Created by Ben on 11/26/2016.
 * This is what the GUI calls - the initial login window that leads to all other windows 
 */
public class LoginWindow extends JFrame {
    private Logger log;
    private Connection connection;

    public LoginWindow(Logger log, Connection connection) {
        // Hook up logger to GUI
        this.log = log;
        this.connection = connection;

        try{
            Boolean exists = User.exists(connection,usernameInput);

            //Check if user exists
            if(!exists){

            }

            // ADD SQL QUERY to look up email and password in Users table.
            // If login successful, bring up a new Main Menu and dispose of the current window
            String pass = User.getPassword(log, connection,usernameInput);

            if (passwordInput.equals(pass)) {
                new MainMenu(log, usernameInput);
            }
            // If username or password are incorrect, ask for it again
            else {
                
            } catch(Exception except){
                log.Log("Error occurred in login window "+except.getMessage());
            }
        }

        log.Log("GUI -- LoginWindow properly loaded");
    }
}
