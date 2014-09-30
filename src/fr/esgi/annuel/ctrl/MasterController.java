package fr.esgi.annuel.ctrl;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import fr.esgi.annuel.constants.ServerAction;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.gui.IdentificationView;
import fr.esgi.annuel.gui.MasterWindow;
import fr.esgi.annuel.gui.ProfilView;
import fr.esgi.annuel.gui.RegisterView;

public final class MasterController
{
	private static final String COOKIE_NAME = "sessId";
	private static UUID cookieValue;
	private static MasterWindow window;
	private static JPanel actualPanel;
	private final JPanel identificationView = new IdentificationView(this), registerView = new RegisterView(this), profileView = new ProfilView(this);
	private final Properties properties;

	public MasterController(Properties properties)
	{
		this.properties = properties;
	}

	public static UUID getCookieValue()
	{
		return cookieValue;
	}

	public static void setCookieValue(String cookieValue)
	{
		MasterController.cookieValue = UUID.fromString(cookieValue);
	}

	public static void setCookieValue(UUID cookieValue)
	{
		MasterController.cookieValue = cookieValue;
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
	 **/
	public final JPanel getActualPanel()
	{
		return MasterController.actualPanel;
	}

	/**
	 * Setter for actualPanel
	 * @param actualPanel the  actual panel to define
	 **/
	public final void setActualPanel(JPanel actualPanel)
	{
		if (!actualPanel.equals(MasterController.actualPanel))
			MasterController.actualPanel = actualPanel;
	}

	public final void launch()
	{
		try // Set System L&F
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch (javax.swing.UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {}
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					window = new MasterWindow(MasterController.this);
					window.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Repaint the JFrame component and make it pack
	 **/
	public final void packFrame()
	{
		window.repaint();
		window.pack();
	}

	public final String callServerAction(ServerAction serverAction, String... parameters)
	{
		//FIXME faire des méthodes d'action spécifiques
		return null;
	}

	public final void setConnectionStatus(boolean isConnected)
	{
		MasterController.window.setConnectionStatus(isConnected);
	}

	public Properties getProperties()
	{
		return properties;
	}
}
