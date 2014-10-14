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

import static fr.esgi.annuel.constants.FieldType.*;
import static fr.esgi.annuel.ctrl.FieldContentValidator.getErrorMessageFor;
import static fr.esgi.annuel.ctrl.FieldContentValidator.isValidFieldContent;
import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;

public class RegisterView extends JPanel implements Resettable
{
	private final RegisterViewKeyPart nextView;
	private final JTextField[] textFields;
	private final JPasswordField[] passwordFields;
	private JLabel lPseudo, lUserEmail, lLastName, lFirstName, lPassword, lPasswordAgain;
	private JTextField fPseudo, fEmail, fLastName, fFirstName;
	private JPasswordField fPassword, fPasswordAgain;
	private JButton btnNext, btnCancel;
	private String pw, login, email, firstName, lastName;
	private MasterController controller;

	/**
	* Create and init the panel.
	**/
	public RegisterView(MasterController controller, RegisterViewKeyPart nextView)
	{
		this.controller = controller;
		MasterController.setLookAndFeel();
		this.nextView = nextView;
		//@formatter:off
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLabelPseudo())
					.addGap(70)
					.addComponent(getFieldPseudo(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLabelUserEmail(), GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(69)
					.addComponent(getFieldEmail(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLabelLastName())
					.addGap(84)
					.addComponent(getFieldLastName(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLabelFirstName())
					.addGap(69)
					.addComponent(getFieldFirstName(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLabelPassword())
					.addGap(41)
					.addComponent(getFieldPassword(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLabelPasswordAgain())
					.addGap(1)
					.addComponent(getFieldPasswordAgain(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(getBtnCancel())
					.addGap(19)
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
							.addComponent(getLabelLastName()))
						.addComponent(getFieldLastName(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(getLabelFirstName()))
						.addComponent(getFieldFirstName(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getLabelPassword())
						.addComponent(getFieldPassword(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(getLabelPasswordAgain()))
						.addComponent(getFieldPasswordAgain(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getBtnCancel())
						.addComponent(getBtnNext())))
		);
		setLayout(groupLayout);
		//@formatter:on
		this.textFields = new JTextField[] {this.fPseudo, this.fEmail, this.fLastName, this.fFirstName};
		this.passwordFields = new JPasswordField[] {this.fPassword, this.fPasswordAgain};
	}

	private JButton getBtnCancel()
	{
		if(this.btnCancel == null)
		{
			this.btnCancel = new JButton("Annuler");
			this.btnCancel.addActionListener(new ButtonListener());
		}
		return this.btnCancel;
	}

	private JButton getBtnNext()
	{
		if (this.btnNext == null)
		{
			this.btnNext = new JButton("Etape suivante");
			this.btnNext.addActionListener(new ButtonListener());
			this.btnNext.setEnabled(false);
		}
		return this.btnNext;
	}

	private JTextField getFieldEmail()
	{
		if (this.fEmail == null)
		{
			this.fEmail = new JTextField(10);
			this.fEmail.getDocument().addDocumentListener(new FieldListener());
			setPrompt("exemple@site.tld", this.fEmail);
			setFocusBehavior(SHOW_PROMPT, this.fEmail);
		}
		return this.fEmail;
	}

	private JTextField getFieldFirstName()
	{
		if (this.fFirstName == null)
		{
			this.fFirstName = new JTextField(10);
			this.fFirstName.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Votre pr\u00E9nom", this.fFirstName);
			setFocusBehavior(SHOW_PROMPT, this.fFirstName);
		}
		return this.fFirstName;
	}

	private JTextField getFieldLastName()
	{
		if (this.fLastName == null)
		{
			this.fLastName = new JTextField(10);
			this.fLastName.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Votre nom", this.fLastName);
			setFocusBehavior(SHOW_PROMPT, this.fLastName);
		}
		return this.fLastName;
	}

	private JPasswordField getFieldPassword()
	{
		if (this.fPassword == null)
		{
			this.fPassword = new JPasswordField();
			this.fPassword.getDocument().addDocumentListener(new FieldListener());
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
			this.fPasswordAgain.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Ressaisir le mot de passe", this.fPasswordAgain);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordAgain);
		}
		return this.fPasswordAgain;
	}

	private JTextField getFieldPseudo()
	{
		if (this.fPseudo == null)
		{
			this.fPseudo = new JTextField(10);
			this.fPseudo.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Pseudonyme", this.fPseudo);
			setFocusBehavior(SHOW_PROMPT, this.fPseudo);
		}
		return this.fPseudo;
	}

	private JLabel getLabelFirstName()
	{
		if (this.lFirstName == null)
			this.lFirstName = new JLabel("Pr\u00E9nom : ");
		return this.lFirstName;
	}

	private JLabel getLabelLastName()
	{
		if (this.lLastName == null)
			this.lLastName = new JLabel("Nom : ");
		return this.lLastName;
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

	public void sendValuesToNextView()
	{
		final HashMap<StoredValues, String> values = new HashMap<>();
		values.put(StoredValues.LOGIN, this.login);
		values.put(StoredValues.EMAIL, this.email);
		values.put(StoredValues.LASTNAME, this.lastName);
		values.put(StoredValues.FIRSTNAME, this.firstName);
		values.put(StoredValues.HASHED_PASSWORD, PasswordUtilities.hashPassword(this.pw));
		this.nextView.setPrevViewValues(values);
	}

	@Override
	public RegisterView reset()
	{
		this.login = this.email = this.pw = this.lastName = this.firstName = null;
		this.fPseudo.setText(null);
		this.fEmail.setText(null);
		this.fLastName.setText(null);
		this.fFirstName.setText(null);
		this.fPassword.setText(null);
		this.fPasswordAgain.setText(null);
		if(! this.fPseudo.requestFocusInWindow())
			this.fPseudo.requestFocus();
		return this;
	}

	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{

			if (RegisterView.this.btnNext.equals(e.getSource()))
			{
				RegisterView.this.pw = String.valueOf(RegisterView.this.fPassword.getPassword());
				RegisterView.this.login = RegisterView.this.fPseudo.getText();
				RegisterView.this.email = RegisterView.this.fEmail.getText();
				RegisterView.this.firstName = RegisterView.this.fFirstName.getText();
				RegisterView.this.lastName = RegisterView.this.fLastName.getText();
				String pwAgain = String.valueOf(RegisterView.this.fPasswordAgain.getPassword());
				if (Strings.isNullOrEmpty(pw) || Strings.isNullOrEmpty(pwAgain))
					return;
				StringBuilder sb = new StringBuilder(1000);

				/**Fields content (except PW) verification**/
				sb.append(!RegisterView.this.pw.equals(pwAgain) ? "Les deux mots de passe doivent \u00EAtre identiques !\n" : "");
				sb.append(!isValidFieldContent(RegisterView.this.login, PSEUDO) ? getErrorMessageFor(PSEUDO) + "\n" : "");
				sb.append(!isValidFieldContent(RegisterView.this.email, EMAIL) ? getErrorMessageFor(EMAIL) + "\n" : "");
				sb.append(!isValidFieldContent(RegisterView.this.lastName, LASTNAME) ? getErrorMessageFor(LASTNAME) + "\n" : "");
				sb.append(!isValidFieldContent(RegisterView.this.firstName, FIRSTNAME) ? getErrorMessageFor(FIRSTNAME) : "");

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
					sendValuesToNextView();
					RegisterView.this.controller.changeView(Views.REGISTER_PART_2);
				}
				else
				{
					String res = sb.toString();
					while (res.contains("\n\n"))
						res = res.replaceAll("\\n\\n", "\n");
					if (res.endsWith("\n"))
						res = res.substring(0, res.length() - 1);
					JOptionPane.showMessageDialog(null, res, "Valeur(s) incorrecte(s) d\u00E0e(s)", JOptionPane.ERROR_MESSAGE);
				}
			}
			else // if (RegisterView.this.btnCancel.equals(e.getSource()))
			{
				reset()
				.nextView.reset();
				RegisterView.this.controller.changeView(Views.IDENTIFICATION);
			}
		}
	}

	private class FieldListener implements DocumentListener
	{
		private void checkFieldsNotEmpty()
		{
			for (JTextField field : RegisterView.this.textFields)
			{
				if (field.getText().trim().isEmpty())
				{
					RegisterView.this.btnNext.setEnabled(false);
					return;
				}
			}
			for (JPasswordField field : RegisterView.this.passwordFields)
			{
				if (Strings.isNullOrEmpty(String.valueOf(field.getPassword())))
				{
					RegisterView.this.btnNext.setEnabled(false);
					return;
				}
			}
			RegisterView.this.btnNext.setEnabled(true);
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

	enum StoredValues
	{
		LOGIN, EMAIL, LASTNAME, FIRSTNAME, HASHED_PASSWORD;
	}
}

