package fr.esgi.annuel.ctrl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Date;
import com.google.common.base.Strings;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.gui.*;
import fr.esgi.annuel.parser.*;
import fr.esgi.annuel.parser.subclasses.ChangedValues;
import fr.esgi.annuel.parser.subclasses.IpAndPort;
import fr.esgi.annuel.server.Server;
import org.json.JSONException;

public final class MasterController
{
	private static JPanel actualPanel;
	private final HttpRequest httpRequest;
	private final PropertiesController propertiesController;
	private boolean userConnected = false;
	private ClientInfo user;
	private ChatView chatView;
	private MasterWindow window;
	private JFrame profileFrame;
	private SearchFrame searchFrame;
	private HttpCookie cookie;
	private IdentificationView identificationView;
	private ProfileView profileView;
	private RegisterView registerView;
	private RegisterViewKeyPart registerKeyPartView;
	private SearchView searchView;

	/**
	* Instantiate a new {@link fr.esgi.annuel.ctrl.MasterController} with the properties loaded on startup
	*
	* @param propertiesController {{@link fr.esgi.annuel.ctrl.PropertiesController}}the properties controller with all the properties loaded on startup
	**/
	public MasterController(PropertiesController propertiesController)
	{
		this.propertiesController = propertiesController;
		this.httpRequest = new HttpRequest(this);
	}

