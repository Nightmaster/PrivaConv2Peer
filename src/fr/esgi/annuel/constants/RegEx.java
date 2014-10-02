package fr.esgi.annuel.constants;

public enum RegEx
{
	VALID_LASTNAME("^[a-zA-Z-]+$"),
	VALID_FIRSTNAME("^[a-zA-Z -]+$"),
	VALID_EMAIL("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"),
	VALID_PSEUDO("^[a-zA-Z0-9_-]+$");

	private final String regEx;

	private RegEx(String regEx)
	{
		this.regEx = regEx;
	}

	public String getRegEx()
	{
		return this.regEx;
	}

}