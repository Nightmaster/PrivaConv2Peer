package fr.esgi.annuel.ctrl;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
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

	/**
	* Instantiate a new {@link fr.esgi.annuel.ctrl.MasterController} with the properties loaded on startup
	*
	* @param properties {{@link java.util.Properties}}the properties loaded on startup
	**/
	public MasterController(Properties properties)
	{
		this.properties = properties;
	}

	/**
	* Getter for the value of the current cookie
	*
	* @return {{@link java.util.UUID}} the uuid value contained in the cookie
	**/
	public static UUID getCookieValue()
	{
		return cookieValue;
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

	/**
	* Set the connection status for the application
	*
	* @param isConnected {<ocde>boolean</ocde>}: the actual status
	**/
	public final void setConnectionStatus(boolean isConnected)
	{
		MasterController.window.setConnectionStatus(isConnected);
	}

	/**
	* Return the properties loaded at the startup
	*
	* @return {{@link java.util.Properties}}: the properties
	**/
	public Properties getProperties()
	{
		return properties;
	}
}