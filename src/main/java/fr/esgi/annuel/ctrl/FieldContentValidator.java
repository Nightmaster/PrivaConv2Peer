package fr.esgi.annuel.ctrl;

import fr.esgi.annuel.constants.FieldType;
import fr.esgi.annuel.constants.RegEx;

/**
* @author Gaël B.
**/
public abstract class FieldContentValidator
{
	public final static boolean isValidFieldContent(String fieldContent, FieldType fieldType)
	{
		if (FieldType.EMAIL.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_EMAIL.getRegEx());
		else if (FieldType.PSEUDO.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_PSEUDO.getRegEx());
		else if (FieldType.FIRSTNAME.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_FIRSTNAME.getRegEx());
		else if (FieldType.LASTNAME.equals(fieldType))
			return fieldContent.matches(RegEx.VALID_LASTNAME.getRegEx());
		else
			throw new IllegalArgumentException();
	}

	public final static String getErrorMessageFor(FieldType fieldType)
	{
		if(FieldType.PSEUDO.equals(fieldType))
			return "Le pseudo doit \u00EAtre compos\u00E9 uniquement de caract\u00E8res alphanum\u00E9riques, sans espace !";
		else if(FieldType.EMAIL.equals(fieldType))
			return "Format d'adresse email incorrect !";
		else if (FieldType.LASTNAME.equals(fieldType))
			return "Votre nom doit \u00EAtre compos\u00E9 de caract\u00E8res alpahb\u00E9tiques uniquement (espaces accept\u00E9s)";
		else
			return "Votre nom doit \u00EAtre compos\u00E9 de caract\u00E8res alphab\u00E9tiques uniquement (espaces et traits d'union accept\u00E9s)";
	}
}