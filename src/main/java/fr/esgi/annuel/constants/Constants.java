package fr.esgi.annuel.constants;

public interface Constants
{
	public final static String // Menu
			APP_NAME = "PrivaConv2Peer",
			ADD_USER = "Ajouter un utilisateur \u00E0 votre liste",
			LAUNCH_CONVERSATION = "Ouvrir une nouvelle conversation",
			PROFILE = "Profil",
			DISCONNECT = "D\u00E9connnexion",
			ABOUT = "\u00C0 Propos",
			QUIT = "Quitter",
			HELP = "Aide";

	// SPEC_CHARS = "-!'з$%&/()=?+*~#\"_:.,@^<>гд╡"
	public final static char[] SPEC_CHARS = "\u002D\u0021\u0027\u00A7\u0025\u0025\u0026\u002F\u0028\u0029\u003D\u003F\u002B\u002A\u007E\u0023\"\u005F\u003A\u002E\u002C\u0040\u005E\u003C\u003E\u00A3\u00A4\u00B5".toCharArray();
}