package fr.esgi.annuel.ctrl;

import java.io.IOException;
import java.net.*;
import fr.esgi.annuel.constants.Parameters;
import fr.esgi.annuel.constants.ServerAction;

/**
* @author Gaël B.
**/
@SuppressWarnings(value = "unused")
public class HttpRequest
{
	private final String serverAddress, serverPort;
	private URLConnection connection = null;
	private CookieManager manager = new CookieManager();
	private CookieStore store;
	private MasterController controller;

	public HttpRequest(MasterController controller)
	{
		this.controller = controller;
		serverAddress = this.controller.getProperties().getProperty("server.address");
		serverPort = this.controller.getProperties().getProperty("server.port");
		CookieHandler.setDefault(this.manager);
		this.store = this.manager.getCookieStore();
	}

	/**
	* Initiate a connection to the server to make a register action
	*
	* @param username  the username used to identify the client
	* @param email     the email associated to this account
	* @param hashPw    the MD5 hash of the password for this account
	* @param firstname the client's firstname
	* @param name      the client lastname
	* @param hashPwKey the MD5 hash of password for the private key for this account
	* @param length    the length of the created private key
	* @throws IOException
	**/
	public final void sendRegisterRequest(String username, String email, String hashPw, String firstname, String name, String hashPwKey, int length) throws IOException
	{
		initConnection(ServerAction.REGISTER,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + email,
					   Parameters.PASSWORD.getParameterValue() + "=" + hashPw,
					   Parameters.FIRSTNAME.getParameterValue() + "=" + firstname,
					   Parameters.LASTNAME.getParameterValue() + "=" + name,
					   Parameters.PASSWORD_KEY.getParameterValue() + "=" + hashPwKey,
					   Parameters.KEY_LENGTH.getParameterValue() + "=" + Integer.toString(length));
	}

	/**
	* Initiate a connection to the server to make a connection (log on) action
	*
	* @param username the username used to log on the client (set it to <code>null</code> if email is used)
	* @param email    the email used to log on the client (set it to <code>null</code> if username is used)
	* @param hashPw   the MD5 hash of the password to log on the client
	* @throws IOException
	**/
	public final void sendConnectionRequest(String username, String email, String hashPw) throws IOException
	{
		initConnection(ServerAction.CONNECT,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + email,
					   Parameters.PASSWORD.getParameterValue() + "=" + hashPw);
	}

	/**
	* Initiate a connection to the server to make a stay alive action
	*
	* @throws IOException
	**/
	public final void sendStayAliveRequest() throws IOException
	{
		initConnection(ServerAction.STAY_ALIVE);
	}

	/**
	* Initiate a connection to the server to make a disconnection (log out) action
	*
	* @throws IOException
	**/
	public final void sendDisonnectRequest() throws IOException
	{
		initConnection(ServerAction.DISCONNECT);
	}

	/**
	* Initiate a connection to the server to make a update on identifications data action
	*
	* @param username  the new username (set it to <code>null</code> if unchanged)
	* @param email     the new email (set it to <code>null</code> if unchanged)
	* @param hashPw    the MD5 hash of the new password (set it to <code>null</code> if unchanged)
	* @param firstname the new firstname (set it to <code>null</code> if unchanged)
	* @param name      the new lastname (set it to <code>null</code> if unchanged)
	* @throws IOException
	**/
	public final void sendUpdateInfosRequest(String username, String email, String hashPw, String firstname, String name) throws IOException
	{
		initConnection(ServerAction.MODIFY_PROFILE,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + email,
					   Parameters.PASSWORD.getParameterValue() + "=" + hashPw,
					   Parameters.FIRSTNAME.getParameterValue() + "=" + firstname,
					   Parameters.LASTNAME.getParameterValue() + "=" + name);
	}

	/**
	* Initiate a connection to the server to make a search action. Set empty values to <code>null</code>
	*
	* @param username  the searched username
	* @param email     the searched email
	* @param firstname the searched firstname
	* @param name      the searched lastname
	* @throws IOException
	**/
	public final void sendSearchRequest(String username, String email, String firstname, String name) throws IOException
	{
		initConnection(ServerAction.SEARCH,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + email,
					   Parameters.FIRSTNAME.getParameterValue() + "=" + firstname,
					   Parameters.LASTNAME.getParameterValue() + "=" + name);
	}

