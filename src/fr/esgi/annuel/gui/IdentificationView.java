package fr.esgi.annuel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.jdesktop.xswingx.PromptSupport;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;
import org.json.JSONArray;
import org.json.JSONObject;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.contact.Contact;
import fr.esgi.annuel.contact.Contacts;
import fr.esgi.annuel.ctrl.MasterController;

/**
 *
 * Class IdentificationView
 * Elle permet de construire la vue d'identification
 * et de valider la connexion
 *
 * */

public class IdentificationView extends JPanel
{
	private final class BtnListener implements ActionListener
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
					JSONObject userDetails = new JSONObject(mainObject.get("user").toString()), detailFriends;
					JSONArray friendsList;
					ClientInfo logedUser = new ClientInfo(userDetails.getString("login"));
					logedUser.setEmail(userDetails.getString("email"));
					logedUser.setFirstname(userDetails.getString("firstname"));
					logedUser.setLastname(userDetails.getString("name"));
					logedUser.setLogin(userDetails.getString("login"));

					// Récupération de la liste des amis
					friendsList = new JSONArray(mainObject.get("friends").toString());
					for (int i = 0; i < friendsList.length(); i++ )
					{
						detailFriends = new JSONObject(friendsList.get(i).toString());
						System.out.println(detailFriends.get("displayLogin"));
						Contacts.addContact(new Contact(detailFriends.getString("displayLogin")));
					}
					ChatWindow cw = new ChatWindow(logedUser);
					ChatWindow.main(null);
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Echec de connexion", "Failure", JOptionPane.OK_OPTION);

			}
		}
	}

	private static final long serialVersionUID = -3948992383967747160L;

	private JButton btnConnnexion, btnRegister;
	private JCheckBox chckbxSeSouvenirDe;
	private JLabel lblIdentifiantDeConnexion, lblPwd;
	private JPasswordField passwordField;

	private JTextField textField;

	private MasterController controller;

	/**
	 * Create the panel.
	 **/
	public IdentificationView(MasterController controller)
	{
		this.controller = controller;
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
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(getBtnRegister()).addComponent(getChckbxSeSouvenirDe()))).addContainerGap(316, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addComponent(getLblIdentifiantDeConnexion()).addGap(4).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(13).addComponent(getLblPwd()).addGap(4).addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(getBtnConnnexion()).addGap(7).addComponent(getChckbxSeSouvenirDe()).addGap(18).addComponent(getBtnRegister()).addGap(99)));
		setLayout(groupLayout);
	}

	private JButton getBtnConnnexion()
	{
		if (this.btnConnnexion == null)
		{
			this.btnConnnexion = new JButton("Connnexion");
			this.btnConnnexion.addActionListener(new BtnListener());
		}
		return this.btnConnnexion;
	}

	private JButton getBtnRegister()
	{
		if (this.btnRegister == null)
		{
			this.btnRegister = new JButton("S'enregistrer");
			this.btnRegister.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					IdentificationView.this.controller.changeView(Views.REGISTER, null);
				}
			});
		}
		return this.btnRegister;
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
		{
			this.passwordField = new JPasswordField();
			PromptSupport.setPrompt("Mot de passe", this.passwordField);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.passwordField);
		}
		return this.passwordField;
	}

	private String getPw()
	{
		return String.valueOf(getPasswordField().getPassword());
	}

	private JTextField getTextField()
	{
		if (this.textField == null)
		{
			this.textField = new JTextField(10);
			PromptSupport.setPrompt("Pseudo ou @ mail", this.textField);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.textField);
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
}