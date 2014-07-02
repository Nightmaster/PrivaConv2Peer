package fr.esgi.annuel.crypt;

public class Vigenere
{
	/**
	 *
	 *
	 * @param keyword
	 * @param line
	 * @return
	 **/
	public static String decrypt(String keyword, String line)
	{
		String result = "";
		int offset;
		int j = 0, shift;
		for (int i = 0; i < line.length(); i++ )
		{
			shift = keyword.charAt(j) - 97;
			j++ ;
			j %= keyword.length();
			offset = (line.charAt(i) - shift) % 256;
			if (offset < 0)
				offset += 256;
			result += (char) offset;
		}
		return result;
	}

	/**
	 *
	 *
	 * @param keyword
	 * @param line
	 * @return
	 **/
	public static String encrypt(String keyword, String line)
	{
		String result = "";
		int offset;
		int j = 0, shift;
		for (int i = 0; i < line.length(); i++ )
		{
			shift = keyword.charAt(j) - 97;
			j++ ;
			j %= keyword.length();
			offset = (line.charAt(i) + shift) % 256;
			result += (char) offset;
		}
		return result;
	}
}