	/**
	* Display a {@link javax.swing.JOptionPane} pop-up that display a warning about error connection with the remote server
	**/
	private static void popUpErrorConnection()
	{
		JOptionPane.showMessageDialog(actualPanel,
									  "La connection au serveur a \u00E9chou\u00E9e !\nVeuillez r\u00E9essayer plus tard. Si le probl\u00E8me persiste, veuillez le reporter sur la page Github du projet",
									  "Erreur de connexion",
									  JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Display a {@link javax.swing.JOptionPane} pop-up that display a warning about error connection with the remote server
	 *
	 * @param username {{@link String}} the username of the person asking for friendship
	 **/
	private void popUpAskFriend(String username)
	{
		boolean exit = false;
		do
		{
			int res = JOptionPane.showOptionDialog(actualPanel,
												   "L'utilisateur " + username + " souhaite vous ajouter dans sa liste d'amis. Que voulez-vous faire ?",
												   "Demande d'amiti\u00E9",
												   JOptionPane.YES_NO_CANCEL_OPTION,
												   JOptionPane.INFORMATION_MESSAGE,
												   null,
												   new String[]{"Voir le profil", "Accepter", "Refuser"},
												   "Voir le profil");
			if (res == JOptionPane.CLOSED_OPTION && 0 == JOptionPane.showConfirmDialog(this.window, "Voulez-vous refuser la demande ?", "Refus ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
				exit = true;
			else if (0 == res)
				showProfile(username, this.window);
			else if (1 == res)
			{
				answerRequest(username, true);
				exit = true;
			}
			else
			{
				answerRequest(username, false);
				exit = true;
			}
		}
		while (!exit);
	}

	private void openDisconnectPopup()
	{
		this.userConnected = false;
		this.user = null;
		JOptionPane.showMessageDialog(this.window,
									  "Vous avez \u00E9t\u00E9 d\u00E9connect\u00E9 suite \u00E0 votre inactivit\u00E9 de plus de 15 minutes",
									  "D\u00E9connexion automatique",
									  JOptionPane.INFORMATION_MESSAGE);
	}

	private void openErrorPopup(String errorMessage, Component focusOwner)
	{
		JOptionPane.showMessageDialog(focusOwner, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/**
	* Set the look and feel of the cally {@link javax.swing.JPanel view}
	**/
	public static void setLookAndFeel()
	{
		try // Set System L&F
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch (javax.swing.UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ignored)
		{
		}
	}

	/**
	* Initialize the main components of the controller
	**/
	public void initializeComponents()
	{
		this.identificationView = new IdentificationView(this);
		this.registerKeyPartView = new RegisterViewKeyPart(this);
		this.registerView = new RegisterView(this, this.registerKeyPartView);
		this.profileView = new ProfileView(this);
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				MasterController.this.searchFrame = new SearchFrame(MasterController.this);
				MasterController.this.searchView = new SearchView(MasterController.this);
			}
		});
	}

	public final void changeView(Views view/*, Map<String, Object> map*/)
	{
		if (Views.IDENTIFICATION.equals(view))
		{
			setLookAndFeel();
			this.window.setView(this.identificationView, view);
			setActualPanel(this.identificationView);
		}
		else if (Views.REGISTER.equals(view))
		{
			setLookAndFeel();
			this.window.setView(this.registerView, view);
			setActualPanel(this.registerView);
		}
		else if (Views.REGISTER_PART_2.equals(view))
		{
			setLookAndFeel();
			this.window.setView(this.registerKeyPartView, view);
			setActualPanel(this.registerKeyPartView);
		}
		else if (Views.PROFILE.equals(view))
		{
			setLookAndFeel();
			this.profileFrame = new JFrame();
			this.profileFrame.setResizable(false);
			this.profileFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.profileView.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.profileFrame.setContentPane(this.profileView);
			this.profileFrame.setVisible(true);
			this.profileFrame.pack();
		}
		else if (Views.SEARCH.equals(view))
		{
			this.searchFrame.initializeContent(MasterController.this.searchView.reset());
			this.searchFrame.setVisible(true);
		}
		else if (Views.CHAT.equals(view))
		{
			this.window.setView(this.chatView, view);
			setActualPanel(this.chatView);
		}
	}

	/**
	* Close the {@link javax.swing.JFrame profile frame} by calling the {@link javax.swing.JFrame#dispose()} function
	**/
	public final void closeProfileFrame()
	{
		this.profileFrame.dispose();
	}

	/**
	* Close the {@link fr.esgi.annuel.gui.SearchFrame profile frame} by calling its {@link fr.esgi.annuel.gui.SearchFrame#closeFrame()}  dispose} function
	**/
	public final void closeSearchFrame()
	{
		this.searchFrame.closeFrame();
	}

	/**
	* Setter for actualPanel
	*
	* @param actualPanel {{@link javax.swing.JPanel}}: the  actual panel to define
	**/
	public final void setActualPanel(JPanel actualPanel)
	{
		if (!actualPanel.equals(MasterController.actualPanel))
			MasterController.actualPanel = actualPanel;
	}

	/**
	* Launch the graphical user interface
	**/
	public final void launch()
	{
		initializeComponents();
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				setLookAndFeel();
				window = new MasterWindow(MasterController.this);
				propertiesController.restorePosition(window);
				MasterController.this.window.addComponentListener(new ComponentAdapter()
				{
					@Override
					public void componentMoved(ComponentEvent e)
					{
						propertiesController.storePosition(window);
					}
				});
				MasterController.this.window.setVisible(true);
			}
		});
	}

	/**
	* Return the properties loaded at the startup
	*
	* @return {{@link java.util.Properties}}: the properties
	**/
	public final PropertiesController getPropertiesController()
	{
		return this.propertiesController;
	}

	/**
	* Launch a register request to the server, and return the answer to the callee
	*
	* @param username     {{@link java.lang.String}}: The chosen username
	* @param emailAddress {{@link java.lang.String}}: The user's email address
	* @param hashPw       {{@link java.lang.String}}: The MD5 hash of the user's password
	* @param firstName    {{@link java.lang.String}}: The user's first name
	* @param lastName     {{@link java.lang.String}}: The user's last name
	* @param keyLength    {<ocde>int</ocde>}: The chosen key length
	* @param hashPwKey    {{@link java.lang.String}}: The user's password for his private key
	* @return {{@link java.lang.String}}: The stringified answer returned by the server after the request
	*
	* @throws java.lang.IllegalArgumentException if password are not MD5 hashed
	**/
	public final String register(String username, String emailAddress, String hashPw, String firstName, String lastName, int keyLength, String hashPwKey) throws IllegalArgumentException
	{
		if (32 != hashPw.length() || 32 != hashPwKey.length())
			throw new IllegalArgumentException("Both arguments hashPw && hashPwK must have been hashed for security reasons !");
		try
		{
			this.cookie = this.httpRequest.sendRegisterRequest(username, emailAddress, hashPw, firstName, lastName, keyLength, hashPwKey).getCookie();
			return this.httpRequest.getContent();
		}
		catch (IOException ignored)
		{
			popUpErrorConnection();
			return null;
		}
	}

