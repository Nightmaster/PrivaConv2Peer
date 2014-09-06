package fr.esgi.annuel.constants;

public enum Views
{
	IDENTIFICATION, REGISTER, CHAT, PROFILE;

	public static Views getViewByName(String name)
	{
		return Views.valueOf(name.toUpperCase());
	}
}
