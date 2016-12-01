package BuzMo.Database;

import BuzMo.Logger.Logger;
import BuzMo.Properties.AppProperties;
import BuzMo.Properties.PropertiesException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.io.Console;
import java.sql.*;
import java.util.Vector;


/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Class to create a connection to desired database {SQL PLUS}
 * This class has a dependency on
 * May also need a function to initialize the database and import the base information provided
 *
 */
public class Database {
    private boolean CSIL = true;

    private Logger log = null;
    private Connection connection = null;
    private AppProperties properties = null;
    private int newMsg = 0;

    private OracleDataSource Osource = null;
    private MysqlDataSource Msource = null;


    //Choose which URL to use depending on where you are running the program
    //private String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
    //private String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";

    //Initialize Database and Establish a connection
    public Database(Logger log) throws DatabaseException {

        //Hook up the log to the JDBC Class
        this.log = log;

        //Read in appropriate username and password
        String username;
        String password;

        try {
            //Read in Properties
            properties = new AppProperties(log, CSIL);
            username = properties.getUsername();
            password = properties.getPassword();
        }catch(PropertiesException e){
            throw new DatabaseException(e);
        }

        if(!CSIL) {
            //MySQL driver usage since SQLPlus sucks
            this.Msource = new MysqlDataSource();

            //Establish MySQL connection
            try {
                Msource.setURL("jdbc:mysql://localhost/buzmo");
                Msource.setUser(username);
                Msource.setPassword(password);

                this.connection = Msource.getConnection();
            } catch (SQLException sql) {
                throw new DatabaseException("Error establishing SQL connection", sql);
            }
            log.Log("MySQL Database properly loaded");
        }
        //Otherwise establish Oracle connection
        else{
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
                this.connection = DriverManager.getConnection(url, username, password);
            }
            catch(Exception e){
                log.Log("Error initializing oracle datasource");
                throw new DatabaseException(e);
            }

            log.Log("Successfully loaded Oracle Database");
        }

        CreateDatabase init = new CreateDatabase(log,connection, this);

    }

    //Closes all connections
    public void dispose() throws DatabaseException{
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error attempting to close SQL connection");
        }
    }

    //Gets
    public int getNewMsg(){
        newMsg++;
        return newMsg - 1;
    }

    public Connection getConnection(){
        return this.connection;
    }


}
