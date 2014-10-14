package fr.esgi.annuel.gui;

import javax.swing.JPanel;

/**
* Specify that a class that extends {@link javax.swing.JPanel} can be reset
*
* @author Gaël B.
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