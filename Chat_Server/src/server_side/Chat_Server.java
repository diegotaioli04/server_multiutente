/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_side;

import javax.swing.JFrame;

/**
 *
 * @author Diego
 */
public class Chat_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Server tcp = new Server();         
        tcp.connetti();
    }
    
}
