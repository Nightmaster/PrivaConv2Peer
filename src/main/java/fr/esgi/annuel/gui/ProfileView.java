package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import fr.esgi.annuel.ctrl.MasterController;

@SuppressWarnings("serial")
public class ProfileView extends JPanel
{
	private JLabel lblEmailAddress, lblPwConfirmation, lblName, lblNewPw, lblFirstName, lblPseudo;
	private JPasswordField pwdFieldChange, pwdFieldConfirm;
	private JTextField textField, textField1, fFirstName, fLastName;
	private MasterController controller;

	/**
	* Create the panel.
	**/
	public ProfileView(MasterController controller)
	{
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(getLblName()).addGap(141).addComponent(getLblEmailAddress()))
			.addGroup(groupLayout.createSequentialGroup().addComponent(getFFirstName(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getTextField1(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(getLblFirstName()).addGap(126).addComponent(getLblNewPw()))
			.addGroup(groupLayout.createSequentialGroup().addComponent(getFLastName(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getPwdFieldChange(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(getLblPseudo()).addGap(101).addComponent(getLblPwConfirmation()))
			.addGroup(groupLayout.createSequentialGroup().addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getPwdFieldConfirm(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblName()).addComponent(getLblEmailAddress())).addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getFFirstName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getTextField1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblFirstName()).addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblNewPw()))).addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getFLastName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getPwdFieldChange(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblPseudo()).addComponent(getLblPwConfirmation())).addGap(11)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getPwdFieldConfirm(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))));
		setLayout(groupLayout);

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

	private JTextField getFFirstName()
	{
		if (this.fFirstName == null)
		{
			this.fFirstName = new JTextField();
			this.fFirstName.setColumns(10);
		}
		return this.fFirstName;
	}

	private JTextField getFLastName()
	{
		if (this.fLastName == null)
		{
			this.fLastName = new JTextField();
			this.fLastName.setColumns(10);
		}
		return this.fLastName;
	}
}