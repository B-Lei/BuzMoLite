package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lucas on 11/28/2016.
 * Base Class for a database object
 */
public class DatabaseObject {
    //Connection to the database via the driver
    Logger log;
    Connection connection;

    //Initializes a new DatabaseObject
    public DatabaseObject(Logger log, Connection connection) throws DatabaseException{
        this.log = log;
        this.connection = connection;

        //Check if connection is valid
        if(connection == null){
            throw new DatabaseException("Cannot initialize a new DatabaseObject with a null connection");
        }
    }

    //Surrounds a string with ticks
    static String addTicks(String original){
        if(original.charAt(0) == '\'')
            return original;
        return "'"+original+"'";
    }

    static Statement getSt(Connection c, Logger log){
        try {
            return c.createStatement();
        } catch (SQLException e) {
            log.Log("Error creating statement: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
