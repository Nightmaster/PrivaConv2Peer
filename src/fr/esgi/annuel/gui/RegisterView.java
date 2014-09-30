package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.esgi.annuel.constants.FieldType;
import fr.esgi.annuel.constants.RegEx;
import fr.esgi.annuel.crypt.PasswordUtilities;
import fr.esgi.annuel.ctrl.MasterController;

import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;

@SuppressWarnings(value = "unused")
public class RegisterView extends JPanel
{
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (RegisterView.this.btnNext.equals(e.getSource()))
			{
			boolean eqPw, rightPwFmt, rightPseudoFmt, rightEmailFmt, rightFnameFmt, rightLnameFmt;
			eqPw = RegisterView.this.fPassword.getPassword().equals(RegisterView.this.fPasswordAgain.getPassword());
			rightPwFmt = true;
			for (Boolean value : PasswordUtilities.isStrongEnough(String.valueOf(RegisterView.this.fPassword.getPassword())).values())
				if (!value)
					rightPwFmt = value;
			rightPseudoFmt = isValidFieldContent(RegisterView.this.fPseudo.getText(), FieldType.PSEUDO);
			rightEmailFmt = isValidFieldContent(RegisterView.this.fEmail.getText(), FieldType.EMAIL);
			rightFnameFmt = isValidFieldContent(RegisterView.this.fFirstname.getText(), FieldType.FIRSTNAME);
			rightLnameFmt = isValidFieldContent(RegisterView.this.fLastname.getText(), FieldType.LASTNAME);
			if (RegisterView.this.btnNext.equals(e.getSource()))
				if (eqPw && rightPwFmt && rightPseudoFmt && rightEmailFmt && rightFnameFmt && rightLnameFmt)
					changeComponents();
				else
				{
					StringBuilder sb = new StringBuilder(550);
					sb.append(!eqPw ? "Les deux mots de passe doivent \u00EAtre identiques !" : "");
					if (! "".equals(sb.toString()))
						sb.append("\n");
					sb.append(!rightPwFmt ? PasswordUtilities.PASSWORD_STANDARD_FORMAT : "");
					if (! "".equals(sb.toString()))
						sb.append("\n");
					sb.append(!rightPseudoFmt ? "Le pseudo doit \u00EAtre compos\u00E9 uniquement de caract\u00E8res alphanum\u00E9riques, sans espace !" : "");
					if (! "".equals(sb.toString()))
						sb.append("\n");
					sb.append(!rightEmailFmt ? "Veuillez rentrer une adresse email correcte !" : "");
					if (! "".equals(sb.toString()))
						sb.append("\n");
					sb.append(!rightLnameFmt ? "Votre nom doit \u00EAtre compos\u00E9 de caract\u00E8res alpahb\u00E9tiques uniquement (espaces accept\u00E9s)" : "");
					if (! "".equals(sb.toString()))
						sb.append("\n");
					sb.append(!rightFnameFmt ? "Votre nom doit \u00EAtre compos\u00E9 de caract\u00E8res alphab\u00E9tiques uniquement (espaces et traits d'union accept\u00E9s)" : "");
					JOptionPane.showMessageDialog(null, sb.toString(), "Invalid content", JOptionPane.ERROR_MESSAGE);
				}
			}
			else // if (RegisterView.this.btnRegister.equals(e.getSource()))
			{
				StringBuilder sb = new StringBuilder(); //FIXME initialiser la valeur du constructeur
				String pwK = String.valueOf(RegisterView.this.fPasswordKey.getPassword()), pwKagain = String.valueOf(RegisterView.this.fPasswordKeyAgain.getPassword());
				boolean eqPw, rightPwFmt;
				eqPw = pwK.equals(pwKagain);
				if(!eqPw)
					sb.append("Les deux mots de passe doivent \u00EAtre identiques !");
				rightPwFmt = true;
				for (Boolean value : PasswordUtilities.isStrongEnough(pwK).values())
					if (!value)
						rightPwFmt = value;
				if(true)
					;
				else
				{
					JOptionPane.showMessageDialog(null, sb.toString(), "Invalid content", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private static final long serialVersionUID = -1354403666889275249L;
	private JLabel lPseudo, lUserEmail, lLastname, lFirstname, lPassword, lPasswordAgain, lPasswordKey, lPasswordKeyAgain, lKeyLength;
	private JComboBox<Integer> fLenKey;
	private JTextField fPseudo, fEmail, fLastname, fFirstname;
	private JPasswordField fPassword, fPasswordAgain, fPasswordKey, fPasswordKeyAgain;
	private JButton btnNext, btnRegister;
	private JComponent[]
			firstPartElements = {this.lPseudo, this.fPseudo, this.fEmail, this.lUserEmail, this.lLastname, this.fLastname, this.lFirstname, this.fFirstname, this.lPassword, this.fPassword, this.lPasswordAgain, this.fPasswordAgain, this.btnNext},
			secondPartElements = {this.lKeyLength, this.fLenKey, this.lPasswordKey, this.fPasswordKey, this.lPasswordKeyAgain, this.fPasswordKeyAgain, this.btnRegister};

	private MasterController controller;

	/**
	 * Create and init the panel.
	 */
	public RegisterView(MasterController controller)
	{
		//@formatter:off
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
						.addComponent(getLabelPseudo())
					.addGap(79)
						.addComponent(getFieldPseudo(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
						.addComponent(getLabelUserEmail(), GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(78)
						.addComponent(getFieldEmail(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
						.addComponent(getLabelLastname())
					.addGap(93)
						.addComponent(getFieldLastname(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
						.addComponent(getLabelFirstname())
					.addGap(78)
						.addComponent(getFieldFirstname(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
						.addComponent(getLabelPassword())
					.addGap(50)
						.addComponent(getFieldPassword(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
						.addComponent(getLabelPasswordAgain())
					.addGap(10)
						.addComponent(getFieldPasswordAgain(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(67)
						.addComponent(getBtnNext()))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLabelPseudo()))
						.addComponent(getFieldPseudo(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLabelUserEmail()))
						.addComponent(getFieldEmail(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLabelLastname()))
						.addComponent(getFieldLastname(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(getLabelFirstname()))
						.addComponent(getFieldFirstname(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(getLabelPassword()))
						.addComponent(getFieldPassword(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(getLabelPasswordAgain()))
						.addComponent(getFieldPasswordAgain(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(getBtnNext()))
		);
		setLayout(groupLayout);
		//@formatter:on
	}

//	private static List<String> createRegisterURL(List<String> details, List<JPasswordField> pwd) throws Exception
//	{
//		JPasswordField pwAccount = pwd.get(0), pwKey = pwd.get(1);
//		MessageDigest mdPwdAccount = null;
//		MessageDigest mdPwdKey = null;
//		String hashtext = "", hashtextKey = "";
//		try
//		{
//			// hachage du mot de passe du compte
//			mdPwdAccount = MessageDigest.getInstance("MD5");
//			mdPwdAccount.reset();
//			mdPwdAccount.update(String.copyValueOf(pwAccount.getPassword()).getBytes());
//			byte[] digest = mdPwdAccount.digest();
//			BigInteger bigInt = new BigInteger(1, digest);
//			hashtext = bigInt.toString(16);
//			while (hashtext.length() < 32)
//				hashtext = "0" + hashtext;
//
//			// hachage du mot de passe de la clef
//			mdPwdKey = MessageDigest.getInstance("MD5");
//			mdPwdKey.reset();
//			mdPwdKey.update(String.copyValueOf(pwKey.getPassword()).getBytes());
//			byte[] digestKey = mdPwdKey.digest();
//			BigInteger bigIntKey = new BigInteger(1, digestKey);
//			hashtextKey = bigIntKey.toString(16);
//			while (hashtextKey.length() < 32)
//				hashtextKey = "0" + hashtextKey;
//
//		}
//		catch (NoSuchAlgorithmException e)
//		{
//			e.printStackTrace();
//		}
//		// construction de l'URL
//		List<String> url = new ArrayList<String>();
//		String urlConnect = PROPERTIES.getProperty("server.address") + ":" + PROPERTIES.getProperty("server.port") + "/" + SRV_API + "/" + SRV_REGISTER_PAGE;
//
//		String params = "?" + USERNAME + "=" + details.get(0) + "&" + EMAIL + "=" + details.get(1) + "&" + FIRSTNAME + "=" + details.get(2) + "&" + LASTNAME + "=" + details.get(3) + "&" + PASSWORD + "=" + hashtext + "&" + PASSWORD_KEY + "=" + hashtextKey + "&" + KEY_LENGTH + "=" + details.get(4);
//
//		url.add(urlConnect);
//		url.add(params);
//		return url;
//	}

	private static boolean isValidFieldContent(String fieldContent, FieldType fieldType)
	{
		if (FieldType.EMAIL.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_EMAIL.getRegEx());
		else if (FieldType.PSEUDO.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_PSEUDO.getRegEx());
		else if (FieldType.FIRSTNAME.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_FIRSTNAME.getRegEx());
		else if (FieldType.LASTNAME.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_LASTNAME.getRegEx());
		else
			throw new IllegalArgumentException();
	}
	private void changeComponents()
	{
		//@formatter:off
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(getLabelKeyLength())
					.addGap(62)
					.addComponent(getFieldLengthKey(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(getLabelPasswordKey())
					.addGap(43)
					.addComponent(getFieldPasswordKey(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(getLabelPasswordKeyAgain())
					.addComponent(getFieldPasswordKeyAgain(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(79)
					.addComponent(getBtnRegister()))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLabelKeyLength()))
						.addComponent(getFieldLengthKey(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLabelPasswordKey()))
						.addComponent(getFieldPasswordKey(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLabelPasswordKeyAgain()))
						.addComponent(getFieldPasswordKeyAgain(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addComponent(getBtnRegister()))
		);
		//@formatter:on
		setLayout(groupLayout);
		setAllEmentsActive(this.secondPartElements, this.firstPartElements);
		this.controller.packFrame();
	}


	private JButton getBtnNext()
	{
		if (this.btnNext == null)
		{
			this.btnNext = new JButton("Etape suivante");
			this.btnNext.addActionListener(new ButtonListener());
		}
		return this.btnNext;
	}

	private JButton getBtnRegister()
	{
		if (this.btnRegister == null)
			this.btnRegister = new JButton("S'enregistrer");
		return this.btnRegister;
	}

	private JTextField getFieldEmail()
	{
		if (this.fEmail == null)
		{
			this.fEmail = new JTextField(10);
			setPrompt("exemple@site.tld", this.fEmail);
			setFocusBehavior(SHOW_PROMPT, this.fEmail);
		}
		return this.fEmail;
	}

	private JTextField getFieldFirstname()
	{
		if (this.fFirstname == null)
		{
			this.fFirstname = new JTextField(10);
			setPrompt("Votre pr\u00E9nom", this.fFirstname);
			setFocusBehavior(SHOW_PROMPT, this.fFirstname);
		}
		return this.fFirstname;
	}

	private JTextField getFieldLastname()
	{
		if (this.fLastname == null)
		{
			this.fLastname = new JTextField(10);
			setPrompt("Votre nom", this.fLastname);
			setFocusBehavior(SHOW_PROMPT, this.fLastname);
		}
		return this.fLastname;
	}

	private JComboBox<Integer> getFieldLengthKey()
	{
		if (this.fLenKey == null)
		{
			this.fLenKey = new JComboBox<Integer>();
			this.fLenKey.setMaximumRowCount(4);
			this.fLenKey.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1024, 2048, 4096}));
			this.fLenKey.setToolTipText("<html>\r\nLongueur de clefs de cryptage :<br>\r\n\t- 1024 : peu s\u00E9curis\u00E9, mais traitement rapide <br>\r\n  \t- 2048 : bon rapport s\u00E9curit\u00E9 / vitesse de traitement <br>\r\n  \t- 4096 : tr\u00E8s s\u00E9curis\u00E9, mais vitesse de traitement plus lente <br>\r\n</html>");
		}
		return this.fLenKey;
	}

	private JPasswordField getFieldPassword()
	{
		if (this.fPassword == null)
		{
			this.fPassword = new JPasswordField();
			setPrompt("Mot de passe de session", this.fPassword);
			setFocusBehavior(SHOW_PROMPT, this.fPassword);
			this.fPassword.setToolTipText("<html>\r\n<pre>\r\nLe mot de passe doit \u00EAtre d'au moins 8 caract\u00E8res et \u00EAtre compos\u00E9 de :\r\n\t- Au moins 1 majuscule\r\n\t- Au moins 1 minuscule\r\n\t- Au moins 1 chiffre\r\n\t- Au moins 1 caract\u00E8re sp\u00E9cial\r\n</pre>\r\n</html>");

		}
		return this.fPassword;
	}

	private JPasswordField getFieldPasswordAgain()
	{
		if (this.fPasswordAgain == null)
		{
			this.fPasswordAgain = new JPasswordField();
			setPrompt("Mot de passe de session", this.fPasswordAgain);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordAgain);
		}
		return this.fPasswordAgain;
	}

	private JPasswordField getFieldPasswordKey()
	{
		if (this.fPasswordKey == null)
		{
			this.fPasswordKey = new JPasswordField();
			setPrompt("Mot de passe de la cl\u00E9", this.fPasswordKey);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordKey);
			this.fPasswordKey.setToolTipText("Mot de passe servant \u00E0 crypter votre paire de clefs RSA\r\nCe mot de passe doit \u00EAtre diff\u00E9rent de celui de connexion (pour des raisons de s\u00E9curit\u00E9)");
		}
		return this.fPasswordKey;
	}

	private JPasswordField getFieldPasswordKeyAgain()
	{
		if (this.fPasswordKeyAgain == null)
		{
			this.fPasswordKeyAgain = new JPasswordField();
			setPrompt("Mot de passe de la cl\u00E9", this.fPasswordKey);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordKey);
		}
		return this.fPasswordKeyAgain;
	}

	private JTextField getFieldPseudo()
	{
		if (this.fPseudo == null)
		{
			this.fPseudo = new JTextField(10);
			setPrompt("Pseudonyme", this.fPseudo);
			setFocusBehavior(SHOW_PROMPT, this.fPseudo);
		}
		return this.fPseudo;
	}

	private JLabel getLabelFirstname()
	{
		if (this.lFirstname == null)
			this.lFirstname = new JLabel("Pr\u00E9nom : ");
		return this.lFirstname;
	}

	private JLabel getLabelKeyLength()
	{
		if (this.lKeyLength == null)
			this.lKeyLength = new JLabel("Longueur clef :");
		return this.lKeyLength;
	}

	private JLabel getLabelLastname()
	{
		if (this.lLastname == null)
			this.lLastname = new JLabel("Nom : ");
		return this.lLastname;
	}

	private JLabel getLabelPassword()
	{
		if (this.lPassword == null)
			this.lPassword = new JLabel("Mot de passe : ");
		return this.lPassword;
	}

	private JLabel getLabelPasswordAgain()
	{
		if (this.lPasswordAgain == null)
			this.lPasswordAgain = new JLabel("Resaisir mot de passe : ");
		return this.lPasswordAgain;
	}

	private JLabel getLabelPasswordKey()
	{
		if (this.lPasswordKey == null)
			this.lPasswordKey = new JLabel("Mot de passe clef: ");
		return this.lPasswordKey;
	}

	private JLabel getLabelPasswordKeyAgain()
	{
		if (this.lPasswordKeyAgain == null)
			this.lPasswordKeyAgain = new JLabel("Resaisir mot de passe clef : ");
		return this.lPasswordKeyAgain;
	}

	private JLabel getLabelPseudo()
	{
		if (this.lPseudo == null)
			this.lPseudo = new JLabel("Pseudo : ");
		return this.lPseudo;
	}

	private JLabel getLabelUserEmail()
	{
		if (this.lUserEmail == null)
			this.lUserEmail = new JLabel("Email : ");
		return this.lUserEmail;
	}

	private void setAllEmentsActive(JComponent[] elementsToShow, JComponent[] elementsToHide)
	{
		for (JComponent element : elementsToShow)
			element.setVisible(true);
		for (JComponent element : elementsToHide)
			element.setVisible(false);
	}
}
