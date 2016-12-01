package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Ben on 11/26/2016.
 * This is what the GUI calls - the initial login window that leads to all other windows
 *
 * Might be useful when implementing actual SQL:
 * http://www.thaicreate.com/java/java-gui-example-login-username-password.html
 * http://www.thepcwizard.in/2011/10/create-login-form-using-netbeans-ide.html
 */
public class DebugMode extends JFrame {
    private Logger log;
    private JFrame debugWindow = new JFrame("Set a new date and time");
    private JButton submitButton =  new JButton("Submit");
    private JTextField dateField = new JTextField(20);
    private JTextField timeField = new JTextField(20);

    public DebugMode(Logger log) {
        // Hook up logger to GUI
        this.log = log;

        // Set the login panel parameters
        debugWindow.setSize(700, 200);
        debugWindow.setLocation(100, 200);
        debugWindow.setResizable(false);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create labels
        JLabel dateText = new JLabel("Date:");
        JLabel timeText = new JLabel("Time:");

        // Set the field + button parameters
        dateField.setBounds(325, 30, 150, 20);
        timeField.setBounds(325, 75, 150, 20);
        dateText.setBounds(225, 30, 150, 20);
        timeText.setBounds(225,75,150,20);
        submitButton.setBounds(310, 120, 80, 20);

        // Add the fields + buttons to the login panel
        mainPanel.add(dateField);
        mainPanel.add(timeField);
        mainPanel.add(dateText);
        mainPanel.add(timeText);
        mainPanel.add(submitButton);

        // Add the login panel + ActionListeners to the Content Pane
        debugWindow.setContentPane(mainPanel);
        debugWindow.setVisible(true);

        handleDateTimeChange();

        log.Log("GUI -- DebugMode properly loaded");
    }

    // ADD SQL QUERY HERE TO CHANGE NEW SYSTEM DATE/TIME
    private void handleDateTimeChange() {
        submitButton.addActionListener(
                (ActionEvent e) -> {
                    String dateInput = dateField.getText();
                    String timeInput = timeField.getText();

                    // DO STUFF HERE
                    System.out.println("Entered date: " + dateInput);
                    System.out.println("Entered time: " + timeInput);

                    debugWindow.dispose();
                    dispose();
                }
        );
    }
}
