
package client_side;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class InviaMessaggio implements Runnable{
    String scn;
    DataOutputStream output = null;
    BufferedReader tastiera;
    InterfacciaClient client;
    
    /**
     * 
     * @param n messaggio da inoltrare al server
     * @param out stream di output
     * @param cl interfaccia grafica
     */
    public InviaMessaggio(String n, DataOutputStream out, InterfacciaClient cl){
        this.output = out;
        this.client = cl;
        this.scn = n;
    }

    @Override
    public synchronized void run() {       
        // read the message to deliver.       
            try{        
                    // write on the output stream 
                    output.writeUTF(scn);           
            } catch (IOException e) { } 
                //catch (InterruptedException ex) { }                
    }   
}
