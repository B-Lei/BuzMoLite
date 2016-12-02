package BuzMo.Database;

import BuzMo.Logger.Logger;

import javax.swing.plaf.nimbus.State;
import java.security.acl.Group;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by lucas on 12/2/2016.
 */
public class GroupInvites extends DatabaseObject{
    public GroupInvites(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    public void newInvite(String host, String invitee, String group){
        String sql = "INSERT INTO MyCircleInvites(host, guest, group_name) VALUES(" +
                addTicks(host)+","+addTicks(invitee)+","+addTicks(group)+")";
        runSQL(log, connection, sql);
    }

    public Vector<GroupInvite> getInvites(String invitee){
        Vector<GroupInvite> response = new Vector<>();

        String sql = "SELECT host, group_name FROM ChatGroupInvites WHERE guest="+invitee;
        Statement st = runSQL(log, connection, sql);
        try {
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                response.add(new GroupInvite(rs.getString(1), rs.getString(2)));
            }
        }catch (Exception e){
            log.bSQL(sql);
            log.Log(e.getMessage());
        }
        return response;
    }


}
