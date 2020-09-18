package nz.ac.vuw.engr301.group9mcs.controller;

import java.util.Observer;

import javax.swing.JPanel;

/**
 * An Interface for the Perspectives.
 * Contains methods that all Perspectives should follow.
 * All other methods should be private.
 * Should have a list somewhere of required menu items.
 *
 * @author Bryony Gatehouse
 *
 */
public interface Perspective{

	/**
	 * Called when the controller switches to this view.
	 * Enables all required menu items (through the passed menu controller).
	 * Returns back its default JFrame - this will be populated by the Perspective.
	 * JFrame won't be passed again until the Perspective is switched to again.
	 * Resource passed holds information from outside (eg from different perspectives)
	 *
	 * @param menu
	 * @param resource 
	 * @return Returns the Perspective's Panel
	 */
	public JPanel enable(MenuController menu, Resources resource);
	
	/**
	 * Called when the Perspective is first made (or passed into the controller).
	 * Adds all required menu items (through the passed menu controller).
	 * Adds the Observer (to allow the Perspective to initialise a Perspective Switch).
	 *
	 * @param menu
	 * @param o
	 */
	public void init(MenuController menu, Observer o);
	/**
	 * The default name for this Perspective, to use when passing.
	 * 
	 * @return The default name.
	 */
	public String name();

}
