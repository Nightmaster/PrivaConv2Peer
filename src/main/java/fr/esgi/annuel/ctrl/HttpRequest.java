package fr.esgi.annuel.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import fr.esgi.annuel.constants.Parameters;
import fr.esgi.annuel.constants.ServerAction;

/**
* Class used to interact with the server's webAPI
* @author Ga�l B.
**/
public class HttpRequest
{
	private final String serverAddress, serverPort;
	private HttpURLConnection  connection = null;

	public HttpRequest(MasterController controller)
	{
		this.serverAddress = controller.getPropertiesController().getProperty("server.address");
		this.serverPort = controller.getPropertiesController().getProperty("server.port");
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
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendRegisterRequest(String username, String email, String hashPw, String firstname, String name) throws IOException
	{
		initConnection(ServerAction.REGISTER,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.EMAIL.getParameterValue() + "=" + email,
					   Parameters.PASSWORD.getParameterValue() + "=" + hashPw,
					   Parameters.FIRSTNAME.getParameterValue() + "=" + firstname,
					   Parameters.LASTNAME.getParameterValue() + "=" + name);
		return this;
	}

	/**
	* Initiate a connection to the server to make a connection (log on) action
	*
	* @param username the username used to log on the client (set it to <code>null</code> if email is used)
	* @param email    the email used to log on the client (set it to <code>null</code> if username is used)
	* @param hashPw   the MD5 hash of the password to log on the client
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendConnectionRequest(String username, String email, String hashPw) throws IOException
	{
		initConnection(ServerAction.CONNECT,
					   (null != username) ? Parameters.USERNAME.getParameterValue() + "=" + username : null,
					   (null != email) ? Parameters.EMAIL.getParameterValue() + "=" + email : null,
					   Parameters.PASSWORD.getParameterValue() + "=" + hashPw);
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a set listening port action
	*
	* @param port {<code>int</code>}: the port value
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	*
	* @throws IOException
	**/
	public final HttpRequest sendSetListeningPortRequest(int port, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.SET_LISTENING_PORT,
					   Parameters.PORT.getParameterValue() + "=" + Integer.toString(port));
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a stay alive action
	*
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	*
	* @throws IOException
	**/
	public final HttpRequest sendStayAliveRequest(HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.STAY_ALIVE);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a disconnection (log out) action
	*
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendDisonnectRequest(HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.DISCONNECT);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a update on identifications data action
	*
	* @param username  the new username (set it to <code>null</code> if unchanged)
	* @param email     the new email (set it to <code>null</code> if unchanged)
	* @param hashPw    the MD5 hash of the new password (set it to <code>null</code> if unchanged)
	* @param firstname the new firstname (set it to <code>null</code> if unchanged)
	* @param name      the new lastname (set it to <code>null</code> if unchanged)
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendUpdateInfosRequest(String username, String email, String hashPw, String firstname, String name, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.MODIFY_PROFILE,
					   (null != username) ? Parameters.USERNAME.getParameterValue() + "=" + username : null,
					   (null != email) ? Parameters.EMAIL.getParameterValue() + "=" + email : null,
					   (null != hashPw) ? Parameters.PASSWORD.getParameterValue() + "=" + hashPw : null,
					   (null != firstname) ? Parameters.FIRSTNAME.getParameterValue() + "=" + firstname : null,
					   (null != name) ? Parameters.LASTNAME.getParameterValue() + "=" + name : null);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a search action. Set empty values to <code>null</code>
	*
	* @param username  the searched username
	* @param email     the searched email
	* @param firstname the searched firstname
	* @param name      the searched lastname
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendSearchRequest(String username, String email, String firstname, String name, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.SEARCH,
					   (null != username) ? Parameters.USERNAME.getParameterValue() + "=" + username : null,
					   (null != email) ? Parameters.EMAIL.getParameterValue() + "=" + email : null,
					   (null != firstname) ? Parameters.FIRSTNAME.getParameterValue() + "=" + firstname : null,
					   (null != name) ? Parameters.LASTNAME.getParameterValue() + "=" + name : null);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a friendship answer to a user
	*
	* @param username the username of the asked user (set it to <code>null</code> if email is used)
	* @param email    the email of the asked user (set it to <code>null</code> if username is used)
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendAddFriendRequest(String username, String email, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.ADD_FRIEND,
					   (null != username) ? Parameters.USERNAME.getParameterValue() + "=" + username : null,
					   (null != username) ? Parameters.EMAIL.getParameterValue() + "=" + email : null);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a answer to a friendship request
	*
	* @param username         the username of the user that asked for friendship status
	* @param validationStatus <code>true</code> if request is accepted, <code>false</code> otherwise
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendAnswerReqRequest(String username, boolean validationStatus, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.ANSWER_REQUEST,
					   Parameters.USERNAME.getParameterValue() + "=" + username,
					   Parameters.VALIDATION.getParameterValue() + "=" + String.valueOf(validationStatus));
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a demand to get the private key for the logged on user
	*
	* @param username the username of logged on user
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendGetPrivateKeyReqRequest(String username, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.GET_PRIVATE_KEY, username);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a demand to get the public key of a given user
	*
	* @param username the username of the user that we want to get the public key
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendGetPublicKeyReqRequest(String username, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.GET_PUBLIC_KEY, username);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a demand to get the IP of a given user
	*
	* @param username the username of the user that we want to get the IP
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendGetClientIpRequest(String username, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.GET_CLIENT_IP, username);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	/**
	* Initiate a connection to the server to make a demand to get the public data of a given user
	*
	* @param username the username of the user that we want to get the public data
	* @param cookie the cookie used by the webAPI to authenticate the user
	*
	* @return the actual instance of the {@link fr.esgi.annuel.ctrl.HttpRequest}
	*
	* @throws IOException
	**/
	public final HttpRequest sendShowProfileRequest(String username, HttpCookie cookie) throws IOException
	{
		initConnection(ServerAction.SHOW_PROFILE, username);
		this.connection.setRequestProperty("Cookie", cookie.toString());
		this.connection.connect();
		return this;
	}

	private void initConnection(ServerAction action, String... parameters) throws IOException
	{
		if (action.getAddressFor().contains("%s"))
			this.connection = (HttpURLConnection) new URL(this.serverAddress + ":" + this.serverPort + "/" + String.format(action.getAddressFor(), parameters[0])).openConnection();
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
			this.connection = (HttpURLConnection) new URL(this.serverAddress + ":" + this.serverPort + "/" + action.getAddressFor() + sb.toString()).openConnection();
		}
	}

