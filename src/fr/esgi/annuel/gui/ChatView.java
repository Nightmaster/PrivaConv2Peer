package fr.esgi.annuel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import fr.esgi.annuel.client.Client;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.contact.Contacts;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;
import fr.esgi.annuel.server.Server;

public class ChatView {

	protected static final int MAJ_KEY = 16;
	private JFrame frame;
	private DefaultListModel<String> contacts;
	JTextArea textArea;
	JTextPane text;
	JScrollPane textPane;
	JList<String> list;
	boolean clearArea = false;
	String currentInterlocuteur = "";
	ClientInfo ci = new ClientInfo("stephen");
	Map<String, String> discution = new HashMap<String, String>();
	private JPanel panel;
	private JButton envoyer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Thread(new Server()).start();
					new Thread(new Client()).start();
					ChatView window = new ChatView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		text = new JTextPane();
		textPane = new JScrollPane(text);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10, 10));
		contacts = new DefaultListModel<String>();
		for (String pseudo : Contacts.getAllPseudo()) {
			contacts.addElement(pseudo);
		}
		contacts.addElement("stephen");
		contacts.addElement("Gael");
		// TODO : r�cup�rer les �l�ments � partir de la base de donn�es
		list = new JList<String>(contacts);
		list.setPreferredSize(new Dimension(100, 100));
		currentInterlocuteur = contacts.firstElement();
		MouseListener l = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Enregistrer les messages qui sont dans le textPane
				if (currentInterlocuteur != list.getSelectedValue()) {
					discution.put(currentInterlocuteur, text.getText());
					// supprimer le textpane en cours
					// en ajouter un nouveau
					currentInterlocuteur = list.getSelectedValue();
					text.setText(discution.get(currentInterlocuteur));
				} else {
					System.out.println("La fenetre est deja sur "
							+ currentInterlocuteur);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		};
		list.addMouseListener(l);
		frame.getContentPane().add(list, BorderLayout.LINE_END);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					List<Message> listM = MessageQueue.getAllMessagesToPrint(ci
							.getStrName());
					if (listM != null && !listM.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						sb.append(text.getText());
						for (Message message : listM) {
							sb.append(message.getMessage() + "\n");
						}
						text.setText(sb.toString());
					}
				}
			}
		}).start();
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		text.setEditable(false);

		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		textArea = new JTextArea();
		textArea.setColumns(30);
		envoyer = new JButton("Envoyer");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE,
								323, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(envoyer, GroupLayout.DEFAULT_SIZE,
								102, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
				.addComponent(envoyer, GroupLayout.DEFAULT_SIZE, 41,
						Short.MAX_VALUE));
		panel.setLayout(gl_panel);

		envoyer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Message mess = new Message();
				mess.setMessage(textArea.getText());
				mess.setReceiveDate(new Date());
				MessageQueue.addMessage(ci.getStrName(), mess);
				textArea.setText("");
				
			}
		});
		
		textArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (clearArea) {
					textArea.setText("");
					clearArea = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER
						&& e.getModifiers() == KeyEvent.SHIFT_MASK) {
					textArea.setText(textArea.getText() + '\n');
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
					Message mess = new Message();
					mess.setMessage(textArea.getText());
					mess.setReceiveDate(new Date());
					MessageQueue.addMessage(ci.getStrName(), mess);
					clearArea = true;
				} 
				textPane.getVerticalScrollBar().setValue(text.getText().length());
			}
		});
	}
}
