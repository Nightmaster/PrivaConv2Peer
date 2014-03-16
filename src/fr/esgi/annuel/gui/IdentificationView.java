package fr.esgi.annuel.gui;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IdentificationView extends JPanel
{
	private static final long serialVersionUID = -3948992383967747160L;
	private JButton btnConnnexion;
	private JCheckBox chckbxSeSouvenirDe;
	private JLabel lblIdentifiantDeConnexion;
	private JLabel lblPwd;
	private JLabel lblSenregistrer;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	* Create the panel.
	**/
	public IdentificationView()
	{
		setLayout(null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblIdentifiantDeConnexion()).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblPwd()))
				.addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addGap(23).addComponent(getBtnConnnexion())).addComponent(getChckbxSeSouvenirDe()).addComponent(getLblSenregistrer()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addComponent(getLblIdentifiantDeConnexion()).addGap(4).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(13).addComponent(getLblPwd()).addGap(4).addComponent(getPasswordField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(getBtnConnnexion()).addGap(7).addComponent(getChckbxSeSouvenirDe()).addGap(17).addComponent(getLblSenregistrer())));
		setLayout(groupLayout);

	}

	private JButton getBtnConnnexion()
	{
		if (btnConnnexion == null)
		{
			btnConnnexion = new JButton("Connnexion");
		}
		return btnConnnexion;
	}

	private JCheckBox getChckbxSeSouvenirDe()
	{
		if (chckbxSeSouvenirDe == null)
		{
			chckbxSeSouvenirDe = new JCheckBox("Se souvenir de moi");
		}
		return chckbxSeSouvenirDe;
	}

	private JLabel getLblIdentifiantDeConnexion()
	{
		if (lblIdentifiantDeConnexion == null)
		{
			lblIdentifiantDeConnexion = new JLabel("Identifiant de connexion :");
		}
		return lblIdentifiantDeConnexion;
	}

	private JLabel getLblPwd()
	{
		if (lblPwd == null)
		{
			lblPwd = new JLabel("Mot de passe :");
		}
		return lblPwd;
	}

	private JLabel getLblSenregistrer()
	{
		if (lblSenregistrer == null)
		{
			lblSenregistrer = new JLabel("<HTML><U>S'enregistrer<U><HTML>");
			lblSenregistrer.setForeground(Color.BLUE);
		}
		return lblSenregistrer;
	}

	public String getLogin()
	{
		return getTextField().getText();
	}

	private JPasswordField getPasswordField()
	{
		if (passwordField == null)
		{
			passwordField = new JPasswordField();
		}
		return passwordField;
	}

	public String getPw()
	{
		return String.valueOf(getPasswordField().getPassword());
	}

	private JTextField getTextField()
	{
		if (textField == null)
		{
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
}