	/**
	* Initiate a connection to the server to make a friendship answer to a user
	*
	* @param username the username of the asked user (set it to <code>null</code> if email is used)
	* @param email    the email of the asked user (set it to <code>null</code> if username is used)
	* @throws IOException
	**/
	public final void sendAddFriendRequest(String username, String email) throws IOException
	{
		initConnection(ServerAction.ADD_FRIEND,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + email);
	}

	/**
	* Initiate a connection to the server to make a answer to a friendship request
	*
	* @param username         the username of the user that asked for friendship status
	* @param validationStatus <code>true</code> if request is accepted, <code>false</code> otherwise
	* @throws IOException
	**/
	public final void sendAnswerReqRequest(String username, boolean validationStatus) throws IOException
	{
		initConnection(ServerAction.ANSWER_REQUEST,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + String.valueOf(validationStatus));
	}

	/**
	* Initiate a connection to the server to make a demand to get the private key for the logged on user
	*
	* @param username the username of logged on user
	* @throws IOException
	**/
	public final void sendGetPrivateKeyReqRequest(String username) throws IOException
	{
		initConnection(ServerAction.GET_PRIVATE_KEY, username);
	}

	/**
	* Initiate a connection to the server to make a demand to get the public key of a given user
	*
	* @param username the username of the user that we want to get the public key
	* @throws IOException
	**/
	public final void sendGetPublicKeyReqRequest(String username) throws IOException
	{
		initConnection(ServerAction.GET_PUBLIC_KEY, username);
	}

	/**
	* Initiate a connection to the server to make a demand to get the IP of a given user
	*
	* @param username the username of the user that we want to get the IP
	* @throws IOException
	**/
	public final void sendGetClientIpRequest(String username) throws IOException
	{
		initConnection(ServerAction.GET_CLIENT_IP, username);
	}

	/**
	* Initiate a connection to the server to make a demand to get the public data of a given user
	*
	* @param username the username of the user that we want to get the public data
	* @throws IOException
	**/
	public final void sendShowProfileRequest(String username) throws IOException
	{
		initConnection(ServerAction.SHOW_PROFILE, username);
	}

	public URLConnection getConnection()
	{
		return this.connection;
	}

	private final void initConnection(ServerAction action, String... parameters) throws IOException
	{
		if (action.getAddressFor().contains("%s"))
			System.out.println(this.serverAddress + ":" + this.serverPort + "/" + String.format(action.getAddressFor(), parameters[0]));
			//this.connection = new URL(this.serverAddress + ":" + this.serverPort + "/" + String.format(action.getAddressFor(), parameters[0]));
		else
		{
			StringBuilder sb = new StringBuilder(512);
			boolean first = true;
			for (String param : parameters)
				if (null != param)
				{
					if (first)
					{
						sb.append('?');
						first = false;
					}
					else
						sb.append('&');
					sb.append(param);
				}
			System.out.println(this.serverAddress + ":" + this.serverPort + "/" + action.getAddressFor() + sb.toString());
			//this.connection = new URL(this.serverAddress + ":" + this.serverPort + "/" + action.getAddressFor() + sb.toString()).openConnection();
		}
	}

	private final void initConnection(ServerAction action) throws IOException
	{
		System.out.println(this.serverAddress + ":" + this.serverPort + "/" + action.getAddressFor());
		//connection = new URL(this.serverAddress + ":" + this.serverPort + "/" + action.getAddressFor()).openConnection();
	}

	/**
	* Return the cookie sent by the server on the request
	*
 	* @return a instance of {@link java.net.HttpCookie} representing the cookie
	* @throws ConnectException if connection is not initiated
	**/
	public final HttpCookie getCookie() throws ConnectException
	{
		if (null != this.connection)
		{
			return this.store.getCookies().get(0);
		}
		throw new ConnectException("Connection not already established!");
	}
}