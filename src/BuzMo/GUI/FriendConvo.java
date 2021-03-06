package BuzMo.GUI;

import BuzMo.Database.*;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/27/2016.
 * This is a ConvoView for personal MyCircle messages.
 */
public class FriendConvo extends View{
    private String friend;
    private Vector<Message> messages;
    private MessageHandler msg;
    //Determines which messages will be displayed
    private int messageStart = 0;
    private ChatGroupInvites chatGroupInvites;
    private ChatGroups chatGroups;
    private CircleInvites circleInvites;
    private CircleOfFriends circleOfFriends;
    private AdminFile admin = null;

    FriendConvo(Scanner scanner, Logger log, Connection connection, String yourUsername, String friendUsername) {
        super(scanner, log, connection,yourUsername);

        try {
            circleInvites = new CircleInvites(log, connection);
            circleOfFriends = new CircleOfFriends(log, connection);
            msg = new MessageHandler(log, connection);
            admin = new AdminFile(log, connection);
            chatGroups = new ChatGroups(log, connection);
            chatGroupInvites = new ChatGroupInvites(log, connection, chatGroups);


        } catch (DatabaseException e) {
            e.printStackTrace();
            log.Log(e.getMessage());
        }
        this.friend = friendUsername;


        String in = "";
        while(!in.contentEquals("exit")){
            display();

            o.setAlignment(GUIOutput.ALIGN.LEFT);
            in = scanner.next();
            log.Log("Read "+in+" in scanner");
            if(in.length() == 1){
                try {
                    Integer response = new Integer(in);
                    switch (response) {
                        case(0): //Scroll Up
                            if(messageStart == 0) break;
                            else this.messageStart--;
                            break;
                        case (1):
                            if(messageStart + 7 >= messages.size()) break;
                            else this.messageStart++;
                            break;
                        case (2):
                            //Add a new private post between the two
                            Vector<String> recipient = new Vector<>();
                            recipient.add(friendUsername);

                            o.write("Insert Message: ");
                            String message = scanner.next();
                            insert(message, yourUsername,yourUsername, friendUsername);
                            insert(message,yourUsername, friendUsername, friendUsername);


                            //msg.insertPrivateMsg(admin.getNextMessage(), yourUsername, message, recipient);
                            break;
                        case (3)://Delete a post
                            o.write("Insert MessageID you wish to delete: ");
                            int input = new Integer(scanner.next());
                            msg.dropPrivatePost(yourUsername,input );
                            break;
                        case (4):
                            o.write("Insert name of group to invite "+friendUsername);
                            in = scanner.next();
                            if(!ChatGroups.exists(log, connection,in)){
                                o.write("Chat Group Does Not Exist");
                                break;
                            }
                            Vector<String> members = ChatGroupMembers.members(log,connection,in);
                            if(!members.contains(yourUsername)){
                                o.write("You must be a member of "+in+" to invite a new member");
                            }
                            else{
                                chatGroupInvites.newInvite(yourUsername,friendUsername,in);
                                o.write("Group Invite Sent");
                            }
                            break;
                        case (5):
                            o.write("Pending ChatGroup Requests ");
                            Vector<ChatGroupInvite> cgr = chatGroupInvites.pendingInvites(yourUsername);
                            if(cgr.size() == 0){
                                o.write("No Pending Friend Requests");
                            }
                            else{
                                o.write("Enter Number To Accept");
                                int i;
                                for(i=0; i<cgr.size(); i++){
                                    o.write(i+": "+cgr.get(i).groupName+ "from: "+ cgr.get(i).host);
                                }

                                //Read in input of friend request to accept
                                in = scanner.next();
                                if(in.contentEquals("exit")){
                                    break;
                                }
                                response = new Integer(in);
                                chatGroupInvites.accept(cgr.get(response), yourUsername);
                            }
                            o.empty();
                            o.writeLine();
                            break;
                        case (6):
                            o.write("Pending Friend Requests ");
                            Vector<CircleInvite> fr = this.circleInvites.getInvites(yourUsername);
                            if(fr.size() == 0){
                                o.write("No Pending Friend Requests");
                            }
                            else{
                                o.write("Enter Number To Accept");
                                int i;
                                for(i=0; i<fr.size(); i++){
                                    o.write(i+": "+fr.get(i));
                                }

                                //Read in input of friend request to accept
                                in = scanner.next();
                                if(in.contentEquals("exit")){
                                    break;
                                }
                                response = new Integer(in);
                                circleInvites.accept(fr.get(response), yourUsername);
                            }
                            o.empty();
                            o.writeLine();
                            break;
                    }
                }catch(Exception e){
                    log.Log(e.getMessage());
                }
            }
        }
    }

    private void insert(String message, String sender, String receiver, String owner){
        int number = admin.getNextMessage();
        String sql = "INSERT INTO MESSAGES (message_id, sender, owner, message, timestamp, is_public VALUES(" +
                number+","+addTicks(sender)+","+ addTicks(owner)+","+addTicks(message)+","+Timestamp.getTimestamp()+")";
                runSQL(log, connection, sql);

        sql = "INSERT INTO messagereceivers (message_id, recipient) VALUES ("+number+","+addTicks(receiver);
        runSQL(log, connection,sql);
    }


    private void display(){
        loadPosts();
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.writeLine();
        o.writeLine();
        o.empty();
        o.write("Current conversations with "+this.friend);
        o.empty();
        o.writeLine();
        o.setAlignment(GUIOutput.ALIGN.LEFT);
        int j=0;
        while(j<7 && j<messages.size()){
            Message m = this.messages.get(j);
            o.write("ID: "+m.id );
            o.write(m.sender+ " to "+m.receiver+":"+m.message );
            j++;
        }
        o.writeLine();
        o.writeLine();
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Enter the Action Number or type exit");

        o.setAlignment(GUIOutput.ALIGN.LEFT);
        o.write("0: ScrollUp");
        o.write("1: ScrollDown");
        o.write("2: Create New Post");
        o.write("3: Delete a Post");
        o.write("4: Send ChatGroup Invite to "+friend);
        o.write("5: View my pending ChatGroup Invites");
        o.write("6: View my pending MyCircle Invites");
        o.empty(5);
    }

    private void loadPosts(){
        try {
            this.messages = msg.getPrivateMessagesBetween(yourUsername, this.friend);
        } catch (DatabaseException e) {
            log.Log(e.getMessage());
        }

    }

    static String addTicks(String original){
        if(original.charAt(0) == '\'') {
            return original;
        }

        String response = "'"+original+"'";
        //System.out.println("added ticks to "+original);
        return response;
    }

    static Statement getSt(Connection c, Logger log){
        try {
            return c.createStatement();
        } catch (SQLException e) {
            log.Log("Error creating statement: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Statement runSQL(Logger log, Connection connection, String sql){
        Statement st = null;
        try{
            st= connection.createStatement();

        }catch (Exception e){
            log.bSQL(sql);
            log.Log("Error executing sql: "+e.getMessage());
        }
        return st;
    }
}
