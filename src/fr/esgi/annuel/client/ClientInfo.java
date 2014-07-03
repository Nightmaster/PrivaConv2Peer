package fr.esgi.annuel.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientInfo
{
	public String email;
	public String firstname;
	public String lastname;
	public String login; // Name by which the user logged into the chat room
	InetAddress clientAdress;

	public ClientInfo(String pseudo)
	{
		this.login = pseudo;
		try
		{
			this.clientAdress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public InetAddress getClientAdress()
	{
		return this.clientAdress;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public String getLogin()
	{
		return this.login;
	}

	public void setClientAdress(InetAddress clientAdress)
	{
		this.clientAdress = clientAdress;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public void setLogin(String strName)
	{
		this.login = strName;
	}

}
