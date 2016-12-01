package BuzMo.Logger;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Formatter class that is used to format messages for logging purposes
 * Format and Format Messages just format strings to time and message
 */
class LogFormatter extends Formatter {
    //Formats messages to Timestamp and Message
    private String format = "[%1$tc]: %2$s\n";
    LogFormatter(){}

    public String formatMessage(LogRecord record)
    {
        return String.format(format, new Date(), record.getMessage());
    }

    public String format(LogRecord record)
    {
        return String.format(format, new Date(), record.getMessage());
    }
}
