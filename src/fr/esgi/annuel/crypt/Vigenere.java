package fr.esgi.annuel.crypt;

public class Vigenere
{
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

	/*
	 * public static void main(String args[]) throws IOException { Vigenere obj = new Vigenere(); BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); int choice; System.out.println("Menu:\n1: Encryption\n2: Decryption"); choice = Integer.parseInt(in.readLine()); System.out.println("Enter the keyword: "); String keyword = in.readLine(); System.out.println("Enter the line: ");
	 * String line = in.readLine(); System.out.println("Result:"); switch (choice) { case 1: System.out.println(obj.encrypt(keyword, line)); break; case 2: System.out.println(obj.decrypt(keyword, line)); break; default: System.out.println("Invalid input!"); break; } }
	 */
}