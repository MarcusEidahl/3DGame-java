package gameEngine.networktesting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketServerFromClient extends Thread {

	DatagramSocket socket = null;
	final static String INET_ADDR = "224.0.1.3";
	final static int PORT = 8889;

	public MulticastSocketServerFromClient() throws IOException {
		super("MulticastSocketServerFromClient");

		socket = new DatagramSocket();
	}

	public void run() {

		byte[] buffer = new byte[1024];

		try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {

			InetAddress address = InetAddress.getByName(INET_ADDR);
			clientSocket.joinGroup(address);

			while (true) {

				DatagramPacket msgPacket = new DatagramPacket(buffer, buffer.length);
				clientSocket.receive(msgPacket);

				String msg = new String(buffer, 0, buffer.length);
				System.out.println("Server received update from Client: " + msg);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
