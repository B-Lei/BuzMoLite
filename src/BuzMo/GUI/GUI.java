package BuzMo.GUI;

import BuzMo.Database.Database;
import BuzMo.Logger.Logger;

import java.sql.Connection;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Initializes the GUI for BuzMo Handles all graphical requests for both Manager and User
 */
public class GUI {
    public GUI(Logger log, Database database) {
        new LoginWindow(log, database.getConnection());
        log.Log("GUI Properly Loaded");
    }
}
