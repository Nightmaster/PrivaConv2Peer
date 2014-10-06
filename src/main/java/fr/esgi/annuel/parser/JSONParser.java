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
	 * Create an instance of {@link AddFriendJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {AddFriendJSONParser} the add friend parser
	 * @throws JSONException
	 **/
	public static AddFriendJsonParser getAddFriendParser(JSONObject json) throws JSONException
	{
		return new AddFriendJsonParser(json);
	}

	/**
	 * Create an instance of {@link AnswerRequestJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {AnswerRequestJSONParser} the answer friend request parser
	 * @throws JSONException
	 **/
	public static AnswerRequestJsonParser getAnswerRequestParser(JSONObject json) throws JSONException
	{
		return new AnswerRequestJsonParser(json);
	}

	/**
	 * Create an instance of {@link ClientIPJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ClientIPJSONParser} the client IP parser
	 * @throws JSONException
	 **/
	public static ClientIPJsonParser getClientIPParser(JSONObject json) throws JSONException
	{
		return new ClientIPJsonParser(json);
	}

	/**
	 * Create an instance of {@link ConnectionJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ConnectionJSONParser} the connection parser
	 * @throws JSONException
	 **/
	public static ConnectionJsonParser getConnectionParser(JSONObject json) throws JSONException
	{
		return new ConnectionJsonParser(json);
	}

	/**
	 * Create an instance of {@link SimpleJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {SimpleJSONParser} the disconnection parser
	 * @throws JSONException
	 **/
	public static SimpleJsonParser getDisconnectionParser(JSONObject json) throws JSONException
	{
		return new SimpleJsonParser(json, DISCONNECTION);
	}

	/**
	 * Create an instance of {@link ModifyProfileJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ModifyProfileJSONParser} the modify profile parser
	 * @throws JSONException
	 **/
	public static ModifyProfileJsonParser getodifyProfileParser(JSONObject json) throws JSONException
	{
		return new ModifyProfileJsonParser(json);
	}

	/**
	 * Create an instance of {@link PrivateKeyJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {PrivateKeyJSONParser} the private key parser
	 * @throws JSONException
	 **/
	public static PrivateKeyJsonParser getPrivateKeyParser(JSONObject json) throws JSONException
	{
		return new PrivateKeyJsonParser(json);
	}

	/**
	 * Create an instance of {@link PublicKeyJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {PublicKeyJSONParser} the public key parser
	 * @throws JSONException
	 **/
	public static PublicKeyJsonParser getPublicKeyParser(JSONObject json) throws JSONException
	{
		return new PublicKeyJsonParser(json);
	}

	/**
	 * Create an instance of {@link SimpleJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {SimpleJSONParser} the registration parser
	 * @throws JSONException
	 **/
	public static SimpleJsonParser getRegistrationParser(JSONObject json) throws JSONException
	{
		return new SimpleJsonParser(json, REGISTRATION);
	}

	/**
	 * Create an instance of {@link SearchJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {SearchJSONParser} the search parser
	 * @throws JSONException
	 **/
	public static SearchJsonParser getSearchParser(JSONObject json) throws JSONException
	{
		return new SearchJsonParser(json);
	}

	/**
	 * Create an instance of {@link ShowProfileJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {ShowProfileJSONParser} the show profile parser
	 * @throws JSONException
	 **/
	public static ShowProfileJsonParser getShowProfileParser(JSONObject json) throws JSONException
	{
		return new ShowProfileJsonParser(json);
	}

	/**
	 * Create an instance of {@link StayAliveJsonParser}
	 *
	 * @param json {JSONObject}: the JSON returned by the web service on this action
	 * @return {StayAliveJSONParser} the stay alive parser
	 * @throws JSONException
	 **/
	public static StayAliveJsonParser getStayAliveParser(JSONObject json) throws JSONException
	{
		return new StayAliveJsonParser(json);
	}
}