package fr.esgi.annuel.server;

import java.net.Socket;
import java.util.Arrays;

public class Client implements Runnable
{
    private String address;
    private int port;
	public Client(String address, int port)
	{
		this.address = address;
        this.port = port;
	}

	@Override
	public void run()
	{
        try {
            // Connexion à la box de l'interlocuteur
            Socket s = new Socket(this.address, this.port);
            byte[] b = new byte[256];
            int count;
            System.out.println("Client lancé");
            while ((count = s.getInputStream().read(b)) > 0) {
                System.out.println(new String(Arrays.copyOf(b, count)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}