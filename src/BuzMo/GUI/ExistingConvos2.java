package BuzMo.GUI;

import BuzMo.Logger.Logger;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Created by Ben on 11/30/2016.
 * This displays a list of all friends [who have at least one message to you].
 * You can click each friend element to send a message to them.
 */
public class ExistingConvos2 extends View{


    public ExistingConvos2(Logger log, Connection connection) {
        super(log, connection);
        o.writeLine();
        o.empty();
        o.write("Your Messages");
        o.empty();
        o.writeLine();



    }
}