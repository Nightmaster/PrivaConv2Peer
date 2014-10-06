package fr.esgi.annuel.ctrl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.gui.*;
import fr.esgi.annuel.parser.ConnectionJsonParser;
import fr.esgi.annuel.parser.JSONParser;
import fr.esgi.annuel.parser.StayAliveJsonParser;
import org.json.JSONException;
import org.json.JSONObject;

public final class MasterController
{
	private static MasterWindow window;
	private static JPanel actualPanel;
	private final HttpRequest httpRequest;
	private final Properties properties;
	private ClientInfo user;
	private IdentificationView identificationView = new IdentificationView(this);
	private RegisterView registerView = new RegisterView(this);
	private ProfileView profileView = new ProfileView(this);
	private HttpCookie cookie;

	/**
	 * Instantiate a new {@link fr.esgi.annuel.ctrl.MasterController} with the properties loaded on startup
	 *
	 * @param properties {{@link java.util.Properties}}the properties loaded on startup
	 */
	public MasterController(Properties properties)
	{
		this.properties = properties;
		this.httpRequest = new HttpRequest(this);
	}

	/**
	* Display a {@link javax.swing.JOptionPane} pop-up that display a warning about error connection with the remote server
	**/
	private static final void popUpErrorConnection()
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
			if(res == JOptionPane.CLOSED_OPTION && 0 == JOptionPane.showConfirmDialog(jop, "Voulez-vous refuser la demande ?", "Refus ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
				exit = true;
			else if (0 == res)
				showProfile(username, jop);
			else if(1 == res)
				answerRequest(true);
			else
				answerRequest(false);

		} while (!exit);
	}

	public final void changeView(Views view, Map<String, Object> map)
	{
		if (Views.IDENTIFICATION.equals(view))
		{
			window.setView(this.identificationView, Views.IDENTIFICATION);
			setActualPanel(this.identificationView);
		}
		else if (Views.REGISTER.equals(view))
		{
			window.setView(this.registerView, Views.REGISTER);
			setActualPanel(this.registerView);
		}
		else if (Views.PROFILE.equals(view))
		{
			window.setView(this.profileView, Views.PROFILE);
			setActualPanel(this.profileView);
		}
		// else if (Views.CHAT.equals(view))
		// window.setView();
	}

	/**
	 * Getter for actualPanel
	 *
	 * @return the actual Panel
	 */
	public final JPanel getActualPanel()
	{
		return MasterController.actualPanel;
	}

	/**
	 * Setter for actualPanel
	 *
	 * @param actualPanel {{@link javax.swing.JPanel}}: the  actual panel to define
	 */
	public final void setActualPanel(JPanel actualPanel)
	{
		if (!actualPanel.equals(MasterController.actualPanel))
			MasterController.actualPanel = actualPanel;
	}

	/**
	 * Launch the graphical user interface
	 */
	public final void launch()
	{
		try // Set System L&F
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch (javax.swing.UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ignored)
		{
		}
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				window = new MasterWindow(MasterController.this);
				window.setVisible(true);
			}
		});
	}

	/**
	 * Repaint the JFrame component and make it pack
	 */
	public final void packFrame()
	{
		window.repaint();
		window.pack();
	}

	/**
	 * Set the connection status for the application
	 *
	 * @param isConnected {<ocde>boolean</ocde>}: the actual status
	 */
	public final void setConnectionStatus(boolean isConnected)
	{
		MasterController.window.setConnectionStatus(isConnected);
	}

	/**
	 * Return the properties loaded at the startup
	 *
	 * @return {{@link java.util.Properties}}: the properties
	 */
	public final Properties getProperties()
	{
		return properties;
	}

	public final String register(String username, String emailAddress, String hashPw, String firstname, String lastname, int keyLength, String hashPwKey)
	{
		try
		{
			return this.httpRequest.sendRegisterRequest(username, emailAddress, hashPw, firstname, lastname, keyLength, hashPwKey).getContent();
		}
		catch (IOException ignored)
		{
			return null;
		}
	}

	public final void connect(String username, String emailAddress, String hashPw)
	{
		try
		{
			this.cookie = this.httpRequest.sendConnectionRequest(username, emailAddress, hashPw).getCookie();
			ConnectionJsonParser connectionJson = JSONParser.getConnectionParser(new JSONObject(this.httpRequest.getContent()));
			if (connectionJson.isError())
				System.err.println(connectionJson.getDisplayMessage());
			else if (connectionJson.isConnectionValidated())
			{
				this.user = new ClientInfo(connectionJson);
				//FIXME revoir ce comportement !
				ChatWindow.main(null);
			}
		}
		catch (JSONException ignored)
		{
		}
		catch (IOException ioe)
		{
			popUpErrorConnection();
		}
	}

	public final void stayAlive()
	{
		//TDL Prévoir comportement changement de nom
		if (0 < new Date().compareTo(new Date(this.cookie.getMaxAge() * 1000)))
			try
			{
				StayAliveJsonParser stAlJson = JSONParser.getStayAliveParser(new JSONObject(this.httpRequest.sendStayAliveRequest(this.cookie).getContent()));
				this.user.setFriendList(stAlJson.getFriendList());
				for (String ask : stAlJson.getAskList())
					popUpAskFriend(ask);
			}
			catch (IOException | JSONException ignored)
			{
			}
		else
		{
			window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			window.openDisconnectPopup();
		}
	}

	public final void answerRequest(boolean answer)
	{
		//TODO faire la requête sendAnswerReqRequest()
	}

	private void showProfile(String username, JComponent component)
	{
		//TODO faire la requete de sendShowProfileRequest()
	}

	private void showProfile(String username, JOptionPane jOptionPane)
	{
		showProfile(username, jOptionPane);
	}
}