// This class is used to receive data from the server 
// and update the users client with the data
package gameEngine.networktesting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
//import java.net.UnknownHostException;

public class MulticastSocketClient {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    

    public static void main(String[] args) throws IOException {
    	
    	//thread to transmit data to server
    	new MulticastSocketClientToServer().start();
    	
        // Get the address that we are going to connect to.
        InetAddress address = InetAddress.getByName(INET_ADDR);
        
        
        // Create a buffer of bytes, which will be used to store incoming data
        byte[] buffer = new byte[1024];
        
        // Create a new Multicast socket
        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
            //Joint the Multicast group.
            clientSocket.joinGroup(address);
     
            while (true) {
                // Receive the information and print it.
                DatagramPacket msgPacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(msgPacket);

                String msg = new String(buffer, 0, buffer.length);
                System.out.println("Client received update from server: " + msg);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
   
}
