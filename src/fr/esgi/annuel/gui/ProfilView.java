package fr.esgi.annuel.gui;

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
		setLayout(null);
		add(getLblNom());
		add(getTextFieldFName());
		add(getLblPrnom());
		add(getTextFieldName());
		add(getLblPseudonyme());
		add(getTextField());
		add(getLblNouveauMotDe());
		add(getPwdFieldChange());
		add(getLblConfirmationDuMot());
		add(getPwdFieldConfirm());
		add(getTextField_1());
		add(getLblAdresseEmail());

	}

	private JLabel getLblAdresseEmail()
	{
		if (lblAdresseEmail == null)
		{
			lblAdresseEmail = new JLabel("Adresse email");
			lblAdresseEmail.setBounds(162, 0, 66, 14);
		}
		return lblAdresseEmail;
	}

	private JLabel getLblConfirmationDuMot()
	{
		if (lblConfirmationDuMot == null)
		{
			lblConfirmationDuMot = new JLabel("Confirmation du mot de passe");
			lblConfirmationDuMot.setBounds(162, 107, 143, 14);
		}
		return lblConfirmationDuMot;
	}

	private JLabel getLblNom()
	{
		if (lblNom == null)
		{
			lblNom = new JLabel("Nom");
			lblNom.setBounds(0, 0, 21, 14);
		}
		return lblNom;
	}

	private JLabel getLblNouveauMotDe()
	{
		if (lblNouveauMotDe == null)
		{
			lblNouveauMotDe = new JLabel("Nouveau mot de passe");
			lblNouveauMotDe.setBounds(162, 56, 110, 14);
		}
		return lblNouveauMotDe;
	}

	private JLabel getLblPrnom()
	{
		if (lblPrnom == null)
		{
			lblPrnom = new JLabel("Pr\u00E9nom");
			lblPrnom.setBounds(0, 51, 36, 14);
		}
		return lblPrnom;
	}

	private JLabel getLblPseudonyme()
	{
		if (lblPseudonyme == null)
		{
			lblPseudonyme = new JLabel("Pseudonyme");
			lblPseudonyme.setBounds(0, 107, 61, 14);
		}
		return lblPseudonyme;
	}

	private JPasswordField getPwdFieldChange()
	{
		if (pwdFieldChange == null)
		{
			pwdFieldChange = new JPasswordField();
			pwdFieldChange.setBounds(162, 76, 143, 20);
		}
		return pwdFieldChange;
	}

	private JPasswordField getPwdFieldConfirm()
	{
		if (pwdFieldConfirm == null)
		{
			pwdFieldConfirm = new JPasswordField();
			pwdFieldConfirm.setBounds(162, 132, 143, 20);
		}
		return pwdFieldConfirm;
	}

	private JTextField getTextField()
	{
		if (textField == null)
		{
			textField = new JTextField();
			textField.setBounds(0, 132, 143, 20);
			textField.setColumns(10);
		}
		return textField;
	}

	private JTextField getTextField_1()
	{
		if (textField_1 == null)
		{
			textField_1 = new JTextField();
			textField_1.setBounds(162, 20, 143, 20);
			textField_1.setColumns(10);
		}
		return textField_1;
	}

	private JTextField getTextFieldFName()
	{
		if (textFieldFName == null)
		{
			textFieldFName = new JTextField();
			textFieldFName.setBounds(0, 20, 143, 20);
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
			textFieldName.setBounds(0, 76, 143, 20);
		}
		return textFieldName;
	}
}
