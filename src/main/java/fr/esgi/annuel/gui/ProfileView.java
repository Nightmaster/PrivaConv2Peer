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

import static fr.esgi.annuel.constants.FieldType.*;
import static fr.esgi.annuel.ctrl.FieldContentValidator.getErrorMessageFor;
import static fr.esgi.annuel.ctrl.FieldContentValidator.isValidFieldContent;
import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;

public class ProfileView extends JPanel
{
	private JButton btnCancel, btnSubmit;
	private JLabel lblEmailAddress, lblPwConfirmation, lblName, lblNewPw, lblFirstName, lblPseudo;
	private JPasswordField fPassword, fPasswordAgain;
	private JTextField fLogin, fEmail, fFirstName, fLastName;
	private JTextField[] textFields;
	private MasterController controller;

	/**
	* Create the panel.
	**/
	public ProfileView(MasterController controller)
	{
		this.controller = controller;
		MasterController.setLookAndFeel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblName())
					.addGap(159)
					.addComponent(getLblEmailAddress()))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getFLastName(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGap(30)
						.addComponent(getFEmail(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(getLblFirstName())
						.addGap(144)
						.addComponent(getLblNewPw()))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(getFFirstName(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGap(30)
						.addComponent(getFPassword(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(getLblPseudo())
						.addGap(119)
						.addComponent(getLblPwConfirmation()))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(getFLogin(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGap(30)
						.addComponent(getFPasswordAgain(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(40)
						.addComponent(getBtnCancel(), GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addGap(100)
						.addComponent(getBtnSubmit(), GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getLblName())
						.addComponent(getLblEmailAddress()))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getFLastName(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(getFEmail(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getLblFirstName())
						.addComponent(getLblNewPw()))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getFFirstName(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(getFPassword(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getLblPseudo())
						.addComponent(getLblPwConfirmation()))
					.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getFLogin(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(getFPasswordAgain(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getBtnCancel())
						.addComponent(getBtnSubmit()))));
		setLayout(groupLayout);
		this.textFields = new JTextField[] {this.fLogin, this.fEmail, this.fFirstName, this.fLastName, this.fPassword, this.fPasswordAgain};
	}

	private JLabel getLblEmailAddress()
	{
		if (this.lblEmailAddress == null)
			this.lblEmailAddress = new JLabel("Adresse email");
		return this.lblEmailAddress;
	}

	private JLabel getLblPwConfirmation()
	{
		if (this.lblPwConfirmation == null)
			this.lblPwConfirmation = new JLabel("Confirmation du mot de passe");
		return this.lblPwConfirmation;
	}

	private JLabel getLblName()
	{
		if (this.lblName == null)
			this.lblName = new JLabel("Nom");
		return this.lblName;
	}

	private JLabel getLblNewPw()
	{
		if (this.lblNewPw == null)
			this.lblNewPw = new JLabel("Nouveau mot de passe");
		return this.lblNewPw;
	}

	private JLabel getLblFirstName()
	{
		if (this.lblFirstName == null)
			this.lblFirstName = new JLabel("Pr\u00E9nom");
		return this.lblFirstName;
	}

	private JLabel getLblPseudo()
	{
		if (this.lblPseudo == null)
			this.lblPseudo = new JLabel("Pseudonyme");
		return this.lblPseudo;
	}

	private JPasswordField getFPassword()
	{
		if (this.fPassword == null)
		{
			this.fPassword = new JPasswordField();
			this.fPassword.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Nouveau mot de passe souhait\u00E9", this.fPassword);
			setFocusBehavior(SHOW_PROMPT, this.fPassword);
		}
		return this.fPassword;
	}

	private JPasswordField getFPasswordAgain()
	{
		if (this.fPasswordAgain == null)
		{
			this.fPasswordAgain = new JPasswordField();
			this.fPasswordAgain.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Confirmation du mot de passe", this.fPasswordAgain);
			setFocusBehavior(SHOW_PROMPT, this.fPasswordAgain);
		}
		return this.fPasswordAgain;
	}

	private JTextField getFLogin()
	{
		if (this.fLogin == null)
		{
			this.fLogin = new JTextField();
			this.fLogin.setColumns(15);
			this.fLogin.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Nouveau login", this.fLogin);
			setFocusBehavior(SHOW_PROMPT, this.fLogin);
		}
		return this.fLogin;
	}

	private JTextField getFEmail()
	{
		if (this.fEmail == null)
		{
			this.fEmail = new JTextField();
			this.fEmail.setColumns(15);
			this.fEmail.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Nouvel email", this.fEmail);
			setFocusBehavior(SHOW_PROMPT, this.fEmail);
		}
		return this.fEmail;
	}

	private JTextField getFFirstName()
	{
		if (this.fFirstName == null)
		{
			this.fFirstName = new JTextField();
			this.fFirstName.setColumns(15);
			this.fFirstName.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Nouveau pr\u00E9nom", this.fFirstName);
			setFocusBehavior(SHOW_PROMPT, this.fFirstName);
		}
		return this.fFirstName;
	}

	private JTextField getFLastName()
	{
		if (this.fLastName == null)
		{
			this.fLastName = new JTextField();
			this.fLastName.setColumns(15);
			this.fLastName.getDocument().addDocumentListener(new FieldListener());
			setPrompt("Nouveau nom", this.fLastName);
			setFocusBehavior(SHOW_PROMPT, this.fLastName);
		}
		return this.fLastName;
	}

	public JButton getBtnSubmit()
	{
		if(this.btnSubmit == null)
		{
			this.btnSubmit = new JButton("Valider");
			this.btnSubmit.setEnabled(false);
			this.btnSubmit.addActionListener(new BtnListener());
		}
		return btnSubmit;
	}

	public JButton getBtnCancel()
	{
		if(this.btnCancel == null)
		{
			this.btnCancel = new JButton("Annuler");
			this.btnCancel.addActionListener(new BtnListener());
		}
		return btnCancel;
	}

	private class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ProfileView.this.controller.stayAlive();
			if(e.getSource().equals(ProfileView.this.btnCancel))
				ProfileView.this.controller.closeProfileFrame();
			else
			{
				String	pw = String.valueOf(ProfileView.this.fPassword.getPassword()),
						login = ProfileView.this.fLogin.getText(),
						email = ProfileView.this.fEmail.getText(),
						firstName = ProfileView.this.fFirstName.getText(),
						lastName = ProfileView.this.fLastName.getText(),
						pwAgain = String.valueOf(ProfileView.this.fPasswordAgain.getPassword());

				StringBuilder sb = new StringBuilder(1000);
				/**Fields content (except PW) verification**/
				sb.append(!pw.equals(pwAgain) ? "Les deux mots de passe doivent \u00EAtre identiques !\n" : "");
				if (! Strings.isNullOrEmpty(login))
					sb.append(!isValidFieldContent(login, PSEUDO) ? getErrorMessageFor(PSEUDO) + "\n" : "");
				if (! Strings.isNullOrEmpty(email))
					sb.append(!isValidFieldContent(email, EMAIL) ? getErrorMessageFor(EMAIL) + "\n" : "");
				if (! Strings.isNullOrEmpty(lastName))
					sb.append(!isValidFieldContent(lastName, LASTNAME) ? getErrorMessageFor(LASTNAME) + "\n" : "");
				if (! Strings.isNullOrEmpty(firstName))
					sb.append(!isValidFieldContent(firstName, FIRSTNAME) ? getErrorMessageFor(FIRSTNAME) : "");
				if(! Strings.isNullOrEmpty(pw))
				{
					/**Password verification**/
					HashMap<PasswordConstraints, Boolean> map = PasswordUtilities.isStrongEnough(pw);
					for (PasswordConstraints constraint : map.keySet())
						if (!map.get(constraint))
						{
							sb.append(constraint.getErrorMessage());
							sb.append('\n');
						}
				}
				if ("".equals(sb.toString())) //if no error has been detected
					ProfileView.this.controller.updateInfos(login, email, PasswordUtilities.hashPassword(pw), firstName, lastName);
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
		}
	}

	private class FieldListener implements DocumentListener
	{
		private void checkFieldsNotEmpty()
		{
			for (JTextField field : ProfileView.this.textFields)
			{
				if (! field.getText().trim().isEmpty())
				{
					ProfileView.this.btnSubmit.setEnabled(true);
					return;
				}
			}
			ProfileView.this.btnSubmit.setEnabled(false);
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