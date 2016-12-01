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
 *
 * Might be useful when implementing actual SQL:
 * http://www.thaicreate.com/java/java-gui-example-login-username-password.html
 * http://www.thepcwizard.in/2011/10/create-login-form-using-netbeans-ide.html
 */
public class LoginWindow extends JFrame {
    private Logger log;
    private Connection connection;
    private JFrame loginWindow = new JFrame("Enter 'test' for both fields to login");
    private JButton loginButton =  new JButton("Login");
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(10);

    public LoginWindow(Logger log, Connection connection) {
        // Hook up logger to GUI
        this.log = log;
        this.connection = connection;

        // Set the login panel parameters
        loginWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginWindow.setSize(400, 200);
        loginWindow.setLocation(100, 100);
        loginWindow.setResizable(false);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create labels
        JLabel usernameText = new JLabel("Email:");
        JLabel passwordText = new JLabel("Password:");

        // Set the field + button parameters
        usernameField.setBounds(175, 30, 150, 20);
        passwordField.setBounds(175, 75, 150, 20);
        usernameText.setBounds(75, 30, 150, 20);
        passwordText.setBounds(75,75,150,20);
        loginButton.setBounds(160, 120, 80, 20);

        // Add the fields + buttons to the login panel
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(usernameText);
        mainPanel.add(passwordText);
        mainPanel.add(loginButton);

        // Add the login panel + ActionListeners to the Content Pane
        loginWindow.setContentPane(mainPanel);
        loginWindow.setVisible(true);

        handleLoginAction();

        log.Log("GUI -- LoginWindow properly loaded");
    }

    private void handleLoginAction() {
        loginButton.addActionListener(
                (ActionEvent e) -> {
                    String usernameInput = usernameField.getText();
                    String passwordInput = new String(passwordField.getPassword());

                    try{
                        Boolean exists = User.exists(connection,usernameInput);


                    //Check if user exists
                    if(!exists){
                        JOptionPane.showMessageDialog(null, "Invalid Username");
                        usernameField.setText("");
                        passwordField.setText("");
                        usernameField.requestFocus();
                    }


                        // ADD SQL QUERY to look up email and password in Users table.
                        // If login successful, bring up a new Main Menu and dispose of the current window
                        String pass = User.getPassword(log, connection,usernameInput);
                        log.Log("in: "+ passwordInput + " other: "+pass );

                        if (passwordInput.equals(pass)) {
                            new MainMenu(log, usernameInput);
                            loginWindow.dispose();
                            dispose();
                        }
                        // If username or password are incorrect, ask for it again
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Password / Username");
                            usernameField.setText("");
                            passwordField.setText("");
                            usernameField.requestFocus();
                        }
                    }catch(Exception except){
                        log.Log("Error occurred in login window "+except.getMessage());
                        JOptionPane.showMessageDialog(null, "Invalid Password / Username");
                        usernameField.setText("");
                        passwordField.setText("");
                        usernameField.requestFocus();
                    }
                }
        );
    }
}
