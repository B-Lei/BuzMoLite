package BuzMo.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Logs all operations that occur and all errors that occur
 */
public class Logger{

    //Log will be used for more execution based information, flow control, etc
    private FileHandler log;
    //Debug will be used to dump SQL queries and requests to check logic
    private FileHandler sql;
    //Formatter determines the format of output files
    private LogFormatter format = new LogFormatter();

    //Keeping currentDay Static since this application will only run for 30 mins
    private String currentDay  = new SimpleDateFormat("yyyy.MM.dd").format(new Date());

    //Creates a new logger for BuzMo
    public Logger() throws Exception {
        //Try to create FileHandlers for both sql and general logging
        try{


            //Set up Log
            log = new FileHandler("Resources/"+currentDay+".log", false);
            log.setFormatter(format);

            //Set up sql
            sql = new FileHandler("Resources/"+currentDay+".sql", false);
            //might need to create new formatter for sql to nicely dump SQL
            sql.setFormatter(format);
        }
        catch(IOException io){
            throw new LoggerException("IOError Initializing Logger", io);
        }
        catch(Exception e){
            throw new LoggerException("Error Initializing Logger", e);
        }
    }

    //dispose must be called when BuzMo is closing to prevent lock files
    public void dispose(){
        Log("\n\n");
        log.close();
        sql.close();
    }

    //Writes an entry to the log file. All null messages are ignored silently
    public void Log(String message){
        log.publish(new LogRecord(Level.ALL, message));
    }

    //Writes an entry to the sql file. All null messages are ignored silently
    public void gSQL(String message){
        sql.publish(new LogRecord(Level.ALL, "success: "+message));
    }

    public void bSQL(String message){
        sql.publish(new LogRecord(Level.ALL, "error: "+message));
    }


}
