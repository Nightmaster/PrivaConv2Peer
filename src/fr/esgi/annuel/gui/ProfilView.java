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
	private JTextField textField_1;
	private JTextField textFieldFName;
	private JTextField textFieldName;

	/**
	 * Create the panel.
	 */
	public ProfilView()
	{
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(getLblNom()).addGap(141).addComponent(getLblAdresseEmail()))
				.addGroup(groupLayout.createSequentialGroup().addComponent(getTextFieldFName(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getTextField_1(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(getLblPrnom()).addGap(126).addComponent(getLblNouveauMotDe()))
				.addGroup(groupLayout.createSequentialGroup().addComponent(getTextFieldName(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getPwdFieldChange(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(getLblPseudonyme()).addGap(101).addComponent(getLblConfirmationDuMot()))
				.addGroup(groupLayout.createSequentialGroup().addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE).addGap(19).addComponent(getPwdFieldConfirm(), GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblNom()).addComponent(getLblAdresseEmail())).addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextFieldFName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getTextField_1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblPrnom()).addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(getLblNouveauMotDe()))).addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextFieldName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getPwdFieldChange(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getLblPseudonyme()).addComponent(getLblConfirmationDuMot())).addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(getPwdFieldConfirm(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))));
		setLayout(groupLayout);

	}

	private JLabel getLblAdresseEmail()
	{
		if (lblAdresseEmail == null)
		{
			lblAdresseEmail = new JLabel("Adresse email");
		}
		return lblAdresseEmail;
	}

	private JLabel getLblConfirmationDuMot()
	{
		if (lblConfirmationDuMot == null)
		{
			lblConfirmationDuMot = new JLabel("Confirmation du mot de passe");
		}
		return lblConfirmationDuMot;
	}

	private JLabel getLblNom()
	{
		if (lblNom == null)
		{
			lblNom = new JLabel("Nom");
		}
		return lblNom;
	}

	private JLabel getLblNouveauMotDe()
	{
		if (lblNouveauMotDe == null)
		{
			lblNouveauMotDe = new JLabel("Nouveau mot de passe");
		}
		return lblNouveauMotDe;
	}

	private JLabel getLblPrnom()
	{
		if (lblPrnom == null)
		{
			lblPrnom = new JLabel("Pr\u00E9nom");
		}
		return lblPrnom;
	}

	private JLabel getLblPseudonyme()
	{
		if (lblPseudonyme == null)
		{
			lblPseudonyme = new JLabel("Pseudonyme");
		}
		return lblPseudonyme;
	}

	private JPasswordField getPwdFieldChange()
	{
		if (pwdFieldChange == null)
		{
			pwdFieldChange = new JPasswordField();
		}
		return pwdFieldChange;
	}

	private JPasswordField getPwdFieldConfirm()
	{
		if (pwdFieldConfirm == null)
		{
			pwdFieldConfirm = new JPasswordField();
		}
		return pwdFieldConfirm;
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

	private JTextField getTextField_1()
	{
		if (textField_1 == null)
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
		}
		return textField_1;
	}

	private JTextField getTextFieldFName()
	{
		if (textFieldFName == null)
		{
			textFieldFName = new JTextField();
			textFieldFName.setColumns(10);
		}
		return textFieldFName;
	}

	private JTextField getTextFieldName()
	{
		if (textFieldName == null)
		{
			textFieldName = new JTextField();
			textFieldName.setColumns(10);
		}
		return textFieldName;
	}
}
