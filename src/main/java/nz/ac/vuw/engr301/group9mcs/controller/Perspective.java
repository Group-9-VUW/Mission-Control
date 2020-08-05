package nz.ac.vuw.engr301.group9mcs.controller;

import java.util.Observer;

import javax.swing.JPanel;

public interface Perspective{

	public JPanel enable(MenuController menu);
	public void init(MenuController menu, Observer o);
	/**
	 * The default name for this Perspective, to use when passing.
	 * 
	 * @return The default name.
	 */
	public String name();

}
