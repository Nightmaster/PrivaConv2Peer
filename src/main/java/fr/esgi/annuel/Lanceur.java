package fr.esgi.annuel;

import java.util.Properties;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.ctrl.PropertiesController;
import fr.esgi.util.Outils;

/**
* @author Gaël B.
**/
public class Lanceur
{
	/**
	 * Launch the application.
	 *
	 * @param args {String ...} : arguments passed to the launcher
	 */
	public static void main(String... args)
	{
		Properties properties = Outils.readPropertyFile(Lanceur.class.getClassLoader().getResourceAsStream("config.properties"));
		new MasterController(new PropertiesController(properties)).launch();
	}
}