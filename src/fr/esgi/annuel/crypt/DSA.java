package fr.esgi.annuel.crypt;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.HashMap;
import org.apache.commons.codec.binary.Base64;

public class DSA
{
	/**
	 * Retourne la cl� priv�e de la paire de cl� re�ue en entr�e
	 * @param pair {KeyPair}: La paire de cl� d'o� l'on va extraire la cl� priv�e
	 * @return {PublicKey} La cl� priv�e extraite de la paire de cl�
	 **/
	private static PrivateKey getPrivateKey(KeyPair pair)
	{
		return pair.getPrivate();
	}

	/**
	 * Retourne la cl� publique de la paire de cl� re�ue en entr�e
	 * @param pair {KeyPair}: La paire de cl� d'o� l'on va extraire la cl� publique
	 * @return {PublicKey} La cl� publique extraite de la paire de cl�
	 **/
	private static PublicKey getPublicKey(KeyPair pair)
	{
		return pair.getPublic();
	}

	/**
	 * Return a HashMap containing both public and private key, as a String. Private key is crypted with Vigenere alg.
	 *
	 * @param length {int}: The length of the key to create
	 * @param pw {String}: The password to encrypt the private key
	 * @return {HashMap&lt;String, String&gt;}: The HashMap containing the private and public keys stringified and crypted for the private key.
	 **/
	public static HashMap<String, String> getNewKeyPair(int length, String pw) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		Base64 b64 = new Base64();
		HashMap<String, String> hm = new HashMap<String, String>();
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(length, random);
		KeyPair pair = keyGen.generateKeyPair();
		Key pubKey = getPublicKey(pair), privKey = getPrivateKey(pair);
		hm.put("publicKey", b64.encode(pubKey.getEncoded()).toString());
		hm.put("PrivateKey", Vigenere.encrypt(pw, b64.encode(privKey.getEncoded()).toString()));
		return hm;
	}
}
