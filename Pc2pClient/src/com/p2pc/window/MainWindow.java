package com.p2pc.window;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.pc2p.communication.client.Client;
import com.pc2p.communication.server.Server;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[]args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					System.out.println("Creation client");
					new Thread(new Client()).start();
					System.out.println("Client créé");
					new Thread(new Server()).start();
					System.out.println("Server Créé");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnServ = new JButton("Serv window");
		frame.getContentPane().add(btnServ, BorderLayout.SOUTH);
	}
}
