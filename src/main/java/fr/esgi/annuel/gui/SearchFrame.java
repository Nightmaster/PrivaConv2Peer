package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import fr.esgi.annuel.ctrl.MasterController;

public class SearchFrame extends JFrame
{
	private MasterController controller;

	/**
	 * Create the frame.
	 */
	public SearchFrame(MasterController controller)
	{
		this.controller = controller;
		MasterController.setLookAndFeel();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Rechercher un utilisateur");
	}

	private void setContent(JPanel panel)
	{
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		repaint();
		pack();
	}

	public void initializeContent(SearchView searchView)
	{
		setContent(searchView);
	}

	public void setResultView(ResultView resultView)
	{
		setContent(resultView);
	}

	public synchronized void closeFrame()
	{
		this.controller.stayAlive();
		if(null != this)
			dispose();
	}
}