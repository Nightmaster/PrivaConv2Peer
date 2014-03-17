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

public class DSAKeysGenerator
{
	/**
	* @param length
	* @param pw
	* @return
	* @throws NoSuchAlgorithmException
	* @throws NoSuchProviderException
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

	/**
	* @param pair
	* @return
	**/
	private static PublicKey getPublicKey(KeyPair pair)
	{
		return pair.getPublic();
	}

	/**
	* @param pair
	* @return
	**/
	private static PrivateKey getPrivateKey(KeyPair pair)
	{
		return pair.getPrivate();
	}
}
