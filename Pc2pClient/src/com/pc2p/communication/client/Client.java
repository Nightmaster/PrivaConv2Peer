package com.pc2p.communication.client;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client implements Runnable {
	
	 public Client() {
	      }


		@Override
		public void run() {
			System.out.println("CLIENT STARTED");
			try{
				BufferedReader inFromUser =
	                 new BufferedReader(new InputStreamReader(System.in));
	              DatagramSocket clientSocket = new DatagramSocket();
	              InetAddress IPAddress = InetAddress.getByName("localhost");
	              byte[] sendData = new byte[1024];
	              byte[] receiveData = new byte[1024];
	              
	              while(true){
		              System.out.print("Ecrire un message: ");
		              String s = inFromUser.readLine();
		              sendData = s.getBytes();
		              DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1111);
		              clientSocket.send(sendPacket);
	              }
			}catch(Exception e){
				e.printStackTrace();
			}

	      }

}
