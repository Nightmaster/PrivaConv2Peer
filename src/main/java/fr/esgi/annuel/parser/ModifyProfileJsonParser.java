package fr.esgi.annuel.parser;

import java.io.UnsupportedEncodingException;
import fr.esgi.annuel.parser.subclasses.ChangedValues;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyProfileJsonParser
{
	private String displayMessage = null;
	private boolean error, profileModified;
	private int httpCode = 200;
	private ChangedValues newValues;

	ModifyProfileJsonParser(JSONObject json) throws JSONException
	{
		this.error = json.getBoolean("error");
		if (this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		else
		{
			this.profileModified = json.getBoolean("modifications");
			this.newValues = new ChangedValues(json.getJSONObject("newValues"));
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

	public ChangedValues getNewValues()
	{
		return this.newValues;
	}

	public boolean isError()
	{
		return this.error;
	}

	public boolean isProfileModified()
	{
		return this.profileModified;
	}
}