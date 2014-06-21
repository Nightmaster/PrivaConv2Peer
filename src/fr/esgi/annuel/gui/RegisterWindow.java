package fr.esgi.annuel.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterWindow
{
	public static void main(String[] args)
	{

		JFrame fenetre = new JFrame();

		JLabel l_pseudo = new JLabel("Pseudo : ");
		JLabel l_usermail = new JLabel("Email : ");
		JLabel l_lastname = new JLabel("Nom : ");
		JLabel l_firstname = new JLabel("Prénom : ");
		JLabel l_password = new JLabel("Mot de passe : ");

		JTextField f_pseudo = new JTextField("");
		JTextField f_mail = new JTextField("");
		JTextField f_lastname = new JTextField("");
		JTextField f_firstname = new JTextField("");
		JTextField f_password = new JTextField("");

		fenetre.setTitle("Inscription");
		fenetre.setSize(400, 400);
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);

		fenetre.setBackground(Color.white);
		JPanel top = new JPanel();
		top.setBounds(0, 39, 1184, 750);
		Font police = new Font("Arial", Font.BOLD, 14);
		fenetre.getContentPane().setLayout(null);
		f_pseudo.setFont(police);
		f_pseudo.setPreferredSize(new Dimension(150, 30));
		f_pseudo.setForeground(Color.BLUE);

		f_mail.setFont(police);
		f_mail.setPreferredSize(new Dimension(150, 30));
		f_mail.setForeground(Color.BLUE);

		f_lastname.setFont(police);
		f_lastname.setPreferredSize(new Dimension(150, 30));
		f_lastname.setForeground(Color.BLUE);

		f_firstname.setFont(police);
		f_firstname.setPreferredSize(new Dimension(150, 30));
		f_firstname.setForeground(Color.BLUE);

		f_password.setFont(police);
		f_password.setPreferredSize(new Dimension(150, 30));
		f_password.setForeground(Color.BLUE);

		fenetre.getContentPane().add(top);

		JButton btnNewButton = new JButton("Création du compte");
		GroupLayout gl_top = new GroupLayout(top);
		gl_top.setHorizontalGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(96).addComponent(l_pseudo).addGap(33).addComponent(f_pseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_top.createSequentialGroup().addGap(107).addComponent(l_usermail).addGap(33).addComponent(f_mail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_top.createSequentialGroup().addGap(110).addComponent(l_lastname).addGap(33).addComponent(f_lastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_top.createSequentialGroup().addGap(95).addComponent(l_firstname).addGap(33).addComponent(f_firstname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_top.createSequentialGroup().addGap(67).addComponent(l_password).addGap(33).addGroup(gl_top.createParallelGroup(Alignment.LEADING).addComponent(btnNewButton).addComponent(f_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(860)));
		gl_top.setVerticalGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(
				gl_top.createSequentialGroup().addGap(13).addGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(8).addComponent(l_pseudo)).addComponent(f_pseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(13)
						.addGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(8).addComponent(l_usermail)).addComponent(f_mail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(8).addComponent(l_lastname)).addComponent(f_lastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(8).addComponent(l_firstname)).addComponent(f_firstname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(11)
						.addGroup(gl_top.createParallelGroup(Alignment.LEADING).addGroup(gl_top.createSequentialGroup().addGap(7).addComponent(l_password)).addComponent(f_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addComponent(btnNewButton).addContainerGap(500, Short.MAX_VALUE)));
		top.setLayout(gl_top);

	}
}
