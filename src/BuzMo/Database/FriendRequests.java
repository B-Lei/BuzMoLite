package BuzMo.Database;

import BuzMo.GUI.FriendConvo;
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
    private CircleOfFriends circleOfFriends;

    public FriendRequests(Logger log, Connection connection, CircleOfFriends circleOfFriends) throws DatabaseException {
        super(log, connection);
        this.circleOfFriends = circleOfFriends;
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

    public void accept(String host, String guest){
        String sql = "DROP FROM MyCircleInvites WHERE host="+addTicks(host)+" AND guest="+addTicks(guest);
        runSQL(log, connection, sql);
        try {
            this.circleOfFriends.addFriends(host, guest);
        }catch (Exception e ){
            log.Log(e.getMessage());
        }
    }
}
