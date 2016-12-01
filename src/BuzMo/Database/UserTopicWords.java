package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 11/28/2016.
 * CRUD Class for UserTopicWords
 */
public class UserTopicWords extends DatabaseObject {

    public UserTopicWords(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    //Get All Topic Words for User
    public Vector<String> getWords(String userID) throws DatabaseException {

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            log.Log("Couldn't create new statement to get usertopicwords");
            throw new DatabaseException(e);
        }

        Vector<String> response = new Vector<>();
        String sql = "SELECT word FROM usertopicwords WHERE user_id="+addTicks(userID);
        try {
            st.execute(sql);

            //Get results
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                response.add(rs.getString(1));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            log.Log("Invalid SQL: "+sql);
            throw new DatabaseException(e);
        }

        return response;
    }

    //Insert New Topic Words
    public Insert insert(String userID, Vector<String> words) throws DatabaseException {
        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            log.Log("failed to create a new statement to insertTopicWords");
            throw new DatabaseException(e);
        }

        if(words == null){
            return Insert.SUCCESS;
        }

        if(!User.exists(connection, userID)){
            return Insert.NOEXIST_USR;
        }

        Vector<String> associated = getWords(userID);

        String sql;
        for(String word : words){
            if(associated.contains(word)){
                log.Log("error: inserting "+word+" for "+userID+" is redundant");
                return Insert.DUPLICATE;
            }

            sql = "INSERT INTO usertopicwords(user_id,word) VALUES (" +
                    addTicks(userID)+","+addTicks(word)+")";

            try {
                st.execute(sql);
                log.gSQL(sql);

            } catch (SQLException e) {
                log.bSQL(sql);
                throw new DatabaseException(e);
            }
        }

        try {
            st.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }


        return Insert.SUCCESS;
    }
}
