package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.util.Outils;

public class MasterWindow extends JFrame
{
	private Views actualView;
	private MasterController controller;
	private JMenuBar menuBar;
	private JMenu mnFichier, menu;
	private JMenuItem mntmLaunchConv, mntmPropos, mntmQuitter, mntmProfil, mntmAddUser, mntmAide, mntmDisconnect;

	public MasterWindow(MasterController controller)
	{
		super();
		super.setResizable(false);
		this.controller = controller;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel = new IdentificationView(controller);
		setView(panel, Views.IDENTIFICATION);
	}

	public void openDisconnectPopup()
	{
		JOptionPane.showMessageDialog(this,
									  "Vous avez \u00E9t\u00E9 d\u00E9connect\u00E9 suite \u00E0 votre inactivit\u00E9 de plus de 15 minutes",
									  "D\u00E9connexion automatique",
									  JOptionPane.INFORMATION_MESSAGE);
	}

	private JMenu getMenu()
	{
		if (null == this.menu)
		{
			this.menu = new JMenu("?");
			this.menu.add(getMntmPropos());
			this.menu.add(getMntmAide());
			this.menu.setMnemonic('?');
		}
		return this.menu;
	}

	private JMenuBar getMnBar()
	{
		if (null == this.menuBar)
		{
			this.menuBar = new JMenuBar();
			this.menuBar.add(getMnPC2P());
			this.menuBar.add(getMenu());
		}
		return this.menuBar;
	}

	private JMenu getMnPC2P()
	{
		if (null == this.mnFichier)
		{
			this.mnFichier = new JMenu(Constants.APP_NAME);
			this.mnFichier.add(getMntmAddUser());
			this.mnFichier.add(getMntmLaunchConv());
			this.mnFichier.add(getMntmProfil());
			this.mnFichier.add(getMntmDisconnect());
			this.mnFichier.add(getMntmQuitter());
			this.mnFichier.setMnemonic('P');
		}
		return this.mnFichier;
	}

	private JMenuItem getMntmAddUser()
	{
		if (null == this.mntmAddUser)
		{
			this.mntmAddUser = new JMenuItem(Constants.ADD_USER);
			this.mntmAddUser.addActionListener(new MenuItemListener());
			this.mntmAddUser.setMnemonic('A');
			this.mntmAddUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		}
		return this.mntmAddUser;
	}

	private JMenuItem getMntmAide()
	{
		if (null == this.mntmAide)
		{
			this.mntmAide = new JMenuItem(Constants.HELP);
			this.mntmAide.addActionListener(new MenuItemListener());
			this.mntmAide.setMnemonic('H');
			this.mntmAide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		return this.mntmAide;
	}

	private JMenuItem getMntmDisconnect()
	{
		if (null == this.mntmDisconnect)
		{
			this.mntmDisconnect = new JMenuItem(Constants.DISCONNECT);
			this.mntmDisconnect.addActionListener(new MenuItemListener());
			this.mntmDisconnect.setMnemonic('D');
		}
		return this.mntmDisconnect;
	}

	private JMenuItem getMntmLaunchConv()
	{
		if (null == this.mntmLaunchConv)
		{
			this.mntmLaunchConv = new JMenuItem(Constants.LAUNCH_CONVERSATION);
			this.mntmLaunchConv.addActionListener(new MenuItemListener());
			this.mntmLaunchConv.setMnemonic('O');
			this.mntmLaunchConv.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		}
		return this.mntmLaunchConv;
	}

	private JMenuItem getMntmProfil()
	{
		if (null == this.mntmProfil)
		{
			this.mntmProfil = new JMenuItem(Constants.PROFILE);
			this.mntmProfil.addActionListener(new MenuItemListener());
			this.mntmProfil.setMnemonic('P');
			this.mntmProfil.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		}
			this.mntmProfil.setEnabled(false);
		return this.mntmProfil;
	}

	private JMenuItem getMntmPropos()
	{
		if (null == this.mntmPropos)
		{
			this.mntmPropos = new JMenuItem(Constants.ABOUT);
			this.mntmPropos.addActionListener(new MenuItemListener());
			this.mntmPropos.setMnemonic('A');
			this.mntmPropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, InputEvent.CTRL_MASK));
		}
		return this.mntmPropos;
	}

	private JMenuItem getMntmQuitter()
	{
		if (null == this.mntmQuitter)
		{
			this.mntmQuitter = new JMenuItem(Constants.QUIT);
			this.mntmQuitter.addActionListener(new MenuItemListener());
			this.mntmQuitter.setMnemonic('Q');
			this.mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		}
		return this.mntmQuitter;
	}

	/**
	* Change the displayed view by the window
	*
	* @param panel {{@link javax.swing.JPanel}}: the view to display in the window
	* @param view  {{@link fr.esgi.annuel.constants.Views}}: the enum defining the view
	**/
	public final void setView(JPanel panel, Views view)
	{
		setJMenuBar(getMnBar());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		this.actualView = view;
		this.controller.setActualPanel(panel);
		this.autoEnableMenuItems();
		this.repaint();
		this.pack();
	}

	private void autoEnableMenuItems()
	{
		if(Views.REGISTER.equals(this.actualView) || Views.IDENTIFICATION.equals(this.actualView))
		{
			this.mntmAddUser.setEnabled(false);
			this.mntmDisconnect.setEnabled(false);
			this.mntmLaunchConv.setEnabled(false);
			this.mntmProfil.setEnabled(false);
		}
	}

	private class MenuItemListener implements ActionListener
	{

		private void aboutChoice()
		{
			JOptionPane.showMessageDialog(null, "Private Conversations Over P2P v 1.0", "PrivaConv2Peer - Version", JOptionPane.PLAIN_MESSAGE);
		}

		private void addUserWindows()
		{
			//FIXME faire une pop-up de recherche ici
		}

		private void conversationWindow()
		{
		}

		private void helpChoice()
		{
			JOptionPane.showMessageDialog(null, "Menu \u00E0 venir", "\u00C0 venir", JOptionPane.INFORMATION_MESSAGE);
		}

		private void profilWindow()
		{
			MasterWindow.this.controller.changeView(Views.PROFILE);
		}

		@Override
		public void actionPerformed(ActionEvent ev)
		{
			String str = ((JMenuItem) ev.getSource()).getText();
			if (Constants.QUIT.equals(str))
				Outils.breakPgm(true);
			else if (Constants.LAUNCH_CONVERSATION.equals(str))
				conversationWindow();
			else if (Constants.ABOUT.equals(str))
				aboutChoice();
			else if (Constants.PROFILE.equals(str))
				profilWindow();
			else if (Constants.ADD_USER.equals(str))
				addUserWindows();
			else if (Constants.HELP.equals(str))
				helpChoice();
		}
	}
}