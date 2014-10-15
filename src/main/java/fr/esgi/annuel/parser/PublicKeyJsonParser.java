package fr.esgi.annuel.parser;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class PublicKeyJsonParser
{
	private String displayMessage = null,
			username = null,
			publicKey = null;
	private boolean error;
	private int httpCode = 200;

	/**
	 * This class is made to parse the JSON returned by the server's web service when a private key demand is done
	 *
	 * @param json {JSONObject}: the JSON returned by the server's web service
	 * @throws JSONException Can throw exceptions because of illegal arguments
	 **/
	PublicKeyJsonParser(JSONObject json) throws JSONException
	{
		this.error = json.getBoolean("error");
		if (this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		else
		{
			try
			{
				this.username = json.getJSONObject("user").getString("username");
				this.publicKey = json.getJSONObject("user").getString("pubKey").replace("\n", "");
			}
			catch (JSONException ignored) {}
		}
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

	public String getPublicKey()
	{
		try
		{
			return new String(this.publicKey.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return this.publicKey;
		}
	}

	public String getUsername()
	{
		return username;
	}
}