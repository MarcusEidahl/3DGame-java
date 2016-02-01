package gameEngine.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.mobs.players.TestPlayer;
import gameEngine.entities.projectiles.Projectile;
import gameEngine.level.TestingLevel;

public class ClientSend extends Thread {

	TestPlayer Player;
	TestingLevel Level;
	int port;
	//ArrayList<Projectile> newProjectiles = new ArrayList<Projectile>();
	int j = 0;

	public ClientSend(int Port, TestPlayer p, TestingLevel l) {

		port = Port;
		Player = p;
		Level = l;
	}

	public void run() {

		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(port);
			// System.out.println(port);
		} catch (Exception e1) {
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
		
		while (Player.isConnected){//DatabaseConnection.getPlayerConnected(Player.getName()).equalsIgnoreCase("Yes")) {
			try {
				

				// figure out response
				String Projs = "";
				String dString;
				
				if (Player.isRemoved()){
					dString = "";
				} 

				for (int i = 0; i < Level.getNewProj().size(); i++) {

					try {
						Projs += Level.getNewProj().get(i).getName() + " " + Level.getNewProj().get(i).getPosition().x + " "
								+ Level.getNewProj().get(i).getPosition().y + " "
								+ Level.getNewProj().get(i).getPosition().z + " " + Level.getNewProj().get(i).getRotX()
								+ " " + Level.getNewProj().get(i).getRotY() + " " + Level.getNewProj().get(i).getRotZ()
								+ " ";
						//j++;
					} catch (NullPointerException e) {
						Projs = "";
						System.out.println("NPE here");
						e.printStackTrace();
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
					dString = Player.getName() + " " + Player.getPosition().getX() + " "
						+ Player.getPosition().getY() + " " + Player.getPosition().getZ() + " " + Player.getRotY() + " " + Projs;
				}

				
				
				//System.out.println(dString);

				buffer = dString.getBytes();

				// send the response to the client at "address" and "port"
				InetAddress address = packet.getAddress();
				int port2 = packet.getPort();
				packet = new DatagramPacket(buffer, buffer.length, address, port2);
				socket.send(packet);
				//System.out.println("Sent packet: " + i);
				buffer = new byte[8192];
				
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		socket.close();
	}

}
