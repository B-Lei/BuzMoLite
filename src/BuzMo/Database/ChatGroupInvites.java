package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by lucas on 12/2/2016.
 */
public class ChatGroupInvites extends DatabaseObject{
    private ChatGroups chatGroups;
    public ChatGroupInvites(Logger log, Connection connection, ChatGroups chatGroups) throws DatabaseException {
        super(log, connection);
        this.chatGroups = chatGroups;
    }

    public Vector<ChatGroupInvite> pendingInvites(String guest){
        Vector<ChatGroupInvite> response = new Vector<>();

        String sql = "SELECT host, group_name FROM ChatGroupInvites WHERE guest="+guest;
        Statement st = runSQL(log, connection,sql);
        try {
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                response.add(new ChatGroupInvite(rs.getString(1), rs.getString(2)));
            }
        }catch (Exception e){
            log.bSQL(sql);
            log.Log(e.getMessage());
        }
        return response;
    }

    public void accept(ChatGroupInvite invite, String guest){
        String sql = "DROP FROM MyCircleInvites WHERE host="+addTicks(invite.host)+" AND guest="+addTicks(guest);
        sql+= " AND group_name="+addTicks(invite.groupName);
        runSQL(log, connection, sql);
        try {
            this.chatGroups.addUser(guest, invite.groupName);
        }catch (Exception e ){
            log.Log(e.getMessage());
        }
    }


}

