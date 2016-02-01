package gameEngine.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.mobs.players.TestPlayer;
import gameEngine.level.TestingLevel;

public class ServerSend extends Thread {
	
	int port;
	TestPlayer Player;
	TestingLevel Level;
	String myName;

	public ServerSend(String myname, int Port, TestPlayer p, TestingLevel l){
        
        port = Port;
        Player = p;
        Level = l;
        myName = myname;
    }

	public void run() {
		
		//System.out.println("started a thread on port: " + port);
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port);
			//System.out.println(port);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		byte[] buffer = new byte[8192];

		// receive request
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			socket.receive(packet);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (Player.isConnected){//DatabaseConnection.getPlayerConnected(myName).equalsIgnoreCase("Yes")) {
			try {
				

				// figure out response
				String Projs = "";
				String dString = null;
				
				for (int i = 0; i < Level.getNewProj().size(); i++) {

					try {
						Projs += Level.getNewProj().get(i).getName() + " " + Level.getNewProj().get(i).getPosition().x + " "
								+ Level.getNewProj().get(i).getPosition().y + " "
								+ Level.getNewProj().get(i).getPosition().z + " " + Level.getNewProj().get(i).getRotX()
								+ " " + Level.getNewProj().get(i).getRotY() + " " + Level.getNewProj().get(i).getRotZ()
								+ " ";
					} catch (NullPointerException e) {
						Projs = "";
						System.out.println("NPE here");
						//e.printStackTrace();
					}
					//System.out.println(Projs);

				}
				
				Level.clearNewProj();
				
				if (Player.isRemoved()){
					dString = Player.getName() + " " + Player.getPosition().getX() + " "
							+ -500.0 + " " + Player.getPosition().getZ() + " " + Player.getRotY();
					
					Player.addDeath();
					Player.setPosition(new Vector3f((int)(Math.random()*750), 30, (int)(Math.random()*-750)));
					Player.setHealth(100);
					Player.setIsRemoved(false);
					DatabaseConnection.setPlayerKills(Player.getName(), Player.getDeaths());

					System.out.println(Player.getDeaths());

				} else {
				dString = Player.getName() + " " + Player.getPosition().getX() + " " + Player.getPosition().getY() + " " + Player.getPosition().getZ() + " " + Player.getRotY() + " " + Projs;
				}
				//System.out.println(dString);

				buffer = dString.getBytes();

				// send the response to the client at "address" and "port"
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buffer, buffer.length, address, port);
				socket.send(packet);
				
				buffer = new byte[8192];
				
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		socket.close();
	}
}
