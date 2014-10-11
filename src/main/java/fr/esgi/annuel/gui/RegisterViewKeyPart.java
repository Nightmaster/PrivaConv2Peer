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
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.crypt.PasswordUtilities;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.gui.RegisterView.StoredValues;
import fr.esgi.annuel.parser.JSONParser;
import fr.esgi.annuel.parser.SimpleJsonParser;
import org.json.JSONException;

import static fr.esgi.annuel.crypt.PasswordUtilities.hashPassword;
import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;

public class RegisterViewKeyPart extends JPanel implements Resettable
{
	private static final Integer[] AVAILABLE_KEY_LENGTHS = {1024, 2048, 4096};
	private final MasterController controller;
	private final JPasswordField[] passwordFields;
	private HashMap<StoredValues, String> prevViewValues;
	private JLabel lPasswordKey, lPasswordKeyAgain, lKeyLength;
	private JComboBox<Integer> fLenKey;
	private JPasswordField fPasswordKey, fPasswordKeyAgain;
	private JButton btnPrevious, btnRegister;

	/**
	 * Instantiate a new {@link fr.esgi.annuel.gui.RegisterViewKeyPart}
	 *
	 * @param controller {{@link fr.esgi.annuel.ctrl.MasterController}}: the application's controller
	 */
	public RegisterViewKeyPart(MasterController controller)
	{
		this.controller = controller;
		this.controller.setLookAndFeel();
		//@formatter:off
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(getLabelKeyLength())
					.addGap(102)
					.addComponent(getFieldLengthKey(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(getLabelPasswordKey())
					.addGap(43)
					.addComponent(getFieldPasswordKey(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(getLabelPasswordKeyAgain())
					.addComponent(getFieldPasswordKeyAgain(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addComponent(getBtnPrevious())
					.addGap(34)
					.addComponent(getBtnRegister()))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(getLabelKeyLength()))
						.addComponent(getFieldLengthKey(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getBtnPrevious())
						.addComponent(getBtnRegister())))
		);
		//@formatter:on
		setLayout(groupLayout);
		this.passwordFields = new JPasswordField[] {this.fPasswordKey, this.fPasswordKeyAgain};
	}

	private JButton getBtnPrevious()
	{
		if (this.btnPrevious == null)
		{
			this.btnPrevious = new JButton("Pr\u00E9c\u00E9dent");
			this.btnPrevious.addActionListener(new ButtonListener());
		}
		return this.btnPrevious;
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
			setPrompt("Ressaisir le mot de passe", this.fPasswordKeyAgain);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordKeyAgain);
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
		{
			this.lPasswordKeyAgain = new JLabel("Resaisir mot de passe clef : ");
		}
		return this.lPasswordKeyAgain;
	}

	@Override
	public RegisterViewKeyPart reset()
	{
		this.fPasswordKey.setText(null);
		this.fPasswordKeyAgain.setText(null);
		if(! this.fLenKey.requestFocusInWindow())
			this.fLenKey.requestFocus();
		return this;
	}

	void setPrevViewValues(final HashMap<StoredValues, String> prevViewValues)
	{
		this.prevViewValues = prevViewValues;
		for(StoredValues value : this.prevViewValues.keySet())
			System.out.println(this.prevViewValues.get(value));
	}

	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (RegisterViewKeyPart.this.btnRegister.equals(e.getSource()))
			{
				int keyLength = AVAILABLE_KEY_LENGTHS[RegisterViewKeyPart.this.fLenKey.getSelectedIndex()];
				StringBuilder sb = new StringBuilder(450);
				String	hashedPw = RegisterViewKeyPart.this.prevViewValues.get(StoredValues.HASHED_PASSWORD),
						pwK = String.valueOf(RegisterViewKeyPart.this.fPasswordKey.getPassword()),
						pwKAgain = String.valueOf(RegisterViewKeyPart.this.fPasswordKeyAgain.getPassword()),
						hashedPwK = hashPassword(pwK);
				if (Strings.isNullOrEmpty(pwK) || Strings.isNullOrEmpty(pwKAgain))
					return;
				/**Equality between similar password verification and verification of a difference between session PW and key PW**/
				if (!pwK.equals(pwKAgain))
					sb.append("Les deux mots de passe doivent \u00EAtre identiques !\n");
				else if (hashedPw.equals(hashedPwK))
					sb.append("Le mot de passe de la cl\u00E9 doit \u00EAtre diff\u00E9rent de celui de votre compte !\n");

				/**Password verification**/
				HashMap<PasswordConstraints, Boolean> map = PasswordUtilities.isStrongEnough(hashedPw);
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
								RegisterViewKeyPart.this.controller.register(RegisterViewKeyPart.this.prevViewValues.get(StoredValues.LOGIN),
																			 RegisterViewKeyPart.this.prevViewValues.get(StoredValues.EMAIL),
																			 hashedPw,
																			 RegisterViewKeyPart.this.prevViewValues.get(StoredValues.FIRSTNAME),
																			 RegisterViewKeyPart.this.prevViewValues.get(StoredValues.LASTNAME),
																			 keyLength,
																			 hashedPwK));
						if (registerJsonParser.isError())
							JOptionPane.showMessageDialog(RegisterViewKeyPart.this, registerJsonParser.getDisplayMessage(), "Erreur \u00E0 l'enregistrement", JOptionPane.ERROR_MESSAGE);
						else
						{
							JOptionPane.showMessageDialog(RegisterViewKeyPart.this, "F\u00E9licitation, vous \u00EAtes bien enregistr\u00E9", "Validation d'enregistrement", JOptionPane.INFORMATION_MESSAGE);
							RegisterViewKeyPart.this.controller.changeView(Views.IDENTIFICATION);
						}
					}
					catch (JSONException ignored) {}
					catch (IllegalArgumentException exc)
					{
						exc.printStackTrace();
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
			else // if(RegisterViewKeyPart.this.btnPrevious.equals(e.getSource()))
				RegisterViewKeyPart.this.controller.changeView(Views.REGISTER);
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
