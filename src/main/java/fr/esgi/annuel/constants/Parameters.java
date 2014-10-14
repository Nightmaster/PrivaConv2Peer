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
	VALIDATION("validate"),
	PORT("port");

	private final String parameterValue;

	private Parameters(String parameterValue)
	{
		this.parameterValue = parameterValue;
	}

	public String getParameterValue()
	{
		return this.parameterValue;
	}
}