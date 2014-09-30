package fr.esgi.annuel.constants;

/**
 * @author Gaël B.
 */
public enum Parameters
{
	USERNAME("username"),
	PASSWORD("pw"),
	PASSWORD_KEY("pwK"),
	EMAIL("email"),
	FIRSTNAME("firstname"),
	LASTNAME("name"),
	KEY_LENGTH("length"),
	VALIDATION("validate");

	private final String value;

	private Parameters(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}
}
