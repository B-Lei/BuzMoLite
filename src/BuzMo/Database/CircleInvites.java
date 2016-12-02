package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 12/2/2016.
 */
public class CircleInvites extends DatabaseObject{
    public CircleInvites(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    public void newInvite(String host, String invitee, String group){
        String sql = "INSERT INTO MyCircleInvites(host, guest) VALUES(" +
                addTicks(host)+","+addTicks(invitee)+","+addTicks(group)+")";
        runSQL(log, connection, sql);
    }

    public Vector<CircleInvite> getInvites(String invitee){
        Vector<CircleInvite> response = new Vector<>();

        String sql = "SELECT host, group_name FROM MyCircleInvites WHERE guest="+invitee;
        Statement st = runSQL(log, connection, sql);
        try {
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                response.add(new CircleInvite(rs.getString(1), rs.getString(2)));
            }
        }catch (Exception e){
            log.bSQL(sql);
            log.Log(e.getMessage());
        }
        return response;
    }

    public void accept(CircleInvite invite, String guest){
        try{
        CircleOfFriends circle = new CircleOfFriends(log,connection);
        String sql = "DROP FROM MyCircleInvites WHERE host="+addTicks(invite.host)+" AND guest"+addTicks(guest);
        runSQL(log, connection, sql);
            circle.addFriends(guest, invite.host);
        }catch (Exception e ){
            e.printStackTrace();
            log.Log(e.getMessage());
        }
    }




}
