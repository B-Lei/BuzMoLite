package BuzMo.GUI;

import BuzMo.Logger.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Created by Ben on 11/30/2016.
 */
public class ManagerToggle {
    private Logger log;
    private JFrame ManagerToggleWindow = new JFrame("Manager Login Portal");

    ManagerToggle(Logger log, String yourUsername) {
        // Hook up logger to GUI
        this.log = log;

        // Set the login window parameters
        ManagerToggleWindow.setSize(700,200);
        ManagerToggleWindow.setLocation(100,200);
        ManagerToggleWindow.setResizable(false);

        // Create and populate the panel
        JPanel p = new JPanel(new GridLayout(2,1));
        JButton loginButton = new JButton("Log in as Manager");
        JButton logoutButton = new JButton("Log out as Manager");

        p.add(loginButton);
        p.add(logoutButton);
        handleToggleButtonClick(loginButton, yourUsername, true);
        handleToggleButtonClick(logoutButton, yourUsername, false);

        p.setOpaque(true);
        ManagerToggleWindow.setContentPane(p);
        ManagerToggleWindow.setVisible(true);

        log.Log("GUI -- ManagerToggle properly loaded");
    }

    // ADD SQL QUERY TO TOGGLE MANAGER ATTRIBUTE
    public void handleToggleButtonClick(JButton newButton, String yourUsername, boolean login) {
        newButton.addActionListener(
                (ActionEvent e) -> {
                    if(login) {
                        JOptionPane.showMessageDialog(null, "Logged in as Manager!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Logged out as Manager.");
                    }
                }
        );
    }
}
