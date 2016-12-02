package BuzMo.GUI;

import BuzMo.Database.*;
import BuzMo.Logger.Logger;

import java.sql.Connection;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by lucas on 12/1/2016.
 */
public class MyCircle extends View {
    Vector<Message> messages;
    MessageHandler mes;
    Vector<String> circle;
    private BuzMo.Database.MyCircle myCircle;

    public MyCircle(Scanner scanner, Logger log, Connection connection, String yourUsername) {
        super(scanner, log, connection, yourUsername);

        try {
            myCircle = new BuzMo.Database.MyCircle(log,connection);
            mes = new MessageHandler(log, connection);
            circle = CircleOfFriends.getCircleOfFriends(log, connection, yourUsername);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        String in="";
        while(!in.contentEquals("exit")){
            try {
                in = scanner.next();
                display();
                Integer choice = new Integer(in);
                String response = "";
                switch (choice) {
                    case (1):
                        o.write("Write new message: ");
                        response = scanner.next();
                        Vector<String> topics = new Vector<>();
                        o.write("insert topic words until you type exit");
                        while(!in.contentEquals("exit")){
                            in = scanner.next();
                            topics.add(in);
                        }
                        AdminFile ad = new AdminFile(log, connection);
                        if(topics.size() == 0){
                            o.write("cannot have empty topic words");
                            break;
                        }
                        mes.insertPublicMessage(ad.getNextMessage(),yourUsername, in,topics );
                        break;
                    case(2):
                        o.write("Insert messageID to delete");
                        response= scanner.next();
                        choice = new Integer(response);
                        if(!mes.exists(log, connection,choice)){
                            o.write("Message doesn't exist");
                        }








                }
            }catch(Exception e){log.Log(e.getMessage());}
        }


    }


    private void display(){
        o.write("MyCircle Messages");
        messages = myCircle.getMessages(yourUsername);

        for(Message m: messages){
            o.write(m.id+ m.sender+": "+m.message);
        }


        o.write("Options: ");
        o.write("1 Create new post");
        o.write("2 delete post");

    }

}
