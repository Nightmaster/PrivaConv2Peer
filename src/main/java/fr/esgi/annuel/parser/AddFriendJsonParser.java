package fr.esgi.annuel.parser;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class AddFriendJsonParser
{
	private String displayMessage = null;
	private boolean error, invitationSent = false;
	private int httpCode = 200;

	/**
	 * This class is made to parse the JSON returned by the server's web service when a add friend action is done
	 *
	 * @param json {JSONObject}: the JSON returned by the server's web service
	 * @throws JSONException Can throw exceptions because of illegal arguments
	 **/
	AddFriendJsonParser(JSONObject json) throws JSONException
	{
		this.error = json.getBoolean("error");
		if (this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		else
			this.invitationSent = json.getBoolean("invitationSent");
	}

	public String getDisplayMessage()
	{
		try
		{
			return new String(this.displayMessage.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return this.displayMessage;
		}
	}

	public int getHttpCode()
	{
		return this.httpCode;
	}

	public boolean isError()
	{
		return this.error;
	}

	public boolean isInvitationSent()
	{
		return this.invitationSent;
	}
}