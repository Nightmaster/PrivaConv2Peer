package fr.esgi.annuel;

import fr.esgi.annuel.ctrl.MasterController;

public class Lanceur
{
	// private static int nbPersonnages;

	/**
	 * Launch the application.
	 *
	 * @param args {String []} : Tableau des arguments passés au lancement
	 **/
	public static void main(String[] args)
	{
		MasterController m = new MasterController();
		m.launch();
	}

}
