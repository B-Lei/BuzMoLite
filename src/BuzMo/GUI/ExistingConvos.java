package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Created by Ben on 11/27/2016.
 * This displays a list of all friends [who have at least one message to you].
 * You can click each friend element to send a message to them.
 */
public class ExistingConvos {
    private Logger log;
    private JFrame ExistingConvoWindow = new JFrame("Current Conversations");

    ExistingConvos(Logger log, String yourUsername) {
        // Get a string array of friends based on SQL query in getFriends();
        String friendUsernames[] = getFriends();
        int numFriends = friendUsernames.length;

        // Hook up logger to GUI
        this.log = log;

        // Set the login window parameters
        ExistingConvoWindow.setSize(300,600);
        ExistingConvoWindow.setLocation(500,100);
        ExistingConvoWindow.setResizable(false);

        // Create and populate the panel
        JPanel p = new JPanel(new GridLayout(0,1));
        for (int i = 0; i < numFriends; i++) {
            JButton temp = new JButton(friendUsernames[i]);
            p.add(temp);
            handleFriendClick(temp, yourUsername, friendUsernames[i]);
        }

        p.setOpaque(true);
        ExistingConvoWindow.setContentPane(p);
        ExistingConvoWindow.setVisible(true);

        log.Log("GUI -- ExistingConvos properly loaded");
    }
    // ADD SQL QUERY HERE! Look up friends who have sent or received one message from you
    public String[] getFriends() {
        String result[] = {"Alice", "Bob", "Calvin"};
        return result;
    }
    public void handleFriendClick(JButton newButton, String yourUsername, String friendUsername) {
        newButton.addActionListener(
                (ActionEvent e) -> {
                    new FriendConvo(log, yourUsername, friendUsername);
                    ExistingConvoWindow.dispose();
                }
        );
    }
}