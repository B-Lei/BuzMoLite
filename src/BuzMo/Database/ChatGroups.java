package BuzMo.Database;

import BuzMo.Logger.Logger;
import jdk.nashorn.internal.ir.CatchNode;

import javax.xml.crypto.Data;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 11/29/2016.
 * CRUD Class for ChatGroups table
 */
public class ChatGroups extends DatabaseObject{
    public ChatGroups(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    public static boolean exists(Logger log, Connection connection, String name) throws DatabaseException{
        String sql = "";

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        try {
            sql = "SELECT COUNT(1) FROM chatgroups WHERE group_name = " + addTicks(name);
            st.execute(sql);
            log.gSQL(sql);

            ResultSet res = st.getResultSet();
            res.next();

            Boolean response = res.getInt(1) != 0;

            res.close();
            st.close();


            return response;

        } catch (Exception e) {
            log.bSQL(sql);
            throw new DatabaseException(e);
        }
    }

    public Insert insertGroupAndUsers(String owner, String name, int msgID, int duration, Vector<String> members) throws DatabaseException{
        Insert result = insertGroup(owner, name, msgID, duration);
        if(result !=Insert.SUCCESS) {
            log.Log("Failed to insert new group" + result.toString());
            return result;
        }

        ChatGroupMembers mem = new ChatGroupMembers(log, connection);
        return mem.insertMembers(name, members);
    }

    //Insert Group into the table
    public Insert insertGroup(String owner, String name, int msg_id, int duration) throws DatabaseException{
        Vector<String> own = new Vector<>();
        own.add(owner);

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        //Check if the owner is a valid name
        if(!User.exists(connection, owner)){
            log.Log("Chatgroup "+name+" has no valid owner");
            return Insert.NOEXIST_USR;
        }

        if(ChatGroups.exists(log, connection, name)){
            log.Log("Chatgroup "+name+" already exists");
            return Insert.DUPLICATE;
        }

        String sql = "INSERT INTO chatgroups (owner, group_name, message_id, duration) VALUES (" +
                addTicks(owner) + "," + addTicks(name) + ","+ msg_id+ "," + duration + ")";

        try {
            st.execute(sql);
            log.gSQL(sql);
            st.close();

            //Insert the owner of the chatgroup to the members if the owner is not already there
            if(!ChatGroupMembers.members(log,connection,name).contains(owner)) {

                ChatGroupMembers m = new ChatGroupMembers(log, connection);
                Insert result = m.insertMembers(name, own);
                if (result != Insert.SUCCESS) {
                    log.Log("Inserting owner of group " + name + " resulted in error " + result.toString());
                    return result;
                }
            }

        } catch (SQLException e) {
            log.bSQL(sql);
            throw new DatabaseException(e);
        }

        return Insert.SUCCESS;
    }

    public Integer getMsgId(Logger log,Connection connection,String name) throws DatabaseException{
        String sql = "SELECT message_id FROM  ChatGroups WHERE group_name="+addTicks(name);

        Integer response = -1;
        try{
            Statement st = connection.createStatement();
            st.execute(sql);
            log.gSQL(sql);

            ResultSet res = st.getResultSet();
            res.next();
            response = res.getInt(1);
            log.Log("MessageID of "+name);

            res.close();
            st.close();

        } catch(Exception e){
            log.bSQL(sql);
            throw new DatabaseException(e);
        }

        return response;
    }

    public void addUser(String user, String group){
        String sql = "INSERT INTO ChatGroupMembers (member, group_name) VALUES ("+addTicks(user)+","+addTicks(group)+")";
        runSQL(log, connection, sql);
    }

    public static Insert changeDuration(Logger log, Connection connection, String name, int duration) throws DatabaseException{
        if(!ChatGroups.exists(log, connection, name)){
            return Insert.NOEXIST_USR;
        }

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        String sql = "UPDATE chatgroups SET duration="+duration+" WHERE group_name="+name;
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

}
