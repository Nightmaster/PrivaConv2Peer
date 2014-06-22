package com.p2pc.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.pc2p.communication.client.Client;
import com.pc2p.communication.contact.Contact;
import com.pc2p.communication.contact.Contacts;
import com.pc2p.communication.message.Message;
import com.pc2p.communication.message.MessageQueue;
import com.pc2p.communication.server.Server;

public class ChatWindow {

	private JFrame frame;
	private DefaultListModel<String> contacts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Thread(new Server()).start();
					new Thread(new Client()).start();
					ChatWindow window = new ChatWindow();
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
	public ChatWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10,10));
		
		JTextArea textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.PAGE_END);

		contacts = new DefaultListModel<String>();
		for(String pseudo : Contacts.getAllPseudo()){
			contacts.addElement(pseudo);
		}
		contacts.addElement("Stephen");
		JList<String> list = new JList<String>(contacts);

		list.setPreferredSize(new Dimension(100, 100));
		frame.getContentPane().add(list, BorderLayout.LINE_END);
		
		final JTextPane textPane = new JTextPane();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					List<Message> listM = MessageQueue.getAllMessages("Stephen");
					if(listM!=null && !listM.isEmpty()){
						StringBuilder sb = new StringBuilder();
						sb.append(textPane.getText());
						for(Message message : listM){
							sb.append(message.getMessage()+"\n");
						}
						textPane.setText(sb.toString());
					}
				}
				
			}
		}).start();
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		
	}

}
