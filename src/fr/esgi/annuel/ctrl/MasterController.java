package fr.esgi.annuel.ctrl;

import java.awt.EventQueue;
import java.util.Map;
import javax.swing.JPanel;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.gui.IdentificationView;
import fr.esgi.annuel.gui.MasterWindow;
import fr.esgi.annuel.gui.ProfilView;
import fr.esgi.annuel.gui.RegisterView;

public final class MasterController
{
	private static MasterWindow window;
	private final JPanel identificationView = new IdentificationView(this), registerView = new RegisterView(this), profileView = new ProfilView(this);
	private static JPanel actualPanel;

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

	public final void launch()
	{
		try
		{
			// Set System L&F
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		//@formatter:off
		catch (javax.swing.UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		//@formatter:on
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					window = new MasterWindow(new MasterController());
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
	 * Setter for actualPanel
	 * @param actualPanel the  actual panel to define
	 **/
	public final void setActualPanel(JPanel actualPanel)
	{
		if (!actualPanel.equals(MasterController.actualPanel))
			MasterController.actualPanel = actualPanel;
	}

	public final void setConnectionStatus(boolean isConnected)
	{
		MasterController.window.setConnectionStatus(isConnected);
	}

}
