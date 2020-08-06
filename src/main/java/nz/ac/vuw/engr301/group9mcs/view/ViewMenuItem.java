package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.event.ActionListener;
/**
 * Holds the information to be passed to the MenuController to create a Menu Item.
 *
 * @author Bryony
 *
 */
public class ViewMenuItem {

	/**
	 * The Path. Eg. "File/location"
	 * Should be two deep and separated by '/'.
	 * Note: will be converted to lowercase when saved into Menu Controller.
	 */
	private String path;
	/**
	 * The Name displayed in the Menu (might be different but why?).
	 */
	private String name;
	/**
	 * The ActionListener.
	 */
	private ActionListener ls;

	/**
	 * Save all the passed info.
	 *
	 * @param p The Path
	 * @param n The Name
	 * @param l The ActionListener
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
