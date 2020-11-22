
package client_side;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Diego
 */
public class RiceveMessaggio implements Runnable{

    DataInputStream input = null;
    InterfacciaClient client = null;
    String msg;
    int index = 0;
    
    /**
     * 
     * @param dis inputstream
     * @param c interfaccia grafica
     */
    public RiceveMessaggio(DataInputStream dis, InterfacciaClient c){  
        this.input = dis;
        this.client = c;     
    }
    
    @Override
    public  synchronized void run() { 
  
        String buffer;                    
            try { 
                while(true){
                    // read the message sent to this client       
                    buffer = input.readUTF();
                    System.out.println("\n"+"risposta.. "+buffer);                                
                    if(index == 0){
                        inserisciNomi(buffer, true);
                    }
                    else{
                        decriptare(buffer);
                    }
                    index++;
                }
            } catch (IOException e) {   } 
    }
    
    /**
     * metodo per capire se ha inviato una lista di nomi o un messaggio
     * @param n stringa da esaminare
     */
     public void decriptare(String n){
         if(n.contains(":")){
             msg = n +'\n';
             this.client.jTextPane1.setText(client.jTextPane1.getText()+msg);
             msg = "";
         }
         else{      
             inserisciNomi(n, false);
         }
    }
            
     /**
      * divisione delle strinnga contenente i nomi con @ = token
      * @param m stringa da dividere
      * @param p flag riconoscimento 
      */
   public void inserisciNomi(String m, boolean p){
       boolean primo = p;
       int cont = 0;
       String recipient;
        this.client.box.removeAllItems();
        this.client.box.addItem("all");
        StringTokenizer st;                                 
        st = new StringTokenizer(m, "@");
            
        while(st.hasMoreElements()){
                
            recipient = st.nextElement().toString();
            if((cont == 0)&&(primo == true)){
                this.client.jTextField1.setText(recipient);                            
                this.client.jTextField1.setEditable(false);
            }            
            else{            
                this.client.box.addItem(recipient);   
            }             
            cont++;
        }
   }
}
    

