package BuzMo.GUI;

import BuzMo.Database.*;
import BuzMo.Logger.Logger;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/27/2016.
 * This is a ConvoView for personal MyCircle messages.
 */
public class FriendConvo extends View{
    Database db = Database.getInstance();
    String friend;
    Vector<Message> messages;
    MessageHandler msg;

    FriendConvo(Scanner scanner, Logger log, MessageHandler handler, Connection connection, String yourUsername, String friendUsername) {
        super(scanner, log, connection,yourUsername);
        this.friend = friendUsername;
        try {
            msg = new MessageHandler(log, connection);
        }
        catch (Exception e) {
            log.Log("ERROR couldnt init Message handler " + e.getMessage());
        }


        String in = "";
        o.setAlignment(GUIOutput.ALIGN.LEFT);
        while(!in.contentEquals("exit")){
            display();
            in = scanner.next();
            log.Log("Read "+in+" in scanner");
            if(in.length() == 1){
                try {
                    Integer response = new Integer(in);
                    switch (response) {
                        case (1): //Add a new private post between the two
                            Vector<String> recipient = new Vector<>();
                            recipient.add(friendUsername);

                            o.write("Insert Message: ");
                            String message = scanner.next();

                            handler.insertPrivateMsg(db.getNewMsg(), yourUsername, message, recipient);
                            break;
                        case (2): //Delete a post
                            o.write("Insert MessageID you wish to delete: ");
                            int input = new Integer(scanner.next());
                            msg.dropPrivatePost(yourUsername,input );
                            break;
                        case (3):
                            break;
                        case (4):
                            o.write("Insert name of group to invite "+friendUsername);
                            in = scanner.next();
                            Vector<String> members = ChatGroupMembers.members(log,connection,in);
                            if(!members.contains(yourUsername)){
                                o.write("You must be a member of "+in+" to invite a new member");
                            }
                            else{
                                db.groupInvites.newInvite(yourUsername,friendUsername,in);
                                o.write("Group Invite Sent");
                            }

                            break;
                        case (5):
                            o.write("Pending MyCircle Requests ");

                            break;
                        case (6):
                            o.write("Pending Friend Requests ");
                            for(String f: db.friendRequests.pendingRequests(yourUsername)){
                                
                            }
                            o.writeLine();
                            break;
                    }
                }catch(Exception e){
                    log.Log(e.getMessage());
                }
            }
        }
    }

    private void display(){
        loadPosts();
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.writeLine();
        o.writeLine();
        o.write("Current conversations with "+this.friend);
        o.writeLine();
        o.setAlignment(GUIOutput.ALIGN.LEFT);
        for(Message m: messages){
            o.write("ID: "+m.id );
            o.write(m.sender+ " to "+m.receiver+":"+m.message );
        }
        o.writeLine();
        o.writeLine();
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Enter the Action Number or type exit");
        o.write("1: Create New Post");
        o.write("2: Delete a Post");
        o.write("3: See More Messages");
        o.write("4: Send ChatGroup Invite to "+friend);
        o.write("5: View my pending ChatGroup Invites");
        o.write("6: View my pending MyCircle Invites");
    }

    private void loadPosts(){
        try {
            this.messages = msg.getPrivateMessagesBetween(yourUsername, this.friend);
        } catch (DatabaseException e) {
            log.Log(e.getMessage());
        }

    }
}
