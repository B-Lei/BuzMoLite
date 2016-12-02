package BuzMo.Database;

import BuzMo.Logger.Logger;
import com.sun.deploy.config.VerboseDefaultConfig;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 12/2/2016.
 */
public class MyCircle extends DatabaseObject {

    public MyCircle(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);


    }

    public Vector<Message> getMessages(String user){
        Vector<Message> response = new Vector<>();

        try{
            Vector<String> friends = CircleOfFriends.getCircleOfFriends(log, connection, user);
            for(String s: friends){
                String sql = "SELECT message_id, message, sender from messages where is_public=1 AND sender="+user;
                Statement st = runSQL(log, connection,sql);
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    response.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }


}
