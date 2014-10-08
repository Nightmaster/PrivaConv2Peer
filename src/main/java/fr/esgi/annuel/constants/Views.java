package fr.esgi.annuel.constants;

public enum Views
{
	IDENTIFICATION("Identification"), REGISTER("Register"), CHAT("Chat"), PROFILE("Profile"), REGISTER_PART_2("Register - key part");

	private final String name;

	Views(String name)
	{
		this.name = name;
	}

	public static Views getViewByName(String name)
	{
		if (null != name)
			return Views.valueOf(name.toUpperCase());
		else
			throw new IllegalArgumentException("The 'name' argument cannot be null");
	}

	/**
	 * Return the selected view's name
	 *
	 * @return the name of the view
	 **/
	public String getName()
	{
		return this.name;
	}
}