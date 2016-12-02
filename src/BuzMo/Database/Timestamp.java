package BuzMo.Database;

import java.util.Calendar;

/**
 * Created by lucas on 11/28/2016.
 * Class that will compare and store the timestamp in a database
 */


//Date Helper Class
class Date{
    public int day;
    public int month;
    public int year;

    Date(String[] date) throws  DatabaseException{
        if(date.length != 3){
            throw new DatabaseException("Cannot create new Date for Timestamp comparison with "+date[0]);
        }
        day = new Integer(date[0]);
        month = new Integer(date[1]);
        year = new Integer(date[2]);
    }
}

class Time{
    public int hour;
    public int minute;
    public boolean am;

    Time(String[] time) throws DatabaseException{
        if(time.length != 2){
            throw new DatabaseException("Cannot create new Time for Timestamp comparison with " + time[0]);
        }

        this.hour = new Integer(time[0]);
        this.am = (time[1].indexOf("AM") == -1) ? false: true;
        this.minute = new Integer(time[1].substring(0,1));
    }
}

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

    private static int compareDates(Date first, Date second){
        if(first.year < second.year) return -1;
        else if(first.year > second.year) return 1;
        //Otherwise investigate month
        else{
            if(first.month < second.month) return -1;
            else if(first.month > second.month) return 1;
            //Otherwise investigate day
            else{
                if(first.day < second.day) return -1;
                else if(first.day > second.day) return 1;
                else return 0;
            }
        }
    }

    private static int compareTimes(Time first, Time second){
        if(first.am == second.am){
            if(first.hour < second.hour) return -1;
            else if(first.hour > second.hour) return 1;
            else{
                if(first.minute < second.minute) return -1;
                else if(first.minute > second.minute) return 1;
                else return 0;
            }
        }
        else if(first.am) return -1;
        else return 1;
    }


    //Returns <0 if first is earlier Returns >0 if second is earlier
    public static int compareTime(String first, String second) throws DatabaseException{
        String[] f = first.split(",");
        String[] s = second.split(",");

        try{
        Date fdate = new Date(f[0].split("."));
        Date sdate = new Date(s[0].split("."));
        Time ftime = new Time(f[1].split(":"));
        Time stime = new Time(s[1].split(":"));

        int dates = compareDates(fdate, sdate);
        if(dates != 0){
            return dates;
        }

        return compareTimes(ftime, stime);


        }catch(Exception e){
            throw new DatabaseException(e);
        }
    }
}
