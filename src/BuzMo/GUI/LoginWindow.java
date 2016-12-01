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
    enum login{QUIT, SUCCESS};

    private Logger log;
    private Connection connection;
    private Scanner userInput = new Scanner(System.in);

    public LoginWindow(Logger log, Connection connection) {
        // Hook up logger to GUI
        this.log = log;
        this.connection = connection;

        while(!display()){
            //Log each try
            log.Log("invalid login entry");
        }

        userInput.close();
    }

    //Prompts for username and password
    //If keyword exit is entered, quits
    private boolean display(){
        String usernameInput;
        System.out.print("ENTER USERNAME (EMAIL): ");
        usernameInput = userInput.next();

        String passwordInput;
        System.out.print("ENTER PASSWORD: ");
        passwordInput = userInput.next();

        //Check if keyword exit
        if(usernameInput.contentEquals("exit") || passwordInput.contentEquals("exit")){
            return true;
        }

        try{
            Boolean exists = User.exists(connection, usernameInput);

            //Check if user exists
            if(!exists){
                System.out.println("LoginWindow -- Invalid username entered!");
                log.Log("LoginWindow -- Invalid username entered!");
                return false;
            }

            // If login successful, bring up a new Main Menu and dispose of the current window
            String pass = User.getPassword(log, connection, usernameInput);

            if (passwordInput.equals(pass)) {
                new MainMenu(log, usernameInput);
            }
            else {
                System.out.println("LoginWindow -- Invalid password entered!");
                log.Log("LoginWindow -- Invalid password entered!");
                return false;
            }
        } catch(Exception except){
            System.out.println("LoginWindow -- EXCEPTION CAUGHT: "+except.getMessage());
            log.Log("LoginWindow -- Error: "+except.getMessage());
        }

        log.Log("GUI -- LoginWindow properly loaded");
        return true;
    }

}
