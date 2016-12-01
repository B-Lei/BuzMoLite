package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Created by Ben on 11/29/2016.
 * This displays a list of ALL friends, period.
 * It allows you to click on one of them and send them a message.
 * It doesn't explicitly create a new convo, but lets you access it after sending a message.
 */
public class CreateNewConvo2 extends JPanel implements ListSelectionListener {
    private Logger log;
    private String yourUsername;
    private JList list;
    private DefaultListModel listModel;
    private static final String sendString = "Send";
    private JTextField messageField;

    public CreateNewConvo2(Logger log, String yourUsername) {
        super(new BorderLayout());
        this.yourUsername = yourUsername;

        listModel = new DefaultListModel();
        String[] friendsList = getFriends();
        for(int i=0; i<friendsList.length; i++)
            listModel.addElement(friendsList[i]);

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        //list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton sendButton = new JButton(sendString);
        SendListener sendListener = new SendListener(sendButton);
        sendButton.setActionCommand(sendString);
        sendButton.addActionListener(sendListener);
        sendButton.setEnabled(false);

        messageField = new JTextField();
        messageField.addActionListener(sendListener);
        messageField.getDocument().addDocumentListener(sendListener);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(messageField);
        buttonPane.add(sendButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // This listener is shared by the text field and the hire button.
    class SendListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        public SendListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        // PUT THE SQL STATEMENT FOR SENDING A MESSAGE HERE!
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String friendName = list.getModel().getElementAt(index).toString();
            String messageContents = messageField.getText();
            System.out.println(yourUsername + " has sent a message sent to " + friendName + ": " + messageContents);

            //Reset the text field.
            messageField.requestFocusInWindow();
            messageField.setText("");
        }
        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }
        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }
        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }
    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {}

    public static void createAndShowGUI(Logger log, String yourUsername) {
        JFrame frame = new JFrame("Send a Message");
        frame.setSize(240,400);
        frame.setLocation(100,200);

        //Create and set up the content pane.
        JComponent newContentPane = new CreateNewConvo2(log, yourUsername);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //frame.pack();
        frame.setVisible(true);
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
}