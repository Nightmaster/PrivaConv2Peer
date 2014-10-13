package fr.esgi.annuel.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.client.contact.Contacts;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;
import fr.esgi.annuel.server.Server;

public class ChatWindow
{

	static ClientInfo logedUser = new ClientInfo();
	protected static final int MAJ_KEY = 16;
	boolean clearArea = false;
	String currentInterlocuteur = "";
	Map<String, String> discution = new HashMap<>();
	JList<String> list;
	JTextPane text;
	JTextArea textArea;
	JScrollPane textPane;
	private DefaultListModel<String> contacts;
	private JButton send;
	private JFrame frame;
	private JPanel panel;

	//FIXME Mettre à jour cette classe !!!
	public ChatWindow(MasterController controller)
	{
		controller.setLookAndFeel();
		initialize();
	}

	public void launchServer()
	{
		new Thread(new Server()).start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		this.frame = new JFrame();
		this.text = new JTextPane();
		this.textPane = new JScrollPane(this.text);
        this.frame.setTitle("PrivaConv2Peer");
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new BorderLayout(10, 10));
		this.contacts = new DefaultListModel<String>();
		for (String pseudo : Contacts.getAllPseudo())
			this.contacts.addElement(pseudo);
		this.list = new JList<String>(this.contacts);
		this.list.setPreferredSize(new Dimension(100, 100));
		if (this.contacts.size() > 0)
			this.currentInterlocuteur = this.contacts.firstElement();
		frame.setVisible(true);
		MouseListener l = new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseExited(MouseEvent e)
			{

			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// changement de l'affichage lorsque l'utilisateur change d'interlocuteur
				if (ChatWindow.this.currentInterlocuteur != ChatWindow.this.list.getSelectedValue())
				{
					ChatWindow.this.discution.put(ChatWindow.this.currentInterlocuteur, ChatWindow.this.text.getText());
					ChatWindow.this.currentInterlocuteur = ChatWindow.this.list.getSelectedValue();
					ChatWindow.this.text.setText(ChatWindow.this.discution.get(ChatWindow.this.currentInterlocuteur));
				}
			}
		};
		this.list.addMouseListener(l);
		this.frame.getContentPane().add(this.list, BorderLayout.LINE_END);

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					// Récupération des nouveaux messages à afficher chez l'utilisateur
					List<Message> listM = MessageQueue.getAllMessagesToPrint(logedUser.getLogin());
					if (listM != null && !listM.isEmpty())
					{
						StringBuilder sb = new StringBuilder();
						sb.append(ChatWindow.this.text.getText());
						for (Message message : listM)
							sb.append(message.getMessage() + "\n");
						ChatWindow.this.text.setText(sb.toString());
					}
				}
			}
		}).start();
		this.frame.getContentPane().add(this.textPane, BorderLayout.CENTER);
		this.text.setEditable(false);

		this.panel = new JPanel();
		this.frame.getContentPane().add(this.panel, BorderLayout.SOUTH);
		this.textArea = new JTextArea();
		this.textArea.setColumns(30);
		this.send = new JButton("Envoyer");
		GroupLayout glPanel = new GroupLayout(this.panel);
		glPanel.setHorizontalGroup(glPanel.createParallelGroup(Alignment.LEADING).addGroup(glPanel.createSequentialGroup().addComponent(this.textArea, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.send, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)));
		glPanel.setVerticalGroup(glPanel.createParallelGroup(Alignment.LEADING).addComponent(this.send, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE).addComponent(this.textArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE));
		this.panel.setLayout(glPanel);

		this.send.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Message mess = new Message();
				mess.setMessage(ChatWindow.this.textArea.getText());
				mess.setReceiveDate(new Date());
				MessageQueue.addMessage(logedUser.getLogin(), mess);
				ChatWindow.this.textArea.setText("");

			}
		});

		this.textArea.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{

				if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers() == InputEvent.SHIFT_MASK)
					ChatWindow.this.textArea.setText(ChatWindow.this.textArea.getText() + '\n');
				else if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					Message mess = new Message();
					mess.setMessage(ChatWindow.this.textArea.getText());
					mess.setReceiveDate(new Date());
					MessageQueue.addMessage(logedUser.getLogin(), mess);
					ChatWindow.this.clearArea = true;
				}
				ChatWindow.this.textPane.getVerticalScrollBar().setValue(ChatWindow.this.text.getText().length());
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (ChatWindow.this.clearArea)
				{
					ChatWindow.this.textArea.setText("");
					ChatWindow.this.clearArea = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e)
			{}
		});
	}
}