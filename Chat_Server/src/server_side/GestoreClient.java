
package server_side;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


/**
 *
 * @author Diego
 */
public class GestoreClient implements Runnable{

    String name, ricevuta;
    int capienza;
    private DataInputStream input; 
    private DataOutputStream output; 
    Server capo;
    private Socket socket; 
    private boolean connesso;
    
    /**
     * 
     * @param s socket
     * @param name nome che usa il client nella chat
     * @param c server centrale
     * @throws IOException 
     */
    public GestoreClient(Socket s, String name, Server c) throws IOException{
        this.socket = s;
        this.name = name;
        this.capo = c;
        this.connesso = true;
    }
    
    @Override
    public void run() {
        try{  
            this.input = new DataInputStream(socket.getInputStream());                   
            this.output = new DataOutputStream(socket.getOutputStream()); 
            int cont = 0;
       
            while(this.connesso = true){
                
                ricevuta = input.readUTF();      
                System.out.println("Echo sul server :"+ricevuta+"\n");        
                /**
                 * La prima volta viene passato il nome
                 * e vienne restituita la lista di nomi di tutti i client connessi
                 */
                if(cont == 0){         
                    this.name = ricevuta;
                    System.out.println("il nome: "+name);
                    output.writeUTF(this.name+"@"+Nomi());     
                }      
                else{             
                    if(ricevuta.equals("logout") || ricevuta == null){                
                        this.connesso = false; 
                        this.output.writeBytes(ricevuta+ "=> server in chiusura "+ '\n');
                        this.socket.close(); 
                    }                           
                    else{
                        smistamento();
                     }
                }      
                cont++;
            }   
            socket.close();
            this.input.close();           
            this.output.close(); 
            System.out.println("Chiuse");
        }catch(IOException e){              
            e.printStackTrace(); 
        } 
    }
    
    /**
     * può classificare il messaggio come broadcast, one to one 
     * o come richiesta di nomi
     * @throws IOException 
     */
    public void smistamento() throws IOException{
       
        StringTokenizer st;                     
        st = new StringTokenizer(ricevuta, "$");
        String recipient = st.nextToken(); 
        String MsgToSend = st.nextToken();
        System.out.print("MsgToSend "+MsgToSend );
        System.out.print("recipient"+recipient );

        if(recipient.equals("all")){                                
            this.capo.InviaBroadcast(MsgToSend, this.name);                        
            System.out.println("broadcast");                           
        }    
        if(converti(recipient) == 1000){
            if(converti(MsgToSend)< Server.ar.size())
                this.capo.InviaAlClient(this.name, Nomi(), " ", false);  
        }
        else{                                     
            this.capo.InviaAlClient(recipient, MsgToSend, this.name, true);                       
            System.out.println("onetoone");                
        }
    }
    
    /**
     * invia la stringa del messaggio al client corrispondente
     * @param frame
     * @throws IOException 
     */
    public synchronized void InviaMessaggio(String frame) throws IOException {  
        output.writeUTF(frame);                         
        System.out.println(this.name+"inviata:"+frame+"\n");
    }
   
    /**
     * il metodo raccoglie tutti i nomi in un unica stringa
     * @return 
     */
    public String Nomi(){
        String buffer = "";
        for (GestoreClient mc : Server.ar)                 
        { 
            if(mc.name != this.name){      
                buffer = buffer+mc.name+"@"; 
            }
        }
        System.out.println(buffer);
        return buffer;
    }
    
    public int converti(String p){
        int in = 0;
        try{
            in = Integer.parseInt(p);
        }catch(Exception e){}
        return in;
    }
    
}
