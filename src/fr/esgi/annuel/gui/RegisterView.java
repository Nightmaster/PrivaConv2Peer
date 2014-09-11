package fr.esgi.annuel.gui;

import java.awt.Dimension;
import java.awt.Font;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.jdesktop.xswingx.PromptSupport;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.crypt.PasswordUtilities;
import fr.esgi.annuel.ctrl.MasterController;

public class RegisterView extends JPanel
{
	private class BtnListener implements ActionListener
	{
		private HashMap<String, Boolean> testMdp, testMdpKey;
		private List params, pwds, invalidParamsMdp, invalidParamsMdpKey;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			this.params = new ArrayList<String>();
			this.pwds = new ArrayList<JPasswordField>();
			StringBuilder sb = new StringBuilder();
			int nbInvalid = 0;
			this.testMdp = PasswordUtilities.isStrongEnough(String.copyValueOf(getFieldPassword().getPassword()));
			this.testMdpKey = PasswordUtilities.isStrongEnough(String.copyValueOf(getFieldPasswordKey().getPassword()));
			this.invalidParamsMdp = new ArrayList<String>();
			this.invalidParamsMdpKey = new ArrayList<String>();

			if (isValidPseudo(getFieldPseudo().getText()))
				this.params.add(getFieldPseudo().getText());
			else
			{
				sb.append("Pseudo invalide" + '\n');
				nbInvalid++ ;
			}
			if (isValidEmail(getFieldEmail().getText()))
				this.params.add(getFieldEmail().getText());
			else
			{
				sb.append("email invalide" + '\n');
				nbInvalid++ ;
			}
			if (isValidName(getFieldLastname().getText()))
				this.params.add(getFieldLastname().getText());
			else
			{
				sb.append("nom invalide" + '\n');
				nbInvalid++ ;
			}
			if (isValidName(getFieldFirstname().getText()))
				this.params.add(getFieldFirstname().getText());
			else
			{
				sb.append("prenom invalide" + '\n');
				nbInvalid++ ;
			}

			for (Entry<String, Boolean> entry : this.testMdp.entrySet())
				if (!entry.getValue())
					this.invalidParamsMdp.add(entry.getKey());
			if (this.invalidParamsMdp.size() > 0)
			{
				StringBuilder sbmdp = new StringBuilder();
				for (String s : (ArrayList<String>) this.invalidParamsMdp)
				{
					if (s.equals("Length"))
						sbmdp.append("Mdp trop court, 8 caractères minimum" + '\n');
					if (s.equals("Maj letter"))
						sbmdp.append("Le mot de passe doit contenir une Majuscule" + '\n');
					if (s.equals("Minus letter"))
						sbmdp.append("Le mot de passe doit contenir une Minuscule" + '\n');
					if (s.equals("Well formated"))
						sbmdp.append("Mot de passe mal formé" + '\n');
					if (s.equals("Special character"))
						sbmdp.append("Le mot de passe doit contenir un caractère spécial " + '\n');
					if (s.equals("Number"))
						sbmdp.append("Le mot de passe doit contenir un numéro" + '\n');
				}
				nbInvalid++ ;
				JOptionPane.showMessageDialog(null, sbmdp.toString(), "Requis", JOptionPane.OK_OPTION);

			}

			for (Entry<String, Boolean> entry : this.testMdpKey.entrySet())
				if (!entry.getValue())
					this.invalidParamsMdpKey.add(entry.getKey());
			if (this.invalidParamsMdpKey.size() > 0)
			{
				StringBuilder sbmdp = new StringBuilder();
				for (String s : (ArrayList<String>) this.invalidParamsMdpKey)
				{
					if (s.equals("Length"))
						sbmdp.append("Mdp trop court, 8 caractères minimum" + '\n');
					if (s.equals("Maj letter"))
						sbmdp.append("Le mot de passe doit contenir une Majuscule" + '\n');
					if (s.equals("Minus letter"))
						sbmdp.append("Le mot de passe doit contenir une Minuscule" + '\n');
					if (s.equals("Well formated"))
						sbmdp.append("Mot de passe mal formé" + '\n');
					if (s.equals("Special character"))
						sbmdp.append("Le mot de passe doit contenir un caractère spécial " + '\n');
					if (s.equals("Number"))
						sbmdp.append("Le mot de passe doit contenir un numéro" + '\n');
				}
				nbInvalid++ ;
				JOptionPane.showMessageDialog(null, sbmdp.toString(), "Requis", JOptionPane.OK_OPTION);

			}

