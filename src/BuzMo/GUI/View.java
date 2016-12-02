package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;
/**
 * Created by lucas on 12/1/2016.
 * Base Template for a view
 */
public class View {
    protected Scanner scanner;
    protected Logger log;
    protected Connection connection;
    protected String yourUsername;
    protected GUIOutput o = GUIOutput.getInstance();

    public View(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        this.scanner = scanner;
        this.log = log;
        this.connection = connection;
        this.yourUsername = yourUsername;
    }
}
