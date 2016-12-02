package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.sql.Connection;

/**
 * Created by lucas on 12/1/2016.
 * Base Template for a view
 */
public class View {
    protected Logger log;
    protected Connection connection;
    protected GUIOutput o = GUIOutput.getInstance();

    public View(Logger log, Connection connection){
        this.log = log;
        this.connection = connection;
    }
}
