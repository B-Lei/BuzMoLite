package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Created by Ben on 11/30/2016.
 * This displays a list of all friends [who have at least one message to you].
 * You can click each friend element to send a message to them.
 */
public class ExistingConvos2 extends JPanel implements ListSelectionListener {
    private Logger log;
    private String yourUsername;
    private JList list;
    private DefaultListModel listModel;
    private static final String checkOutString = "Check out Conversations";

    public ExistingConvos2(Logger log, String yourUsername) {
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

        JButton sendButton = new JButton(checkOutString);
        CheckOutListener checkOutListener = new CheckOutListener(sendButton);
        sendButton.setActionCommand(checkOutString);
        sendButton.addActionListener(checkOutListener);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(sendButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // This listener is shared by the text field and the hire button.
    class CheckOutListener implements ActionListener, DocumentListener {
        private JButton button;
        public CheckOutListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        // PUT THE SQL STATEMENT FOR SENDING A MESSAGE HERE!
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String friendName = list.getModel().getElementAt(index).toString();
            System.out.println(yourUsername + " is opening Conversations for " + friendName);
            new FriendConvo(log, yourUsername, friendName);
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

    public static void createAndShowGUI(Logger log, String yourUsername) {
        JFrame frame = new JFrame("Check out Existing Conversations");
        frame.setSize(240,400);
        frame.setLocation(100,200);

        //Create and set up the content pane.
        JComponent newContentPane = new ExistingConvos2(log, yourUsername);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //frame.pack();
        frame.setVisible(true);
    }

    // ADD SQL QUERY HERE! Just get a list of all your friends
    public String[] getFriends() {
        String result[] = {"Alice", "Bob", "Calvin"};
        return result;
    }
}