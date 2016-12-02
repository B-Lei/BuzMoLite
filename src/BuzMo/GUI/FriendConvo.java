package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Ben on 11/27/2016.
 * This is a ConvoView for personal MyCircle messages.
 */
public class FriendConvo extends View{
    String friend;
    // UNIMPLEMENTED FOR NOW
    FriendConvo(Scanner scanner, Logger log, Connection connection, String yourUsername, String friendUsername) {
        super(scanner, log, connection,yourUsername);
        this.friend = friendUsername;
        System.out.println("Created a FriendConvo!");
    }
}
