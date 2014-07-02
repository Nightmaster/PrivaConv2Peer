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
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.crypt.PasswordUtilities;

public class RegisterWindow
{
	protected static List<String> createRegisterURL(List<String> details, List<JPasswordField> pwd) throws Exception
	{
		JPasswordField pw_account = pwd.get(0);
		JPasswordField pw_key = pwd.get(1);
		MessageDigest md_pwd_account = null;
		MessageDigest md_pwd_key = null;
		String hashtext = "";
		String hashtext_key = "";
		try
		{
			md_pwd_account = MessageDigest.getInstance("MD5");
			md_pwd_account.reset();
			md_pwd_account.update(String.copyValueOf(pw_account.getPassword()).getBytes());
			byte[] digest = md_pwd_account.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			while (hashtext.length() < 32)
				hashtext = "0" + hashtext;

			md_pwd_key = MessageDigest.getInstance("MD5");
			md_pwd_key.reset();
			md_pwd_key.update(String.copyValueOf(pw_key.getPassword()).getBytes());
			byte[] digest_key = md_pwd_key.digest();
			BigInteger bigInt_key = new BigInteger(1, digest_key);
			hashtext_key = bigInt_key.toString(16);
			while (hashtext_key.length() < 32)
				hashtext_key = "0" + hashtext_key;

		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		List<String> url = new ArrayList<String>();
		String urlConnect = Constants.SRV_URL + ":" + Constants.SRV_PORT + "/" + Constants.SRV_API + "/" + Constants.SRV_REGISTER_PAGE;

		String params = "?" + Constants.PARAM_USER + "=" + details.get(0) + "&" + Constants.PARAM_EMAIL + "=" + details.get(1) + "&" + Constants.PARAM_FIRSTNAME + "=" + details.get(2) + "&" + Constants.PARAM_NAME + "=" + details.get(3) + "&" + Constants.PARAM_PWD + "=" + hashtext + "&" + Constants.PARAM_PWD_KEY + "=" + hashtext_key + "&" + Constants.PARAM_LENGTH + "=" + details.get(4);

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

	protected static boolean isValidName(String text)
	{

		if (text.matches("^[a-zA-Z-]*$") && text.length() > 1)
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

	public static void main(String[] args)
	{

		final JFrame fenetre = new JFrame();

		final JLabel l_pseudo = new JLabel("Pseudo : ");
		final JLabel l_usermail = new JLabel("Email : ");
		final JLabel l_lastname = new JLabel("Nom : ");
		final JLabel l_firstname = new JLabel("Prénom : ");
		final JLabel l_password = new JLabel("Mot de passe : ");
		final JLabel l_password_again = new JLabel("Resaisir mot de passe : ");
		final JLabel l_password_key = new JLabel("Mot de passe clef: ");
		final JLabel l_password_key_again = new JLabel("Resaisir mot de passe clef : ");
		final JComboBox<Integer> f_len_key = new JComboBox<Integer>();

		final JTextField f_pseudo = new JTextField("");
		final JTextField f_mail = new JTextField("");
		f_mail.setToolTipText("");
		final JTextField f_lastname = new JTextField("");
		final JTextField f_firstname = new JTextField("");
		final JPasswordField f_password = new JPasswordField();
		f_password.setToolTipText("<html>\r\n<pre>\r\nLe mot de passe doit \u00EAtre d'au moins 8 caract\u00E8res et \u00EAtre compos\u00E9 de :\r\n\t- Au moins 1 majuscule\r\n\t- Au moins 1 minuscule\r\n\t- Au moins 1 chiffre\r\n\t- Au moins 1 caract\u00E8re sp\u00E9cial\r\n</pre>\r\n</html>");
		final JPasswordField f_password_again = new JPasswordField();
		final JPasswordField f_password_key = new JPasswordField();
		f_password_key.setToolTipText("Mot de passe servant \u00E0 crypter votre paire de clefs RSA\r\nCe mot de passe doit \u00EAtre diff\u00E9rent de celui de connexion (pour des raisons de s\u00E9curit\u00E9)");
		final JPasswordField f_password_key_again = new JPasswordField();

		fenetre.setTitle("Inscription");
		fenetre.setSize(613, 557);
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);

		fenetre.setBackground(Color.white);
		JPanel top = new JPanel();
		top.setBounds(0, 39, 1184, 750);
		Font police = new Font("Arial", Font.BOLD, 14);
		fenetre.getContentPane().setLayout(null);
		f_pseudo.setFont(police);
		f_pseudo.setPreferredSize(new Dimension(150, 30));
		f_pseudo.setForeground(Color.BLUE);

		f_mail.setFont(police);
		f_mail.setPreferredSize(new Dimension(150, 30));
		f_mail.setForeground(Color.BLUE);

		f_lastname.setFont(police);
		f_lastname.setPreferredSize(new Dimension(150, 30));
		f_lastname.setForeground(Color.BLUE);

		f_firstname.setFont(police);
		f_firstname.setPreferredSize(new Dimension(150, 30));
		f_firstname.setForeground(Color.BLUE);

		f_password.setFont(police);
		f_password.setPreferredSize(new Dimension(150, 30));
		f_password.setForeground(Color.BLUE);

		f_password_again.setFont(police);
		f_password_again.setPreferredSize(new Dimension(150, 30));
		f_password_again.setForeground(Color.BLUE);

		f_password_key.setFont(police);
		f_password_key.setPreferredSize(new Dimension(150, 30));
		f_password_key.setForeground(Color.BLUE);

		f_password_key_again.setFont(police);
		f_password_key_again.setPreferredSize(new Dimension(150, 30));
		f_password_key_again.setForeground(Color.BLUE);

		fenetre.getContentPane().add(top);

		JButton btnNewButton = new JButton("S'enregistrer");

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
					int nb_invalid = 0;
					HashMap<String, Boolean> test_mdp = PasswordUtilities.isStrongEnough(String.copyValueOf(f_password.getPassword()));
					HashMap<String, Boolean> test_mdp_key = PasswordUtilities.isStrongEnough(String.copyValueOf(f_password_key.getPassword()));
					List<String> invalid_params_mdp = new ArrayList<String>();
					List<String> invalid_params_mdp_key = new ArrayList<String>();

					if (isValidPseudo(f_pseudo.getText()))
						params.add(f_pseudo.getText());
					else
					{
						sb.append("Pseudo invalide" + '\n');
						nb_invalid++ ;
					}
					if (isValidEmail(f_mail.getText()))
						params.add(f_mail.getText());
					else
					{
						sb.append("email invalide" + '\n');
						nb_invalid++ ;
					}
					if (isValidName(f_lastname.getText()))
						params.add(f_lastname.getText());
					else
					{
						sb.append("nom invalide" + '\n');
						nb_invalid++ ;
					}
					if (isValidName(f_firstname.getText()))
						params.add(f_firstname.getText());
					else
					{
						sb.append("prenom invalide" + '\n');
						nb_invalid++ ;
					}

					for (Entry<String, Boolean> entry : test_mdp.entrySet())
						if (!entry.getValue())
							invalid_params_mdp.add(entry.getKey());
					if (invalid_params_mdp.size() > 0)
					{
						StringBuilder sbmdp = new StringBuilder();
						for (String s : invalid_params_mdp)
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
						nb_invalid++ ;
						JOptionPane.showMessageDialog(null, sbmdp.toString(), "Requis", JOptionPane.OK_OPTION);

					}

					for (Entry<String, Boolean> entry : test_mdp_key.entrySet())
						if (!entry.getValue())
							invalid_params_mdp_key.add(entry.getKey());
					if (invalid_params_mdp_key.size() > 0)
					{
						StringBuilder sbmdp = new StringBuilder();
						for (String s : invalid_params_mdp_key)
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
						nb_invalid++ ;
						JOptionPane.showMessageDialog(null, sbmdp.toString(), "Requis", JOptionPane.OK_OPTION);

					}

					if (String.copyValueOf(f_password.getPassword()).equals(String.copyValueOf(f_password_again.getPassword())) && !String.copyValueOf(f_password_key.getPassword()).equals(String.copyValueOf(f_password.getPassword())))
						pwds.add(f_password);
					else
					{
						sb.append("Mot de passe invalide" + '\n');
						nb_invalid++ ;
					}

					if (String.copyValueOf(f_password_key.getPassword()).equals(String.copyValueOf(f_password_key_again.getPassword())) && !String.copyValueOf(f_password_key.getPassword()).equals(String.copyValueOf(f_password.getPassword())))
						pwds.add(f_password_key);
					else
					{
						sb.append("Mot de passe de la clef invalide" + '\n');
						nb_invalid++ ;
					}

					params.add(f_len_key.getSelectedItem().toString());

					if (nb_invalid == 0)
					{
						List<String> url_register = createRegisterURL(params, pwds);
						URL url = new URL(url_register.get(0) + url_register.get(1));
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		f_len_key.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1024, 2048, 4096}));
		f_len_key.setToolTipText("<html>\r\nLongueur de clefs de cryptage :<br>\r\n\t- 1024 : peu s\u00E9curis\u00E9, mais traitement rapide <br>\r\n  \t- 2048 : bon rapport s\u00E9curit\u00E9 / vitesse de traitement <br>\r\n  \t- 4096 : tr\u00E8s s\u00E9curis\u00E9, mais vitesse de traitement plus lente <br>\r\n</html>");

		JLabel lblNewLabel = new JLabel("Longueur clef :");

		GroupLayout gl_top = new GroupLayout(top);
		gl_top.setHorizontalGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(
				gl_top.createSequentialGroup()
				.addGroup(
						gl_top.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_top.createSequentialGroup().addGap(221).addComponent(btnNewButton))
						.addGroup(
								gl_top.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_top.createParallelGroup(Alignment.TRAILING).addComponent(l_password_key_again).addComponent(l_password_key).addComponent(l_password_again).addComponent(l_password).addComponent(l_firstname).addComponent(l_usermail).addComponent(l_pseudo).addComponent(l_lastname).addComponent(lblNewLabel))
								.addGroup(
										gl_top.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_top.createSequentialGroup()
												.addGap(33)
												.addGroup(
														gl_top.createParallelGroup(Alignment.LEADING).addComponent(f_lastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(f_firstname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(f_mail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(f_pseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																Alignment.TRAILING,
																gl_top.createSequentialGroup()
																.addGap(33)
																.addGroup(
																		gl_top.createParallelGroup(Alignment.LEADING, false).addComponent(f_password_again, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(f_password, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(f_password_key, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(f_password_key_again, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(f_len_key, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
																		.addContainerGap(826, Short.MAX_VALUE)));
		gl_top.setVerticalGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(
				gl_top.createSequentialGroup().addGap(21).addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(l_pseudo).addComponent(f_pseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(19)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(f_mail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(l_usermail)).addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(f_lastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(l_lastname)).addGap(9)
				.addGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(8).addComponent(l_firstname)).addComponent(f_firstname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(f_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(l_password)).addGap(9)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(l_password_again).addComponent(f_password_again, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(l_password_key).addComponent(f_password_key, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(l_password_key_again).addComponent(f_password_key_again, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(20)
				.addGroup(gl_top.createParallelGroup(Alignment.BASELINE).addComponent(f_len_key, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblNewLabel)).addGap(18).addComponent(btnNewButton).addContainerGap(314, Short.MAX_VALUE)));
		top.setLayout(gl_top);
		fenetre.setVisible(true);
	}
}
