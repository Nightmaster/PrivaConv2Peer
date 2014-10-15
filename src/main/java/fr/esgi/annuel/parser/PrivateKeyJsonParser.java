package fr.esgi.annuel.parser;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class PrivateKeyJsonParser
{
	private String displayMessage = null, privateKey;
	private boolean error;
	private int httpCode = 200;

	/**
	 * This class is made to parse the JSON returned by the server's web service when the private key is asked to the server
	 *
	 * @param json {JSONObject}: the JSON returned by the server's web service
	 * @throws JSONException Can throw exceptions because of illegal arguments
	 **/
	PrivateKeyJsonParser(JSONObject json) throws JSONException
	{
		this.error = json.getBoolean("error");
		if (this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		this.privateKey = json.getString("prKey").replace("\n", "");
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

	public String getPrivateKey()
	{
		try
		{
			return new String(this.privateKey.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return this.privateKey;
		}
	}

	public boolean isError()
	{
		return this.error;
	}
}