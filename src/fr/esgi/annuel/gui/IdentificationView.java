package fr.esgi.annuel.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
import org.json.JSONArray;
import org.json.JSONObject;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.contact.Contact;
import fr.esgi.annuel.contact.Contacts;

/**
 *
 * Class IdentificationView
 * Elle permet de construire la vue d'identification
 * et de valider la connexion
 *
 * */


public class IdentificationView extends JPanel
{
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
				throw new NullPointerException();
			this.uri = u;
		}

		private static void handleException(URI u, Exception e)
		{
			JOptionPane.showMessageDialog(null, "Sorry, a problem occurred while trying to open this link in your system's standard browser.", "A problem occured", JOptionPane.ERROR_MESSAGE);
		}

		@Override
		protected Void doInBackground() throws Exception
		{
			Desktop desktop = java.awt.Desktop.getDesktop();
			desktop.browse(this.uri);
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
				handleException(this.uri, ee);
			}
			catch (InterruptedException ie)
			{
				handleException(this.uri, ie);
			}
		}
	}

	private static final String A_HREF = "<a href=\"", HREF_CLOSED = "\">", HREF_END = "</a>", HTML = "<html>", HTML_END = "</html>";
	private static final long serialVersionUID = -3948992383967747160L;
	private JButton btnConnnexion;
	private JButton btnNewButton;
	private JCheckBox chckbxSeSouvenirDe;

	private JLabel lblIdentifiantDeConnexion;
	private JLabel lblPwd;

	private JPasswordField passwordField;

	private JTextField textField;

	/**
	 * Create the panel.
	 **/
	public IdentificationView()
	{
		setLayout(null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblPwd())).addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)).addContainerGap(316, Short.MAX_VALUE))
				.addGroup(
						groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblIdentifiantDeConnexion()).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addGap(23).addComponent(getBtnConnnexion()))
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(getBtnNewButton()).addComponent(getChckbxSeSouvenirDe()))).addContainerGap(316, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addComponent(getLblIdentifiantDeConnexion()).addGap(4).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(13).addComponent(getLblPwd()).addGap(4).addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(getBtnConnnexion()).addGap(7).addComponent(getChckbxSeSouvenirDe()).addGap(18).addComponent(getBtnNewButton()).addGap(99)));
		setLayout(groupLayout);

	}

	// WARNING
	// This method requires that s is a plain string that requires no further
	// escaping
	private static String htmlIfy(String s)
	{
		return HTML.concat(s).concat(HTML_END);
	}

	private static boolean isBrowsingSupported()
	{
		if (!Desktop.isDesktopSupported())
			return false;
		boolean result = false;
		Desktop desktop = java.awt.Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE))
			result = true;
		return result;

	}

	// WARNING
	// This method requires that s is a plain string that requires no further
	// escaping
	private static String linkIfy(String link, String value)
	{
		return A_HREF.concat(link).concat(HREF_CLOSED).concat(value).concat(HREF_END);
	}

	private static void makeLinkable(JLabel c, String link, MouseListener ml)
	{
		assert ml != null;
		c.setText(htmlIfy(linkIfy(link, c.getText())));
		c.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		c.addMouseListener(ml);
	}

	static String getPlainLink(String s)
	{
		return s.substring(s.indexOf(A_HREF) + A_HREF.length(), s.indexOf(HREF_CLOSED));
	}

	private JButton getBtnConnnexion()
	{
		if (this.btnConnnexion == null)
		{
			this.btnConnnexion = new JButton("Connnexion");
			this.btnConnnexion.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					String connect = getLogin();
					JPasswordField pwd = getPasswordField();
					try
					{
						String source = "";
						List<String> Urlparams = createConnectionURL(connect, pwd);
						URL url = new URL(Urlparams.get(0) + Urlparams.get(1));
						URLConnection urlConn = url.openConnection();
						urlConn.setDoOutput(true);

						BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
						StringBuilder inputLine = new StringBuilder();
						String input;
						while ((input = in.readLine()) != null)
							inputLine.append(input);
						System.out.println(inputLine);
						in.close();

						JSONObject mainObject = new JSONObject(inputLine.toString());
						if (!mainObject.getBoolean("error"))
						{
							// Récupération des données du client
							JSONObject userDetails = new JSONObject(mainObject.get("user").toString());
							JSONArray friendsList;
							JSONObject detailFriends;
							ClientInfo logedUser = new ClientInfo(userDetails.getString("login"));
							logedUser.setEmail(userDetails.getString("email"));
							logedUser.setFirstname(userDetails.getString("firstname"));
							logedUser.setLastname(userDetails.getString("name"));
							logedUser.setLogin(userDetails.getString("login"));


							// Récupération de la liste des amis
							friendsList = new JSONArray(mainObject.get("friends").toString());
							for( int i = 0; i < friendsList.length() ;i++)
							{
								detailFriends = new JSONObject(friendsList.get(i).toString());
								System.out.println(detailFriends.get("displayLogin"));
								Contacts.addContact(new Contact(detailFriends.getString("displayLogin")));
							}
							ChatWindow cw = new ChatWindow(logedUser);
							cw.main(null);
						}
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Echec de connexion", "Failure", JOptionPane.OK_OPTION);

					}
				}
			});
		}
		return this.btnConnnexion;
	}

	private JButton getBtnNewButton()
	{
		if (this.btnNewButton == null)
		{
			this.btnNewButton = new JButton("S'enregistrer");
			this.btnNewButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					RegisterWindow.main(null);
				}
			});
		}
		return this.btnNewButton;
	}

	private JCheckBox getChckbxSeSouvenirDe()
	{
		if (this.chckbxSeSouvenirDe == null)
			this.chckbxSeSouvenirDe = new JCheckBox("Se souvenir de moi");
		return this.chckbxSeSouvenirDe;
	}

	private JLabel getLblIdentifiantDeConnexion()
	{
		if (this.lblIdentifiantDeConnexion == null)
			this.lblIdentifiantDeConnexion = new JLabel("Identifiant de connexion");
		return this.lblIdentifiantDeConnexion;
	}

	private JLabel getLblPwd()
	{
		if (this.lblPwd == null)
			this.lblPwd = new JLabel("Mot de passe :");
		return this.lblPwd;
	}

	private JPasswordField getPasswordField()
	{
		if (this.passwordField == null)
			this.passwordField = new JPasswordField();
		return this.passwordField;
	}

	private JTextField getTextField()
	{
		if (this.textField == null)
		{
			this.textField = new JTextField();
			this.textField.setColumns(10);
		}
		return this.textField;
	}

	protected List<String> createConnectionURL(String connect, JPasswordField pwd) throws Exception
	{
		MessageDigest mdPwd = null;
		String hashtext = "";
		try
		{
			// Hachage du mot de passe
			mdPwd = MessageDigest.getInstance("MD5");
			mdPwd.reset();
			mdPwd.update(String.copyValueOf(pwd.getPassword()).getBytes());
			byte[] digest = mdPwd.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			while (hashtext.length() < 32)
				hashtext = "0" + hashtext;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		List<String> url = new ArrayList<String>();
		String urlConnect = Constants.SRV_URL + ":" + Constants.SRV_PORT + "/" + Constants.SRV_API + "/" + Constants.SRV_CONNECT_PAGE;
		String params = "?" + Constants.PARAM_USER + "=" + connect + "&" + Constants.PARAM_PWD + "=" + hashtext;

		url.add(urlConnect);
		url.add(params);
		return url;
	}

	public String getLogin()
	{
		return getTextField().getText();
	}

	public String getPw()
	{
		return String.valueOf(getPasswordField().getPassword());
	}
}