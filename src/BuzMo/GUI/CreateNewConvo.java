package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Ben on 11/27/2016.
 * This displays a list of ALL friends, period.
 * It allows you to click on one of them and send them a message.
 */
public class CreateNewConvo {
    private Logger log;
    private JFrame NewConvoWindow = new JFrame("Send a Message to someone");

    CreateNewConvo(Logger log, String userName) {
        // Get a string array of friends based on SQL query in getFriends();
        String friendUsernames[] = getFriends();
        int numFriends = friendUsernames.length;

        // Hook up logger to GUI
        this.log = log;

        // Set the login window parameters
        NewConvoWindow.setSize(300,600);
        NewConvoWindow.setLocation(500,100);
        NewConvoWindow.setResizable(false);

        // Create and populate the panel
        JPanel p = new JPanel(new GridLayout(0,1));
        for (int i = 0; i < numFriends; i++) {
            JButton temp = new JButton(friendUsernames[i]);
            p.add(temp);
            handleFriendClick(temp, userName, friendUsernames[i]);
        }

        p.setOpaque(true);
        NewConvoWindow.setContentPane(p);
        NewConvoWindow.setVisible(true);

        log.Log("GUI -- CreateNewConvo properly loaded");
    }
    // ADD SQL QUERY HERE! Just get a list of all your friends
    public String[] getFriends() {
        String result[] = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
                "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana",
                "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
                "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
                "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
                "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
                "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        return result;
    }
    public void handleFriendClick(JButton newButton, String yourUsername, String friendUsername) {
        newButton.addActionListener(
                (ActionEvent e) -> {
                    new NewMessage(log, yourUsername, friendUsername);
                }
        );
    }
}