package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Ben on 11/30/2016.
 * Allows you to either search for messages by topic word,
 * or search for users.
 */
public class BrowseMessages {
    private Logger log;
    private JFrame browseMessagesWindow = new JFrame("Search for Messages or Users");
    private JButton messageSearchButton =  new JButton("Search Messages");
    private JButton userSearchButton =  new JButton("Search Users");
    private JTextField messageSearchField = new JTextField(20);
    private JTextField userSearchField = new JTextField(20);

    public BrowseMessages(Logger log, String yourUsername) {
        // Hook up logger to GUI
        this.log = log;

        // Set the login panel parameters
        browseMessagesWindow.setSize(300, 200);
        browseMessagesWindow.setLocation(100, 200);
        browseMessagesWindow.setResizable(false);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create labels
        JLabel messageSearchText = new JLabel("Search Messages:");
        JLabel userSearchText = new JLabel("Search Users:");

        // Set the field + button parameters
        messageSearchText.setBounds(20, 20, 150, 20);
        messageSearchField.setBounds(150, 20, 100, 20);
        messageSearchButton.setBounds(100, 50, 150, 20);

        userSearchText.setBounds(20,95,150,20);
        userSearchField.setBounds(150, 95, 100, 20);
        userSearchButton.setBounds(100, 125, 150, 20);

        // Add the fields + buttons to the login panel
        mainPanel.add(messageSearchField);
        mainPanel.add(userSearchField);
        mainPanel.add(messageSearchText);
        mainPanel.add(userSearchText);
        mainPanel.add(messageSearchButton);
        mainPanel.add(userSearchButton);

        // Add the login panel + ActionListeners to the Content Pane
        browseMessagesWindow.setContentPane(mainPanel);
        browseMessagesWindow.setVisible(true);

        handleMessageSearchButtonClick(log, yourUsername, messageSearchField.getText());
        handleUserSearchButtonClick(log, yourUsername, userSearchField.getText());

        log.Log("GUI -- BrowseMessages properly loaded");
    }

    private void handleMessageSearchButtonClick(Logger log, String yourUsername, String input) {
        messageSearchButton.addActionListener(
                (ActionEvent e) -> {
                    TopicSearchResults.createAndShowGUI(log, yourUsername, input);
                }
        );
    }

    private void handleUserSearchButtonClick(Logger log, String yourUsername, String input) {
        userSearchButton.addActionListener(
                (ActionEvent e) -> {
                    new UserSearchResults(log, yourUsername, input);
                }
        );
    }
}
