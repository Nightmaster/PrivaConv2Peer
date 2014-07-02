package fr.esgi.annuel.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientInfo
{
	public String login; // Name by which the user logged into the chat room
	public String email ;
	public String lastname ;
	public String firstname ; 
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InetAddress getClientAdress()
	{
		return this.clientAdress;
	}

	public String getLogin()
	{
		return this.login;
	}

	public void setClientAdress(InetAddress clientAdress)
	{
		this.clientAdress = clientAdress;
	}

	public void setLogin(String strName)
	{
		this.login = strName;
	}

}
