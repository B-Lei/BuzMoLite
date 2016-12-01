package BuzMo.GUI;

import BuzMo.Logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Ben on 11/27/2016.
 * This is a simple form allowing you to create a new ChatGroup.
 */
public class NewChatGroup {
    private Logger log;
    private JFrame newChatGroupWindow = new JFrame("Create a new ChatGroup");
    private JButton sendButton =  new JButton("Send");
    private JTextField groupNameField = new JTextField();
    private JTextField recipientsField = new JTextField();

    public NewChatGroup (Logger log, String yourUsername) {
        // Hook up logger to GUI
        this.log = log;

        // Set the login panel parameters
        newChatGroupWindow.setSize(700, 200);
        newChatGroupWindow.setLocation(100, 200);
        newChatGroupWindow.setResizable(false);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout(5,5));
        mainPanel.setLayout(null);

        // Create labels
        JLabel groupNameText = new JLabel("GroupName:");
        JLabel recipientText = new JLabel("Recipients:");

        // Set the field + button parameters
        groupNameField.setBounds(325, 30, 150, 20);
        groupNameText.setBounds(225, 30, 150, 20);
        recipientsField.setBounds(325, 75, 150, 20);
        recipientText.setBounds(225, 75, 150, 20);

        sendButton.setBounds(310, 120, 80, 20);

        // Add the fields + buttons to the login panel
        mainPanel.add(groupNameField);
        mainPanel.add(groupNameText);
        mainPanel.add(recipientsField);
        mainPanel.add(recipientText);
        mainPanel.add(sendButton);

        // Add the login panel + ActionListeners to the Content Pane
        newChatGroupWindow.setContentPane(mainPanel);
        newChatGroupWindow.setVisible(true);

        handleMessageSubmit(yourUsername);

        log.Log("GUI -- NewChatGroup properly loaded");
    }

    // ADD A SQL QUERY UPDATING THE TABLE
    private void handleMessageSubmit(String yourUsername) {
        sendButton.addActionListener(
                (ActionEvent e) -> {
                    // Convert the String of comma-separated Strings into a String Array, also strip whitespace
                    String[] recipientsList = recipientsField.getText().split("\\s*,\\s*");
                    String groupName = groupNameField.getText();

                    for (int i=0; i<recipientsList.length; i++) {
                        System.out.println(yourUsername + " has added " + recipientsList[i] + " to " + groupName);
                    }
                    newChatGroupWindow.dispose();
                }
        );
    }
}
