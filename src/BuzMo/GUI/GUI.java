package BuzMo.GUI;

import BuzMo.Database.Database;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Initializes the GUI for BuzMo Handles all graphical requests for both Manager and User
 */
public class GUI {
    public GUI(Logger log, Database database) {
        //new LoginWindow(log, database.getConnection());
        new MainMenu(new Scanner(System.in), log, database.getConnection(), "JBieber@gmail.com");
        log.Log("GUI Properly Loaded");
    }
}


/* Code for adding friend req
                            o.write("Insert ChatGroup Name For "+friendUsername+":");
                            String group = scanner.next();
                            Vector<String> members = ChatGroupMembers.members(log, connection, group);
                            if(!members.contains(yourUsername)){
                                o.write("You must be a member of "+group+" to invite another member");
                            }
                            else if(members.contains(friendUsername)){
                                o.write("You cannot invite a member to the group");
                            }
                            else{
                                //Needs to be group not friend req
                                db.friendRequests.newRequest(yourUsername,friendUsername);
                                o.write("Friend Request Sent");
                            }
 */