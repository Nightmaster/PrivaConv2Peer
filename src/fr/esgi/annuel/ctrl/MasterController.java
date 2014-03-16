package fr.esgi.annuel.ctrl;

import java.awt.EventQueue;
import fr.esgi.annuel.gui.MasterWindow;

public class MasterController
{
	public MasterController()
	{}

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
					MasterWindow window = new MasterWindow(new MasterController());
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
