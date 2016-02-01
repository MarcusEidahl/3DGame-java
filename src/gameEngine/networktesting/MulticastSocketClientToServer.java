// This class is used to send client data to the server 
// so it can then be sent back out to all clients so they can update

package gameEngine.networktesting;

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastSocketClientToServer extends Thread {
	
	private int delay = 10000;
	Player me;
	DatagramSocket socket = null;
	final static String INET_ADDR = "224.0.1.3";
    final static int PORT = 8889;

	public MulticastSocketClientToServer() throws IOException {
		super("MulticastSocketClientToServer");
		me = new Player("Client");
		socket = new DatagramSocket();
	}
	
	public MulticastSocketClientToServer(Player p) throws IOException {
		super("MulticastSocketClientToServer");
		me = p;
		socket = new DatagramSocket();
	}
	
	public void run(){
		me.connect();
		int i = 0;
		while(me.isConnected){
			
			try{
				byte[] buffer = new byte[1024];
				
				String pktmsg = me.name + Arrays.toString(me.getPlayerData());
				buffer = pktmsg.getBytes();
				InetAddress addr = InetAddress.getByName(INET_ADDR);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, PORT);
				socket.send(packet);
	
				System.out.println("Client sent update to server " + i + ": " + new String(buffer, 0, buffer.length));
	            Thread.sleep(delay);
	                i++;
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
