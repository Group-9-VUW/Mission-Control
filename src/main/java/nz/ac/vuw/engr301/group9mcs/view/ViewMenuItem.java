package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.event.ActionListener;
/**
 * @author Bryony
 *
 */
public class ViewMenuItem {

	/**
	 *
	 */
	private String path;
	/**
	 *
	 */
	private String name;
	/**
	 *
	 */
	private ActionListener ls;

	/**
	 * @param p
	 * @param n
	 * @param l
	 */
	public ViewMenuItem(String p, String n, ActionListener l) {
		this.path = p;
		this.name = n;
		this.ls = l;
	}

	/**
	 * @return The Entire Path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @return The Name Displayed
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The Listener
	 */
	public ActionListener getListener() {
		return this.ls;
	}

}
