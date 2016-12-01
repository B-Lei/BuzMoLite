package BuzMo.Properties;

import BuzMo.Logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Lucas Lopilato on 11/25/2016.
 * Class used to retrieve system properties
 */
public class AppProperties {
    private Properties prop = new Properties();
    private Logger log;
    private boolean CSIL;

    public AppProperties(Logger log, boolean CSIL) throws PropertiesException{
        //Hook up Properties to log
        this.log = log;
        this.CSIL = CSIL;

        //Read in the config.properties file
        try{
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
            if(in == null){
                throw new PropertiesException("config.properties could not be located");
            }

            //Read in the properties stream
            prop.load(in);
            log.Log("Read in Properties File");

            //Close the input stream
            in.close();
            log.Log("Successfully Initialized AppProperties");
        }
        catch(IOException io){
            //Throws the error if the Properties file is incorrect
            throw new PropertiesException("Error occurred reading in config.properties", io);
        }
    }


    public String getUsername() throws PropertiesException{
        //Look up the Username Property
        String response = CSIL ? prop.getProperty("xe_u", "") : prop.getProperty("db_u", "");

        //If no username value found, throw an error
        if(response.compareTo("") == 0){
            throw new PropertiesException("Could not find username");
        }
        //Otherwise return the response
        else {
            log.Log("Read in Database username");
            return response;
        }
    }

    public String getPassword() throws PropertiesException {
        //Look up the password Property
        String response = CSIL ? prop.getProperty("xe_p", "") : prop.getProperty("db_p", "");

        //If no username value found, throw an error
        if (response.compareTo("") == 0) {
            throw new PropertiesException("Could not find password");
        }
        //Otherwise return the response
        else {
            log.Log("Read in Database password");
            return response;
        }
    }

}
