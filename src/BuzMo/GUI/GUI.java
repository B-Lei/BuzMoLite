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
        new MainMenu(new Scanner(System.in), log, database.getConnection(), "JeffBezos@yahoo.com");
        log.Log("GUI Properly Loaded");
    }
}
