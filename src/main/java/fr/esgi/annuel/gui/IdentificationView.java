package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class IdentificationView extends JPanel
{
	private JButton btnConnection, btnRegister;
	private JCheckBox chckbxRememberMe;
	private JLabel lblConnectionIdentifier, lblPwd;
	private JPasswordField fPassword;
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
		this.controller.setLookAndFeel();
		setLayout(null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblPwd())).addComponent(getFPassword(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)).addContainerGap(316, Short.MAX_VALUE))
				.addGroup(groupLayout
					.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getLblConnectionIdentifier()).addComponent(getFLoginValue(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addGap(23).addComponent(getBtnConnection()))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(getBtnRegister()).addComponent(getChckbxRememberMe()))).addContainerGap(316, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			groupLayout.createSequentialGroup()
				.addComponent(getLblConnectionIdentifier()).addGap(4).addComponent(getFLoginValue(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(13).addComponent(getLblPwd()).addGap(4).addComponent(getFPassword(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
					.addComponent(getBtnConnection()).addGap(7).addComponent(getChckbxRememberMe()).addGap(18).addComponent(getBtnRegister()).addGap(99)));
		setLayout(groupLayout);
	}

	private JButton getBtnConnection()
	{
		if (this.btnConnection == null)
		{
			this.btnConnection = new JButton("Connnexion");
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

	private JLabel getLblConnectionIdentifier()
	{
		if (this.lblConnectionIdentifier == null)
			this.lblConnectionIdentifier = new JLabel("Identifiant de connexion");
		return this.lblConnectionIdentifier;
	}

	private JLabel getLblPwd()
	{
		if (this.lblPwd == null)
			this.lblPwd = new JLabel("Mot de passe :");
		return this.lblPwd;
	}

	private JPasswordField getFPassword()
	{
		if (this.fPassword == null)
		{
			this.fPassword = new JPasswordField();
			PromptSupport.setPrompt("Mot de passe", this.fPassword);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.fPassword);
		}
		return this.fPassword;
	}

	private JTextField getFLoginValue()
	{
		if (this.fLoginValue == null)
		{
			this.fLoginValue = new JTextField(10);
			PromptSupport.setPrompt("Pseudo ou @ mail", this.fLoginValue);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.fLoginValue);
		}
		return this.fLoginValue;
	}

	public final IdentificationView reset()
	{
		this.fPassword.setText(null);
		if(this.chckbxRememberMe.isSelected())
		{
			this.fLoginValue.setText(this.fLoginValue.getText());
			if (! this.fLoginValue.requestFocusInWindow())
				this.fLoginValue.requestFocus();
		}
		else
		{
			this.fLoginValue.setText(null);
			if (! this.fPassword.requestFocusInWindow())
				this.fPassword.requestFocus();
		}
		return new IdentificationView(this.controller);
	}

	public final void setEnableChckBox(boolean enabled)
	{
		this.chckbxRememberMe.setEnabled(enabled);
	}

	public final void setLoginValue(String value)
	{
		this.fLoginValue.setText(value);
	}

	public final String getLogin()
	{
		return this.fLoginValue.getText();
	}

	public final String getHashedPassword()
	{
		return 0 != this.fPassword.getPassword().length ? PasswordUtilities.hashPassword(String.valueOf(getFPassword().getPassword())) : null;
	}

	private final class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			final String login = getLogin(),
						 password = getHashedPassword();

			if(Strings.isNullOrEmpty(password))
				return;
			if(login.contains("@"))
				if(! isValidFieldContent(login, EMAIL))
				{
					JOptionPane.showMessageDialog(IdentificationView.this, getErrorMessageFor(EMAIL), "Identifiant incorrect", JOptionPane.ERROR_MESSAGE);
				}
				else
					IdentificationView.this.controller.connect(null, login, password);
			else if (!isValidFieldContent(login, PSEUDO))
			{
				JOptionPane.showMessageDialog(IdentificationView.this, getErrorMessageFor(PSEUDO), "Identifiant incorrect", JOptionPane.ERROR_MESSAGE);
			}
			else
				IdentificationView.this.controller.connect(login, null, password);
		}
	}
}