	/**
	* Perform a connection request to the server, and log the user in if credentials are good. If an error has occurred, it will display a message
	*
	* @param username     {{@link java.lang.String}}: The user's username. Set null if email is used to connect
	* @param emailAddress {{@link java.lang.String}}: The user's email. Set null if username is used to connect
	* @param hashPw       {{@link java.lang.String}}: The MD5 hash of the user's password
	**/
	public final void connect(String username, String emailAddress, String hashPw)
	{
		try
		{
			this.cookie = this.httpRequest.sendConnectionRequest(username, emailAddress, hashPw).getCookie();
            ConnectionJsonParser connectionJson = JSONParser.getConnectionParser(this.httpRequest.getContent());
			if (connectionJson.isError())
				openErrorPopup(connectionJson.getDisplayMessage(), this.window);
			else if (connectionJson.isConnectionValidated())
			{
				this.userConnected = true;
				this.user = new ClientInfo(connectionJson);
                // Démarrer le serveur
                this.chatView = new ChatView(this,this.user);
                Thread t = new Thread(new Server(this));
                t.start();
				changeView(Views.CHAT);
			}
		}
		catch (JSONException ignored) {}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			popUpErrorConnection();
		}
	}

	/**
	* Perform a stay alive request to the server. When the answer is parsed, if an error occur, it will disconnect the user.
	* <br/> If a new answer for friendship is done, it will display a pop-up to answer the request.
	* <br/> If the cookie is outdated, the function disconnect the user.
	**/
	public final void stayAlive()
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendStayAliveRequest(this.cookie).getCookie();
				StayAliveJsonParser stAlJson = JSONParser.getStayAliveParser(this.httpRequest.getContent());
				if (stAlJson.isError())
				{
					openErrorPopup(stAlJson.getDisplayMessage(), this.window);
					this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
					openDisconnectPopup();
					return;
				}
				boolean newFriends = false;
				if (null != stAlJson.getFriendList())
					this.user.setFriendList(Arrays.asList(stAlJson.getFriendList()));
				else
					this.user.setFriendList(null);
				if (null != stAlJson.getAskList())
					for (String ask : stAlJson.getAskList())
					{
						popUpAskFriend(ask);
						newFriends = true;
					}
				this.cookie = this.httpRequest.getCookie();
				if (newFriends)
					stayAlive();
			}
			catch (IOException | JSONException ignored) {}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	/**
	* Perform a request to answer a friendship ask
	*
	* @param askerName {{@link java.lang.String}}: the username of the user that asked the friendship
	* @param answer    {<code>boolean</code>}: <code>true</code> if the answer if yes, <code>false</code> otherwise
	**/
	public final void answerRequest(String askerName, boolean answer)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendAnswerReqRequest(askerName, answer, this.cookie).getCookie();
				AnswerRequestJsonParser arJson = JSONParser.getAnswerRequestParser(this.httpRequest.getContent());
				if (arJson.isError())
					openErrorPopup(arJson.getDisplayMessage(), this.window);
			}
			catch (JSONException ignored) {}
			catch (IOException ioe)
			{
				popUpErrorConnection();
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	/**
	* Perform a show profile request, and display the content in a pop-up
	*
	* @param username  {{@link java.lang.String}}: the user's username that the actual user want to see
	* @param component {{@link java.awt.Component}}: the component used when the function has been called (to maintain order in pop-up closing)
	**/
	public final void showProfile(String username, Component component)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendShowProfileRequest(username, this.cookie).getCookie();
				ShowProfileJsonParser showProfileJson = JSONParser.getShowProfileParser(this.httpRequest.getContent());
				this.cookie = this.httpRequest.getCookie();
				if (showProfileJson.isError())
					openErrorPopup(showProfileJson.getDisplayMessage(), component);
				JOptionPane.showMessageDialog(component, showProfileJson.getProfile().toString(), "Profil de " + username, JOptionPane.PLAIN_MESSAGE);
			}
			catch (JSONException ignored) {}
			catch (IOException e)
			{
				popUpErrorConnection();
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	/**
	* Perform a send invitation request
	*
	* @param login           {{@link java.lang.String}}: the login value of the user to invite
	* @param componentCallee {{@link java.awt.Component}}: the callee component to refer to when open the validation/error pop-up
	**/
	public void addFriend(String login, Component componentCallee)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				String username = login.contains("@") ? null : login, email = login.contains("@") ? login : null;
				this.cookie = this.httpRequest.sendAddFriendRequest(username, email, this.cookie).getCookie();
				AddFriendJsonParser addFriendJson = JSONParser.getAddFriendParser(this.httpRequest.getContent());
				if (addFriendJson.isError())
					openErrorPopup(addFriendJson.getDisplayMessage(), componentCallee);
				else
					JOptionPane.showMessageDialog(componentCallee, "Votre invitation a \u00E9t\u00E9 correctement envoy\u00E9e", "Invitation envoy\u00E9e", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (JSONException ignored) {}
			catch (IOException e)
			{
				popUpErrorConnection();
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	/**
	* Perform a search profiles request, and display the result in the {@link fr.esgi.annuel.gui.SearchFrame}
	*
	* @param username  {{@link java.lang.String}}: the searched username
	* @param email     {{@link java.lang.String}}: the searched email address
	* @param lastName  {{@link java.lang.String}}: the searched lastName
	* @param firstName {{@link java.lang.String}}: the searched firstName
	**/
	public final void search(String username, String email, String lastName, String firstName)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendSearchRequest(username, email, firstName, lastName, this.cookie).getCookie();
				SearchJsonParser searchJson = JSONParser.getSearchParser(this.httpRequest.getContent());
				this.cookie = this.httpRequest.getCookie();
				if (searchJson.isError())
					openErrorPopup(searchJson.getDisplayMessage(), this.searchFrame);
				else
					this.searchFrame.setResultView(new ResultView(this, searchJson.getProfiles()));
			}
			catch (JSONException ignored) {}
			catch (IOException e)
			{
				popUpErrorConnection();
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	/**
	* Perform a disconnect request to the server, erase the user's information and go back to the identification view
	**/
	public final void disconnect()
	{
		if (userConnected)
			if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
				try
				{
					SimpleJsonParser disconnectionJson = JSONParser.getDisconnectionParser(this.httpRequest.sendDisonnectRequest(this.cookie).getContent());
					if(disconnectionJson.isError())
						openErrorPopup(disconnectionJson.getDisplayMessage(), this.window);
				}
				catch (JSONException ignored) {ignored.printStackTrace();}
				catch (IOException e)
				{
					popUpErrorConnection();
				}
			else
			{
				this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
				openDisconnectPopup();
			}
		changeView(Views.IDENTIFICATION);
		this.cookie = null;
		this.userConnected = false;
		this.user = null;
	}

	/**
	* Perform a user's information update request to the server with the given values
	*
	* @param login {{@link java.lang.String}}: the new user's login
	* @param email {{@link java.lang.String}}:  the new user'semail
	* @param hashPw {{@link java.lang.String}}:  the MD5 hash of the new user's password
	* @param firstName {{@link java.lang.String}}:  the new user's firstname
	* @param lastName {{@link java.lang.String}}:  the new user's lastname
	**/
	public final void updateInfos(String login, String email, String hashPw, String firstName, String lastName)
	{
		assert 32 == hashPw.length() : "Le mot de passe doit \u00E7tre envoyé hashé !";
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendUpdateInfosRequest(login, email, hashPw, firstName, lastName, this.cookie).getCookie();
				ModifiedProfileJsonParser modifiedProfileJson = JSONParser.getModifyProfileParser(this.httpRequest.getContent());
				if(modifiedProfileJson.isError())
					JOptionPane.showMessageDialog(this.searchFrame, modifiedProfileJson.getDisplayMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				else
				{
					ChangedValues cv = modifiedProfileJson.getNewValues();
					String	newLogin = cv.getLogin(),
							newEmail = cv.getEmail(),
							newFirstName = cv.getFirstName(),
							newLastName = cv.getName();
					boolean pwChanged = cv.isPasswordChanged();
					StringBuilder sb = new StringBuilder(500);
					if(! Strings.isNullOrEmpty(newLogin))
					{
						sb.append("Nouveau login : ");
						sb.append(newEmail);
						sb.append('\n');
					}
					if(! Strings.isNullOrEmpty(newEmail))
					{
						sb.append("Nouvelle adresse email : ");
						sb.append(newEmail);
						sb.append('\n');
					}
					if(pwChanged)
					{
						sb.append("Mot de passe correctement changé");
						sb.append('\n');
					}
					if(! Strings.isNullOrEmpty(newFirstName))
					{
						sb.append("Nouveau pr\u00E9nom : ");
						sb.append(newFirstName);
						sb.append('\n');
					}
					if(! Strings.isNullOrEmpty(newLastName))
					{
						sb.append("Nouveau nom : ");
						sb.append(newLastName);
						sb.append('\n');
					}
					String res = sb.toString();
					while (res.contains("\n\n"))
						res = res.replaceAll("\\n\\n", "\n");
					if (res.endsWith("\n"))
						res = res.substring(0, res.length() - 1);
					JOptionPane.showMessageDialog(this.profileFrame, res, "Confirmation de changements", JOptionPane.INFORMATION_MESSAGE);
					closeProfileFrame();
				}
			}
			catch (JSONException ignored) {}
			catch (IOException e)
			{
				popUpErrorConnection();
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	/**
	* Perform a demand for a user's IP and port request to the server
	*
	* @param username {{@link java.lang.String}}: the user's username from which one you want the both IP and port
	*
	* @return {{@link fr.esgi.annuel.parser.subclasses.IpAndPort}}: the class containing the requested data
	**/
	public final IpAndPort getUserIpAndPort(String username)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendGetClientIpRequest(username, this.cookie).getCookie();
				ClientIpJsonParser clientIpJson = JSONParser.getClientIpParser(this.httpRequest.getContent());
				if (clientIpJson.isError())
				{
					openErrorPopup(clientIpJson.getDisplayMessage(), this.window);
					return null;
				}
				return clientIpJson.getIpAndPort();
			}
			catch (JSONException ignored)
			{
				return null;
			}
			catch (IOException e)
			{
				popUpErrorConnection();
				return null;
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
			return null;
		}
	}

	/**
	* Perform a databse registration of the listening port request to the server
	*
	* @param port {<code>int</code>}: the port value
	**/
	public final void setListeningPort(int port)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.cookie = this.httpRequest.sendSetListeningPortRequest(port, this.cookie).getCookie();
				SimpleJsonParser listeningPortJson = JSONParser.getListeningPortJsonParser(this.httpRequest.getContent());
				if (listeningPortJson.isError())
					openErrorPopup(listeningPortJson.getDisplayMessage(), this.window);
			}
			catch (JSONException ignored) {}
			catch (IOException e)
			{
				popUpErrorConnection();
			}
			finally
			{
				stayAlive();
			}
		else
		{
			this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			openDisconnectPopup();
		}
	}

	public final void repaintSearchFrame()
	{
		this.searchFrame.repaint();
	}
}