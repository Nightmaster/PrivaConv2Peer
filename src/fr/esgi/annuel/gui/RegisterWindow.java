package fr.esgi.annuel.gui;

import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.crypt.PasswordUtilities;

public class RegisterWindow
{
	protected static List<String> createRegisterURL(List<String> details, List<JPasswordField> pwd) throws Exception
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

	protected static boolean isValidEmail(String text)
	{
		if (text.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))
			return true;
		else
			return false;
	}

	protected static boolean isValidName(String text, boolean isFisrtame)
	{
		if (true == isFisrtame)
			if (text.matches("^[-a-zA-Z]*$") && text.length() > 1)
				return true;
			else
				return false;
		else if (text.matches("^[-a-zA-Z ]*$") && text.length() > 1)
			return true;
		else
			return false;
	}

	protected static boolean isValidPseudo(String text)
	{
		if (text.matches("^[a-zA-Z0-9_-]*$") && text.length() > 2)
			return true;
		else
			return false;
	}

	protected static void pwInformations(List<String> invalidParamsMdp, int invalid)
	{
		StringBuilder sbmdp = new StringBuilder();
		for (String s : invalidParamsMdp)
		{
			if (s.equals("Length"))
				sbmdp.append("Le mot de passe est trop court. Il doit faire au moins 8 caractères de long." + '\n');
			if (s.equals("Maj letter"))
				sbmdp.append("Le mot de passe doit contenir au moins une majuscule." + '\n');
			if (s.equals("Minus letter"))
				sbmdp.append("Le mot de passe doit contenir au moins une minuscule." + '\n');
			if (s.equals("Well formated"))
				sbmdp.append("Mot de passe incorrect. Il ne peut contenir que les caractères suivants:" + stringFromCharSequence(Constants.SPEC_CHARS) + '\n');
			if (s.equals("Special character"))
				sbmdp.append("Le mot de passe doit contenir au moins un caractère spécial parmi les suivants:" + stringFromCharSequence(Constants.SPEC_CHARS) + '\n');
			if (s.equals("Number"))
				sbmdp.append("Le mot de passe doit contenir au moins un numéro." + '\n');
		}
		invalid++ ;
		JOptionPane.showMessageDialog(null, sbmdp.toString(), "Requis", JOptionPane.OK_OPTION);
	}

	protected static String stringFromCharSequence(char[] charSeq)
	{
		String res = new String();
		for (char c : charSeq)
			res += c;
		return res;
	}

	public static void main(String[] args)
	{

		final JFrame fenetre = new JFrame();

		final JLabel lPseudo = new JLabel("Pseudo : "), lUsermail = new JLabel("Email : "), lLastname = new JLabel("Nom : "), lFirstname = new JLabel("Prénom : "), lPassword = new JLabel("Mot de passe : "), lPasswordAgain = new JLabel("Resaisir mot de passe : "), lPasswordKey = new JLabel("Mot de passe clef: "), lPasswordKeyAgain = new JLabel("Resaisir mot de passe clef : ");
		lLastname.setBounds(113, 119, 31, 14);
		lPseudo.setBounds(99, 29, 45, 14);
		lUsermail.setBounds(110, 78, 34, 14);
		lFirstname.setBounds(98, 158, 46, 14);
		lPassword.setBounds(70, 199, 74, 14);
		lPasswordAgain.setBounds(30, 238, 114, 14);
		lPasswordKey.setBounds(53, 279, 91, 14);
		lPasswordKeyAgain.setBounds(10, 320, 134, 14);
		final JComboBox<Integer> fLenKey = new JComboBox<Integer>();
		fLenKey.setBounds(177, 362, 150, 22);

		final JTextField fPseudo = new JTextField(""), fMail = new JTextField(""), fLastname = new JTextField(""), fFirstname = new JTextField("");
		fPseudo.setBounds(177, 21, 150, 30);
		fMail.setBounds(177, 70, 150, 30);
		fFirstname.setBounds(177, 150, 150, 30);
		fLastname.setBounds(177, 111, 150, 30);
		fMail.setToolTipText("");
		final JPasswordField fPassword = new JPasswordField(), fPasswordAgain = new JPasswordField(), fPasswordKey = new JPasswordField(), fPasswordKeyAgain = new JPasswordField();
		fPasswordKeyAgain.setBounds(177, 312, 150, 30);
		fPasswordKey.setBounds(177, 271, 150, 30);
		fPassword.setBounds(177, 191, 150, 30);
		fPasswordAgain.setBounds(177, 230, 150, 30);
		fPassword.setToolTipText("<html>\r\n<pre>\r\nLe mot de passe doit \u00EAtre d'au moins 8 caract\u00E8res et \u00EAtre compos\u00E9 de :\r\n\t- Au moins 1 majuscule\r\n\t- Au moins 1 minuscule\r\n\t- Au moins 1 chiffre\r\n\t- Au moins 1 caract\u00E8re sp\u00E9cial\r\n</pre>\r\n</html>");
		fPasswordKey.setToolTipText("Mot de passe servant \u00E0 crypter votre paire de clefs RSA\r\nCe mot de passe doit \u00EAtre diff\u00E9rent de celui de connexion (pour des raisons de s\u00E9curit\u00E9)");

		fenetre.setTitle("Inscription");
		fenetre.setSize(613, 557);
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);

		fenetre.setBackground(Color.white);
		JPanel top = new JPanel();
		top.setBounds(0, 0, 341, 433);
		Font police = new Font("Arial", Font.BOLD, 14);
		fenetre.getContentPane().setLayout(null);
		fPseudo.setFont(police);
		fPseudo.setPreferredSize(new Dimension(150, 30));
		fPseudo.setForeground(Color.BLUE);

		fMail.setFont(police);
		fMail.setPreferredSize(new Dimension(150, 30));
		fMail.setForeground(Color.BLUE);

		fLastname.setFont(police);
		fLastname.setPreferredSize(new Dimension(150, 30));
		fLastname.setForeground(Color.BLUE);

		fFirstname.setFont(police);
		fFirstname.setPreferredSize(new Dimension(150, 30));
		fFirstname.setForeground(Color.BLUE);

		fPassword.setFont(police);
		fPassword.setPreferredSize(new Dimension(150, 30));
		fPassword.setForeground(Color.BLUE);

		fPasswordAgain.setFont(police);
		fPasswordAgain.setPreferredSize(new Dimension(150, 30));
		fPasswordAgain.setForeground(Color.BLUE);

		fPasswordKey.setFont(police);
		fPasswordKey.setPreferredSize(new Dimension(150, 30));
		fPasswordKey.setForeground(Color.BLUE);

		fPasswordKeyAgain.setFont(police);
		fPasswordKeyAgain.setPreferredSize(new Dimension(150, 30));
		fPasswordKeyAgain.setForeground(Color.BLUE);

		fenetre.getContentPane().add(top);

		JButton btnNewButton = new JButton("S'enregistrer");
		btnNewButton.setBounds(221, 402, 93, 23);

		// TODO déplacer ce code dans la master Window
		btnNewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					List<String> params = new ArrayList<String>();
					List<JPasswordField> pwds = new ArrayList<JPasswordField>();
					StringBuilder sb = new StringBuilder();
					int nbInvalid = 0;
					HashMap<String, Boolean> testMdp = PasswordUtilities.isStrongEnough(String.copyValueOf(fPassword.getPassword())), testMdpKey = PasswordUtilities.isStrongEnough(String.copyValueOf(fPasswordKey.getPassword()));
					List<String> invalidParamsMdp = new ArrayList<String>(), invalidParamsMdpKey = new ArrayList<String>();

					if (isValidPseudo(fPseudo.getText()))
						params.add(fPseudo.getText());
					else
					{
						sb.append("Pseudo invalide. Il doit faire au moins 3 caractères et ne peut contenir que des caractères alphanumériques, des chiffres et les 2 caractères suivants: \"-\" & \"_\"." + '\n');
						nbInvalid++ ;
					}
					if (isValidEmail(fMail.getText()))
						params.add(fMail.getText());
					else
					{
						sb.append("Email invalide. Veuillez le vérifier." + '\n');
						nbInvalid++ ;
					}
					if (isValidName(fLastname.getText(), false))
						params.add(fLastname.getText());
					else
					{
						sb.append("Nom invalide. Il doit faire au moins 3 caractères et ne peut contenir que des caractères alphanumériques, des chiffres et les 3 caractères suivants: \"-\", \"_\" & \" \"." + '\n');
						nbInvalid++ ;
					}
					if (isValidName(fFirstname.getText(), true))
						params.add(fFirstname.getText());
					else
					{
						sb.append("Prénom invalide. Il ne peut pas contenir de caractère espace." + '\n');
						nbInvalid++ ;
					}

					for (Entry<String, Boolean> entry : testMdp.entrySet())
						if (!entry.getValue())
							invalidParamsMdp.add(entry.getKey());
					if (invalidParamsMdp.size() > 0)
						pwInformations(invalidParamsMdp, nbInvalid);

					for (Entry<String, Boolean> entry : testMdpKey.entrySet())
						if (!entry.getValue())
							invalidParamsMdpKey.add(entry.getKey());
					if (invalidParamsMdpKey.size() > 0)
						pwInformations(invalidParamsMdp, nbInvalid);

					if (String.copyValueOf(fPassword.getPassword()).equals(String.copyValueOf(fPasswordAgain.getPassword())) && !String.copyValueOf(fPasswordKey.getPassword()).equals(String.copyValueOf(fPassword.getPassword())))
						pwds.add(fPassword);
					else
					{
						sb.append("Mot de passe invalide" + '\n');
						nbInvalid++ ;
					}

					if (String.copyValueOf(fPasswordKey.getPassword()).equals(String.copyValueOf(fPasswordKeyAgain.getPassword())) && !String.copyValueOf(fPasswordKey.getPassword()).equals(String.copyValueOf(fPassword.getPassword())))
						pwds.add(fPasswordKey);
					else
					{
						sb.append("Mot de passe de la clef invalide" + '\n');
						nbInvalid++ ;
					}

					params.add(fLenKey.getSelectedItem().toString());

					if (nbInvalid == 0)
					{
						List<String> urlRegister = createRegisterURL(params, pwds);
						URL url = new URL(urlRegister.get(0) + urlRegister.get(1));
						URLConnection urlConn = url.openConnection();
						urlConn.setDoOutput(true);

						BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
						in.close();
						fenetre.setVisible(false);

					}
					else
						JOptionPane.showMessageDialog(null, sb.toString(), "Requis", JOptionPane.OK_OPTION);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});

		fLenKey.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1024, 2048, 4096}));
		fLenKey.setToolTipText("<html>\r\nLongueur de clefs de cryptage :<br>\r\n\t- 1024 : peu s\u00E9curis\u00E9, mais traitement rapide <br>\r\n  \t- 2048 : bon rapport s\u00E9curit\u00E9 / vitesse de traitement <br>\r\n  \t- 4096 : tr\u00E8s s\u00E9curis\u00E9, mais vitesse de traitement plus lente <br>\r\n</html>");

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(144, 362, 0, 0);
		top.setLayout(null);
		top.add(btnNewButton);
		top.add(lPasswordKeyAgain);
		top.add(lPasswordKey);
		top.add(lPasswordAgain);
		top.add(lPassword);
		top.add(lFirstname);
		top.add(lUsermail);
		top.add(lPseudo);
		top.add(lLastname);
		top.add(lblNewLabel);
		top.add(fLastname);
		top.add(fFirstname);
		top.add(fMail);
		top.add(fPseudo);
		top.add(fPasswordAgain);
		top.add(fPassword);
		top.add(fPasswordKey);
		top.add(fPasswordKeyAgain);
		top.add(fLenKey);
		fenetre.setVisible(true);
	}
}
