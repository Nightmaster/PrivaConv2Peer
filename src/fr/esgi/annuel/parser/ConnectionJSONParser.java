package fr.esgi.annuel.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionJSONParser
{
	boolean error, connection;
	int validity;
	String[] askFriendship;
	User user;
	Friend[] fl;

	public ConnectionJSONParser(JSONObject json) throws JSONException
	{
		JSONArray ask = json.getJSONArray("askFriend"), fList = json.getJSONArray("friends");
		this.error = json.getBoolean("error");
		this.connection = json.getBoolean("connection");
		this.askFriendship = new String[ask.length()];
		for (int i = 0; i < ask.length(); i++ )
			this.askFriendship[i] = ask.get(i).toString();
		this.user = new User(json.getJSONObject("user"));
		this.fl = new Friend[fList.length()];
		for (int i = 0; i < fList.length(); i++ )
			this.fl[i] = new Friend(fList.getJSONObject(i));
	}
}

class Friend
{
	String username;
	boolean connected;

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

class User
{
	public String login, email, name, firstname;

	public User(JSONObject json) throws JSONException
	{
		this.email = json.getString("email");
		this.email = json.getString("email");
		this.name = json.getString("name");
		this.firstname = json.getString("firstname");
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public String getLogin()
	{
		return this.login;
	}

	public String getName()
	{
		return this.name;
	}
}
