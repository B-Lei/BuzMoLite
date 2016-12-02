package BuzMo.Database;

import BuzMo.Logger.Logger;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 12/2/2016.
 */
public class FriendRequests extends DatabaseObject{

    public FriendRequests(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    public void newRequest(String user1, String user2){
        String sql = "INSERT INTO MyCircleInvites(host, guest) VALUES(" +
                addTicks(user1)+","+addTicks(user2)+")";
        runSQL(log, connection, sql);
    }

    public Vector<String> pendingRequests(String guest){
        Vector<String> response = new Vector<>();

        String sql = "SELECT host FROM MyCircleInvites WHERE guest="+guest;
        Statement st = runSQL(log, connection,sql);
        try {
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                response.add(rs.getString(1));
            }
        }catch (Exception e){
            log.bSQL(sql);
            log.Log(e.getMessage());
        }
        return response;
    }
}
