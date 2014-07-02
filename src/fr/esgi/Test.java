package fr.esgi;

import fr.esgi.annuel.constants.Constants;
import fr.esgi.annuel.crypt.PasswordUtilities;

public class Test
{
	public static void main(String[] args)
	{
		System.out.println(PasswordUtilities.isStrongEnough("AbC*1234").get(Constants.PW_GOOD_FMT));
		System.out.println(PasswordUtilities.isStrongEnough("abc%1234").get(Constants.PW_GOOD_FMT));
		System.out.println(PasswordUtilities.isStrongEnough("AbC*1234").get(Constants.PW_GOOD_FMT));
		System.out.println(PasswordUtilities.isStrongEnough("ABC%1234").get(Constants.PW_GOOD_FMT));
	}

}
