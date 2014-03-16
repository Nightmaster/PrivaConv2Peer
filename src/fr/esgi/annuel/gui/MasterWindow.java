package fr.esgi.annuel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import fr.esgi.annuel.Outils;
import fr.esgi.annuel.ctrl.MasterController;

@SuppressWarnings("serial")
public class MasterWindow extends JFrame
{
	private class MenuItemListener implements ActionListener
	{

		private void aboutChoice()
		{
			JOptionPane.showMessageDialog(null, "Private Conversations Over P2P v 0.1", "PrivaConv2Peer - Version", JOptionPane.PLAIN_MESSAGE);
		}

		@Override
		public void actionPerformed(ActionEvent ev)
		{
			String str = ((JMenuItem) ev.getSource()).getText();
			if (QUIT == str)
				Outils.breakPgm(true);
			else if (LAUNCH_CONVERSATION == str)
				conversationWindow();
			else if (ABOUT == str)
				aboutChoice();
			else if (PROFIL == str)
				profilWindow();
			else if (ADD_USER == str)
				addUserWindows();
			else if (HELP == str)
				helpChoice();
		}

		private void addUserWindows()
		{

		}

		private void conversationWindow()
		{

		}

		private void helpChoice()
		{
			JOptionPane.showMessageDialog(null, "Menu à venir", "À venir", JOptionPane.INFORMATION_MESSAGE);
		}

		private void profilWindow()
		{

		}
	}

	private static final String APP_NAME = "PrivaConf2Peer", ADD_USER = "Ajouter un utilisateur à votre liste", LAUNCH_CONVERSATION = "Ouvrir une nouvelle conversation", PROFIL = "Profile", DISCONNECT = "D\u00E9connnexion", ABOUT = "\u00C0 Propos", QUIT = "Quitter", HELP = "Aide";
	private JPanel contentPane;
	private final MasterController controller;
	private JMenuBar menuBar;
	private JMenu mnFichier, menu;
	private JMenuItem mntmLaunchConv, mntmPropos, mntmQuitter, mntmProfil, mntmAddUser, mntmAide, mntmDisconnect;
	private String pathToFile, gameName, actualView;

	public MasterWindow(MasterController controller)
	{
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMnBar());
		contentPane = new IdentificationView();
		actualView = "Identification";
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.pack();
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
			this.mnFichier = new JMenu(APP_NAME);
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
			this.mntmAddUser = new JMenuItem(ADD_USER);
			this.mntmAddUser.addActionListener(new MenuItemListener());
			this.mntmAddUser.setMnemonic('A');
			this.mntmAddUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		}
		if ("Identification" == actualView)
			this.mntmAddUser.setEnabled(false);
		return this.mntmAddUser;
	}

	private JMenuItem getMntmAide()
	{
		if (null == this.mntmAide)
		{
			this.mntmAide = new JMenuItem(HELP);
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
			this.mntmDisconnect = new JMenuItem(DISCONNECT);
			this.mntmDisconnect.addActionListener(new MenuItemListener());
			this.mntmDisconnect.setMnemonic('D');
		}
		if ("Identification" == actualView)
			this.mntmDisconnect.setEnabled(false);
		return this.mntmDisconnect;
	}

	private JMenuItem getMntmLaunchConv()
	{
		if (null == this.mntmLaunchConv)
		{
			this.mntmLaunchConv = new JMenuItem(LAUNCH_CONVERSATION);
			this.mntmLaunchConv.addActionListener(new MenuItemListener());
			this.mntmLaunchConv.setMnemonic('O');
			this.mntmLaunchConv.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		}
		if ("Identification" == actualView)
			this.mntmLaunchConv.setEnabled(false);
		return this.mntmLaunchConv;
	}

	private JMenuItem getMntmProfil()
	{
		if (null == this.mntmProfil)
		{
			this.mntmProfil = new JMenuItem(PROFIL);
			this.mntmProfil.addActionListener(new MenuItemListener());
			this.mntmProfil.setMnemonic('P');
			this.mntmProfil.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		}
		if ("Identification" == actualView)
			this.mntmProfil.setEnabled(false);
		return this.mntmProfil;
	}

	private JMenuItem getMntmPropos()
	{
		if (null == this.mntmPropos)
		{
			this.mntmPropos = new JMenuItem(ABOUT);
			this.mntmPropos.addActionListener(new MenuItemListener());
			this.mntmPropos.setMnemonic('A');
			this.mntmPropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, KeyEvent.CTRL_MASK));
		}
		return this.mntmPropos;
	}

	private JMenuItem getMntmQuitter()
	{
		if (null == this.mntmQuitter)
		{
			this.mntmQuitter = new JMenuItem(QUIT);
			this.mntmQuitter.addActionListener(new MenuItemListener());
			this.mntmQuitter.setMnemonic('Q');
			this.mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		}
		return this.mntmQuitter;
	}

	public void setView(JPanel gv)
	{
		this.contentPane = gv;
		;
	}
}
