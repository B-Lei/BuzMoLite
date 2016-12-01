package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Ben on 11/30/2016.
 * This displays the four options available to managers:
 * 1) Find active users (top 3 message counts in last 7 days)
 * 2) Find top messages (tp[ read counts in last 7 days)
 * 3) Find inactive users (3 or less messages sent)
 * 4) generate a report of all 3
 */
public class ManagerMenu {
    private Logger log;
    private JFrame ManagerWindow = new JFrame("Manager Menu");

    ManagerMenu(Logger log, String yourUsername) {
        // Get a string array of friends based on SQL query in getFriends();
        String options[] = {"Top 3 Active Users", "Top 3 Messages", "# of Inactive Users", "Total Report"};
        int numOptions = options.length;

        // Hook up logger to GUI
        this.log = log;

        // Set the login window parameters
        ManagerWindow.setSize(350,400);
        ManagerWindow.setLocation(100,200);

        // Create and populate the panel
        JPanel managerPanel;
        if (isManager(yourUsername)) {
            managerPanel = new JPanel(new GridLayout(0, 2));
            for (int i = 0; i < numOptions; i++) {
                JButton temp = new JButton(options[i]);
                managerPanel.add(temp);
                handleOptionClick(temp, yourUsername, options[i], i);
            }
        }
        else {
            managerPanel = new JPanel(new GridLayout(1,1));
            managerPanel.add(new JLabel("                       You aren't logged in as a Manager!"));
        }

        managerPanel.setOpaque(true);
        ManagerWindow.setContentPane(managerPanel);
        ManagerWindow.setVisible(true);

        log.Log("GUI -- ManagerMenu properly loaded");
    }

    public void handleOptionClick(JButton newButton, String yourUsername, String optionName, int optionIndex) {
        newButton.addActionListener(
                (ActionEvent e) -> {
                    new ManagerWindowResult(log, yourUsername, optionName, optionIndex);
                }
        );
    }

    // ADD SQL QUERY TO CHECK IF YOU'RE A MANAGER
    public boolean isManager(String yourUsername) {
        if(yourUsername.equals("test")) return true;
        else return false;
    }
}