package fr.esgi.annuel.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import fr.esgi.annuel.parser.subclasses.UserInfos;

/**
 * Parse search JSON
 *
 * @author Nightmaster
 **/
public class SearchJSONParser
{
	private boolean error;
	private int httpCode = 200;
	private String displayMessage = null;
	private UserInfos[] profiles = null;

	/**
	* This class is made to parse the JSON returned by the server's web service when a search is performed
	*
	* @param json {JSONObject}: the JSON returned by the server's web service
	* @throws JSONException Can throw exceptions because of illegal arguments
	**/
	public SearchJSONParser(JSONObject json) throws JSONException
	{
		JSONArray profiles = json.getJSONArray("profiles");
		this.profiles = new UserInfos[profiles.length()];
		this.error = json.getBoolean("error");
		if (true == this.error)
		{
			this.displayMessage = json.getString("displayMessage");
			this.httpCode = json.getInt("httpErrorCode");
		}
		for (int i = 0; i < profiles.length(); i++ )
			this.profiles[i] = new UserInfos(profiles.getJSONObject(i));
	}

	public String getDisplayMessage()
	{
		return this.displayMessage;
	}

	public int getHttpCode()
	{
		return this.httpCode;
	}

	public UserInfos[] getProfiles()
	{
		return this.profiles;
	}

	public boolean isError()
	{
		return this.error;
	}
}
