package fr.esgi.annuel.ctrl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.gui.*;
import fr.esgi.annuel.parser.ConnectionJsonParser;
import fr.esgi.annuel.parser.JSONParser;
import fr.esgi.annuel.parser.ShowProfileJsonParser;
import fr.esgi.annuel.parser.StayAliveJsonParser;
import fr.esgi.annuel.server.Server;
import org.json.JSONException;

public final class MasterController
{
	private static JPanel actualPanel;
	private final HttpRequest httpRequest;
	private final PropertiesController propertiesController;
	private ClientInfo user;
	private HttpCookie cookie;
	private IdentificationView identificationView;
	private JFrame profileFrame;
	private MasterWindow window;
	private ProfileView profileView;
	private RegisterView registerView;
	private RegisterViewKeyPart registerKeyPartView;

	/**
	* Instantiate a new {@link fr.esgi.annuel.ctrl.MasterController} with the properties loaded on startup
	*
	* @param properties {{@link java.util.Properties}}the properties loaded on startup
	**/
	public MasterController(Properties properties)
	{
		propertiesController = new PropertiesController(properties);
		this.httpRequest = new HttpRequest(this);
		this.identificationView = new IdentificationView(this);
		this.registerKeyPartView = new RegisterViewKeyPart(this);
		this.registerView = new RegisterView(this, this.registerKeyPartView);
		this.profileView = new ProfileView(this);
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
		JOptionPane jop = new JOptionPane();
		do
		{
			int res = jop.showOptionDialog(actualPanel,
										   "L'utilisateur " + username + " souhaite vous ajouter dans sa liste d'amis. Que voulez-vous faire ?",
										   "Demande d'amiti\u00E9",
										   JOptionPane.YES_NO_CANCEL_OPTION,
										   JOptionPane.INFORMATION_MESSAGE,
										   null,
										   new String[]{"Voir le profil", "Accepter", "Refuser"},
										   "Voir le profil");
			if (res == JOptionPane.CLOSED_OPTION && 0 == JOptionPane.showConfirmDialog(jop, "Voulez-vous refuser la demande ?", "Refus ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
				exit = true;
			else if (0 == res)
				showProfile(username, jop);
			else if (1 == res)
				answerRequest(username, true);
			else
				answerRequest(username, false);
		} while (!exit);
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
			this.profileFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.profileFrame.setContentPane(this.profileView);
			this.profileFrame.setVisible(true);
		}
		// else if (Views.CHAT.equals(view))
		// this.window.setView();
	}

	/**
	* Close the {@link javax.swing.JFrame profile frame}
	**/
	public final void closeProfileFrame()
	{
		this.profileFrame.dispose();
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
				if (! MasterController.this.propertiesController.isFileCreated())
					MasterController.this.identificationView.setEnabledChckBox(false);
				else
					MasterController.this.identificationView.setLoginValue(MasterController.this.propertiesController.getRegisteredProperties().getProperty("login"));
			}
		});
	}

	/**
	* Set the look and feel of the cally {@link javax.swing.JPanel view}
	**/
	public final void setLookAndFeel()
	{
		try // Set System L&F
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch (javax.swing.UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {}
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
	* @param username {{@link java.lang.String}}: The chosen username
	* @param emailAddress {{@link java.lang.String}}: The user's email address
	* @param hashPw {{@link java.lang.String}}: The MD5 hash of the user's password
	* @param firstName {{@link java.lang.String}}: The user's first name
	* @param lastName {{@link java.lang.String}}: The user's last name
	* @param keyLength {<ocde>int</ocde>}: The chosen key length
	* @param hashPwKey {{@link java.lang.String}}: The user's password for his private key
	*
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
			return this.httpRequest.sendRegisterRequest(username, emailAddress, hashPw, firstName, lastName, keyLength, hashPwKey).getContent();
		}
		catch (IOException ignored)
		{
			return null;
		}
	}

	/**
	* Perform a connection request to the server, and log the user in if credentials are good. If an error has occurred, it will display a message
	*
	* @param username {{@link java.lang.String}}: The user's username. Set null if email is used to connect
	* @param emailAddress {{@link java.lang.String}}: The user's email. Set null if username is used to connect
	* @param hashPw {{@link java.lang.String}}: The MD5 hash of the user's password
	**/
	public final void connect(String username, String emailAddress, String hashPw)
	{
		try
		{
			this.cookie = this.httpRequest.sendConnectionRequest(username, emailAddress, hashPw).getCookie();
			ConnectionJsonParser connectionJson = JSONParser.getConnectionParser(this.httpRequest.getContent());
			if (connectionJson.isError())
				System.err.println(connectionJson.getDisplayMessage());
			else if (connectionJson.isConnectionValidated())
			{
				this.user = new ClientInfo(connectionJson);
                // Démarrer le serveur
                Thread t = new Thread(new Server());
                t.start();
                //FIXME revoir ce comportement !
                // Changer la vue
                this.window.setVisible(false);
                new ChatWindow(this);
			}
		}
		catch (JSONException ignored) {}
		catch (IOException ioe)
		{
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
		//TDL Prévoir comportement changement de nom
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				StayAliveJsonParser stAlJson = JSONParser.getStayAliveParser(this.httpRequest.sendStayAliveRequest(this.cookie).getContent());
				if(stAlJson.isError())
				{
					JOptionPane.showMessageDialog(MasterController.this.window, stAlJson.getDisplayMessage(), "Erreur Stay Alive !!!", JOptionPane.ERROR_MESSAGE);
					this.window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
					this.window.openDisconnectPopup();
					return;
				}
				boolean newFriends = false;
				this.user.setFriendList(Arrays.asList(stAlJson.getFriendList()));
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
			this.window.openDisconnectPopup();
		}
	}

	/**
	* Perform a request to answer a friendship ask
	* @param askerName {{@link java.lang.String}}: the username of the user that asked the friendship
	* @param answer {<code>boolean</code>}: <code>true</code> if the answer if yes, <code>false</code> otherwise
	**/
	public final void answerRequest(String askerName, boolean answer)
	{
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				this.httpRequest.sendAnswerReqRequest(askerName, answer, this.cookie);
				this.cookie = this.httpRequest.getCookie();
			}
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
			this.window.openDisconnectPopup();
		}
	}

	/**
	* Perform a show profile request, and display the content in a pop-up
	*
	* @param username {{@link java.lang.String}}: the user's username that the actual user want to see
	* @param component {{@link javax.swing.JComponent}}: the component used when the function has been called (to maintain order in pop-up closing)
	**/
	public final void showProfile(String username, JComponent component)
	{
		//TODO si les utilisateurs ne sont pas en demande d'amis, ni amis, faire une pop-up de demande d'amitié
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				ShowProfileJsonParser showProfileJson = JSONParser.getShowProfileParser(this.httpRequest.sendShowProfileRequest(username, this.cookie).getContent());
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
			this.window.openDisconnectPopup();
		}
	}
}