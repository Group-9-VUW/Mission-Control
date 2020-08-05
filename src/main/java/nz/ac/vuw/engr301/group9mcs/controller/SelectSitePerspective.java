package nz.ac.vuw.engr301.group9mcs.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * Perspective that holds the Panels for the Selecting a Launch Site.
 * 
 * @author Bryony
 *
 */
public class SelectSitePerspective extends Observable implements Perspective{

	/**
	 * The Panel displayed on the screen that holds all other panels.
	 */
	private JPanel panel;
	
	/**
	 * Create the Perspective and construct the Panel.
	 */
	public SelectSitePerspective() {
		this.panel = new JPanel();
	}
	
	@Override
	public JPanel enable(MenuController menu) {
		return this.panel;
	}

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
	}

	@Override
	public String name() {
		return "select";
	}
	
}
