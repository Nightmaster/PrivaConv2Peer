package fr.esgi.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;
import fr.esgi.annuel.ctrl.MasterController;

/**
 * @author Ga�l BLAISE
 *
 * Classe d'utilitaires type : outils de contr�le, fermeture de programme, etc.
 **/
public class Outils
{
	/**
	* Transforme une String Base64 repr�sentant une image en une BufferedImage
	 *
	* @param imageString {String}: la cha�ne de caract�re Base64 de l'image � d�coder
	* @return {BufferedImage} le buffer de l'image d�cod�e depuis la String en entr�e
	**/
	public static BufferedImage base64StringToImage(String imageString)
	{
		BufferedImage image = null;
		byte[] imageByte;
		try
		{
			imageByte = Base64.getDecoder().decode(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}

	/**
	* Fonction qui permet de quitter l'application compl�te, avec ou sans v�rification aupr�s de l'utilisateur
	 *
	* @param confirm {boolean} : <code>true</code> pour avoir une confirmation utilisateur, <code>false</code> sinon
	**/
	public static void breakPgm(boolean confirm, MasterController controller)
	{
		int quit;
		if (confirm) // Quitter avec confirmation
			quit = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
		else
			quit = 0; // Quitter sans confirmation
		if (quit == 0)
		{
			controller.disconnect();
			System.exit(0);
		}
	}

	/**
	* Transformation d'un tableau d'octets (Byte Array) en un UUID
	* @see http://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
	*
	* @param byteArray {<code>byte []</code>} : le byte array � transformer en UUID (Unique User Id)
	*
	* @return {UUID} l'UUID issu du tableau d'octet re�u en entr�e
	**/
	public static UUID byteArrayAsUuid(byte[] byteArray)
	{
		long msb = 0;
		long lsb = 0;
		for (int i = 0; i < 8; i++ )
			msb = (msb << 8) | (byteArray[i] & 0xff);
		for (int i = 8; i < 16; i++ )
			lsb = (lsb << 8) | (byteArray[i] & 0xff);
		UUID result = new UUID(msb, lsb);

		return result;
	}

	/**
	* Copie un flux entrant dans un flux sortant en tenant compte d'un caract�re de fin de s�quence
	 *
	* @param in {InputStream} : Le flux entrant � copier
	* @param out {OutputStream} : Le flux sortant o� la copie doit �tre effectu�e
	* @param end {String} : Le caract�re de fin de s�quence
	* @throws IOException En cas d'utilisation sur un flux (entrant ou sortant) d�j� ferm�
	**/
	public static void copyStream(InputStream in, OutputStream out, String end) throws Exception, IOException
	{
		if (!estDefinit(end))
			throw new Exception("End string can not be null or empty");
		int i = -1, pos = 0;
		while (-1 != (i = in.read()))
		{
			out.write(i);
			pos = i == end.charAt(pos) ? pos + 1 : 0;
			if (pos == end.length())
				break;
		}
	}

	/**
	* Teste si un objet est d�finit.
	 *
	* @param obj {Object} : l'objet � tester.
	* @return {boolean} : renvoie <code>false</code> si l'objet est <code>null</code>. Dans le cas d'une string vide renvoie <code>false</code>.<br>Sinon renvoie <code>true</code>.
	**/
	public static boolean estDefinit(Object obj)
	{
		if (null == obj)
			return false;
		else if (obj.equals(""))
			return false;
		return true;
	}

	/**
	* Teste si un nombre est (strictement) positif ou non
	 *
	* @param nombre {double} : le double � v�rifier.
	* @param strict {boolean} : indique si on veut v�rifier ou non la nullit� du nombre.
	* @return {boolean} : renvoie <code>false</code> si le nombre est n�gatif, <code>true</code> si il est positif. En cas d'�galit� avec 0 : renvoie <code>true</code> si strict est vrai, <code>false</code> sinon.
	**/
	public static boolean estPositif(double nombre, boolean strict)
	{
		if (strict && nombre == 0)
			return true;
		else if (nombre > 0)
			return true;
		return false;
	}

	/**
	* Teste si un nombre est (strictement) positif ou non
	 *
	* @param nombre {double} : le float � v�rifier.
	* @param strict {boolean} : indique si on veut v�rifier ou non la nullit� du nombre.
	* @return {boolean} : renvoie <code>false</code> si le nombre est n�gatif, <code>true</code> si il est positif. En cas d'�galit� avec 0 : renvoie <code>true</code> si strict est vrai, <code>false</code> sinon.
	**/
	public static boolean estPositif(float nombre, boolean strict)
	{
		if (strict && nombre == 0)
			return true;
		else if (nombre > 0)
			return true;
		return false;
	}

	/**
	* Teste si un nombre est (strictement) positif ou non
	 *
	* @param nombre {int} : l'int � v�rifier.
	* @param strict {boolean} : indique si on veut v�rifier ou non la nullit� du nombre.
	* @return {boolean} <code>false</code> si le nombre est n�gatif, <code>true</code> si il est positif. Si <code>nombre</code> == 0 : renvoie <code>true</code> si strict est vrai, <code>false</code> sinon.
	**/
	public static boolean estPositif(int nombre, boolean strict)
	{
		if (strict && nombre == 0)
			return true;
		else if (nombre > 0)
			return true;
		return false;
	}

	/**
	* Teste si un nombre est (strictement) positif ou non
	 *
	* @param nombre {long} : le long � v�rifier.
	* @param strict {boolean} : indique si on veut v�rifier ou non la nullit� du nombre.
	* @return {boolean} : renvoie <code>false</code> si le nombre est n�gatif, <code>true</code> si il est positif. En cas d'�galit� avec 0 : renvoie <code>true</code> si strict est vrai, <code>false</code> sinon.
	**/
	public static boolean estPositif(long nombre, boolean strict)
	{
		if (strict && nombre == 0)
			return true;
		else if (nombre > 0)
			return true;
		return false;
	}

	/**
	* Teste si un nombre est (strictement) positif ou non
	 *
	* @param nombre {short} : le short � v�rifier.
	* @param strict {boolean} : indique si on veut v�rifier ou non la nullit� du nombre.
	* @return {boolean} : renvoie <code>false</code> si le nombre est n�gatif, <code>true</code> si il est positif. En cas d'�galit� avec 0 : renvoie <code>true</code> si strict est vrai, <code>false</code> sinon.
	**/
	public static boolean estPositif(short nombre, boolean strict)
	{
		if (strict && nombre == 0)
			return true;
		else if (nombre > 0)
			return true;
		return false;
	}

	/**
	* Transforme un BufferedImage en String sous forme Base64
	 *
	* @param image {{@link java.awt.image.BufferedImage}}: Le buffer de l'image a transformer en String base64
	* @param type {{@link String}}: La String d�crivant le type d'image (jpeg, png, tiff, gif...)
	* @return
	**/
	public static String imageToBase64String(BufferedImage image, String type)
	{
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try
		{
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();

			imageString = Base64.getEncoder().encodeToString(imageBytes);

			bos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return imageString;
	}

	/**
	* Fonction permettant d'obtenir un tableau de couleur � �quidistance dans la panel TLS de la lumi�re, avec une saturation et une luminosit� � 100%
	 *
	* @param nbElem {int} : nombre de couleur diff�rentes voulues
	* @return {Color []} Un tableau contenant <code><b>nbElem</b></code> couleurs diff�rentes
	**/
	public static Color[] rainbow(int nbElem)
	{
		Color[] res = new Color[nbElem];
		float teinte, saturation = 1, luminosite = 1;
		for (int i = 0; i < nbElem; i++ )
		{
			teinte = (float) i / (float) nbElem;
			res[i] = Color.getHSBColor(teinte, saturation, luminosite);
		}
		return res;
	}

	/**
	* Fonction permettant d'obtenir un tableau de couleur � �quidistance dans la panel TLS de la lumi�re
	 *
	* @param nbElem {int} : nombre de couleur diff�rentes voulues
	* @param saturation {float} : Nombre compris entre 0 & 1 indiquant le taux saturation des couleurs
	* @param luminosite {float} : Nombre compris entre 0 & 1 indiquant la luminosit� des couleurs
	* @return {Color []} Un tableau contenant <code><b>nbElem</b></code> couleurs diff�rentes
	**/
	public static Color[] rainbow(int nbElem, float saturation, float luminosite)
	{
		Color[] res = new Color[nbElem];
		float teinte;
		for (int i = 0; i < nbElem; i++ )
		{
			teinte = (float) i / (float) nbElem;
			res[i] = Color.getHSBColor(teinte, saturation, luminosite);
		}
		return res;
	}

	/**
	* Renvoie un objet ButtonGroup � partir du tableau de JRadioButton re�u en entr�e
	 *
	* @param tab {JRadioButton []}: Le tableau conteant l'ensemble des JRadioButton � lier entre eux
	* @return {ButtonGroup} L'objet ButtonGroup faisant la laison et l'int�raction entre tous les JRadioButton(s) envoy�s en entr�e dans le tableau
	**/
	public static ButtonGroup regroupeBoutons(JRadioButton[] tab)
	{
		ButtonGroup grpBtn = new ButtonGroup();
		for (JRadioButton btn : tab)
			grpBtn.add(btn);
		return grpBtn;
	}

	/**
	* Transformation d'un UUID en un tableau d'octets (Byte Array)
	* @see http://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
	 *
	* @param uuid {UUID}: l'UUID (Unique User Id) � transformer en un byte array
	* @return {byte []} le tableau d'octet issu de l'UUID re�u en entr�e
	* @see http://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
	**/
	public static byte[] uuidAsByteArray(UUID uuid)
	{
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		byte[] buffer = new byte[16];

		for (int i = 0; i < 8; i++ )
			buffer[i] = (byte) (msb >>> 8 * (7 - i));
		for (int i = 8; i < 16; i++ )
			buffer[i] = (byte) (lsb >>> 8 * (7 - i));

		return buffer;
	}

	/**
	* Return the default directory for application on the current OS
	*
	* @return {{@link String}} the path to the directory
	**/
	public static String getDefaultDirectory()
	{
		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("WIN"))
			return System.getenv("APPDATA");
		else if (os.contains("MAC"))
			return System.getProperty("user.home") + "/Library/Application "
				   + "Support";
		else if (os.contains("NUX"))
			return System.getProperty("user.home");
		return System.getProperty("user.dir");
	}

	public static final Properties readPropertyFile(InputStream is)
	{
		Properties prop = new Properties();
		try
		{
			prop.load(is);
			return prop;
		}
		catch (IOException e)
		{
			return null;
		}
		finally
		{
			if (null != is)
				try
				{
					is.close();
				}
				catch (IOException ignored)
				{}
		}
	}

	public static final Properties readPropertyFile(String pathToFile)
	{
		try
		{
			return readPropertyFile(new FileInputStream(new File(pathToFile)));
		}
		catch (IOException ignored)
		{
			return null;
		}
	}
}