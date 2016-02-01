package gameEngine.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.mobs.players.Player;
import gameEngine.entities.mobs.players.TestPlayer;
import gameEngine.entities.projectiles.Projectile;
import gameEngine.entities.projectiles.TestProjectile;
import gameEngine.level.Level;
import gameEngine.level.TestingLevel;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class ServerReceive extends Thread{
	
	String playerName;
	TestPlayer player;
	TestPlayer playerMe;
	TestingLevel level;
	String myName;

	public ServerReceive(Player me, String myname, String Player, Player p, Level l){
      playerName = Player;
      player = (TestPlayer) p;
      level = (TestingLevel) l;
      myName = myname;
      playerMe = (TestPlayer) me;

    }

	public void run() {

		try {

			DatagramSocket receiveSocket = new DatagramSocket();

			byte[] inbuffer = new byte[8192];
			//System.out.println("looking for Player: " + playerName);
			InetAddress clientIP = InetAddress.getByName(DatabaseConnection.getPlayerIP(playerName));
			int port = 54321 - DatabaseConnection.getLobbyID(playerName);
			DatagramPacket packet = new DatagramPacket(inbuffer, inbuffer.length, clientIP, port);
			//System.out.println(port);
			Scanner Scan = null;
			receiveSocket.send(packet);
			
			while (playerMe.isConnected){//DatabaseConnection.getPlayerConnected(myName).equalsIgnoreCase("Yes")) { // while the player is connected to the server/game

				

				packet = new DatagramPacket(inbuffer, inbuffer.length);
				receiveSocket.receive(packet);

				String received = new String(packet.getData(), 0, packet.getLength());
		        //System.out.println(received);
				
				Scan = new Scanner(received);
//				Player p;
//
				while (Scan.hasNext()) {
//
					String currentString = Scan.next();
//
					if (currentString.contains("Projectile")){
							//System.out.println("got proj");
							float posX = Scan.nextFloat();
							float posY = Scan.nextFloat();
							float posZ = Scan.nextFloat();
							float rotX = Scan.nextFloat();
							float rotY = Scan.nextFloat();
							float rotZ = Scan.nextFloat();
							System.out.println("got proj: " + posX + " " + posY + " " + posZ + " " + rotX + " " + rotY + " " + rotZ);
							level.add(new TestProjectile(currentString, RawModel.rawSphere, ModelTexture.white, .5f, posX, posY, posZ, rotX, rotY, rotZ));
							level.addNewProj(new TestProjectile(currentString, RawModel.rawSphere, ModelTexture.white, .5f, posX, posY, posZ, rotX, rotY, rotZ));
							
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
		// receiveSocket.close();

	}
}
