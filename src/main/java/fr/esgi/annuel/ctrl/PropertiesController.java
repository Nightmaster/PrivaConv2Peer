package fr.esgi.annuel.ctrl;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import fr.esgi.annuel.gui.MasterWindow;
import fr.esgi.util.Outils;

/**
 * @author Gaël B.
 */
public class PropertiesController
{
	private static String directoryName;
	private boolean fileCreated;
	private Properties properties;
	private Properties registeredProperties;
	private File configFile;

	static
	{
		if (System.getProperty("os.name").toUpperCase().contains("WIN"))
			directoryName = "/pc2p/";
		else
			directoryName = "/.pc2p/";
	}

	PropertiesController(Properties properties)
	{

		this.properties = properties;
		boolean created = false;
		String defaultDir = Outils.getDefaultDirectory();
		boolean dirCreated;
		File directory = new File(defaultDir + directoryName);
		if (!(dirCreated = directory.exists()))
			dirCreated = directory.mkdir();
		if (dirCreated)
		{
			this.configFile = new File(defaultDir + "/pc2p/config.ini");
			try
			{
				if (!this.configFile.exists())
					created = this.configFile.createNewFile();
				else
				{
					created = true;
					this.registeredProperties = Outils.readPropertyFile(defaultDir + "/pc2p/config.ini");
				}
			}
			catch (IOException ignored)
			{
			}
		}
		this.fileCreated = created;
	}

	private void centerWindow(MasterWindow window)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
	}

	private synchronized void saveFile()
	{
		try
		{
			Writer wr = new FileWriter(this.configFile);
			this.registeredProperties.store(wr, "Application properties");
		}
		catch (IOException ignored) {}
	}

	public Properties getRegisteredProperties()
	{
		return this.registeredProperties;
	}

	public Properties getProperties()
	{
		return properties;
	}

	public boolean isFileCreated()
	{
		return fileCreated;
	}

	/**
	 * Store the position of the main {@link fr.esgi.annuel.gui.MasterWindow window} in the property file
	 *
	 * @param window {{@link fr.esgi.annuel.gui.MasterWindow}}: the window from which one the position will be stored
	 */
	public void storePosition(final MasterWindow window)
	{
		if (!fileCreated)
			return;
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				Rectangle rect = window.getBounds();
				int x = (int) rect.getX(),
						y = (int) rect.getY();
				PropertiesController.this.registeredProperties.setProperty("x", Integer.toString(x));
				PropertiesController.this.registeredProperties.setProperty("y", Integer.toString(y));
				saveFile();
			}
		};
		thread.start();
	}

	public void restorePosition(MasterWindow window)
	{
		if(! this.fileCreated)
			centerWindow(window);
		try
		{
			int x = Integer.parseInt(registeredProperties.getProperty("x")),
					y = Integer.parseInt(registeredProperties.getProperty("y")),
					width = (int) window.getBounds().getWidth(),
					height = (int) window.getBounds().getHeight();
			window.setBounds(x, y, width, height);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			centerWindow(window);
		}
	}

	public void storeLogin(final String loginValue)
	{
		if (!fileCreated)
			return;

		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				PropertiesController.this.registeredProperties.setProperty("login", loginValue);
				saveFile();
			}
		};
		thread.start();
	}
}