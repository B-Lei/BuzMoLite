package BuzMo.GUI;

import BuzMo.Logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Ben on 11/30/16.
 * This is the popup window after a manager menu option is selected.
 */
public class ManagerWindowResult extends JFrame {
    private Logger log;
    private JFrame managerResultWindow;
    private JButton loginButton =  new JButton("Login");
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(10);

    public ManagerWindowResult(Logger log, String yourUsername, String optionName, int optionIndex) {
        // Hook up logger to GUI
        this.log = log;

        managerResultWindow = new JFrame(optionName);
        managerResultWindow.setSize(350, 400);
        managerResultWindow.setLocation(450, 200);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Option text
        JLabel optionText = new JLabel(optionName);
        optionText.setBounds(75,15,400,100);

        // THE RESULT
        String resultText[] = getResult(optionIndex);
        int offset = 0;
        int extraLabelOffset = 3;
        for (int i=0; i<resultText.length; i++) {
            if (resultText[i] != null && resultText[i].length() > 0) {
                if(optionIndex == 3 && extraLabelOffset%3 ==0) {
                    JLabel temp = new JLabel(additionOptionText(extraLabelOffset-3));
                    System.out.println("CURRENT LABEL: "+additionOptionText(extraLabelOffset-3));
                    temp.setBounds(75,55 + (extraLabelOffset-3)*27,400,100);
                    mainPanel.add(temp);
                    offset += 20;
                }
                JLabel temp = new JLabel(resultText[i]);
                temp.setBounds(95, 55 + offset, 400, 100);
                mainPanel.add(temp);
                offset += 20;
                extraLabelOffset++;
            }
        }

        mainPanel.add(optionText);

        managerResultWindow.setContentPane(mainPanel);
        managerResultWindow.setVisible(true);

        log.Log("GUI -- LoginWindow properly loaded");
    }

    // ADD SQL QUERIES HERE!
    public String[] getResult(int optionIndex) {
        String[] result = new String[7];
        // Find top 3 active users last 7 days (highest message count)
        if (optionIndex == 0 || optionIndex == 3) {
            String[] activeUsers = {"Alice", "Bob", "Calvin"};
            for (int i=0; i<activeUsers.length; i++)
                result[i] = (i+1) + ".) " + activeUsers[i];
        }
        // Find top 3 messages in last 7 days (highest read count)
        if (optionIndex == 1 || optionIndex == 3) {
            String[] topMessages = {"Hello Eric, how are you?", "LMAO", "Hi there"};
            for (int i=0; i<topMessages.length; i++)
                result[i+3] = (i+1) + ".) " + topMessages[i];
        }
        // Find number of inactive users (3 or less messages sent)
        if (optionIndex == 2 || optionIndex == 3) {
            int numInactive = 4;
            result[6] = Integer.toString(numInactive);
        }
        return result;
    }

    public String additionOptionText(int optionIndex) {
        if(optionIndex == 0) return "Top 3 Active Users";
        else if(optionIndex == 3) return "Top 3 Messages";
        else return "# of Inactive Users";
    }
}