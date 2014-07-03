package fr.esgi.annuel.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;

public class Client implements Runnable
{
	ClientInfo cli ;
	public Client(ClientInfo c)
	{
		cli = c;
	}

	@Override
	public void run()
	{
		System.out.println("CLIENT STARTED");
		try
		{
			// Création du socket client
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("10.66.126.173");
			byte[] sendData = new byte[1024];

			while (true)
			{
				// Récupération de la liste des messages
				List<Message> listM = MessageQueue.getAllMessages(cli.getLogin());
				if (listM != null && !listM.isEmpty())
					for (Message message : listM)
					{
						StringBuilder sb = new StringBuilder();
						sb.append(cli.getLogin() + " : ");
						sb.append(message.getMessage());
						sendData = sb.toString().getBytes();
						// envoi du packet contenant le message à transmettre
						DatagramPacket sendPacket = new DatagramPacket(
								sendData, sendData.length, IPAddress, 1112);
						clientSocket.send(sendPacket);
					}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
