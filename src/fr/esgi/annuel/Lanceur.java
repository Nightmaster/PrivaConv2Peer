package fr.esgi.annuel;

import fr.esgi.annuel.ctrl.MasterController;

public class Lanceur
{
	// private static int nbPersonnages;

	/**
	 * Launch the application.
	 *
	 * @param args {String []} : Tableau des arguments pass�s au lancement
	 **/
	public static void main(String[] args)
	{
		MasterController m = new MasterController();
		m.launch();
	}

}
