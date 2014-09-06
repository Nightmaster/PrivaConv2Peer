package fr.esgi.annuel.ctrl;

import java.awt.EventQueue;
import javax.swing.JPanel;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.gui.IdentificationView;
import fr.esgi.annuel.gui.MasterWindow;
import fr.esgi.annuel.gui.ProfilView;
import fr.esgi.annuel.gui.RegisterView;

public final class MasterController
{
	private static MasterWindow window;
	private JPanel identificationView = new IdentificationView(this), registerView = new RegisterView(this), profileView = new ProfilView(this);

	public MasterController()
	{}

	public void changeView(String viewName)
	{
		Views view = Views.getViewByName(viewName);
		if (Views.IDENTIFICATION.equals(view))
			window.setView(this.identificationView);
		else if (Views.REGISTER.equals(view))
			window.setView(this.registerView);
		else if (Views.PROFILE.equals(view))
			window.setView(this.profileView);
		// else if (Views.CHAT.equals(view))
		// window.setView();
	}

	public void launch()
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

}
