package fr.esgi.annuel.gui;

import fr.esgi.annuel.client.ClientInfo;
import fr.esgi.annuel.client.Friend;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;
import fr.esgi.annuel.parser.ConnectionJsonParser;
import fr.esgi.annuel.parser.subclasses.IpAndPort;
import fr.esgi.annuel.server.Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatView extends JPanel
{
	private static ClientInfo logedUser;
	private boolean clearArea = false;
    private MasterController controller;
	private String currentInterlocuteur = "";
	private Map<String, String> discution = new HashMap<>();
	private JList<String> list;
	private JTextPane text = new JTextPane();
	private JTextArea textArea = new JTextArea();
	private JScrollPane textPane = new JScrollPane(this.text);
	private DefaultListModel<String> contacts = new DefaultListModel<>();
	private JButton send = new JButton("Envoyer");
	private JPanel panel = new JPanel();
    private ClientInfo user;
    private ConnectionJsonParser cjp;

	/**
	 * Create the panel.
	 */
	public ChatView(MasterController controller, ClientInfo user, ConnectionJsonParser jsonParser)
	{
        this.controller = controller;
        this.user = user;
        this.cjp = jsonParser;
		MasterController.setLookAndFeel();

		initialize(user);
		setLayout(new BorderLayout(10, 10));
		add(this.list, BorderLayout.EAST);
		add(this.textPane, BorderLayout.CENTER);
		this.text.setEditable(false);
		add(this.panel, BorderLayout.SOUTH);
		this.textArea.setColumns(30);
		GroupLayout glPanel = new GroupLayout(this.panel);
		glPanel.setHorizontalGroup(
				glPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glPanel.createSequentialGroup()
						.addComponent(this.textArea, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(this.send, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)));
		glPanel.setVerticalGroup(
				glPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(this.send, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
				.addComponent(this.textArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE));
		this.panel.setLayout(glPanel);
		this.send.addActionListener(new BtnListener());
		this.textArea.addKeyListener(new EnterListener());
	}

	private void initialize(ClientInfo user)
	{
        logedUser = user;
		for (Friend pseudo : cjp.getFriendList()) {
            this.contacts.addElement(pseudo.getUsername());
        }
        this.contacts.addElement("stefens");
        this.contacts.addElement("brunogb");
        this.contacts.addElement("nightmaster");
		if (this.contacts.size() > 0)
			this.currentInterlocuteur = this.contacts.firstElement();
		this.list = new JList<String>(this.contacts);
		this.list.setPreferredSize(new Dimension(100, 100));
		this.list.addMouseListener(new MouseClickListener());
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					// Récupération des nouveaux messages à afficher chez l'utilisateur
					List<Message> listM = MessageQueue.getAllMessagesToPrint(logedUser.getLogin());
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    if (listM != null && !listM.isEmpty())
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ChatView.this.text.getText());
                        for (Message message : listM) {
                            sb.append(logedUser.getLogin());
                            sb.append(" (");
                            sb.append(df.format(message.getReceiveDate()));
                            sb.append(")");
                            sb.append(" : ");
                            sb.append(message.getMessage() + "\n");
                            System.out.println(sb.toString());
                        }
                        ChatView.this.text.setText(sb.toString());
                    }
				}
			}
		}).start();
	}

	private class BtnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Message mess = new Message();
			mess.setMessage(ChatView.this.textArea.getText());
			mess.setReceiveDate(new Date());
			MessageQueue.addMessage(logedUser.getLogin(), mess);
            MessageQueue.addMessageToPrint(logedUser.getLogin(), mess);
			ChatView.this.textArea.setText("");
            ChatView.this.repaint();
		}

	}

	private class EnterListener implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{

			if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers() == InputEvent.SHIFT_MASK)
				ChatView.this.textArea.setText(ChatView.this.textArea.getText() + '\n');
			else if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				Message mess = new Message();
				mess.setMessage(ChatView.this.textArea.getText());
                mess.setReceiveDate(new Date());
                MessageQueue.addMessage(logedUser.getLogin(), mess);
                MessageQueue.addMessageToPrint(logedUser.getLogin(), mess);
                ChatView.this.clearArea = true;
			}
			ChatView.this.textPane.getVerticalScrollBar().setValue(ChatView.this.text.getText().length());
            ChatView.this.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			if (ChatView.this.clearArea)
			{
				ChatView.this.textArea.setText("");
				ChatView.this.clearArea = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e)
		{}
	}

	private class MouseClickListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{}

		@Override
		public void mouseEntered(MouseEvent e)
		{}

		@Override
		public void mouseExited(MouseEvent e)
		{}

		@Override
		public void mousePressed(MouseEvent e)
		{}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// changement de l'affichage lorsque l'utilisateur change d'interlocuteur
			if (ChatView.this.currentInterlocuteur != ChatView.this.list.getSelectedValue())
			{
				ChatView.this.discution.put(ChatView.this.currentInterlocuteur, ChatView.this.text.getText());
				ChatView.this.currentInterlocuteur = ChatView.this.list.getSelectedValue();
                IpAndPort ipPort = ChatView.this.controller.getUserIpAndPort(ChatView.this.currentInterlocuteur);
                System.out.println(ipPort.getPort());
                Thread t = new Thread (new Client(ipPort.getIpAdress(),ipPort.getPort(),ChatView.this.currentInterlocuteur));
                t.start();
				ChatView.this.text.setText(ChatView.this.discution.get(ChatView.this.currentInterlocuteur));
			}
		}
	}
}