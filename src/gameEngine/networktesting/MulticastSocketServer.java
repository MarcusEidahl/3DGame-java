package gameEngine.networktesting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class MulticastSocketServer {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws IOException, InterruptedException {
        
    	//thread to transmit data to server
    	new MulticastSocketServerFromClient().start();
    	
    	// Get the address that we are going to connect to.
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        int i = 0;
        
        Player me = new Player("server");
     
        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            while(true) {

                byte[] buffer = new byte[1024];
                buffer = (me.name + Arrays.toString(me.getPlayerData())).getBytes();
                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(buffer,
                        buffer.length, addr, PORT);
                serverSocket.send(msgPacket);
     
                System.out.println("Server sent update to clients " + i + ": " + new String(buffer, 0, buffer.length));
                Thread.sleep(5000);
                i++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
 
}
