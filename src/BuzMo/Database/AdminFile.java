package BuzMo.Database;

import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lucas on 12/2/2016.
 */
public class AdminFile extends DatabaseObject{


    public AdminFile(Logger log, Connection connection) throws DatabaseException {
        super(log, connection);
    }

    public void initialize(){
        String sql = "INSERT INTO Admin (instance, message_id, group_id) values("+addTicks("Main")+","+0+","+0+")";
        runSQL(log, connection,sql);
    }


    public int getNextGroup(){
        String sql = "SELECT group_id FROM admin where instance='Main'";
        int response = -1;
        Statement st = runSQL(log,connection, sql);
        try{
            ResultSet rs = st.getResultSet();
            rs.next();
            response=rs.getInt(1);
        } catch (SQLException e) {
            log.Log("Error getting next group in Admin");
        }

        sql = "UPDATE admin set group_id="+response+1+" where instance='Main'";
        return response;
    }

    public int getNextMessage(){
        String sql = "SELECT message_id FROM admin where instance='Main'";
        int response = -1;
        Statement st = runSQL(log,connection, sql);
        try{
            ResultSet rs = st.getResultSet();
            rs.next();
            response=rs.getInt(1);
        } catch (SQLException e) {
            log.Log("Error getting next message in Admin");
        }

        sql = "UPDATE admin set message_id="+response+1+" where instance='Main'";
        return response;
    }


}
