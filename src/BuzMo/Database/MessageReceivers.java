package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 11/28/2016.
 * CRUD Class for the MessageReceivers Table
 */
public class MessageReceivers extends DatabaseObject {

    public MessageReceivers(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }


    //Gets a list of all recipients for a specific message
    public Vector<String> recipients(int messageID) throws DatabaseException{
        Vector<String> response = new Vector<>();

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        //Check if the user exists
        if(!Message.exists(log, connection,messageID)){
            throw new DatabaseException("Cannot find recipients for non existent message: "+messageID);
        }
        String sql = "SELECT recipient FROM messagereceivers C WHERE C.message_id="+messageID;
        try {
            st.execute(sql);
            log.gSQL(sql);
            ResultSet rs = st.getResultSet();

            while(rs.next()){
                response.add(rs.getString("recipient"));
            }
            st.close();

            log.Log("Recipients retrieved for "+messageID);
            return response;
        } catch (SQLException e) {
            log.bSQL(sql);
            throw new DatabaseException(e);
        }
    }


    //Attempt to insert recipients for a message
    //Function intended as a helper
    public static Insert insertRecipients(Logger log, Connection connection, int messageID, Vector<String> userList) throws DatabaseException{
        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        if(userList == null){
            return Insert.SUCCESS;
        }

        if(!Message.exists(log, connection, messageID)){
            return Insert.NOEXIST_MSG;
        }

        //Check if the message exists
        //Associate each user with a messageID
        for(String s: userList){
            String sql = "INSERT INTO messagereceivers (message_id, recipient) VALUES (";

            if(!User.exists(connection, s)){
                return Insert.NOEXIST_USR;
            }

            sql += messageID + "," + addTicks(s) + ')';

            try {
                st.execute(sql);
                log.gSQL(sql);
            } catch (SQLException e) {
                log.bSQL(sql);
                throw new DatabaseException(e);
            }
        }

        try{
            st.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return Insert.SUCCESS;
    }

}
