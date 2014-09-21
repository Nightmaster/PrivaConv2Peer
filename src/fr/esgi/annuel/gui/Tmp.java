package fr.esgi.annuel.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.jdesktop.xswingx.PromptSupport;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;

public class Tmp extends JPanel
{
	private JLabel lPasswordKey, lPasswordKeyAgain, lKeyLength;
	private JComboBox<Integer> fLenKey;
	private JPasswordField fPasswordKey, fPasswordKeyAgain;
	private JButton btnRegister;

	/**
	 * Create the panel.
	 */
	public Tmp()
	{
		setLayout(null);
		add(getLabelKeyLength());
		add(getFieldLengthKey());
		add(getLabelPasswordKey());
		add(getFieldPasswordKey());
		add(getLabelPasswordKeyAgain());
		add(getFieldPasswordKeyAgain());
		add(getBtnRegister());
	}

	private JButton getBtnRegister()
	{
		if (this.btnRegister == null)
		{
			this.btnRegister = new JButton("S'enregistrer");
			this.btnRegister.setBounds(79, 91, 93, 23);
		}
		return this.btnRegister;
	}

	private JComboBox<Integer> getFieldLengthKey()
	{
		if (this.fLenKey == null)
		{
			this.fLenKey = new JComboBox<Integer>();
			this.fLenKey.setMaximumRowCount(4);
			this.fLenKey.setBounds(144, 8, 95, 22);
			this.fLenKey.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1024, 2048, 4096}));
			this.fLenKey.setToolTipText("<html>\r\nLongueur de clefs de cryptage :<br>\r\n\t- 1024 : peu s\u00E9curis\u00E9, mais traitement rapide <br>\r\n  \t- 2048 : bon rapport s\u00E9curit\u00E9 / vitesse de traitement <br>\r\n  \t- 4096 : tr\u00E8s s\u00E9curis\u00E9, mais vitesse de traitement plus lente <br>\r\n</html>");
		}
		return this.fLenKey;
	}

	private JPasswordField getFieldPasswordKey()
	{
		if (this.fPasswordKey == null)
		{
			this.fPasswordKey = new JPasswordField();
			PromptSupport.setPrompt("Mot de passe de la clé", this.fPasswordKey);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.fPasswordKey);
			this.fPasswordKey.setBounds(144, 35, 95, 22);
		}
		return this.fPasswordKey;
	}

	private JPasswordField getFieldPasswordKeyAgain()
	{
		if (this.fPasswordKeyAgain == null)
		{
			this.fPasswordKeyAgain = new JPasswordField();
			PromptSupport.setPrompt("Mot de passe de la clé", this.fPasswordKey);
			PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, this.fPasswordKey);
			this.fPasswordKeyAgain.setBounds(144, 62, 95, 22);
		}
		return this.fPasswordKeyAgain;
	}

	private JLabel getLabelKeyLength()
	{
		if (this.lKeyLength == null)
		{
			this.lKeyLength = new JLabel("Longueur clef :");
			this.lKeyLength.setBounds(10, 12, 72, 14);
		}
		return this.lKeyLength;
	}

	private JLabel getLabelPasswordKey()
	{
		if (this.lPasswordKey == null)
		{
			this.lPasswordKey = new JLabel("Mot de passe clef: ");
			this.lPasswordKey.setBounds(10, 39, 91, 14);
		}
		return this.lPasswordKey;
	}

	private JLabel getLabelPasswordKeyAgain()
	{
		if (this.lPasswordKeyAgain == null)
		{
			this.lPasswordKeyAgain = new JLabel("Resaisir mot de passe clef : ");
			this.lPasswordKeyAgain.setBounds(10, 66, 134, 14);
		}
		return this.lPasswordKeyAgain;
	}

	private void setPasswordField(JPasswordField pf)
	{
		Font police = new Font("Arial", Font.BOLD, 14);
		pf.setFont(police);
		pf.setPreferredSize(new Dimension(150, 30));
	}

	private void setTextField(JTextField tf)
	{
		Font police = new Font("Arial", Font.BOLD, 14);
		tf.setFont(police);
		tf.setPreferredSize(new Dimension(150, 30));
	}
}
