package fr.esgi.annuel.crypt;

import java.util.HashMap;
import java.util.regex.Pattern;
import fr.esgi.annuel.constants.Constants;

public class PasswordUtilities
{

	/**
	 * Retourne <code>Vrai</code> si le mot de passe contient un caractère spécial, <code>Faux</code> sinon
	 *
	 * @param pw {String}: le mot de passe à vérifier
	 * @return {boolean} <code>true</code> si pw.contains(["-!\"§$%&/()=?+*~#'_:.,;@\^<>£¤µ"]) == true, <code>false</code> sinon
	 **/
	private static boolean contientCaracSpe(String pw)
	{
		boolean res = false;
		int i = 0;
		CharSequence c;
		do
		{
			c = Constants.SPEC_CHARS[i];
			if (pw.contains(c))
				res = true;
			i++ ;
		}while (true != res && Constants.SPEC_CHARS.length > i); // Tant que res est faux ET que i < longueur de SPEC_CHAR
		return res;
	}

	private static boolean contientNombre(String pw)
	{
		// TODO trouver une RegEx qui fonctionne !
		return Pattern.compile(".*\\d+.*").matcher(pw).find();
	}

	/**
	 * Retourne <code>Vrai</code> si la longueur du mot de passe est supérieure à 8, <code>Faux</code> sinon
	 *
	 * @param pw {String}: le mot de passe à vérifier
	 * @return {boolean} <code>true</code> si pw.length >= 8, <code>false</code> sinon
	 **/
	private static boolean estSuffisammentLong(String pw)
	{
		return pw.length() >= 8;
	}

	/**
	 * Return a HashMap containing the tests name and a boolean indicating if it has been validated, for the given password
	 *
	 * @param pw {String}: the password to check
	 * @return {HashMap&lt;String, Boolean&gt;} The <code>HashMap</code> with the tests results. Ex: "Length":false
	 **/
	public static HashMap<String, Boolean> isStrongEnough(String pw)
	{
		HashMap<String, Boolean> res = new HashMap<String, Boolean>();
		res.put(Constants.PW_LENGTH, estSuffisammentLong(pw));
		res.put(Constants.PW_SPE_CHAR, contientCaracSpe(pw));
		res.put(Constants.PW_NUMBER, contientNombre(pw));
		return res;
	}
}
