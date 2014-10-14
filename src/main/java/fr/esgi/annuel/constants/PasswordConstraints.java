package fr.esgi.annuel.constants;

import java.util.Arrays;

import static fr.esgi.annuel.constants.Constants.*;

/**
 * @author Gaël B.
 */
public enum PasswordConstraints
{
	LENGTH("Votre mot de passe doit faire au moins 8 carat\u00E8res"),
	SPECIAL_CHARACTER("Votre mot de passe doit contenir au moins l'un des caract\u00E8res suivants : " + Arrays.toString(SPEC_CHARS)),
	CONTAINS_NUMBER ("Votre mot de passe doit contenir au moins un chiffre"),
	CONTAINS_MINUS_LETTER ("Votre mot de passe doit contenir une lettre minuscule"),
	CONTAINS_MAJUSCULE_LETTER ("Votre mot de passe doit contenir une lettre majusucule"),
	NO_SPACE_CHAR ("Votre mot de passe ne doit pas contenir le caractère espace"),
	WELL_FORMATED ("Votre mot de passe contient des caractères non-autorisés");

	private String errorMessage;

	private PasswordConstraints(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage()
	{
		return this.errorMessage;
	}
}