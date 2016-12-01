package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lucas on 11/28/2016.
 * CRUD Class for User table
 */
public class User extends DatabaseObject{
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String screenname = null;

    //Base Information for a user
    public User(Logger log, Connection connection, String name , String email, String password, String phoneNumber) throws DatabaseException {
        super(log, connection);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;

        //Check all constraints before trying to enter
        if (name.length() > 20) {
            throw new DatabaseException("Cannot have userID > 20 chars");
        }

        if (phoneNumber.length() > 10) {
            throw new DatabaseException("Cannot have phone number > 10 chars");
        }

        if (email.length() > 20) {
            throw new DatabaseException("Cannot have email > 20 chars");
        }

        if (password.length() < 2 || password.length() > 10) {
            throw new DatabaseException("password " + password + " is not valid");
        }
    }

    //Initialize User with optional screenname
    public User(Logger log, Connection connection, String name, String email, String password, String phoneNumber,   String screenname) throws DatabaseException {
        super(log, connection);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.screenname = screenname;

            //Check all constraints before trying to enter
            if(name.length() > 20){
                throw new DatabaseException("Cannot have userID > 20 chars");
            }

            if(phoneNumber.length() > 10){
                throw new DatabaseException("Cannot have phone number > 10 chars");
            }

            if(email.length() > 20){
                throw new DatabaseException("Cannot have email > 20 chars");
            }

            if(password.length() < 2 || password.length() > 10){
                throw new DatabaseException("password "+password+" is not valid");
            }

            if(screenname.length() > 20){
                throw new DatabaseException("Cannot have screenname > 20 chars");
            }

            log.Log("created new User "+email);
    }

    //CRUD Functions

    public boolean exists() throws DatabaseException{
        return exists(this.connection, this.email);
    }

    public static boolean exists(User user) throws DatabaseException{
        return exists(user.connection, user.email);
    }

    //Check if user already exists
    public static boolean exists(Connection connection, String email) throws DatabaseException{
        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        String sql;
        try {
            sql = "SELECT COUNT(1) FROM Users WHERE email_address = " + addTicks(email);
            st.execute(sql);

            ResultSet res = st.getResultSet();
            res.next();

            Boolean response = res.getInt(1) != 0;

            res.close();
            st.close();

            return response;

        } catch (Exception e) {
            System.out.println("Error in finding number of users");
            throw new DatabaseException(e);
        }
    }

    //Insert
    public static Insert insert(Connection connection, User user, Boolean isManager) throws DatabaseException {
        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            user.log.Log("Couldnt create new statement for user insert.");
            throw new DatabaseException(e);
        }

        if(user.exists()){
            user.log.Log("user "+user.email+ " already exists");
            return Insert.DUPLICATE;
            //throw new DatabaseException("User already exists");
        }
        String sql = "INSERT INTO users (name,phone_number,email_address,password,screenname,isManager) " +
                "VALUES ("+addTicks(user.name)+","+addTicks(user.phoneNumber)+","+addTicks(user.email)+","+
                addTicks(user.password)+",";

                //Insert screenname on selector
                sql += (user.screenname == null) ? "NULL" : addTicks(user.screenname);
                sql += ",";
                sql += (isManager) ? 1 : 0;
                sql += ")";

        try {
            st.execute(sql);
            user.log.gSQL(sql);
            st.close();
        } catch (SQLException e) {
            user.log.Log("sql exception inserting new user "+ e.getMessage());
            throw new DatabaseException(e);
        }
        user.log.Log("successfully executed "+sql);
        return Insert.SUCCESS;

    }

    //makeManager
    public static Insert makeManager(Logger log, Connection connection, String email) throws DatabaseException{
        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        if(!User.exists(connection, email)){
            return Insert.NOEXIST_USR;
        }

        String sql = "UPDATE users SET isManager=1 WHERE email_address="+addTicks(email);
        try {
            st.execute(sql);
            log.gSQL(sql);
            st.close();
        } catch (SQLException e) {
            log.bSQL(sql);
            throw new DatabaseException(e);
        }

        return Insert.SUCCESS;
    }

    //makeManager
    public static Insert makeUser(Logger log, Connection connection, String email) throws DatabaseException{
        if(!User.exists(connection, email)){
            return Insert.NOEXIST_USR;
        }

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        String sql = "UPDATE users SET isManager=0 WHERE email_address="+addTicks(email);
        try {
            st.execute(sql);
            log.gSQL(sql);
            st.close();
        } catch (SQLException e) {
            log.bSQL(sql);
            throw new DatabaseException(e);
        }

        return Insert.SUCCESS;
    }

    public static String getPassword(Logger log, Connection connection, String email) throws DatabaseException{
        Statement st = getSt(connection, log);

        String sql = "SELECT password FROM USERS WHERE email_address="+email;

        String response = "";

        try{
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            rs.next();
            response = rs.getString(1);

            rs.close();
            st.close();

            log.gSQL(sql);

        } catch (SQLException e) {
            log.bSQL(sql);
            log.Log("error retrieving user password");
            throw new DatabaseException(e);
        }
        return response;

    }





}
