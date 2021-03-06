package fr.esgi.annuel.parser;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

import static fr.esgi.annuel.parser.JSONParser.*;

public class SimpleJsonParser
{
	private String displayMessage = null;
	private boolean error, status;
	private int httpCode = 200;

	/**
	 * This parser is made to return informations on simple actions: action is done or not. If the action is not done, because of an error, then it's possible to get informations on it
	 *
	 * @param json {JSONObject}: the JSON returned by the server's web service
	 * @param action {String}: the name of the current action
	 * @throws JSONException Can throw exceptions because of illegal arguments
	 **/
	SimpleJsonParser(JSONObject json, String action) throws JSONException
	{
		if (! REGISTRATION.equalsIgnoreCase(action) && ! DISCONNECTION.equalsIgnoreCase(action) && ! SET_LISTENING_PORT.equalsIgnoreCase(action))
			throw new IllegalArgumentException("action parameter must be: \"Registration\" or \"Disconnection\" (not case sensitive)!");
		this.error = json.getBoolean("error");
		if (this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		if(REGISTRATION.equals(action))
			this.status = json.getBoolean("validation");
		else if(DISCONNECTION.equals(action))
			this.status = json.getBoolean("disconnect");
		else
			this.status = json.getBoolean("portRegistered");
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

	public boolean isActionValidated()
	{
		return this.status;
	}

	public boolean isError()
	{
		return this.error;
	}

	public boolean isRegistred()
	{
		return this.status;
	}
}