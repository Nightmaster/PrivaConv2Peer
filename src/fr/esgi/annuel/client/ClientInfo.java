package fr.esgi.annuel.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientInfo
{
	public String strName; // Name by which the user logged into the chat room
	InetAddress clientAdress;

	public ClientInfo(String pseudo)
	{
		this.strName = pseudo;
		try
		{
			this.clientAdress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InetAddress getClientAdress()
	{
		return this.clientAdress;
	}

	public String getStrName()
	{
		return this.strName;
	}

	public void setClientAdress(InetAddress clientAdress)
	{
		this.clientAdress = clientAdress;
	}

	public void setStrName(String strName)
	{
		this.strName = strName;
	}

}
