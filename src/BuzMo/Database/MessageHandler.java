package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 11/28/2016.
 *CRUD Class to handle all CRUD operations on Messages
 */

public class MessageHandler extends DatabaseObject{

    public MessageHandler(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    public static boolean exists(Logger log, Connection connection, Integer messageID) throws DatabaseException {
        String sql = "";

        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            throw new DatabaseException(e);
        }

        try {
            sql = "SELECT COUNT(1) FROM messages WHERE message_id= " + messageID;
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

    //Inserts Public Message with Timestamp
    Insert insertPublicMessage(Integer messageID, String sender, String message, String timestamp, Vector<String> topicWords) throws DatabaseException{
        Vector<String> recipients = CircleOfFriends.getCircleOfFriends(log, connection, sender);
        return insert(messageID, sender, message, timestamp, 1, topicWords, recipients);
    }

    //Inserts Public Message without Timestamp
    public Insert insertPublicMessage(Integer messageID, String sender, String message, Vector<String> topicWords) throws DatabaseException{
        Vector<String> recipients = CircleOfFriends.getCircleOfFriends(log, connection, sender);
        return insert(messageID, sender, message, Timestamp.getTimestamp(), 1, topicWords, recipients);
    }

    //Insert Private Group Message with timestamp and topicWords
    Insert insertPrivateGroupMessage(Integer messageID, String sender, String message, String timestamp, Vector<String> topicWords, String group_name, Integer groupID) throws DatabaseException{
        Vector<String> recipients = ChatGroupMembers.members(log, connection, group_name);
        return insert(messageID, sender, message, timestamp, groupID, topicWords, recipients);
    }

    //Insert Private Group Message with timestamp and no topic words
    Insert insertPrivateGroupMessage(Integer messageID, String sender, String message, String group_name, String timestamp, Integer groupID) throws DatabaseException{
        Vector<String> recipients = ChatGroupMembers.members(log, connection, group_name);
        return insert(messageID, sender, message, timestamp, groupID,null, recipients);
    }

    //Insert Private Group Message with no timestamp and no topic words
    public Insert insertPrivateGroupMessage(Integer messageID, String sender, String message, String group_name, Integer groupID) throws DatabaseException{
        Vector<String> recipients = ChatGroupMembers.members(log, connection, group_name);
        return insert(messageID, sender, message, Timestamp.getTimestamp(), groupID,null, recipients);
    }

    //Insert Private Group Message with no timestamp and topic words
    public Insert insertPrivateGroupMessage(Integer messageID, String sender, String message, Vector<String> topicWords, String group_name, Integer groupID) throws DatabaseException{
        Vector<String> recipients = ChatGroupMembers.members(log, connection, group_name);
        return insert(messageID, sender, message, Timestamp.getTimestamp(), groupID,topicWords, recipients);
    }


    //Insert Private Message Functions
    Insert insertPrivateMsg(Integer messageID, String sender, String message, String timestamp, Vector<String> topicWords, Vector<String> recipients){
        return insert(messageID, sender, message, timestamp, 0, topicWords, recipients);
    }

    //Insert Private message with no topic words
    Insert insertPrivateMsg(Integer messageID, String sender, String message, String timestamp, Vector<String> recipients){
        return insert(messageID,sender,message,timestamp, 0, null, recipients);
    }

    //Insert Private message with no topic words and no timestamp
    public Insert insertPrivateMsg(Integer messageID, String sender, String message, Vector<String> recipients){
        return insert(messageID, sender, message, Timestamp.getTimestamp(), 0, null, recipients);
    }

    //Insert Private Message With no timestamp
    public Insert insertPrivateMsg(Integer messageID, String sender, String message, Vector<String> topicWords, Vector<String> recipients){
        return insert(messageID, sender, message, Timestamp.getTimestamp(), 0, topicWords, recipients);
    }

    public Insert insert(Integer messageID, String sender, String message, String timestamp, int isPublic, Vector<String> topicWords, Vector<String> recipients){
        Statement st;
        try{
            st = connection.createStatement();
        }catch(Exception e ){
            log.Log("Error creating statment for Insert statement");
            return Insert.INVALID;
        }
        String sql = "INSERT INTO Messages(message_id, sender, owner,  message, timestamp, is_public) VALUES (";

        try{
            if(!User.exists(connection, sender)){
                return Insert.NOEXIST_USR;
            }

            if(MessageHandler.exists(log, connection, messageID)){
                log.Log("Duplicate messageID" + messageID);
                return Insert.INVALID;
            }


            //Screen for any 's to be inserted
            message = message.replaceAll("'", "\'\'");
            sql += messageID + "," + addTicks(sender) + "," + addTicks(sender)+ "," + addTicks(message) + "," + addTicks(timestamp) + "," + isPublic+")";

            st.execute(sql);
            log.gSQL(sql);
            st.close();

            //Add all recipients
            Insert addRecipients = MessageReceivers.insertRecipients(log, connection, messageID, recipients);
            if(addRecipients != Insert.SUCCESS){
                log.Log("couldn't add msg recipients "+addRecipients.toString());
                return addRecipients;
            }

            //If it is a private message save a copy with the owner switched
            if(isPublic == 0) {
                messageID++;
                st = connection.createStatement();
                sql = "INSERT INTO Messages(message_id, sender, owner,  message, timestamp, is_public) VALUES (";
                sql += messageID + "," + addTicks(sender) + "," + addTicks(recipients.get(0)) + "," + addTicks(message) + "," + addTicks(timestamp) + "," + isPublic + ")";
                st.execute(sql);
                st.close();
                log.gSQL(sql);

                Vector<String> owner = new Vector<>();
                MessageReceivers.insertRecipients(log, connection, messageID, owner);

                //Add all recipients
                addRecipients = MessageReceivers.insertRecipients(log, connection, messageID, recipients);
                if(addRecipients != Insert.SUCCESS){
                    log.Log("couldn't add msg recipients "+addRecipients.toString());
                    return addRecipients;
                }
            }




            //Make sure public messages have at least one topic words
            if(isPublic==1 && topicWords.isEmpty()) {
                log.Log("cannot have empty topic words on public message" + message);
                return Insert.EMPTY_TOPIC_WORDS;
            }

            //Insert topic words
            MessageTopicWords.insert(log, connection, messageID, topicWords);


        }catch (Exception e){
            log.bSQL(sql);
            log.Log(e.getMessage());
            return Insert.INVALID;
        }

        return Insert.SUCCESS;
    }


    public Vector<String> getPrivateUserMessages(String email) throws DatabaseException {

        //Get Users you have sent a message
        Vector<String> response = new Vector<>();

        String sql = "SELECT message_id FROM messages WHERE is_Public=0 AND owner="+addTicks(email);
        Vector<Integer> messages = new Vector<>();

        try{
            Statement st = connection.createStatement();
            st.execute(sql);
            ResultSet rs = st.getResultSet();

            while(rs.next()){
                messages.add(rs.getInt(1));
            }

            log.gSQL(sql);
            rs.close();

            for(Integer i: messages) {
                sql = "SELECT recipient FROM MessageReceivers WHERE message_id=" + i;
                st.execute(sql);
                log.gSQL(sql);
                rs = st.getResultSet();
                rs.next();

                String current = rs.getString(1);
                if (!response.contains(current))
                    response.add(current);

                rs.close();
            }
            }catch(Exception e){
                throw new DatabaseException(e);
            }

            return response;
        }
    }


        //Get all users who you have sent a private message or they sent you one
    /*public Vector<String> getPrivateUserMessages(String email) throws DatabaseException{
        Vector<String> response = new Vector<>();

        //Get Users you have sent a message
        String sql = "SELECT message_id FROM messages WHERE is_Public=0 AND sender="+addTicks(email);
        Vector<Integer> messages = new Vector<>();

        try{
            Statement st = connection.createStatement();
            st.execute(sql);
            ResultSet rs = st.getResultSet();

            while(rs.next()){
                messages.add(rs.getInt(1));
            }

            log.gSQL(sql);
            rs.close();

            for(Integer i: messages) {
                sql = "SELECT recipient FROM MessageReceivers WHERE message_id=" + i;
                st.execute(sql);
                log.gSQL(sql);
                rs = st.getResultSet();
                rs.next();

                String current = rs.getString(1);
                if(!response.contains(current))
                    response.add(current);

                rs.close();
            }


            //Collect all messages you have received from other users
            sql = "SELECT message_id FROM MessageReceivers WHERE recipient="+addTicks(email);
            st.execute(sql);
            log.gSQL(sql);

            rs = st.getResultSet();
            messages = new Vector<>();

            while(rs.next()){
                messages.add(rs.getInt(1));
            }
            rs.close();

            for(Integer i: messages) {
                //Find all Users that sent the messages to you
                sql = "SELECT sender FROM messages WHERE message_id=" +i;
                st.execute(sql);
                log.gSQL(sql);

                rs = st.getResultSet();
                rs.next();

                //If it is a unique user, add it to the response
                String current = rs.getString(1);
                if(!response.contains(current))
                    response.add(current);                rs.close();
            }
            st.close();

        }catch(Exception e){
            log.bSQL(sql);
            throw new DatabaseException(e);
        }*/
