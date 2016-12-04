package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;
/**
 * Created by Ben on 11/30/2016.
 * Allows you to either search for messages by topic word,
 * or search for users.
 */
public class BrowseMessages extends View {

    public BrowseMessages(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);
        log.Log("GUI -- BrowseMessages properly loaded");
    }
}
