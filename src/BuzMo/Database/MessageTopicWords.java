package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 11/28/2016.
 * CRUD Class for the MessageTopicWords table
 */
public class MessageTopicWords extends DatabaseObject{

    public MessageTopicWords(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    //Get All Topic Words for User
    public static Vector<String> getWords(Logger log, Connection connection, int messageID) throws DatabaseException {
        Vector<String> response = new Vector<>();
        String sql = "SELECT word FROM messagetopicwords WHERE message_id="+messageID;

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        try {
            st.execute(sql);
            log.gSQL(sql);

            //Get results
            ResultSet rs = st.getResultSet();
            st.close();
            while(rs.next()){
                response.add(rs.getString(1));
            }
        } catch (SQLException e) {
            log.bSQL(sql);
            throw new DatabaseException(e);
        }

        return response;
    }

    //Insert New Topic Words
    public static Insert insert(Logger log, Connection connection, int messageID, Vector<String> words) throws DatabaseException {
        if(words == null){
            log.Log("no topic words added for message "+messageID);
            return Insert.SUCCESS;
        }

        if(!Message.exists(log, connection, messageID)){
            log.Log("cannot add topic words, message "+messageID+" doesn't exist");
            return Insert.NOEXIST_MSG;
        }

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        Vector<String> associated = getWords(log, connection, messageID);

        String sql;
        for(String word : words){
            if(associated.contains(word)){
                log.Log("error: inserting "+word+" for "+messageID+" is redundant");
                return Insert.DUPLICATE;
            }

            sql = "INSERT INTO messagetopicwords(message_id,word) VALUES (" +
                    messageID+","+addTicks(word)+")";

            try {
                st.execute(sql);
                log.gSQL(sql);
                st.close();
            } catch (SQLException e) {
                log.bSQL(sql);
                throw new DatabaseException(e);
            }
        }

        return Insert.SUCCESS;
    }
}
