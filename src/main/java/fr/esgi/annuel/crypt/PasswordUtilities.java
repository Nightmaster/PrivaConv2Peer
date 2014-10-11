package fr.esgi.annuel.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Pattern;
import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.constants.PasswordConstraints;

import static fr.esgi.annuel.constants.PasswordConstraints.*;

public class PasswordUtilities
{
	public static final String PASSWORD_STANDARD_FORMAT = "Le mot de passe doit \u00EAtre d'au moins 8 caract\u00E8res et \u00EAtre compos\u00E9 de :\r\n\t- Au moins 1 majuscule\r\n\t- Au moins 1 minuscule\r\n\t- Au moins 1 chiffre\r\n\t- Au moins 1 caract\u00E8re sp\u00E9cial";

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
		char c;
		do
		{
			c = Constants.SPEC_CHARS[i];
			if (-1 != pw.indexOf(c))
				res = true;
			i++ ;
		}while (!res && Constants.SPEC_CHARS.length > i); // Tant que res est faux ET que i < longueur de SPEC_CHAR
		return res;
	}

	/**
	 * Vérifie que le pw contient au moins une majuscule
	 *
	 * @param pw {String}: le mot de passe à vérifier
	 * @return {boolean} <code>true</code> si le pw contient au moins une majuscule, <code>false</code> sinon
	 **/
	private static boolean contientMaj(String pw)
	{
		return Pattern.compile("(?=.*[A-Z])").matcher(pw).find();
	}

	/**
	 * Vérifie que le pw contient au moins une minuscule
	 *
	 * @param pw {String}: le mot de passe à vérifier
	 * @return {boolean} <code>true</code> si le pw contient au moins une minuscule, <code>false</code> sinon
	 **/
	private static boolean contientMin(String pw)
	{
		return Pattern.compile("(?=.*[a-z])").matcher(pw).find();
	}

	/**
	 * Vérifie que le pw contient au moins un chiffre
	 *
	 * @param pw {String}: le mot de passe à vérifier
	 * @return {boolean} <code>true</code> si le pw contient au moins un chiffre, <code>false</code> sinon
	 **/
	private static boolean contientNombre(String pw)
	{
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
	 * Vérifie que le mot de passe ne contient que les caractères acceptés
	 *
	 * @param pw {String]: Le mot de passe à vérifier
	 * @return {boolean}: <code>true</code> si le mot de passe valide les attentes, <code>false</code> sinon
	 **/
	private static boolean seulementAcceptes(String pw)
	{
		return Pattern.compile("^[-!\"§$%&/()=?+*~#'_:\\.,@^<>£¤µa-zA-Z0-9 ]+$").matcher(pw).find();
	}


	private static Boolean pasEspace(String pw)
	{
		return ! pw.contains(" ");
	}

	/**
	 * Return a HashMap containing the tests name and a boolean indicating if it has been validated, for the given password
	 *
	 * @param pw {String}: the password to check
	 * @return {HashMap&lt;String, Boolean&gt;} The <code>HashMap</code> with the tests results. Ex: "Length":false
	 **/
	public static HashMap<PasswordConstraints, Boolean> isStrongEnough(String pw)
	{
		HashMap<PasswordConstraints, Boolean> res = new HashMap<>();
		res.put(LENGTH, estSuffisammentLong(pw));
		res.put(SPECIAL_CHARACTER, contientCaracSpe(pw));
		res.put(CONTAINS_NUMBER, contientNombre(pw));
		res.put(CONTAINS_MINUS_LETTER, contientMin(pw));
		res.put(CONTAINS_MAJUSCULE_LETTER, contientMaj(pw));
		res.put(WELL_FORMATED, seulementAcceptes(pw));
		res.put(NO_SPACE_CHAR, pasEspace(pw));
		return res;
	}

	/**
	* Hash (MD5) the password
	*
	* @param pw {{@link String}}: the password to hash
	*
	* @return {{@link String}}: the hash of the password
	**/
	public static String hashPassword(String pw)
	{
		try
		{
			byte[] byteArray = MessageDigest.getInstance("MD5").digest(pw.getBytes());
			StringBuilder sb = new StringBuilder(32);
			for (byte aByte : byteArray)
			{
				sb.append(Integer.toHexString((aByte & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}