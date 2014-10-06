package fr.esgi.annuel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import fr.esgi.annuel.ctrl.MasterController;

public class Lanceur
{
	private static final Properties PROPERTIES = new Properties();
	/**
	 * Launch the application.
	 *
	 * @param args {String ...} : arguments passed to the launcher
	 */
	public static void main(String... args)
	{
		InputStream input = null;
		try
		{
			PROPERTIES.load(input = Lanceur.class.getClassLoader().getResourceAsStream("ressources/config.properties"));
		}
		catch (IOException ignored) {}
		finally
		{
			if (null != input)
				try
				{
					input.close();
				}
				catch (IOException ignored)
				{}
		}
		MasterController m = new MasterController(PROPERTIES);
		m.launch();
	}
}