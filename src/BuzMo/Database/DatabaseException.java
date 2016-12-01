package BuzMo.Database;

/**
 * Created by Lucas Lopilato on 11/26/2016.
 * Default exception wrapper for Database Errors
 */
public class DatabaseException extends Exception {
    DatabaseException() { super(); }
    DatabaseException(String message) { super(message); }
    DatabaseException(String message, Throwable e) { super(message, e); }
    DatabaseException(Throwable e) { super(e); }
}
