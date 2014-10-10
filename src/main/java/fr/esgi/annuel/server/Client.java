package fr.esgi.annuel.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable
{
	private final Socket socket;
	public Client(String address, int port)
	{
		Socket soc;
		try
		{
			soc = new Socket(address, port);
		}
		catch (IOException ignored)
		{
			soc = null;
		}
		this.socket = soc;
		if(null == this.socket)
			return;
	}

	@Override
	public void run()
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			String st;
			while ((st = scanner.nextLine()).trim().length() > 0)
				this.socket.getOutputStream().write(st.getBytes());
			scanner.close();
		}
		catch (Exception ignored) {}
	}
}