package fr.esgi.annuel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
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

public class ChatWindow
{

	protected static final int MAJ_KEY = 16;
	private DefaultListModel<String> contacts;
	private JButton envoyer;
	private JFrame frame;
	private JPanel panel;
	boolean clearArea = false;
	String currentInterlocuteur = "";
	Map<String, String> discution = new HashMap<String, String>();
	JList<String> list;
	static ClientInfo logedUser  = new ClientInfo("");
	JTextPane text;
	JTextArea textArea;
	JScrollPane textPane;

	public ChatWindow()
	{
		initialize();
	}

	/**
	 * Create the application.
	 * @param logedUser
	 */
	public ChatWindow(ClientInfo logedUser)
	{
		initialize();
		this.logedUser.setLogin(logedUser.getLogin());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					new Thread(new Server()).start();
					new Thread(new Client(logedUser)).start();
					ChatWindow window = new ChatWindow();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		this.frame = new JFrame();
		this.text = new JTextPane();
		this.textPane = new JScrollPane(this.text);
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new BorderLayout(10, 10));
		this.contacts = new DefaultListModel<String>();
		for (String pseudo : Contacts.getAllPseudo())
			contacts.addElement(pseudo);
		this.list = new JList<String>(this.contacts);
		this.list.setPreferredSize(new Dimension(100, 100));
		if(this.contacts.size()>0)
			this.currentInterlocuteur = this.contacts.firstElement();
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
					// R�cup�ration des nouveaux messages � afficher chez l'utilisateur
					List<Message> listM = MessageQueue.getAllMessagesToPrint(logedUser.getLogin());
					if (listM != null && !listM.isEmpty())
					{
						StringBuilder sb = new StringBuilder();
						sb.append(text.getText());
						for (Message message : listM)
							sb.append(message.getMessage() + "\n");
						text.setText(sb.toString());
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
		this.envoyer = new JButton("Envoyer");
		GroupLayout glPanel = new GroupLayout(this.panel);
		glPanel.setHorizontalGroup(glPanel.createParallelGroup(Alignment.LEADING).addGroup(glPanel.createSequentialGroup().addComponent(this.textArea, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.envoyer, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)));
		glPanel.setVerticalGroup(glPanel.createParallelGroup(Alignment.LEADING).addComponent(this.textArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE).addComponent(this.envoyer, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE));
		this.panel.setLayout(glPanel);

		this.envoyer.addActionListener(new ActionListener()
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
