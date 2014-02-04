/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.telnet;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.telnet.TelnetClient;


/**
 *
 * @author mxbailey
 */
public class PortScanner {
    
    TelnetClient client = null;
    
    public void connect(String ip, int port) {
        client = new TelnetClient();
        try {
            client.connect(ip, port);
        } catch (SocketException ex) {
            Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    
    
}
