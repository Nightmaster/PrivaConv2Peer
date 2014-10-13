package fr.esgi.annuel.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			ServerSocket server = new ServerSocket(6991);
            System.out.println("Server Launched");
            while (true)
			{
				Socket socket = server.accept();
				System.out.println(socket.getInetAddress() + " " + socket.getPort());
				//FIXME ajouter la couche de décryptage
				byte[] b = new byte[256]; //FIXME voir comment gérer des messages très long sans splitter
				int count;
				while ((count = socket.getInputStream().read(b)) > 0)
					System.out.println(new String(Arrays.copyOf(b, count)));
				socket.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}