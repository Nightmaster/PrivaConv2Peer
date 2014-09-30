package fr.esgi.annuel.crypt;

public class Vigenere
{
	/**
	 * Static method to encrypt a string with the Vigenere algorithm
	 *
	 * @param passphrase the "passphrase" to encrypt the entry
	 * @param entry the {@link String} to encrypt
	 * @return the encrypted {@link String}
	 **/
	public static String decrypt(String passphrase, String entry)
	{
		String result = "";
		int offset;
		int j = 0, shift;
		for (int i = 0; i < entry.length(); i++ )
		{
			shift = passphrase.charAt(j) - 97;
			j++ ;
			j %= passphrase.length();
			offset = (entry.charAt(i) - shift) % 256;
			if (offset < 0)
				offset += 256;
			result += (char) offset;
		}
		return result;
	}

	/**
	 * Static method to decrypt a string with the Vigenere algorithm
	 *
	 * @param passphrase the "passphrase" to decrypt the entry
	 * @param entry the {@link String} to decrypt
	 * @return the decrypted {@link String}
	 **/
	public static String encrypt(String passphrase, String entry)
	{
		String result = "";
		int offset;
		int j = 0, shift;
		for (int i = 0; i < entry.length(); i++ )
		{
			shift = passphrase.charAt(j) - 97;
			j++ ;
			j %= passphrase.length();
			offset = (entry.charAt(i) + shift) % 256;
			result += (char) offset;
		}
		return result;
	}
}