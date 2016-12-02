package BuzMo.GUI;

import BuzMo.Database.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Ben on 11/27/2016.
 * This is a ConvoView for personal MyCircle messages.
 */
public class FriendConvo extends View{
    private Database db = Database.getInstance();
    private String friend;
    private Vector<Message> messages;
    private MessageHandler msg = db.messageHandler;
    //Determines which messages will be displayed
    private int messageStart = 0;

    FriendConvo(Scanner scanner, String yourUsername, String friendUsername) {
        super(scanner, Database.getInstance().log, Database.getInstance().getConnection(),yourUsername);
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
                            if(messageStart + 1 == messages.size()) break;
                            else this.messageStart++;
                            break;
                        case (2):
                            //Add a new private post between the two
                            Vector<String> recipient = new Vector<>();
                            recipient.add(friendUsername);

                            o.write("Insert Message: ");
                            String message = scanner.next();

                            msg.insertPrivateMsg(db.getNewMsg(), yourUsername, message, recipient);
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
                                db.groupInvites.newInvite(yourUsername,friendUsername,in);
                                o.write("Group Invite Sent");
                            }
                            break;
                        case (5):
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
                                db.chatGroupInvites.accept(cgr.get(response), yourUsername);
                            }
                            o.empty();
                            o.writeLine();
                            break;
                        case (6):
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
                                db.friendRequests.accept(fr.get(response), yourUsername);
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
}
