package fr.esgi.annuel.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Class MessageQueue
 * Elle permet de cr�er une queue de messages afin de les afficher sur les diff�rent clients
 * */
public class MessageQueue
{
	private static final Map<String, List<Message>> messages = new HashMap<String, List<Message>>();
	private static final Map<String, List<Message>> messagesToPrint = new HashMap<String, List<Message>>();

	synchronized public static void addMessage(String pseudo, Message message)
	{
		if (messages.get(pseudo) == null)
			messages.put(pseudo, new ArrayList<Message>());
		messages.get(pseudo).add(message);
		return;
	}

	synchronized public static void addMessageToPrint(String pseudo, Message message)
	{
		if (messagesToPrint.get(pseudo) == null)
			messagesToPrint.put(pseudo, new ArrayList<Message>());
		messagesToPrint.get(pseudo).add(message);
		return;
	}

	synchronized public static List<Message> getAllMessages(String pseudo)
	{
		return messages.remove(pseudo);
	}

	synchronized public static List<Message> getAllMessagesToPrint(String pseudo)
	{
		return messagesToPrint.remove(pseudo);
	}
}
