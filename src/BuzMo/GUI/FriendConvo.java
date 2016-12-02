package BuzMo.GUI;

import BuzMo.Database.*;
import BuzMo.Logger.Logger;

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
    //Determines which messages will be displayed
    int messageStart = 0;

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
                        case(1): //Scroll Up
                            if(messageStart == 0) break;
                            else this.messageStart--;
                        case (2):
                            if(messageStart + 1 == messages.size()) break;
                            else this.messageStart++;
                        case (3):
                            //Add a new private post between the two
                            Vector<String> recipient = new Vector<>();
                            recipient.add(friendUsername);

                            o.write("Insert Message: ");
                            String message = scanner.next();

                            handler.insertPrivateMsg(db.getNewMsg(), yourUsername, message, recipient);
                            break;
                        case (4)://Delete a post
                            o.write("Insert MessageID you wish to delete: ");
                            int input = new Integer(scanner.next());
                            msg.dropPrivatePost(yourUsername,input );
                            break;
                        case (5):
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
                                db.groupInvites.newInvite(yourUsername,friendUsername,in);
                                o.write("Group Invite Sent");
                            }
                            break;
                        case (6):
                            o.write("Pending ChatGroup Requests ");
                            Vector<ChatGroupInvite> cgr = db.chatGroupInvites.pendingInvites(yourUsername);
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
                                db.chatGroupInvites.accept(cgr.get(i), yourUsername);
                            }
                            o.empty();
                            o.writeLine();
                            break;
                        case (7):
                            o.write("Pending Friend Requests ");
                            Vector<String> fr = db.friendRequests.pendingRequests(yourUsername);
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
                                db.friendRequests.accept(fr.get(i), yourUsername);
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
        for(Message m: messages){
            o.write("ID: "+m.id );
            o.write(m.sender+ " to "+m.receiver+":"+m.message );
        }
        o.writeLine();
        o.writeLine();
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.write("Enter the Action Number or type exit");
        o.write("0: ScrollUp");
        o.write("1: ScrollDown");
        o.write("2: Create New Post");
        o.write("3: Delete a Post");
        o.write("4: See More Messages");
        o.write("5: Send ChatGroup Invite to "+friend);
        o.write("6: View my pending ChatGroup Invites");
        o.write("7: View my pending MyCircle Invites");
        o.empty(5);
    }

    private void loadPosts(){
        try {
            this.messages = msg.getPrivateMessagesBetween(yourUsername, this.friend);
        } catch (DatabaseException e) {
            log.Log(e.getMessage());
        }

    }
}
