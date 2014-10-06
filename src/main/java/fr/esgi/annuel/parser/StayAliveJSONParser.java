package fr.esgi.annuel.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import fr.esgi.annuel.parser.subclasses.Friend;
import fr.esgi.annuel.parser.subclasses.UserInfos;

public class StayAliveJsonParser
{
	private String[] askFriendship;
	private String displayMessage = null;
	private boolean error, statusOk;
	private Friend[] fl;
	private int httpCode = 200, validity;
	private UserInfos userInfos;

	/**
	 * This class is made to parse the JSON returned by the server's web service when a stay alive action is performed
	 *
	 * @param json {JSONObject}: the JSON returned by the server's web service
	 * @throws JSONException Can throw exceptions because of illegal arguments
	 **/
	StayAliveJsonParser(JSONObject json) throws JSONException
	{
		JSONArray ask = null, fList = null;
		this.error = json.getBoolean("error");
		if (true == this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		else
		{
			ask = json.getJSONArray("askFriend");
			fList = json.getJSONArray("friends");
			this.askFriendship = new String[ask.length()];
			for (int i = 0; i < ask.length(); i++ )
				this.askFriendship[i] = ask.get(i).toString();
			this.userInfos = new UserInfos(json.getJSONObject("user"));
			this.fl = new Friend[fList.length()];
			for (int i = 0; i < fList.length(); i++ )
				this.fl[i] = new Friend(fList.getJSONObject(i));
		}
		this.statusOk = json.getBoolean("stayAlive");
		this.validity = json.getInt("validity");
	}

	public String[] getAskList()
	{
		return this.askFriendship;
	}

	public String getDisplayMessage()
	{
		return this.displayMessage;
	}

	public Friend[] getFriendList()
	{
		return this.fl;
	}

	public int getHttpCode()
	{
		return this.httpCode;
	}

	public UserInfos getUserInfos()
	{
		return this.userInfos;
	}

	public int getValidity()
	{
		return this.validity;
	}

	public boolean isError()
	{
		return this.error;
	}

	public boolean isStatusOk()
	{
		return this.statusOk;
	}
}