package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import com.google.common.base.Strings;
import fr.esgi.annuel.constants.PasswordConstraints;
import fr.esgi.annuel.crypt.PasswordUtilities;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.parser.JSONParser;
import fr.esgi.annuel.parser.SimpleJsonParser;
import org.json.JSONException;

import static fr.esgi.annuel.crypt.PasswordUtilities.hashPassword;
import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;

public class RegisterViewKeyPart extends JPanel
{
	private static final Integer[] AVAILABLE_KEY_LENGTHS = {1024, 2048, 4096};
	private final RegisterView registerView;
	private final MasterController controller;
	private final JPasswordField[] passwordFields;
	private JLabel lPasswordKey, lPasswordKeyAgain, lKeyLength;
	private JComboBox<Integer> fLenKey;
	private JPasswordField fPasswordKey, fPasswordKeyAgain;
	private JButton btnRegister;

	public RegisterViewKeyPart(MasterController controller, RegisterView registerView)
	{
		this.controller = controller;
		this.controller.setLookAndFeel();
		this.registerView = registerView;
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
		this.passwordFields = new JPasswordField[] {this.fPasswordKey, this.fPasswordKeyAgain};
	}

	private JButton getBtnRegister()
	{
		if (this.btnRegister == null)
		{
			this.btnRegister = new JButton("S'enregistrer");
			this.btnRegister.addActionListener(new ButtonListener());
			this.btnRegister.setEnabled(false);
		}
		return this.btnRegister;
	}

	private JComboBox<Integer> getFieldLengthKey()
	{
		if (this.fLenKey == null)
		{
			this.fLenKey = new JComboBox<>();
			this.fLenKey.setMaximumRowCount(4);
			this.fLenKey.setModel(new DefaultComboBoxModel<>(AVAILABLE_KEY_LENGTHS));
			this.fLenKey.setToolTipText("<html>\r\nLongueur de clefs de cryptage :<br>\r\n\t- 1024 : peu s\u00E9curis\u00E9, mais traitement rapide <br>\r\n  \t- 2048 : bon rapport s\u00E9curit\u00E9 / vitesse de traitement <br>\r\n  \t- 4096 : tr\u00E8s s\u00E9curis\u00E9, mais vitesse de traitement plus lente <br>\r\n</html>");
		}
		return this.fLenKey;
	}

	private JPasswordField getFieldPasswordKey()
	{
		if (this.fPasswordKey == null)
		{
			this.fPasswordKey = new JPasswordField();
			this.fPasswordKey.getDocument().addDocumentListener(new FieldListener());
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
			this.fPasswordKeyAgain.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Mot de passe de la cl\u00E9", this.fPasswordKey);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordKey);
		}
		return this.fPasswordKeyAgain;
	}

	private JLabel getLabelKeyLength()
	{
		if (this.lKeyLength == null)
			this.lKeyLength = new JLabel("Longueur clef :");
		return this.lKeyLength;
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

	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String pw = RegisterViewKeyPart.this.registerView.getPassword();
			if (RegisterViewKeyPart.this.btnRegister.equals(e.getSource()))
			{
				int keyLength = AVAILABLE_KEY_LENGTHS[RegisterViewKeyPart.this.fLenKey.getSelectedIndex()];
				StringBuilder sb = new StringBuilder(450);
				String pwK = String.valueOf(RegisterViewKeyPart.this.fPasswordKey.getPassword()),
						pwKAgain = String.valueOf(RegisterViewKeyPart.this.fPasswordKeyAgain.getPassword());
				if (Strings.isNullOrEmpty(pwK) || Strings.isNullOrEmpty(pwKAgain))
					return;
				/**Equality between similar password verification and verification of a difference between session PW and key PW**/
				if (!pwK.equals(pwKAgain))
					sb.append("Les deux mots de passe doivent \u00EAtre identiques !");
				else if (pw.equals(pwK))
					sb.append("Le mot de passe de la cl\u00E9 doit \u00EAtre diff\u00E9rent de celui de votre compte !");

				/**Password verification**/
				HashMap<PasswordConstraints, Boolean> map = PasswordUtilities.isStrongEnough(pw);
				for (PasswordConstraints constraint : map.keySet())
					if (!map.get(constraint))
					{
						sb.append(constraint.getErrorMessage());
						sb.append('\n');
					}

				if ("".equals(sb.toString())) //if no error has been detected
				{
					try
					{
						SimpleJsonParser registerJsonParser = JSONParser.getRegistrationParser(
								RegisterViewKeyPart.this.controller.register(RegisterViewKeyPart.this.registerView.getLogin(),
																			 RegisterViewKeyPart.this.registerView.getEmail(),
																			 hashPassword(pw),
																			 RegisterViewKeyPart.this.registerView.getFirstName(),
																			 RegisterViewKeyPart.this.registerView.getLastName(),
																			 keyLength,
																			 pwK));
						if (registerJsonParser.isError())
							JOptionPane.showMessageDialog(RegisterViewKeyPart.this, registerJsonParser.getDisplayMessage(), "Erreur \u00E0 l'enregistrement", JOptionPane.ERROR_MESSAGE);
						else
							JOptionPane.showMessageDialog(RegisterViewKeyPart.this, "F\u00E9licitation, vous \u00EAtes bien enregistr\u00E9", "Vlidation d'enregistrement", JOptionPane.INFORMATION_MESSAGE);
					}
					catch (JSONException ignored)
					{
					}
				}
				else
				{
					String res = sb.toString();
					while (res.contains("\n\n"))
						res = res.replace("\n\n", "\n");
					if (res.endsWith("\n"))
						res = res.substring(0, res.length() - 1);
					JOptionPane.showMessageDialog(null, res, "Valeur(s) incorrecte(s) d\u00E0e(s)", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private class FieldListener implements DocumentListener
	{
		private void checkFieldsNotEmpty()
		{
			for (JPasswordField field : RegisterViewKeyPart.this.passwordFields)
			{
				if (Strings.isNullOrEmpty(String.valueOf(field.getPassword())))
				{
					RegisterViewKeyPart.this.btnRegister.setEnabled(false);
					return;
				}
			}
			RegisterViewKeyPart.this.btnRegister.setEnabled(true);
		}

		@Override
		public void insertUpdate(DocumentEvent e)
		{
			checkFieldsNotEmpty();
		}

		@Override
		public void removeUpdate(DocumentEvent e)
		{
			checkFieldsNotEmpty();
		}

		@Override
		public void changedUpdate(DocumentEvent e)
		{
			checkFieldsNotEmpty();
		}
	}
}
