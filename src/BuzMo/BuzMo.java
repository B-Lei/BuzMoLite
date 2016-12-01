package BuzMo;

import BuzMo.Database.Database;
import BuzMo.Database.DatabaseException;
import BuzMo.GUI.GUI;
import BuzMo.Logger.Logger;
import BuzMo.Logger.LoggerException;
import BuzMo.Properties.AppProperties;

import javax.security.auth.login.LoginException;

import static java.lang.System.exit;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Main class to initialize all component parts for BuzMo
 */
public class BuzMo {
    private Logger log;
    private Database db;
    private GUI gui;


    public static void main(String args[]){
        //Create new Instance of BuzMo
        BuzMo app = new BuzMo();
    }

    //Loads Logger
    private BuzMo()
    {
        try {
            //Load Logger
            //Use log.Log(Message) to write a message to the .log file
            log = new Logger();

            //Load Database Connector
            db = new Database(log);

            //Load GUI
            gui = new GUI(log, db);

            db.dispose();
            log.Log("BuzMo Successfully Exited");
            log.Log("===========================");
        }
        catch(LoggerException le){
            System.out.println("Error: "+le.getMessage()+" occurred when initializing the logger");
            le.printStackTrace();
            exit(2);
        }
        catch(DatabaseException de){
            System.out.println("Error: "+de.getMessage()+" occurred when initializing the database");
            log.Log("Error: "+de.getMessage()+" occurred when initializing the database");
            de.printStackTrace();
            log.dispose();
            exit(2);
        }
        catch(Exception e){
            log.Log("Error: "+e.getMessage()+" occurred when loading BuzMo");
            System.out.println("Error: "+e.getMessage()+" occurred when loading BuzMo");
            e.printStackTrace();
            log.dispose();
            exit(2);
        }

        //Close all files
        log.dispose();
    }
}
