
package client_side;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import javax.swing.JFrame;


/**
 *
 * @author Diego
 */
public class Client_Menu {
    int ServerPort = 6789;
    String nome ="localhost", messaggio = null, utente, msg = null;
    InterfacciaClient client;
    DataInputStream input;
    DataOutputStream output;
    InviaMessaggio send; 
    RiceveMessaggio get;
    BufferedReader tastiera;
    Thread arrivo;
    Thread partenza;

    
    Socket s;
    public Client_Menu() {
        client = new InterfacciaClient(this);
        client.setVisible(true);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * invio del nome al server 
     * @param mes stringa da inviare
     * @param client interfaccia grafica
     */
    public synchronized void Connetti(String mes, InterfacciaClient client) {
        try{        
            // establish the connection 
            this.messaggio = mes;
            s = new Socket(nome, ServerPort);       
            output = new DataOutputStream(s.getOutputStream()); 
            input = new DataInputStream(s.getInputStream());        
            this.client.box.addItem("all");
            invia(messaggio);
            ricevi();
            utente = this.client.jTextField1.getText();
            
        }catch(UnknownHostException e){}
        catch(IOException e){}
    }
 
    /**
     * istanzia l'oggetto InviaMessaggio e ne avvia il thread
     * @param mes strinnga da inviare
     */
    public void invia(String mes){        
        send = new InviaMessaggio(mes, output, client);           
        partenza = new Thread(send);                 
        partenza.start();
    }
    
    /**
     * istanzia l'oggetto RiceviMessaggio e ne avvia il thread
     */
    public void ricevi(){           
        get = new RiceveMessaggio(input, client);     
        arrivo = new Thread(get);          
        arrivo.start();      
    }
   
}
