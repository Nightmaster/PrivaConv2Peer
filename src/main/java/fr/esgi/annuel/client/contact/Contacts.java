package fr.esgi.annuel.client.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Contacts
 * Elle contiendra une liste de contact, de l'utilisateur
 * Cette liste sera remplie lors de la connexion
 *
 * */
public class Contacts
{
	private static final Map<String, Contact> contactList = new HashMap<>();

	synchronized public static Contact addContact(Contact contact)
	{
		Contact oldContact = contactList.get(contact.getPseudo());
		contactList.put(contact.getPseudo(), contact);
		return oldContact;
	}

	synchronized public static List<Contact> getAllContact()
	{
		return new ArrayList<Contact>(contactList.values());
	}

	synchronized public static List<String> getAllPseudo()
	{
		return new ArrayList<String>(contactList.keySet());
	}

	synchronized public static Contact getContact(String pseudo)
	{
		return contactList.get(pseudo);
	}

	synchronized public static Contact removeContact(String pseudo)
	{
		return contactList.remove(pseudo);
	}

}
