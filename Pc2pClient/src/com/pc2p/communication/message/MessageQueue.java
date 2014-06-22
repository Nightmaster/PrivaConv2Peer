package com.pc2p.communication.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageQueue {
	private static final Map<String,List<Message>> messages=new HashMap<String,List<Message>>();
	
	
	synchronized public static void addMessage(String pseudo, Message message){
		if(messages.get(pseudo)==null){
			messages.put(pseudo, new ArrayList<Message>());
		}
		messages.get(pseudo).add(message);
		return ;
	}
	
	synchronized public static List<Message> getAllMessages(String pseudo){
		return messages.remove(pseudo);
	}
	
}
