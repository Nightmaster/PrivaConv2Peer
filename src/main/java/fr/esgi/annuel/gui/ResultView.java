package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.esgi.annuel.constants.Views;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.parser.subclasses.UserInfos;

public class ResultView extends JPanel
{
	private final MasterController controller;
	private JLabel lblCorrespondingUser, lblNoResults;
	private JTextArea txtrDescription;
	private JButton btnPrevious, btnNext, btnCancel, btnInvite, btnLaunchAnotherSearch, btnOk;
	private JSeparator separator;
	private UserInfos[] results;
	private int index = 0;

	/**
	 * Create the panel.
	 */
	public ResultView(MasterController controller, UserInfos[] results)
	{
		this.results = results;
		this.controller = controller;
		MasterController.setLookAndFeel();
		GroupLayout groupLayout = new GroupLayout(this);
		if(null != this.results && 0 != this.results.length)
		{
			groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(getLblCorrespondingUser())
				.addComponent(getTxtrDescription(), GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getBtnPrevious())
					.addGap(126)
					.addComponent(getBtnNext()))
				.addComponent(getSeparator(), GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(getBtnCancel())
					.addGap(34)
					.addComponent(getBtnInvite()))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(getLblCorrespondingUser())
						.addGap(11)
						.addComponent(getTxtrDescription(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(getBtnPrevious())
							.addComponent(getBtnNext()))
						.addGap(13)
						.addComponent(getSeparator(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(getBtnCancel())
							.addComponent(getBtnInvite())))
			);
		}
		else
		{
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(getLblNoResult())
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(20)
						.addComponent(getBtnLaunchAnotherSearch())
						.addGap(21)
						.addComponent(getBtnOk()))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(getLblNoResult())
						.addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(getBtnLaunchAnotherSearch())
							.addComponent(getBtnOk())))
			);
		}
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

	private JButton getBtnInvite()
	{
		if (this.btnInvite == null)
		{
			this.btnInvite = new JButton("Inviter");
			this.btnInvite.addActionListener(new BtnListener());
		}
		return this.btnInvite;
	}

	private JButton getBtnLaunchAnotherSearch()
	{
		if (this.btnLaunchAnotherSearch == null)
		{
			this.btnLaunchAnotherSearch = new JButton("Lancer une autre recherche");
			this.btnLaunchAnotherSearch.addActionListener(new BtnListener());
		}
		return this.btnLaunchAnotherSearch;
	}

	private JButton getBtnNext()
	{
		if (this.btnNext == null)
		{
			this.btnNext = new JButton("Suivant");
			if(1 == this.results.length)
				this.btnNext.setEnabled(false);
			this.btnNext.addActionListener(new BtnListener());
		}
		return this.btnNext;
	}

	private JButton getBtnOk()
	{
		if (this.btnOk == null)
		{
			this.btnOk = new JButton("Ok");
			this.btnOk.addActionListener(new BtnListener());
		}
		return this.btnOk;
	}

	private JButton getBtnPrevious()
	{
		if (this.btnPrevious == null)
		{
			this.btnPrevious = new JButton("Pr\u00E9c\u00E9dant");
			this.btnPrevious.setEnabled(false);
			this.btnPrevious.addActionListener(new BtnListener());
		}
		return this.btnPrevious;
	}

	private JLabel getLblCorrespondingUser()
	{
		if (this.lblCorrespondingUser == null)
		{
			this.lblCorrespondingUser = new JLabel("Utilisateur correspondant \u00E0 vos crit\u00E8res n\u00B0" + Integer.toString(this.index));
			this.lblCorrespondingUser.setForeground(Color.BLUE);
			this.lblCorrespondingUser.setFont(new Font("Calibri", Font.BOLD, 15));
		}
		return this.lblCorrespondingUser;
	}

	private JLabel getLblNoResult()
	{
		if (this.lblNoResults == null)
		{
			this.lblNoResults = new JLabel("Votre recherche n'a retourn\u00E9 aucun r\u00E9sultat");
			this.lblNoResults.setFont(new Font("Calibri", Font.BOLD, 15));
			this.lblNoResults.setForeground(Color.BLUE);
		}
		return this.lblNoResults;
	}

	private JSeparator getSeparator()
	{
		if (this.separator == null)
			this.separator = new JSeparator();
		return this.separator;
	}

	private JTextArea getTxtrDescription()
	{
		if (this.txtrDescription == null)
		{
			this.txtrDescription = new JTextArea();
			this.txtrDescription.setText("Identifiant de connexion :\r\n" + this.results[this.index].getLogin()
										 + "\r\n___________________________\r\nNom :\r\n" + this.results[this.index].getName()
										 + "\r\n___________________________\r\nPr\u00E9nom :\r\n" + this.results[this.index].getFirstName());
		}
		return this.txtrDescription;
	}

	private void updateButtons()
	{
		this.btnPrevious.setEnabled(0 != this.index);
		this.btnNext.setEnabled(this.results.length -1 != this.index);
	}

	private void updateLabels()
	{
		this.lblCorrespondingUser.setText("Utilisateur correspondant \u00E0 vos crit\u00E8res n\u00B0" + Integer.toString(this.index));
		this.txtrDescription.setText("Identifiant de connexion :\r\n" + this.results[this.index].getLogin() + "\r\n___________________________\r\nNom :\r\n"
									 + this.results[this.index].getName()
									 + "\r\n___________________________\r\nPr\u00E9nom :\r\n" + this.results[this.index].getFirstName());
	}

	private class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ResultView.this.controller.stayAlive();
			if(e.getSource().equals(ResultView.this.btnCancel) || e.getSource().equals(ResultView.this.btnOk))
				ResultView.this.controller.closeSearchFrame();
			else if (e.getSource().equals(ResultView.this.btnInvite))
			{
				int res = JOptionPane.showConfirmDialog(ResultView.this,
														"Vous allez envoyer une demande d'ami \u00E0 " + ResultView.this.results[ResultView.this.index].getLogin() + ".\nValider ?",
														"Validation",
														JOptionPane.YES_NO_OPTION);
				if (-1 == res)
					ResultView.this.controller.closeSearchFrame();
				else if (0 == res)
					ResultView.this.controller.addFriend(ResultView.this.results[ResultView.this.index].getLogin(), ResultView.this);
			}
			else if (e.getSource().equals(ResultView.this.btnNext))
			{
				ResultView.this.index ++;
				ResultView.this.updateButtons();
				ResultView.this.updateLabels();
			}
			else if (e.getSource().equals(ResultView.this.btnPrevious))
			{
				ResultView.this.index --;
				ResultView.this.updateLabels();
				ResultView.this.updateButtons();
			}
			else // if (e.getSource().equals(ResultView.this.btnLaunchAnotherSearch))
				ResultView.this.controller.changeView(Views.SEARCH);
		}
	}
}