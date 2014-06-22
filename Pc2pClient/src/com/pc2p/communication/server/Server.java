package com.pc2p.communication.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

import com.p2pc.window.MainWindow;
import com.p2pc.window.ServerWindow;
import com.pc2p.communication.message.Message;
import com.pc2p.communication.message.MessageQueue;

public class Server implements Runnable {
	
	static DatagramSocket serverSocket;


	@Override
	 public void run(){
		System.out.println("SERVER STARTED");
	        try {
				serverSocket = new DatagramSocket(1111);

	        byte[] receiveData = new byte[1024];
	        byte[] sendData = new byte[1024];
	        while(true)
	        {	
	        	 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	              serverSocket.receive(receivePacket);
	              String s = new String( receivePacket.getData()).substring(0, receivePacket.getLength());
	              System.out.println("Client dit : " + s);
	              Message message = new Message();
	              message.setMessage(s);
	              message.setReceiveDate(new Date());
	              MessageQueue.addMessage("Stephen",message);
	              
	              
//	              InetAddress IPAddress = receivePacket.getAddress();
//	              boolean found = false;
//	              int port = receivePacket.getPort();
//	              sendData = s.getBytes();
//	              System.out.println("Server Launched");
	           }
	        } catch (Exception e) {
				e.printStackTrace();
			}

	    }
	
	
}
