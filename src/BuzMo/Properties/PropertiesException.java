package BuzMo.Properties;

/**
 * Created by LucasLopilato on 11/25/2016.
 * Basic Exception Extension for Application Properties Exceptions
 */
public class PropertiesException extends Exception {
    PropertiesException() { super(); }
    PropertiesException(String message) { super(message); }
    PropertiesException(String message, Throwable e) { super(message, e); }
    PropertiesException(Throwable e) { super(e); }
}
