package fr.esgi.annuel.parser.subclasses;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend
{
	private boolean connected;
	private String username;

	public Friend(JSONObject json) throws JSONException
	{
		this.username = json.getString("displayLogin");
		this.connected = json.getBoolean("connected");
	}

	public String getUsername()
	{
		return this.username;
	}

	public boolean isConnected()
	{
		return this.connected;
	}
}