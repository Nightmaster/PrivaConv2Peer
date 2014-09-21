package fr.esgi.annuel.gui;

import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;
import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.constants.RegEx;
import fr.esgi.annuel.ctrl.MasterController;

public class RegisterView extends JPanel
{

	private enum FieldType
	{
		PSEUDO, EMAIL, FIRSTNAME, LASTNAME, PASSWORD;

		public final static FieldType getValue(String fieldType)
		{
			if (null != fieldType)
				for (FieldType v : values())
					if (v.toString().equalsIgnoreCase(fieldType))
						return v;
			throw new IllegalArgumentException();
		}
	}

	private static final long serialVersionUID = -2404019214589537423L;
	private JLabel lPseudo, lUserEmail, lLastname, lFirstname, lPassword, lPasswordAgain, lPasswordKey, lPasswordKeyAgain, lKeyLength;
	private JComboBox<Integer> fLenKey;
	private JTextField fPseudo, fEmail, fLastname, fFirstname;
	private JPasswordField fPassword, fPasswordAgain, fPasswordKey, fPasswordKeyAgain;
	private JButton btnNext, btnRegister;
	//@formatter:off
	private JComponent[]
			firstPartElements = {this.lPseudo, this.fPseudo, this.fEmail, this.lUserEmail, this.lLastname, this.fLastname, this.lFirstname, this.fFirstname, this.lPassword, this.fPassword, this.lPasswordAgain, this.fPasswordAgain, this.btnNext},
			secondPartElements = {this.lKeyLength, this.fLenKey, this.lPasswordKey, this.fPasswordKey, this.lPasswordKeyAgain, this.fPasswordKeyAgain, this.btnRegister};
	//@formatter:on

	private MasterController controller;

	/**
	 * Create and init the panel.
	 */
	public RegisterView(MasterController controller)
	{
		setLayout(null);
		add(getLabelPseudo());
		add(getFieldPseudo());
		add(getFieldEmail());
		add(getLabelUserEmail());
		add(getLabelLastname());
		add(getFieldLastname());
		add(getLabelFirstname());
		add(getFieldFirstname());
		add(getLabelPassword());
		add(getFieldPassword());
		add(getFieldPasswordAgain());
		add(getLabelPasswordAgain());
		add(getBtnNext());
		setAllEmentsActive(this.firstPartElements, this.secondPartElements);
	}

	private static List<String> createRegisterURL(List<String> details, List<JPasswordField> pwd) throws Exception
	{
		JPasswordField pwAccount = pwd.get(0), pwKey = pwd.get(1);
		MessageDigest mdPwdAccount = null;
		MessageDigest mdPwdKey = null;
		String hashtext = "", hashtextKey = "";
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

	private JButton getBtnNext()
	{
		if (this.btnNext == null)
		{
			this.btnNext = new JButton("Etape suivante");
			this.btnNext.setBounds(67, 174, 105, 23);
		}
		return this.btnNext;
	}

	private JButton getBtnRegister()
	{
		if (this.btnRegister == null)
		{
			this.btnRegister = new JButton("S'enregistrer");
			this.btnRegister.setBounds(74, 174, 93, 23);
		}
		return this.btnRegister;
	}

	private JTextField getFieldEmail()
	{
		if (this.fEmail == null)
		{
			this.fEmail = new JTextField(10);
			setPrompt("exemple@site.tld", this.fEmail);
			setFocusBehavior(SHOW_PROMPT, this.fEmail);
			this.fEmail.setBounds(134, 35, 95, 22);
		}
		return this.fEmail;
	}

	private JTextField getFieldFirstname()
	{
		if (this.fFirstname == null)
		{
			this.fFirstname = new JTextField(10);
			setPrompt("Votre prénom", this.fFirstname);
			setFocusBehavior(SHOW_PROMPT, this.fFirstname);
			this.fFirstname.setBounds(134, 89, 95, 22);
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
			this.fLastname.setBounds(134, 62, 95, 22);
		}
		return this.fLastname;
	}

	private JComboBox<Integer> getFieldLengthKey()
	{
		if (this.fLenKey == null)
		{
			this.fLenKey = new JComboBox<Integer>();
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
			this.fPassword.setBounds(134, 116, 95, 20);
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
			this.fPasswordAgain.setBounds(134, 143, 95, 20);
		}
		return this.fPasswordAgain;
	}

	private JPasswordField getFieldPasswordKey()
	{
		if (this.fPasswordKey == null)
		{
			this.fPasswordKey = new JPasswordField();
			setPrompt("Mot de passe de la clé", this.fPasswordKey);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordKey);
		}
		return this.fPasswordKey;
	}

	private JPasswordField getFieldPasswordKeyAgain()
	{
		if (this.fPasswordKeyAgain == null)
		{
			this.fPasswordKeyAgain = new JPasswordField();
			setPrompt("Mot de passe de la clé", this.fPasswordKey);
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
			this.fPseudo.setBounds(134, 8, 95, 22);
		}
		return this.fPseudo;
	}

	private JLabel getLabelFirstname()
	{
		if (this.lFirstname == null)
		{
			this.lFirstname = new JLabel("Prénom : ");
			this.lFirstname.setBounds(10, 91, 46, 14);
		}
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
		{
			this.lLastname = new JLabel("Nom : ");
			this.lLastname.setBounds(10, 66, 31, 14);
		}
		return this.lLastname;
	}

	private JLabel getLabelPassword()
	{
		if (this.lPassword == null)
		{
			this.lPassword = new JLabel("Mot de passe : ");
			this.lPassword.setBounds(10, 119, 74, 14);
		}
		return this.lPassword;
	}

	private JLabel getLabelPasswordAgain()
	{
		if (this.lPasswordAgain == null)
		{
			this.lPasswordAgain = new JLabel("Resaisir mot de passe : ");
			this.lPasswordAgain.setBounds(10, 146, 114, 14);
		}
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
		{
			this.lPseudo = new JLabel("Pseudo : ");
			this.lPseudo.setBounds(10, 12, 45, 14);
		}
		return this.lPseudo;
	}

	private JLabel getLabelUserEmail()
	{
		if (this.lUserEmail == null)
		{
			this.lUserEmail = new JLabel("Email : ");
			this.lUserEmail.setBounds(10, 39, 46, 14);
		}
		return this.lUserEmail;
	}

	private void setAllEmentsActive(JComponent[] elementsToShow, JComponent[] elementsToHide)
	{
		for (JComponent element : elementsToShow)
			element.setVisible(true);
		for (JComponent element : elementsToHide)
			element.setVisible(false);
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
