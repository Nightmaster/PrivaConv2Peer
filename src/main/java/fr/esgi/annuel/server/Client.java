package fr.esgi.annuel.server;


import fr.esgi.annuel.gui.ChatView;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;

import java.net.Socket;
import java.util.List;

public class Client implements Runnable
{
    private String address;
    private int port;
    private String user ;
    private List<Message> mess;
	public Client(String address, int port, String user)
	{
		this.address = address;
        this.port = port;
        this.user = user;
	}

	@Override
	public void run()
	{
        while(true) {
            try {
                // Connexion à la box de l'interlocuteur
                Socket s = new Socket(this.address, this.port);
                System.out.println("Client lancé");
                MessageQueue mq = new MessageQueue();

                mess = mq.getAllMessages(this.user);
                for (Message messag : mess) {
                    s.getOutputStream().write(messag.getMessage().getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}