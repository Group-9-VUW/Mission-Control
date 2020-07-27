package nz.ac.vuw.engr301.group9mcs.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import nz.ac.vuw.engr301.group9mcs.commons.InvariantViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;

/**
 * Controller Class for the Menu.
 * Creates the Menu.
 * Allows an outside class to manipulate the Launch menus (select, pre, and launch)
 * 
 * @author Bryony
 *
 */
public class MenuController extends Observable{

	/**
	 * Map of Menu Items. Uses canonical path name to search.
	 */
	private final Map<String, JMenuItem> items = new HashMap<>();
	
	/**
	 * Map of menus, uses the first element of canonical 
	 */
	private final Map<String, JMenu> menus = new HashMap<>();
	
	/**
	 * Set of Menu Items that are used in every perspective.
	 * Could be saved into the Perspectives. 
	 * Saved by path name.
	 */
	private final Set<String> globalItems = new HashSet<>();
	
	/**
	 * Menu Bar
	 */
	private JMenuBar menu;
	
	/**
	 * Add a menu to the given frame.
	 * 
	 * @param frame
	 */
	public MenuController(JFrame frame) {
		this.menu = new JMenuBar();
		frame.add(this.menu);
	}
	
	/**
	 * Enables all the Menu Items in the list (search by pathname).
	 * Only enables an item if it exists. Does not create new items.
	 * 
	 * @param paths
	 */
	public void enableItems(String[] paths) {
		for(String path : paths) {
			this.enableItem(Null.nonNull(path));
		}
	}
	
	/**
	 * Enables the given item
	 * 
	 * @param pathParam The path to the JMenuItem
	 */
	public void enableItem(String pathParam)
	{
		String path = canonicalizePath(pathParam);
		if(!this.items.containsKey(path))
			throw new PreconditionViolationException("Invalid path: path not void in menu item map.");
		Null.nonNull(this.items.get(path)).setEnabled(true);
	}
	
	/**
	 * Disables all Menu Items expect ones used globally.
	 */
	public void disableAll() {
		for(String key : this.items.keySet()) {
			if(!this.globalItems.contains(key)) {
				Null.nonNull(this.items.get(key)).setEnabled(false);
			}
		}
	}
	
	/**
	 * Adds the Menu Bar to the Frame.
	 * To be called after the Frame has been cleared.
	 * 
	 * @param frame
	 */
	public void addMenuBar(JFrame frame) {
		frame.add(this.menu);
	}
	
	/**
	 * Creats a canonical path from a given path.
	 * 
	 * @param paramPath
	 * @return
	 */
	private static String canonicalizePath(String paramPath)
	{
		String path = Null.nonNull(paramPath.toLowerCase().replace('\\', '/'));
		String[] split = path.split("/");
		if( split.length == 1 
	    || (split.length == 2 && (split[0].length() == 0 || split[1].length() == 0)) 
	    || (split.length == 3 && ((split[0].length() != 0 && split[2].length() != 0) || split[1].length() == 0))
	    || (split.length == 4 && (split[0].length() != 0 || split[1].length() == 0 || split[2].length() == 0 || split[3].length() != 0))
	    ||  split.length > 4) {
			throw new PreconditionViolationException("Invalid path, unrecognized format.");
		}
		
		String first = "";
		String second = "";
		for(String s : split) {
			if(s.length() > 0) {
				if(first.length() == 0) {
					first = s;
				} else if(second.length() == 0) {
					second = s;
				}
			}
		}
		
		if(first.length() == 0 || second.length() == 0)
			throw new InvariantViolationException("Error in assumptions, given path did not contain two full names");
		
		return first + "/" + second;
	}
	
}











