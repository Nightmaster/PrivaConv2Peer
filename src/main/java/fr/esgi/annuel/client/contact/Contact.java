package fr.esgi.annuel.client.contact;

public class Contact
{

	private String pseudo;
	private boolean connected;

	public Contact(String pseudo, boolean connected)
	{
		this.pseudo = pseudo;
		this.connected = connected;
	}

	public String getPseudo()
	{
		return this.pseudo;
	}

	public boolean isConnected()
	{
		return this.connected;
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	// contact info

}
