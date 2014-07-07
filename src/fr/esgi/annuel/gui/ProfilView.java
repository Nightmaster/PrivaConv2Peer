package fr.esgi.annuel.gui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ProfilView extends JPanel
{
	private JLabel lblAdresseEmail;
	private JLabel lblConfirmationDuMot;
	private JLabel lblNom;
	private JLabel lblNouveauMotDe;
	private JLabel lblPrnom;
	private JLabel lblPseudonyme;
	private JPasswordField pwdFieldChange;
	private JPasswordField pwdFieldConfirm;
	private JTextField textField;
	private JTextField textField1;
	private JTextField textFieldFName;
	private JTextField textFieldName;

	/**
	 * Create the panel.
	 */
	public ProfilView()
	{
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(getLblNom()).addGap(141).addComponent(getLblAdresseEmail()))
				.addGroup(groupLayout.createSequentialGroup().addComponent(getTextFieldFName(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getTextField1(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(getLblPrnom()).addGap(126).addComponent(getLblNouveauMotDe()))
				.addGroup(groupLayout.createSequentialGroup().addComponent(getTextFieldName(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getPwdFieldChange(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(getLblPseudonyme()).addGap(101).addComponent(getLblConfirmationDuMot()))
				.addGroup(groupLayout.createSequentialGroup().addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getPwdFieldConfirm(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblNom()).addComponent(getLblAdresseEmail())).addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextFieldFName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getTextField1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblPrnom()).addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblNouveauMotDe()))).addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextFieldName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getPwdFieldChange(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblPseudonyme()).addComponent(getLblConfirmationDuMot())).addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getPwdFieldConfirm(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))));
		setLayout(groupLayout);

	}

	private JLabel getLblAdresseEmail()
	{
		if (this.lblAdresseEmail == null)
			this.lblAdresseEmail = new JLabel("Adresse email");
		return this.lblAdresseEmail;
	}

	private JLabel getLblConfirmationDuMot()
	{
		if (this.lblConfirmationDuMot == null)
			this.lblConfirmationDuMot = new JLabel("Confirmation du mot de passe");
		return this.lblConfirmationDuMot;
	}

	private JLabel getLblNom()
	{
		if (this.lblNom == null)
			this.lblNom = new JLabel("Nom");
		return this.lblNom;
	}

	private JLabel getLblNouveauMotDe()
	{
		if (this.lblNouveauMotDe == null)
			this.lblNouveauMotDe = new JLabel("Nouveau mot de passe");
		return this.lblNouveauMotDe;
	}

	private JLabel getLblPrnom()
	{
		if (this.lblPrnom == null)
			this.lblPrnom = new JLabel("Pr\u00E9nom");
		return this.lblPrnom;
	}

	private JLabel getLblPseudonyme()
	{
		if (this.lblPseudonyme == null)
			this.lblPseudonyme = new JLabel("Pseudonyme");
		return this.lblPseudonyme;
	}

	private JPasswordField getPwdFieldChange()
	{
		if (this.pwdFieldChange == null)
			this.pwdFieldChange = new JPasswordField();
		return this.pwdFieldChange;
	}

	private JPasswordField getPwdFieldConfirm()
	{
		if (this.pwdFieldConfirm == null)
			this.pwdFieldConfirm = new JPasswordField();
		return this.pwdFieldConfirm;
	}

	private JTextField getTextField()
	{
		if (this.textField == null)
		{
			this.textField = new JTextField();
			this.textField.setColumns(10);
		}
		return this.textField;
	}

	private JTextField getTextField1()
	{
		if (this.textField1 == null)
		{
			this.textField1 = new JTextField();
			this.textField1.setColumns(10);
		}
		return this.textField1;
	}

	private JTextField getTextFieldFName()
	{
		if (this.textFieldFName == null)
		{
			this.textFieldFName = new JTextField();
			this.textFieldFName.setColumns(10);
		}
		return this.textFieldFName;
	}

	private JTextField getTextFieldName()
	{
		if (this.textFieldName == null)
		{
			this.textFieldName = new JTextField();
			this.textFieldName.setColumns(10);
		}
		return this.textFieldName;
	}
}
