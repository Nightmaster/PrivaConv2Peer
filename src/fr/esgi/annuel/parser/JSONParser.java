package fr.esgi.annuel.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The factory for all JSON Parsers
 *
 * @author Gael B.
 */
public class JSONParser
{
	private static final String DISCONNECTION = "Disconnection", REGISTRATION = "Registration";

	/**
	 * Create an instance of {@link AddFriendJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {AddFriendJSONParser} the add friend parser
	 * @throws JSONException
	 **/
	public static AddFriendJSONParser getAddFriendParser(JSONObject json) throws JSONException
	{
		return new AddFriendJSONParser(json);
	}

	/**
	 * Create an instance of {@link AnswerRequestJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {AnswerRequestJSONParser} the answer friend request parser
	 * @throws JSONException
	 **/
	public static AnswerRequestJSONParser getAnswerRequestParser(JSONObject json) throws JSONException
	{
		return new AnswerRequestJSONParser(json);
	}

	/**
	 * Create an instance of {@link ClientIPJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ClientIPJSONParser} the client IP parser
	 * @throws JSONException
	 **/
	public static ClientIPJSONParser getClientIPParser(JSONObject json) throws JSONException
	{
		return new ClientIPJSONParser(json);
	}

	/**
	 * Create an instance of {@link ConnectionJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ConnectionJSONParser} the connection parser
	 * @throws JSONException
	 **/
	public static ConnectionJSONParser getConnectionParser(JSONObject json) throws JSONException
	{
		return new ConnectionJSONParser(json);
	}

	/**
	 * Create an instance of {@link SimpleJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {SimpleJSONParser} the disconnection parser
	 * @throws JSONException
	 **/
	public static SimpleJSONParser getDisconnectionParser(JSONObject json) throws JSONException
	{
		return new SimpleJSONParser(json, DISCONNECTION);
	}

	/**
	 * Create an instance of {@link ModifyProfileJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ModifyProfileJSONParser} the modify profile parser
	 * @throws JSONException
	 **/
	public static ModifyProfileJSONParser getodifyProfileParser(JSONObject json) throws JSONException
	{
		return new ModifyProfileJSONParser(json);
	}

	/**
	 * Create an instance of {@link PrivateKeyJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {PrivateKeyJSONParser} the private key parser
	 * @throws JSONException
	 **/
	public static PrivateKeyJSONParser getPrivateKeyParser(JSONObject json) throws JSONException
	{
		return new PrivateKeyJSONParser(json);
	}

	/**
	 * Create an instance of {@link PublicKeyJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {PublicKeyJSONParser} the public key parser
	 * @throws JSONException
	 **/
	public static PublicKeyJSONParser getPublicKeyParser(JSONObject json) throws JSONException
	{
		return new PublicKeyJSONParser(json);
	}

	/**
	 * Create an instance of {@link SimpleJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {SimpleJSONParser} the registration parser
	 * @throws JSONException
	 **/
	public static SimpleJSONParser getRegistrationParser(JSONObject json) throws JSONException
	{
		return new SimpleJSONParser(json, REGISTRATION);
	}

	/**
	 * Create an instance of {@link SearchJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {SearchJSONParser} the search parser
	 * @throws JSONException
	 **/
	public static SearchJSONParser getSearchParser(JSONObject json) throws JSONException
	{
		return new SearchJSONParser(json);
	}

	/**
	 * Create an instance of {@link ShowProfileJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ShowProfileJSONParser} the show profile parser
	 * @throws JSONException
	 **/
	public static ShowProfileJSONParser getShowProfileParser(JSONObject json) throws JSONException
	{
		return new ShowProfileJSONParser(json);
	}

	/**
	 * Create an instance of {@link StayAliveJSONParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {StayAliveJSONParser} the stay alive parser
	 * @throws JSONException
	 **/
	public static StayAliveJSONParser getStayAliveParser(JSONObject json) throws JSONException
	{
		return new StayAliveJSONParser(json);
	}
}