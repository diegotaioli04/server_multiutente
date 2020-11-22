
package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Diego
 */
public class Server {
    String ricevuta;
    Socket s; 
    ServerSocket server = null;
    public static ArrayList<GestoreClient> ar = new ArrayList(); 
    
    public Server(){ }
    
    public void connetti(){ 
        try{
            int i = 0;
            server = new ServerSocket(6789);  
            while (true) {        
            
                System.out.print("\n"+"1 Server in attesa"+"\n");
                s = server.accept();
                System.out.print("1 Server socket "+s);
                 
                GestoreClient mtch = new GestoreClient(s,"client"+i, this);
                Thread t = new Thread(mtch); 
                // add this client to active clients list            
                ar.add(mtch); 
                // start the thread. 
                 t.start();
                 i++;
            }     
        }catch(Exception e){
            System.exit(1);
        }
    
    }
    
    /**
     * 
     * @param destinatario del messaggio
     * @param msg stringa da inviare
     * @param nome del mittente
     * @param n flag di riconoscimento false = invio nomi
     * true = messaggio normale
     * @throws IOException 
     */
    public void InviaAlClient(String destinatario, String msg, String nome, boolean n) throws IOException{
        System.out.println("sono fritto");
        System.out.println("destinatario: "+destinatario);
        for (GestoreClient mc : Server.ar)  
        {          
            System.out.println(mc.name+" = "+destinatario);
            if(mc.name.equals(destinatario)){                 
                if(n == true){       
                    mc.InviaMessaggio(nome+": "+msg);
                }
                else{
                    mc.InviaMessaggio(msg);
                }
            }
        }
    }
    
    /**
     * manda il messaggio a tutti tranne che al mittente
     * @param msg stringa da inviare
     * @param nome nome del mittente a cui non bisogna mandarlo
     * @throws IOException 
     */
    public void InviaBroadcast(String msg, String nome) throws IOException{
        for (GestoreClient mc : Server.ar)  
        {                 
            if(!mc.name.equals(nome)){
                mc.InviaMessaggio(nome+": "+msg);  
            }
            System.out.println("nome client"+ mc.name);      
        }
    }
}
