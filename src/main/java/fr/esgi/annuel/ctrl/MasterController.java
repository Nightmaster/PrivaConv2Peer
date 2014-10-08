package fr.esgi.annuel.ctrl;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
import fr.esgi.util.Outils;
import org.json.JSONException;

public final class MasterController
{
	private static MasterWindow window;
	private static JPanel actualPanel;
	private final HttpRequest httpRequest;
	private final Properties properties;
	private ClientInfo user;
	private IdentificationView identificationView = new IdentificationView(this);
	private RegisterView registerView = new RegisterView(this);
	private RegisterViewKeyPart registerKeyPartView = new RegisterViewKeyPart(this, this.registerView);
	private ProfileView profileView = new ProfileView(this);
	private HttpCookie cookie;
	private Properties registeredProperties;

	/**
	 * Instantiate a new {@link fr.esgi.annuel.ctrl.MasterController} with the properties loaded on startup
	 *
	 * @param properties {{@link java.util.Properties}}the properties loaded on startup
	 */
	public  MasterController(Properties properties)
	{
		this.properties = properties;
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
				answerRequest(username, true);
			else
				answerRequest(username, false);

		} while (!exit);
	}

	public final void changeView(Views view/*, Map<String, Object> map*/)
	{
		setLookAndFeel();
		if (Views.IDENTIFICATION.equals(view))
		{
			window.setView(this.identificationView, view);
			setActualPanel(this.identificationView);
		}
		else if (Views.REGISTER.equals(view))
		{
			window.setView(this.registerView, view);
			setActualPanel(this.registerView);
		}
		else if(Views.REGISTER_PART_2.equals(view))
		{
			window.setView(this.registerKeyPartView, view);
			setActualPanel(this.registerKeyPartView);
		}
		else if (Views.PROFILE.equals(view))
		{
			window.setView(this.profileView, view);
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
		String defaultDir = Outils.getDefaultDirectory();
		boolean dirCreated;
		File directory = new File(defaultDir + "/pc2p/");
		if(! (dirCreated = directory.exists()))
			dirCreated = directory.mkdir();
		if(dirCreated)
		{
			File config = new File(defaultDir + "/pc2p/config.ini");
			try
			{
				if (!config.exists())
					config.createNewFile();
				else
					registeredProperties = Outils.readPropertyFile(defaultDir + "/pc2p/config.ini");
			}
			catch (IOException ignored) {}
		}
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				setLookAndFeel();
				window = new MasterWindow(MasterController.this);
				window.setVisible(true);
				if (null == MasterController.this.registeredProperties)
					MasterController.this.identificationView.setEnableChckBox(false);
				else
					MasterController.this.identificationView.setLoginValue(MasterController.this.registeredProperties.getProperty("login"));
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
			ConnectionJsonParser connectionJson = JSONParser.getConnectionParser(this.httpRequest.getContent());
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
				StayAliveJsonParser stAlJson = JSONParser.getStayAliveParser(this.httpRequest.sendStayAliveRequest(this.cookie).getContent());
				this.user.setFriendList(Arrays.asList(stAlJson.getFriendList()));
				for (String ask : stAlJson.getAskList())
					popUpAskFriend(ask);
				this.cookie = this.httpRequest.getCookie();
			}
			catch (IOException | JSONException ignored) {}
		else
		{
			window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			window.openDisconnectPopup();
		}
	}

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
			window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			window.openDisconnectPopup();
		}
	}

	public final void showProfile(String username, JComponent component)
	{
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
			window.setView(actualPanel = this.identificationView.reset(), Views.IDENTIFICATION);
			window.openDisconnectPopup();
		}
	}
}