package BuzMo.Database;

import BuzMo.Logger.Logger;
import BuzMo.Properties.AppProperties;
import BuzMo.Properties.PropertiesException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.Vector;

import static java.lang.System.exit;


/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Class to create a connection to desired database {SQL PLUS}
 * This class has a dependency on
 * May also need a function to initialize the database and import the base information provided
 *
 */
public class Database {
    private static Database instance = new Database();
    private boolean CSIL = true;

    private Logger log = null;
    private Connection connection = null;
    private AppProperties properties = null;
    private int newMsg = 0;
    private int newGroup = 0;

    private OracleDataSource Osource = null;
    private MysqlDataSource Msource = null;

    public FriendRequests friendRequests;
    public GroupInvites groupInvites;

    public static Database getInstance(){
        return instance;
    }


    //Choose which URL to use depending on where you are running the program
    //private String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
    //private String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";

    //Initialize Database and Establish a connection
    private Database(){

        //Hook up the log to the JDBC Class
        this.log = Logger.getInstance();

        //Read in appropriate username and password
        String username;
        String password;

        try {
            //Read in Properties
            properties = new AppProperties(log, CSIL);
            username = properties.getUsername();
            password = properties.getPassword();
        }catch(PropertiesException e){
            log.Log(e.getMessage());
            e.printStackTrace();
            return;
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
                log.Log("error initializing database");
                exit(1);
                //throw new DatabaseException("Error establishing SQL connection", sql);
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
                log.Log("Error initializing oracle datasource" + e.getMessage());
                e.printStackTrace();

                //throw new DatabaseException(e);
            }

            log.Log("Successfully loaded Oracle Database");
        }

        try {
            CreateDatabase init = new CreateDatabase(log, connection, this);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            this.friendRequests = new FriendRequests(log, connection);
            this.groupInvites = new GroupInvites(log, connection);

        }catch(Exception e){
            log.Log("Error trying initialize database handlers"+e.getMessage());
        }
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

    public int getNewGroup(){
        newGroup++;
        return newGroup -1;
    }

    public Connection getConnection(){
        return this.connection;
    }


}
