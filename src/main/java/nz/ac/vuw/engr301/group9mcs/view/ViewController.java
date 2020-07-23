package main.java.nz.ac.vuw.engr301.group9mcs.view;

import javax.swing.JPanel;

/**
 * Controller class for the Views.
 * Returns the current view based off information passed.
 * 
 * @author Bryony
 *
 */
public class ViewController{
	
	/**
	 * Current View.
	 */
	private JPanel current;
	
	/**
	 * Create a new Panel
	 */
	public ViewController() {
		this.current = new JPanel();
	}
	
	/**
	 * Returns the current view based off information passed.
	 * 
	 * @param name
	 * @return Returns the current view
	 */
	public JPanel getCurrentView(String name) {
		return this.current;
	}
	
}
