package fr.esgi.annuel.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import com.google.common.base.Strings;
import fr.esgi.annuel.parser.ConnectionJsonParser;
import fr.esgi.annuel.parser.ModifyProfileJsonParser;
import fr.esgi.annuel.parser.subclasses.ChangedValues;

/**
 * Class ClientInfo
 * Elle contiendra les informations de l'utilisateur
 */
public class ClientInfo
{
	InetAddress clientAdress;
	private String email;
	private String firstName;
	private String lastName;
	private String login; // Name by which the user logged into the chat room
	private List<Friend> friendList;

	public ClientInfo()
	{
		try
		{
			this.clientAdress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public ClientInfo(String login, String email, String lastName, String firstName, List<Friend> friendList)
	{
		this.login = login;
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.friendList = friendList;
	}

	public ClientInfo(ConnectionJsonParser connectionJson)
	{
		this(connectionJson.getUserInfos().getLogin(),
			 connectionJson.getUserInfos().getEmail(),
			 connectionJson.getUserInfos().getName(),
			 connectionJson.getUserInfos().getFirstName(),
			 Arrays.asList(connectionJson.getFriendList()));
	}

	public InetAddress getClientAdress()
	{
		return this.clientAdress;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public String getLogin()
	{
		return this.login;
	}

	/**
	* Set the new values from the server answer
	*
	* @param updatedInformation {{@link fr.esgi.annuel.parser.ModifyProfileJsonParser}} the parsed JSON from the ModifyProfile request
	**/
	public void updateInformation(ModifyProfileJsonParser updatedInformation)
	{
		ChangedValues newValues = updatedInformation.getNewValues();
		this.login = Strings.isNullOrEmpty(newValues.getLogin()) ? this.login : newValues.getLogin();
		this.email = Strings.isNullOrEmpty(newValues.getEmail()) ? this.email : newValues.getEmail();
		this.lastName = Strings.isNullOrEmpty(newValues.getEmail()) ? this.lastName : newValues.getEmail();
		this.firstName = Strings.isNullOrEmpty(newValues.getFirstName()) ? this.firstName : newValues.getFirstName();
	}

	public List<Friend> getFriendList()
	{
		return friendList;
	}

	public void setFriendList(List<Friend> friendList)
	{
		this.friendList = friendList;
	}
}