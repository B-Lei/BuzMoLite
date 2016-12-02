package BuzMo.GUI;

import BuzMo.Database.CircleOfFriends;
import BuzMo.Logger.Logger;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Created by Ben on 11/29/2016.
 * This displays a list of ALL friends, period.
 * It allows you to click on one of them and send them a message.
 * It doesn't explicitly create a new convo, but lets you access it after sending a message.
 */
public class CreateNewConvo2 extends View {
    private Vector<String> friends;

    public CreateNewConvo2(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);
        try {
            String s="";
            while(!s.contentEquals("exit")){
                this.friends = CircleOfFriends.getCircleOfFriends(log, connection, yourUsername);
                display();

                s = scanner.next();
                if(friends.contains(s)){
                    new FriendConvo(scanner,log, connection, yourUsername, s);
                }
            }
        }catch(Exception e){
            log.Log(e.getMessage());
        }





    }

    private void display(){
        //Display all friends
        o.setAlignment(GUIOutput.ALIGN.CENTER);
        o.writeLine();
        o.empty();
        o.write("Select A Friend to create a new Conversation");
        o.empty();
        o.writeLine();

        if(friends!=null) {
            for (String s : friends) {
                o.write(s);
            }
        }
    }


}