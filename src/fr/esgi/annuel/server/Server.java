package fr.esgi.annuel.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;

public class Server implements Runnable
{

	static DatagramSocket serverSocket;

	@Override
	public void run()
	{
		try
		{
			serverSocket = new DatagramSocket(1111);
			byte[] receiveData = new byte[1024];

			while (true)
			{
				Message mess = new Message();
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String s = new String(receivePacket.getData()).substring(0, receivePacket.getLength());
				mess.setMessage(s);
				String[] splits = s.split(" :");
				int iterator = 0;
				String pseudo = "";
				for(String part : splits)
				{
					if (iterator == 0)
					{
						pseudo = part;
						iterator++;
					}
				}
				iterator = 0;
				mess.setReceiveDate(new Date());
				System.out.println(pseudo);
				MessageQueue.addMessageToPrint(pseudo, mess);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
