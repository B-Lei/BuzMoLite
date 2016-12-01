package BuzMo.Database;

import java.util.Calendar;

/**
 * Created by lucas on 11/28/2016.
 * Class that will compare and store the timestamp in a database
 */
public class Timestamp {

    public static String getTimestamp() {
        String response;
        Calendar c = Calendar.getInstance();
        response = c.MONTH + "." + c.DAY_OF_WEEK + "." + c.YEAR + ", " +
                c.HOUR + ":" + c.MINUTE;
        if (c.AM == 1) {
            response += " AM";
        } else {
            response += " PM";
        }

        return response;
    }

    //Returns <0 if first is earlier Returns >0 if second is earlier
    public static int compareTime(String first, String second){
        String[] f = first.split(",");
        String[] s = second.split(",");
        String[] fDate = f[0].split(".");
        String[] sDate = s[0].split(".");

        //Compare Years
        //switch()

        //Stub
        return 0;


    }
}
