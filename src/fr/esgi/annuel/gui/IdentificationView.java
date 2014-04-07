package fr.esgi.annuel.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import fr.esgi.annuel.constants.Constants;

public class IdentificationView extends JPanel
{
	private static final long serialVersionUID = -3948992383967747160L;
	private JButton btnConnnexion;
	private JCheckBox chckbxSeSouvenirDe;
	private JLabel lblIdentifiantDeConnexion;
	private JLabel lblPwd;
	private JLabel lblSenregistrer;
	private JPasswordField passwordField;
	private JTextField textField;

	private static final String A_HREF = "<a href=\"", HREF_CLOSED = "\">", HREF_END = "</a>", HTML = "<html>", HTML_END = "</html>";

	/**
	 * Create the panel.
	 **/
	public IdentificationView()
	{
		setLayout(null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblIdentifiantDeConnexion()).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblPwd())).addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup().addGap(23).addComponent(getBtnConnnexion())).addComponent(getChckbxSeSouvenirDe()).addComponent(getLblSenregistrer())).addContainerGap(316, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addComponent(getLblIdentifiantDeConnexion()).addGap(4).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(13).addComponent(getLblPwd()).addGap(4).addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(getBtnConnnexion()).addGap(7).addComponent(getChckbxSeSouvenirDe()).addGap(17).addComponent(getLblSenregistrer()).addGap(105)));
		setLayout(groupLayout);

	}

	private JButton getBtnConnnexion()
	{
		if (btnConnnexion == null)
		{
			btnConnnexion = new JButton("Connnexion");
		}
		return btnConnnexion;
	}

	private JCheckBox getChckbxSeSouvenirDe()
	{
		if (chckbxSeSouvenirDe == null)
		{
			chckbxSeSouvenirDe = new JCheckBox("Se souvenir de moi");
		}
		return chckbxSeSouvenirDe;
	}

	private JLabel getLblPwd()
	{
		if (lblPwd == null)
		{
			lblPwd = new JLabel("Mot de passe :");
		}
		return lblPwd;
	}

	private JLabel getLblIdentifiantDeConnexion()
	{
		if (lblIdentifiantDeConnexion == null)
		{
			lblIdentifiantDeConnexion = new JLabel("Identifiant de connexion");
		}
		return lblIdentifiantDeConnexion;
	}

	private JLabel getLblSenregistrer()
	{
		if (lblSenregistrer == null)
		{
			lblSenregistrer = new JLabel("S'enregistrer");
			if (isBrowsingSupported())
			{
				makeLinkable(lblSenregistrer, Constants.SRV_URL.concat(Constants.SRV_REGISTER_PAGE), new LinkMouseListener());
			}
			lblSenregistrer.setForeground(Color.BLUE);
		}
		return lblSenregistrer;
	}

	public String getLogin()
	{
		return getTextField().getText();
	}

	private JPasswordField getPasswordField()
	{
		if (passwordField == null)
		{
			passwordField = new JPasswordField();
		}
		return passwordField;
	}

	public String getPw()
	{
		return String.valueOf(getPasswordField().getPassword());
	}

	private JTextField getTextField()
	{
		if (textField == null)
		{
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}

	private static void makeLinkable(JLabel c, String link, MouseListener ml)
	{
		assert ml != null;
		c.setText(htmlIfy(linkIfy(link, c.getText())));
		c.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		c.addMouseListener(ml);
	}

	private static boolean isBrowsingSupported()
	{
		if (!Desktop.isDesktopSupported())
		{
			return false;
		}
		boolean result = false;
		Desktop desktop = java.awt.Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE))
		{
			result = true;
		}
		return result;

	}

	private static class LinkMouseListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(java.awt.event.MouseEvent evt)
		{
			JLabel l = (JLabel) evt.getSource();
			try
			{
				URI uri = new java.net.URI(getPlainLink(l.getText()));
				new LinkRunner(uri).execute();
			}
			catch (URISyntaxException use)
			{
				throw new AssertionError(use + ": " + l.getText()); // NOI18N
			}
		}
	}

	private static class LinkRunner extends SwingWorker<Void, Void>
	{
		private final URI uri;

		private LinkRunner(URI u)
		{
			if (u == null)
			{
				throw new NullPointerException();
			}
			uri = u;
		}

		@Override
		protected Void doInBackground() throws Exception
		{
			Desktop desktop = java.awt.Desktop.getDesktop();
			desktop.browse(uri);
			return null;
		}

		@Override
		protected void done()
		{
			try
			{
				get();
			}
			catch (ExecutionException ee)
			{
				handleException(uri, ee);
			}
			catch (InterruptedException ie)
			{
				handleException(uri, ie);
			}
		}

		private static void handleException(URI u, Exception e)
		{
			JOptionPane.showMessageDialog(null, "Sorry, a problem occurred while trying to open this link in your system's standard browser.", "A problem occured", JOptionPane.ERROR_MESSAGE);
		}
	}

	static String getPlainLink(String s)
	{
		return s.substring(s.indexOf(A_HREF) + A_HREF.length(), s.indexOf(HREF_CLOSED));
	}

	// WARNING
	// This method requires that s is a plain string that requires no further escaping
	private static String linkIfy(String link, String value)
	{
		return A_HREF.concat(link).concat(HREF_CLOSED).concat(value).concat(HREF_END);
	}

	// WARNING
	// This method requires that s is a plain string that requires no further escaping
	private static String htmlIfy(String s)
	{
		return HTML.concat(s).concat(HTML_END);
	}
}