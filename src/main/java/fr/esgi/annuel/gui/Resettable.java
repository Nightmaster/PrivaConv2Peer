package fr.esgi.annuel.gui;

import javax.swing.JPanel;

/**
* @author Ga�l B.
**/
public interface Resettable<T extends JPanel>
{
	/**
	* Reset the components of the view, and return it
	*
	* @return {{@link T}}: the actual {@link javax.swing.JPanel view} with its components back to their default value
	**/
	public T reset();
}