package fr.esgi.annuel.constants;

/**
 * @author Gaël B.
 */
public enum FieldType
{
	PSEUDO, EMAIL, FIRSTNAME, LASTNAME, PASSWORD;

	public final static FieldType getValue(String fieldType)
	{
		if (null != fieldType)
			for (FieldType v : values())
				if (v.toString().equalsIgnoreCase(fieldType))
					return v;
		throw new IllegalArgumentException();
	}
}