	private void initConnection(ServerAction action) throws IOException
	{
		connection = (HttpURLConnection) new URL(this.serverAddress + ":" + this.serverPort + "/" + action.getAddressFor()).openConnection();
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
			String headerName, cookie = "", uuid;
			for (int i = 1; (headerName = this.connection.getHeaderFieldKey(i)) != null; i++)
				if (headerName.equals("Set-Cookie"))
					cookie = this.connection.getHeaderField(i);
			uuid = cookie.split("=")[1].substring(0, 36);
			return new HttpCookie("sessId", uuid);
		}
		throw new ConnectException("Connection not already established!");
	}

	/**
	* Read the connection content and return it as a String
	*
	* @return the content as a String
	* @throws IOException
	**/
	public final String getContent() throws IOException
	{
		if (null != this.connection)
		{
			StringBuilder sb = new StringBuilder(this.connection.getContentLength());
			BufferedReader buff;
			if(400 <= this.connection.getResponseCode())
				buff = new BufferedReader(new InputStreamReader(this.connection.getErrorStream()));
			else
				buff = new BufferedReader(new InputStreamReader((InputStream) this.connection.getContent()));
			String content;
			do
			{
				content = buff.readLine();
				if (null != content)
					sb.append(content.trim());
			}
			while (null != content);
			return sb.toString();
		}
		throw new ConnectException("Connection not already established!");
	}
}