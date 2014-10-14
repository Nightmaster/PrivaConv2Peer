package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.esgi.annuel.ctrl.MasterController;

import static org.jdesktop.xswingx.PromptSupport.FocusBehavior.SHOW_PROMPT;
import static org.jdesktop.xswingx.PromptSupport.setFocusBehavior;
import static org.jdesktop.xswingx.PromptSupport.setPrompt;

public class SearchView extends JPanel
{
	private static final String TOOL_TIP_TEXT = "<html>\r\n\t<p>\r\n\t\tUtilisez * comme un joker pour des recherches partielles.\r\n\t\t<br/>\r\n\t\tLaissez vide si inutilis\u00E9\r\n\t</p>\r\n</html>";
	private JButton btnCancel, btnSearch;
	private JLabel lblTypeYourResearch, lblUsername, lblEmail, lblName, lblFirstName;
	private JTextField fUsername, fEmail, fName, fFirstName;

	private MasterController controller;

	/**
	 * Create the panel.
	 */
	public SearchView(MasterController controller)
	{
		this.controller = controller;
		MasterController.setLookAndFeel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(getLblTypeYourResearch())
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblUsername())
					.addGap(15)
					.addComponent(getFUsername(), GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblEmail())
					.addGap(70)
					.addComponent(getFEmail(), GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblName())
					.addGap(73)
					.addComponent(getFName(), GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblFirstName())
					.addGap(58)
					.addComponent(getFFirstName(), GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(getBtnCancel())
					.addGap(15)
					.addComponent(getBtnSearch()))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getLblTypeYourResearch())
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLblUsername()))
						.addComponent(getFUsername(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLblEmail()))
						.addComponent(getFEmail(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLblName()))
						.addComponent(getFName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(getLblFirstName()))
						.addComponent(getFFirstName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(getBtnCancel())
						.addComponent(getBtnSearch())))
		);
		setLayout(groupLayout);
	}

	private JButton getBtnCancel()
	{
		if (this.btnCancel == null)
		{
			this.btnCancel = new JButton("Annuler");
			this.btnCancel.addActionListener(new BtnListener());
		}
		return this.btnCancel;
	}

	private JButton getBtnSearch()
	{
		if (this.btnSearch == null)
		{
			this.btnSearch = new JButton("Lancer la recherche");
			this.btnSearch.addActionListener(new BtnListener());
		}
		return this.btnSearch;
	}

	private JTextField getFEmail()
	{
		if (this.fEmail == null)
		{
			this.fEmail = new JTextField();
			this.fEmail.setToolTipText(TOOL_TIP_TEXT);
			this.fEmail.setColumns(10);
			setPrompt("Adresse email \u00E0 rechercher", this.fEmail);
			setFocusBehavior(SHOW_PROMPT, this.fEmail);
		}
		return this.fEmail;
	}

	private JTextField getFFirstName()
	{
		if (this.fFirstName == null)
		{
			this.fFirstName = new JTextField();
			this.fFirstName.setToolTipText(TOOL_TIP_TEXT);
			this.fFirstName.setColumns(10);
			setPrompt("Pr\u00E9nom \u00E0 rechercher", this.fFirstName);
			setFocusBehavior(SHOW_PROMPT, this.fFirstName);
		}
		return this.fFirstName;
	}

	private JTextField getFName()
	{
		if (this.fName == null)
		{
			this.fName = new JTextField();
			this.fName.setToolTipText(TOOL_TIP_TEXT);
			this.fName.setColumns(10);
			setPrompt("Nom \u00E0 rechercher", this.fName);
			setFocusBehavior(SHOW_PROMPT, this.fName);
		}
		return this.fName;
	}

	private JTextField getFUsername()
	{
		if (this.fUsername == null)
		{
			this.fUsername = new JTextField();
			fUsername.setToolTipText(TOOL_TIP_TEXT);
			this.fUsername.setColumns(10);
			setPrompt("Nom d'utilisateur \u00E0 rechercher", this.fUsername);
			setFocusBehavior(SHOW_PROMPT, this.fUsername);
		}
		return this.fUsername;
	}

	private JLabel getLblEmail()
	{
		if (this.lblEmail == null)
			this.lblEmail = new JLabel("Email");
		return this.lblEmail;
	}

	private JLabel getLblFirstName()
	{
		if (this.lblFirstName == null)
			this.lblFirstName = new JLabel("Pr\u00E9nom");
		return this.lblFirstName;
	}

	private JLabel getLblName()
	{
		if (this.lblName == null)
			this.lblName = new JLabel("Nom");
		return this.lblName;
	}

	private JLabel getLblTypeYourResearch()
	{
		if (this.lblTypeYourResearch == null)
		{
			this.lblTypeYourResearch = new JLabel("Entrez votre recherche dans le champs.");
			this.lblTypeYourResearch.setForeground(Color.WHITE);
			this.lblTypeYourResearch.setFont(new Font("Calibri", Font.BOLD, 15));
		}
		return this.lblTypeYourResearch;
	}

	private JLabel getLblUsername()
	{
		if (this.lblUsername == null)
			this.lblUsername = new JLabel("Nom d'utilisateur");
		return this.lblUsername;
	}

	private class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource().equals(SearchView.this.btnCancel))
			{
				SearchView.this.controller.stayAlive();
				SearchView.this.controller.closeSearchFrame();
			}
			else if (e.getSource().equals(SearchView.this.btnSearch))
			{
				String username = SearchView.this.fUsername.getText(),
						email = SearchView.this.fEmail.getText(),
						name = SearchView.this.fName.getText(),
						firstName = SearchView.this.fFirstName.getText();
				SearchView.this.controller.search(username, email, name, firstName);
			}
		}
	}
}