			if (String.copyValueOf(getFieldPassword().getPassword()).equals(String.copyValueOf(getFieldPasswordAgain().getPassword())) && !String.copyValueOf(getFieldPasswordKey().getPassword()).equals(String.copyValueOf(getFieldPassword().getPassword())))
				this.pwds.add(getFieldPassword());
			else
			{
				sb.append("Mot de passe invalide" + '\n');
				nbInvalid++ ;
			}

			if (String.copyValueOf(getFieldPasswordKey().getPassword()).equals(String.copyValueOf(getFieldPasswordKeyAgain().getPassword())) && !String.copyValueOf(getFieldPasswordKey().getPassword()).equals(String.copyValueOf(getFieldPassword().getPassword())))
				this.pwds.add(getFieldPasswordKey());
			else
			{
				sb.append("Mot de passe de la clef invalide" + '\n');
				nbInvalid++ ;
			}

			this.params.add(getFieldLengthKey().getSelectedItem().toString());

			try
			{
				if (nbInvalid == 0)
				{
					List<String> urlRegister = createRegisterURL(this.params, this.pwds);
					URL url = new URL(urlRegister.get(0) + urlRegister.get(1));
					URLConnection urlConn = url.openConnection();
					urlConn.setDoOutput(true);

					BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
					in.close();

				}
				else
					JOptionPane.showMessageDialog(null, sb.toString(), "Requis", JOptionPane.OK_OPTION);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 **/
	private static final long serialVersionUID = 4899250229943737308L;

	private JLabel lPseudo, lUserEmail, lLastname, lFirstname, lPassword, lPasswordAgain, lPasswordKey, lPasswordKeyAgain, lKeyLength;
	private JComboBox<Integer> fLenKey;
	private JTextField fPseudo, fEmail, fLastname, fFirstname;
	private JPasswordField fPassword, fPasswordAgain, fPasswordKey, fPasswordKeyAgain;
	private JButton btnRegister;
	private MasterController controller;

	/**
	 * Create the panel.
	 */
	public RegisterView(MasterController controller)
	{
		this.controller = controller;
		setTextField(getFieldPseudo());
		setTextField(getFieldEmail());
		setTextField(getFieldLastname());
		setTextField(getFieldFirstname());
		setPasswordField(getFieldPassword());
		setPasswordField(getFieldPasswordAgain());
		setPasswordField(getFieldPasswordKey());
		setPasswordField(getFieldPasswordKeyAgain());
		this.fPassword.setToolTipText("<html>\r\n<pre>\r\nLe mot de passe doit \u00EAtre d'au moins 8 caract\u00E8res et \u00EAtre compos\u00E9 de :\r\n\t- Au moins 1 majuscule\r\n\t- Au moins 1 minuscule\r\n\t- Au moins 1 chiffre\r\n\t- Au moins 1 caract\u00E8re sp\u00E9cial\r\n</pre>\r\n</html>");
		this.fPasswordKey.setToolTipText("Mot de passe servant \u00E0 crypter votre paire de clefs RSA\r\nCe mot de passe doit \u00EAtre diff\u00E9rent de celui de connexion (pour des raisons de s\u00E9curit\u00E9)");
		this.setBounds(0, 39, 1184, 750);
		GroupLayout glTop = new GroupLayout(this);
		glTop.setHorizontalGroup(glTop.createParallelGroup(Alignment.LEADING)
				.addGroup(
						glTop.createSequentialGroup()
								.addGroup(
										glTop.createParallelGroup(Alignment.LEADING)
												.addGroup(glTop.createSequentialGroup().addGap(221).addComponent(getBtnRegister()))
												.addGroup(
														glTop.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		glTop.createParallelGroup(Alignment.TRAILING).addComponent(getLabelPasswordKeyAgain()).addComponent(getLabelPasswordKey()).addComponent(getLabelPasswordAgain()).addComponent(getLabelPassword()).addComponent(getLabelFirstname()).addComponent(getLabelUserEmail()).addComponent(getLabelPseudo()).addComponent(getLabelLastname())
																				.addComponent(getLabelKeyLength()))
																.addGroup(
																		glTop.createParallelGroup(Alignment.LEADING)
																				.addGroup(
																						glTop.createSequentialGroup()
																								.addGap(33)
																								.addGroup(
																										glTop.createParallelGroup(Alignment.LEADING).addComponent(getFieldLastname(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getFieldFirstname(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(getFieldEmail(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getFieldPseudo(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
																				.addGroup(
																						Alignment.TRAILING,
																						glTop.createSequentialGroup()
																								.addGap(33)
																								.addGroup(
																										glTop.createParallelGroup(Alignment.LEADING, false).addComponent(getFieldPasswordAgain(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(getFieldPassword(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																												.addComponent(getFieldPasswordKey(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(getFieldPasswordKeyAgain(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																												.addComponent(getFieldLengthKey(), 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))).addContainerGap(826, Short.MAX_VALUE)));
		glTop.setVerticalGroup(glTop.createParallelGroup(Alignment.LEADING).addGroup(
				glTop.createSequentialGroup().addGap(21).addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getLabelPseudo()).addComponent(getFieldPseudo(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(19)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getLabelUserEmail(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getLabelUserEmail())).addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getLabelLastname(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getLabelLastname())).addGap(9)
						.addGroup(glTop.createParallelGroup(Alignment.LEADING).addGroup(glTop.createSequentialGroup().addGap(8).addComponent(getLabelFirstname())).addComponent(getFieldFirstname(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getFieldPassword(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getLabelPassword())).addGap(9)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getLabelPasswordAgain()).addComponent(getFieldPasswordAgain(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getLabelPasswordKey()).addComponent(getFieldPasswordKey(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getLabelPasswordKeyAgain()).addComponent(getFieldPasswordKeyAgain(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(20)
						.addGroup(glTop.createParallelGroup(Alignment.BASELINE).addComponent(getFieldLengthKey(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getLabelKeyLength())).addGap(18).addComponent(getBtnRegister()).addContainerGap(314, Short.MAX_VALUE)));
		setLayout(glTop);
	}

	private static List<String> createRegisterURL(List<String> details, List<JPasswordField> pwd) throws Exception
	{
		JPasswordField pwAccount = pwd.get(0);
		JPasswordField pwKey = pwd.get(1);
		MessageDigest mdPwdAccount = null;
		MessageDigest mdPwdKey = null;
		String hashtext = "";
		String hashtextKey = "";
		try
		{
			// hachage du mot de passe du compte
			mdPwdAccount = MessageDigest.getInstance("MD5");
			mdPwdAccount.reset();
			mdPwdAccount.update(String.copyValueOf(pwAccount.getPassword()).getBytes());
			byte[] digest = mdPwdAccount.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			while (hashtext.length() < 32)
				hashtext = "0" + hashtext;

			// hachage du mot de passe de la clef
			mdPwdKey = MessageDigest.getInstance("MD5");
			mdPwdKey.reset();
			mdPwdKey.update(String.copyValueOf(pwKey.getPassword()).getBytes());
			byte[] digestKey = mdPwdKey.digest();
			BigInteger bigIntKey = new BigInteger(1, digestKey);
			hashtextKey = bigIntKey.toString(16);
			while (hashtextKey.length() < 32)
				hashtextKey = "0" + hashtextKey;

		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		// construction de l'URL
		List<String> url = new ArrayList<String>();
		String urlConnect = Constants.SRV_URL + ":" + Constants.SRV_PORT + "/" + Constants.SRV_API + "/" + Constants.SRV_REGISTER_PAGE;

		String params = "?" + Constants.PARAM_USER + "=" + details.get(0) + "&" + Constants.PARAM_EMAIL + "=" + details.get(1) + "&" + Constants.PARAM_FIRSTNAME + "=" + details.get(2) + "&" + Constants.PARAM_NAME + "=" + details.get(3) + "&" + Constants.PARAM_PWD + "=" + hashtext + "&" + Constants.PARAM_PWD_KEY + "=" + hashtextKey + "&" + Constants.PARAM_LENGTH + "=" + details.get(4);

		url.add(urlConnect);
		url.add(params);
		return url;
	}

	private static boolean isValidEmail(String text)
	{
		if (text.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))
			return true;
		else
			return false;
	}

	private static boolean isValidName(String text)
	{

		if (text.matches("^[a-zA-Z-]*$") && text.length() > 1)
			return true;
		else
			return false;
	}

	private static boolean isValidPseudo(String text)
	{
		if (text.matches("^[a-zA-Z0-9_-]*$") && text.length() > 2)
			return true;
		else
			return false;
	}

	private JButton getBtnRegister()
	{
		if (null == this.btnRegister)
			this.btnRegister = new JButton("S'enregistrer");
		return this.btnRegister;
	}

	private JTextField getFieldEmail()
	{
		if (null == this.fEmail)
		{
			this.fEmail = new JTextField("");
			PromptSupport.setPrompt("addresse@exemple.com", this.fEmail);
		}
		return this.fEmail;
	}

	private JTextField getFieldFirstname()
	{
		if (null == this.fFirstname)
		{
			this.fFirstname = new JTextField("");
			PromptSupport.setPrompt("Votre prénom", this.fFirstname);
		}
		return this.fFirstname;
	}

	private JTextField getFieldLastname()
	{
		if (null == this.fLastname)
		{
			this.fLastname = new JTextField("");
			PromptSupport.setPrompt("Votre prénom", this.fLastname);
		}
		return this.fLastname;
	}

	private JComboBox<Integer> getFieldLengthKey()
	{
		if (null == this.fLenKey)
		{
			this.fLenKey = new JComboBox<Integer>();
			this.fLenKey.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1024, 2048, 4096}));
			this.fLenKey.setToolTipText("<html>\r\nLongueur de clefs de cryptage :<br>\r\n\t- 1024 : peu s\u00E9curis\u00E9, mais traitement rapide <br>\r\n  \t- 2048 : bon rapport s\u00E9curit\u00E9 / vitesse de traitement <br>\r\n  \t- 4096 : tr\u00E8s s\u00E9curis\u00E9, mais vitesse de traitement plus lente <br>\r\n</html>");
		}
		return this.fLenKey;
	}

	private JPasswordField getFieldPassword()
	{
		if (null == this.fPassword)
			this.fPassword = new JPasswordField();
		return this.fPassword;
	}

	private JPasswordField getFieldPasswordAgain()
	{
		if (null == this.fPasswordAgain)
			this.fPasswordAgain = new JPasswordField();
		return this.fPasswordAgain;
	}

	private JPasswordField getFieldPasswordKey()
	{
		if (null == this.fPasswordKey)
			this.fPasswordKey = new JPasswordField();
		return this.fPasswordKey;
	}

	private JPasswordField getFieldPasswordKeyAgain()
	{
		if (null == this.fPasswordKeyAgain)
			this.fPasswordKeyAgain = new JPasswordField();
		return this.fPasswordKeyAgain;
	}

	private JTextField getFieldPseudo()
	{
		if (null == this.fPseudo)
		{
			this.fPseudo = new JTextField("");
			PromptSupport.setPrompt("Votre pseudonyme", this.fPseudo);
		}
		return this.fPseudo;
	}

	private JLabel getLabelFirstname()
	{
		if (null == this.lFirstname)
			this.lFirstname = new JLabel("Prénom : ");
		return this.lFirstname;
	}

	private JLabel getLabelKeyLength()
	{
		if (null == this.lKeyLength)
			this.lKeyLength = new JLabel("Longueur clef :");
		return this.lKeyLength;
	}

	private JLabel getLabelLastname()
	{
		if (null == this.lLastname)
			this.lLastname = new JLabel("Nom : ");
		return this.lLastname;
	}

	private JLabel getLabelPassword()
	{
		if (null == this.lPassword)
			this.lPassword = new JLabel("Mot de passe : ");
		return this.lPassword;
	}

	private JLabel getLabelPasswordAgain()
	{
		if (null == this.lPasswordAgain)
			this.lPasswordAgain = new JLabel("Resaisir mot de passe : ");
		return this.lPasswordAgain;
	}

	private JLabel getLabelPasswordKey()
	{
		if (null == this.lPasswordKey)
			this.lPasswordKey = new JLabel("Mot de passe clef: ");
		return this.lPasswordKey;
	}

	private JLabel getLabelPasswordKeyAgain()
	{
		if (null == this.lPasswordKeyAgain)
			this.lPasswordKeyAgain = new JLabel("Resaisir mot de passe clef : ");
		return this.lPasswordKeyAgain;
	}

	private JLabel getLabelPseudo()
	{
		if (null == this.lPseudo)
			this.lPseudo = new JLabel("Pseudo : ");
		return this.lPseudo;
	}

	private JLabel getLabelUserEmail()
	{
		if (null == this.lUserEmail)
			this.lUserEmail = new JLabel("Email : ");
		return this.lUserEmail;
	}

	private void setPasswordField(JPasswordField pf)
	{
		Font police = new Font("Arial", Font.BOLD, 14);
		pf.setFont(police);
		pf.setPreferredSize(new Dimension(150, 30));
	}

	private void setTextField(JTextField tf)
	{
		Font police = new Font("Arial", Font.BOLD, 14);
		tf.setFont(police);
		tf.setPreferredSize(new Dimension(150, 30));
	}
}