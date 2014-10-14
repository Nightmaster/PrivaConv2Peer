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
	private JMenu mnFile, menu;
	private JMenuItem mntmPropos, mntmQuit, mntmProfile, mntmAddUser, mntmHelp, mntmDisconnect;

	public MasterWindow(MasterController controller)
	{
		super();
		super.setResizable(false);
		this.controller = controller;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel = new IdentificationView(controller);
		setView(panel, Views.IDENTIFICATION);
	}

	private void autoEnableMenuItems()
	{
		if(Views.REGISTER.equals(this.actualView) || Views.IDENTIFICATION.equals(this.actualView))
		{
			this.mntmAddUser.setEnabled(false);
			this.mntmDisconnect.setEnabled(false);
			this.mntmProfile.setEnabled(false);
			return;
		}
		this.mntmAddUser.setEnabled(true);
		this.mntmDisconnect.setEnabled(true);
		this.mntmProfile.setEnabled(true);
	}

	private JMenu getMenu()
	{
		if (null == this.menu)
		{
			this.menu = new JMenu("?");
			this.menu.add(getMntmPropos());
			this.menu.add(getMntmHelp());
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
		if (null == this.mnFile)
		{
			this.mnFile = new JMenu(Constants.APP_NAME);
			this.mnFile.add(getMntmAddUser());
			this.mnFile.add(getMntmProfile());
			this.mnFile.add(getMntmDisconnect());
			this.mnFile.add(getMntmQuit());
			this.mnFile.setMnemonic('P');
		}
		return this.mnFile;
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

	private JMenuItem getMntmHelp()
	{
		if (null == this.mntmHelp)
		{
			this.mntmHelp = new JMenuItem(Constants.HELP);
			this.mntmHelp.addActionListener(new MenuItemListener());
			this.mntmHelp.setMnemonic('H');
			this.mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		return this.mntmHelp;
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

	private JMenuItem getMntmProfile()
	{
		if (null == this.mntmProfile)
		{
			this.mntmProfile = new JMenuItem(Constants.PROFILE);
			this.mntmProfile.addActionListener(new MenuItemListener());
			this.mntmProfile.setMnemonic('P');
			this.mntmProfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		}
		this.mntmProfile.setEnabled(false);
		return this.mntmProfile;
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

	private JMenuItem getMntmQuit()
	{
		if (null == this.mntmQuit)
		{
			this.mntmQuit = new JMenuItem(Constants.QUIT);
			this.mntmQuit.addActionListener(new MenuItemListener());
			this.mntmQuit.setMnemonic('Q');
			this.mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		}
		return this.mntmQuit;
	}

	public void openDisconnectPopup()
	{
		JOptionPane.showMessageDialog(this,
				"Vous avez \u00E9t\u00E9 d\u00E9connect\u00E9 suite \u00E0 votre inactivit\u00E9 de plus de 15 minutes",
				"D\u00E9connexion automatique",
				JOptionPane.INFORMATION_MESSAGE);
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

	private class MenuItemListener implements ActionListener
	{
		private void aboutChoice()
		{
			JOptionPane.showMessageDialog(null, "Private Conversations Over P2P v 1.0", "PrivaConv2Peer - Version", JOptionPane.PLAIN_MESSAGE);
		}

		private void addUserWindows()
		{
			MasterWindow.this.controller.changeView(Views.SEARCH);
		}

		private void helpChoice()
		{
			JOptionPane.showMessageDialog(null, "Menu \u00E0 venir", "\u00C0 venir", JOptionPane.INFORMATION_MESSAGE);
		}

		private void profileWindow()
		{
			MasterWindow.this.controller.changeView(Views.PROFILE);
		}

		@Override
		public void actionPerformed(ActionEvent ev)
		{
			String str = ((JMenuItem) ev.getSource()).getText();
			if (Constants.QUIT.equals(str))
				Outils.breakPgm(true, MasterWindow.this.controller);
			else if (Constants.ABOUT.equals(str))
				aboutChoice();
			else if (Constants.PROFILE.equals(str))
				profileWindow();
			else if (Constants.ADD_USER.equals(str))
				addUserWindows();
			else if (Constants.HELP.equals(str))
				helpChoice();
			else if (Constants.DISCONNECT.equals(str))
				MasterWindow.this.controller.disconnect();
		}
	}
}