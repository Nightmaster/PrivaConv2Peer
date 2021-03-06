package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import com.google.common.base.Strings;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.crypt.PasswordUtilities;
import fr.esgi.annuel.ctrl.MasterController;
import org.jdesktop.xswingx.PromptSupport;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;

import static fr.esgi.annuel.constants.FieldType.EMAIL;
import static fr.esgi.annuel.constants.FieldType.PSEUDO;
import static fr.esgi.annuel.ctrl.FieldContentValidator.getErrorMessageFor;
import static fr.esgi.annuel.ctrl.FieldContentValidator.isValidFieldContent;

/**
 * Create the identification view ({@link javax.swing.JPanel})
 **/
public class IdentificationView extends JPanel implements Resettable
{
	private JButton btnConnection, btnRegister;
	private JCheckBox chckbxRememberMe;
	private JLabel lblConnectionIdentifier, lblPwd, lblNoAccount;
	private JPasswordField fPassword;
	private JSeparator horizontalSeparator;
	private JTextField fLoginValue;

	private MasterController controller;

	/**
	* Create the a new {@link fr.esgi.annuel.gui.IdentificationView}
	*
	* @param controller {{@link fr.esgi.annuel.ctrl.MasterController}}: the controller of the application
	**/
	public IdentificationView(MasterController controller)
	{
		this.controller = controller;
		MasterController.setLookAndFeel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getLblConnectionIdentifier()))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getFLoginValue(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getLblPwd()))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getFPassword(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getChckbxRememberMe()))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(getBtnConnection(), GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getHorizontalSeparator(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(getLblNoAccount()))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addComponent(getBtnRegister()))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblConnectionIdentifier())
					.addGap(5)
					.addComponent(getFLoginValue(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(getLblPwd())
					.addGap(5)
					.addComponent(getFPassword(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(getChckbxRememberMe())
					.addGap(10)
					.addComponent(getBtnConnection())
					.addGap(15)
					.addComponent(getHorizontalSeparator(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(getLblNoAccount())
					.addGap(5)
					.addComponent(getBtnRegister()))
		);
		setLayout(groupLayout);
		setLoginValue();
	}

	private JButton getBtnConnection()
	{
		if (this.btnConnection == null)
		{
			this.btnConnection = new JButton("Connnexion");
			this.btnConnection.setEnabled(false);
			this.btnConnection.addActionListener(new BtnListener());
		}
		return this.btnConnection;
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
					IdentificationView.this.controller.changeView(Views.REGISTER);
				}
			});
		}
		return this.btnRegister;
	}

	private JCheckBox getChckbxRememberMe()
	{
		if (this.chckbxRememberMe == null)
			this.chckbxRememberMe = new JCheckBox("Se souvenir de moi");
		return this.chckbxRememberMe;
	}

	private JTextField getFLoginValue()
	{
		if (this.fLoginValue == null)
		{
			this.fLoginValue = new JTextField(10);
			PromptSupport.setPrompt("Pseudo ou @ mail", this.fLoginValue);
			this.fLoginValue.addKeyListener(new KeyPressed());
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.fLoginValue);
			this.fLoginValue.getDocument().addDocumentListener(new FieldListener());
		}
		return this.fLoginValue;
	}

	private JPasswordField getFPassword()
	{
		if (this.fPassword == null)
		{
			this.fPassword = new JPasswordField();
			this.fPassword.getDocument().addDocumentListener(new FieldListener());
			this.fPassword.addKeyListener(new KeyPressed());
			PromptSupport.setPrompt("Mot de passe", this.fPassword);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.fPassword);
		}
		return this.fPassword;
	}

	private JSeparator getHorizontalSeparator()
	{
		if (this.horizontalSeparator == null)
			this.horizontalSeparator = new JSeparator();
		return this.horizontalSeparator;
	}

	private JLabel getLblConnectionIdentifier()
	{
		if (this.lblConnectionIdentifier == null)
			this.lblConnectionIdentifier = new JLabel("Identifiant de connexion");
		return this.lblConnectionIdentifier;
	}

	private JLabel getLblNoAccount()
	{
		if (this.lblNoAccount == null)
			this.lblNoAccount = new JLabel("Pas encore de compte ?");
		return this.lblNoAccount;
	}

	private JLabel getLblPwd()
	{
		if (this.lblPwd == null)
			this.lblPwd = new JLabel("Mot de passe");
		return this.lblPwd;
	}

	/**
	* Return the MD5 hash of the typed password
	*
	* @return {@link java.lang.String}: the MD5 hash of the password if it is not an empty value, <code>null</code> otherwise
	**/
	public final String getHashedPassword()
	{
		return 0 != this.fPassword.getPassword().length ? PasswordUtilities.hashPassword(String.valueOf(getFPassword().getPassword())) : null;
	}

	/**
	* Return the typed value in the {@link javax.swing.JTextField login field}
	*
	* @return {@link java.lang.String}: the content of the {@link javax.swing.JTextField login field} ({@link javax.swing.JTextField#getText exactly})
	**/
	public final String getLogin()
	{
		return this.fLoginValue.getText().trim();
	}

	/**
	* Reset the components of the view, and return it
	*
	* @return {{@link fr.esgi.annuel.gui.IdentificationView}}: the {@link fr.esgi.annuel.gui.IdentificationView identification view} with its components back to their default value
	**/
	@Override
	public final IdentificationView reset()
	{
		return new IdentificationView(this.controller);
	}

	/**
	* Initialize the {@link javax.swing.JTextField login field} with a default value.
	* This function is to used when the status of the saved properties file is known as read, and contains a default login value
	**/
	private void setLoginValue()
	{
		if (this.controller.getPropertiesController().isFileCreated())
		{
			this.fLoginValue.setText(this.controller.getPropertiesController().getRegisteredProperty("login"));
			this.chckbxRememberMe.setSelected(true);
			if (! this.fPassword.requestFocusInWindow())
				this.fPassword.requestFocus();
		}
		else
			this.chckbxRememberMe.setEnabled(false);
	}

	private void login()
	{
		final String login = getLogin(),
				password = getHashedPassword(),
				toRegister = this.chckbxRememberMe.isSelected() ? login : "";
		this.controller.getPropertiesController().storeLogin(toRegister);
		if (Strings.isNullOrEmpty(password))
			return;
		if (login.contains("@"))
			if (! isValidFieldContent(login, EMAIL))
				JOptionPane.showMessageDialog(this, getErrorMessageFor(EMAIL), "Identifiant incorrect", JOptionPane.ERROR_MESSAGE);
			else
				IdentificationView.this.controller.connect(null, login, password);
		else if (!isValidFieldContent(login, PSEUDO))
			JOptionPane.showMessageDialog(this, getErrorMessageFor(PSEUDO), "Identifiant incorrect", JOptionPane.ERROR_MESSAGE);
		else
			this.controller.connect(login, null, password);}

	private final class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			login();
		}
	}

	private class FieldListener implements DocumentListener
	{
		private void checkFieldsContent()
		{
			String pw = String.valueOf(IdentificationView.this.fPassword.getPassword()).trim();
			boolean lengthOk = 2 < getLogin().length() && 7 < pw.length();
			if (! lengthOk)
			{
				IdentificationView.this.btnConnection.setEnabled(false);
				return;
			}
			IdentificationView.this.btnConnection.setEnabled(true);
		}

		@Override
		public void insertUpdate(DocumentEvent e)
		{
			checkFieldsContent();
		}

		@Override
		public void removeUpdate(DocumentEvent e)
		{
			checkFieldsContent();
		}

		@Override
		public void changedUpdate(DocumentEvent e)
		{
			checkFieldsContent();
		}
	}

	private class KeyPressed extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK);
			int key = e.getKeyCode();
			Object source = e.getSource();
			if (key == KeyEvent.VK_ENTER)
				login();
			else if(ks.getKeyCode() == key && (source.equals(IdentificationView.this.fPassword) || source.equals(IdentificationView.this.fLoginValue)))
				if(e.getSource().equals(IdentificationView.this.fPassword))
					IdentificationView.this.fPassword.setText(null);

		}
	}
}