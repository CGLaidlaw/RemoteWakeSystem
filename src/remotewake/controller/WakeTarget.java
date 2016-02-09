package remotewake.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WakeTarget {
	private static final int port = 9;

	public WakeTarget (String IP, String MAC) {
		try {
			byte[] macAddress = MAC.getBytes();
			InetAddress address = InetAddress.getByName(IP);
			DatagramPacket packet = new DatagramPacket(macAddress, macAddress.length, address, port);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			socket.close();
			
			System.out.println("Packet Sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
