package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Created by Ben on 11/30/2016.
 * Takes an input of topic words and returns messages containing them.
 * If empty, then return messages containing the user's topic words.
 * Also allows you to select a message and send its author a friend request.
 */
public class TopicSearchResults extends JPanel implements ListSelectionListener {
    private Logger log;
    private JList list;
    private String yourUsername;
    private DefaultListModel listModel;
    private static final String sendFriendRequestString = "Send this User a Friend Request";
    private UserMsgPair[] matchingMessages;

    TopicSearchResults(Logger log, String yourUsername, String input) {
        super(new BorderLayout());
        this.yourUsername = yourUsername;
        String topicWordsInput = input;
        listModel = new DefaultListModel();

        // Get the list of matching UserMsg pairs and populate the list with them
        if (input.isEmpty() || input == null)
            topicWordsInput = getUserTopicWords();
        matchingMessages = findMatchingMessages(topicWordsInput);
        String[] matchingMessagesFormatted = new String[matchingMessages.length];
        for(int i=0; i<matchingMessages.length; i++) {
            matchingMessagesFormatted[i] = matchingMessages[i].user + ": " + matchingMessages[i].msg;
            listModel.addElement(matchingMessagesFormatted[i]);
        }

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton sendFriendRequestButton = new JButton(sendFriendRequestString);
        FriendRequestListener friendRequestListener = new FriendRequestListener(sendFriendRequestButton);
        sendFriendRequestButton.setActionCommand(sendFriendRequestString);
        sendFriendRequestButton.addActionListener(friendRequestListener);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(sendFriendRequestButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // This listener is shared by the text field and the hire button.
    class FriendRequestListener implements ActionListener, DocumentListener {
        private JButton button;
        public FriendRequestListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        // PUT THE SQL STATEMENT FOR SENDING A FRIEND REQUEST HERE!
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String friendName = matchingMessages[index].user;
            System.out.println(yourUsername + " is sending a friend request to " + friendName);
        }
        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {}
        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {}
        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {}
    }
    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {}

    public static void createAndShowGUI(Logger log, String yourUsername, String input) {
        JFrame frame = new JFrame("Matching Messages");
        frame.setSize(400,200);
        frame.setLocation(400,200);

        //Create and set up the content pane.
        JComponent newContentPane = new TopicSearchResults(log, yourUsername, input);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //frame.pack();
        frame.setVisible(true);
    }

    // ADD SQL QUERY HERE! Get a list of UserMsg pairs that match the topic words provided.
    // searches ALL MESSAGES!
    public UserMsgPair[] findMatchingMessages(String input) {
        String[] topicWords = input.split("\\s*,\\s*"); // split on comma
        UserMsgPair result[] = {new UserMsgPair("Alice", "I'm Alice!"),
                new UserMsgPair("Bob", "I'm Bob!"), new UserMsgPair("Adam", "I'm Adam!"),
                new UserMsgPair("Terry", "I'm Terry!"), new UserMsgPair("Jack", "I'm Jack!"),
                new UserMsgPair("Tim", "I'm Tim!"), new UserMsgPair("Mac", "I'm Mac!"),
                new UserMsgPair("Jon", "I'm Jon!"), new UserMsgPair("Rod", "I'm Rod!"),
                new UserMsgPair("Sally", "I'm Sally!"), new UserMsgPair("Meg", "I'm Meg!")};
        return result;
    }

    // ADD SQL QUERY HERE! Get a list of the user's topic words.
    public String getUserTopicWords() {
        String exampleString = "Apples, Bananas, Grapes";
        return exampleString;
    }

    // Data structure used to store both the User and Message
    public class UserMsgPair {
        public String user;
        public String msg;
        UserMsgPair(String user, String msg) {
            this.user = user;
            this.msg = msg;
        }
    }
}
