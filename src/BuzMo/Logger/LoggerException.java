package BuzMo.Logger;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Custom Exception for all errors that could occur in the Logger
 * Package Private to BuzMo.Logger
 */
public class LoggerException extends Exception {
    LoggerException() { super(); }
    LoggerException(String message) { super(message); }
    LoggerException(String message, Throwable e) { super(message, e); }
    LoggerException(Throwable e) { super(e); }
}
