package fr.esgi.annuel.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server extends Thread{

	ArrayList clientList;
	static DatagramSocket serverSocket;
	public Server() {
		run();
	}
	
	 public void run(){
	        try {
				serverSocket = new DatagramSocket(1111);

	        byte[] receiveData = new byte[1024];
	        byte[] sendData = new byte[1024];
	        while(true)
	        {	
	        	 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	              serverSocket.receive(receivePacket);
	              String s = new String( receivePacket.getData());
	              System.out.println("Client dit : " + s);
	              InetAddress IPAddress = receivePacket.getAddress();
	              boolean found = false;
	              int port = receivePacket.getPort();
	              sendData = s.getBytes();
	              System.out.println("Server Launched");
	           }
	        } catch (Exception e) {
				e.printStackTrace();
			}

	    }
}
