package gameEngine.multiplayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.mobs.players.Player;
import gameEngine.entities.mobs.players.TestPlayer;
import gameEngine.entities.projectiles.TestProjectile;
import gameEngine.level.Level;
import gameEngine.level.TestingLevel;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class ClientReceive extends Thread {
	
	
	String myname;
	TestPlayer player;
	TestPlayer playerMe;
	TestingLevel level;
	int i = 0;
	
	public ClientReceive(Player me, String Myname,Player player2, Level l) {
        myname = Myname;
        player = (TestPlayer) player2;
        level = (TestingLevel) l;
        playerMe = (TestPlayer) me;
        
    }



	public void run() {

		try {

			DatagramSocket receiveSocket = new DatagramSocket();

			byte[] inbuffer = new byte[8192];
			InetAddress serverIP = InetAddress.getByName(DatabaseConnection.getHostIP());
			//System.out.println(serverIP);
			int port = 54321 + DatabaseConnection.getLobbyID(myname);
			//System.out.println(myname + " listening on port: " + port);
			//System.out.println(port);
			DatagramPacket packet = new DatagramPacket(inbuffer, inbuffer.length, serverIP,port);
			Scanner Scan = null;
			
			receiveSocket.send(packet);
			while (playerMe.isConnected){//DatabaseConnection.getPlayerConnected(myname).equalsIgnoreCase("Yes")) { // while the player is connected to the server/game

				

				packet = new DatagramPacket(inbuffer, inbuffer.length);
				receiveSocket.receive(packet);

				String received = new String(packet.getData(), 0, packet.getLength());
				//System.out.println("Received packet: " + i);
				i++;
				
				Scan = new Scanner(received);
//				Player p;
//
				while (Scan.hasNext()) {
//
					String currentString = Scan.next();
//
					 if (currentString.contains("Projectile")){
							
							float posX = Scan.nextFloat();
							float posY = Scan.nextFloat();
							float posZ = Scan.nextFloat();
							float rotX = Scan.nextFloat();
							float rotY = Scan.nextFloat();
							float rotZ = Scan.nextFloat();
							System.out.println("got proj: " + posX + " " + posY + " " + posZ + " " + rotX + " " + rotY + " " + rotZ);
							if (currentString.contains(myname)){
								
							} else {
							level.add(new TestProjectile("Projectile" + myname ,RawModel.rawSphere, ModelTexture.white, .5f, posX, posY, posZ, rotX, rotY, rotZ));
							}							
						} else if (currentString.contains("Player")) {
//							
								//Player = Level.getPlayerByName(currentString
								player.setPosition(new Vector3f(Scan.nextFloat(), Scan.nextFloat(), Scan.nextFloat()));
								player.setRotY(Scan.nextFloat());
						
							}
					}
				
				}
				receiveSocket.close();
				Scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 

	}

}
