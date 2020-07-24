package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.eclipse.jdt.annotation.Nullable;

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
	 * Map of Map of Menu Items. Uses path name to search.
	 */
	private Map<String, Map<String, JMenuItem>> items;
	/**
	 * Set of Menu Items that are used in every perspective.
	 * Could be saved into the Perspectives. 
	 * Saved by path name.
	 */
	private Set<String> globalItems;
	
	/**
	 * Add a menu to the given frame.
	 * 
	 * @param frame
	 */
	public MenuController(JFrame frame) {
		this.items = new HashMap<>();
		this.globalItems = new HashSet<>();
		frame.add(createMenu());
	}
	
	/**
	 * Creates a menu bar with a file menu, and three disabled menus.
	 * 
	 * @return Returns a Menu with all important items added
	 */
	private JMenuBar createMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				System.exit(0);
			}
		});
		file.add(quit);
		addItem("File/Quit", quit, true);
		JMenuItem select = new JMenuItem("Select a Launch Site");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				// TODO Auto-generated method stub
				switchView("select");
			}
		});
		file.add(select);
		addItem("File/Select a Launch Site", select, true);
		JMenuItem pre = new JMenuItem("Setup for Launch");
		pre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				// TODO
				switchView("pre");
			}
		});
		file.add(pre);
		addItem("File/Setup for Launch", pre, true);
		
		menu.add(file);
		
		return menu;
	}
	
	/**
	 * Enables all the Menu Items in the list (search by pathname).
	 * Only enables an item if it exists. Does not create new items.
	 * 
	 * @param paths
	 */
	@SuppressWarnings("null")
	public void enableItems(String[] paths) {
		for(String path : paths) {
			String[] split = path.split("/");
			if(this.items.containsKey(split[0])) {
				if(this.items.get(split[0]).containsKey(split[1])) {
					this.items.get(split[0]).get(split[1]).setEnabled(true);
				}
			}
		}
	}
	
	/**
	 * Disables all Menu Items expect ones used globally.
	 */
	@SuppressWarnings("null")
	public void disableAll() {
		for(String map : this.items.keySet()) {
			for(String item : this.items.get(map).keySet()) {
				if(!this.globalItems.contains(map + "/" + item)) {
					this.items.get(map).get(item).setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * Notify the Observer that the View needs switching.
	 * 
	 * @param name
	 */
	void switchView(String name) {
		String[] arg = {"Switch View", name};
		this.notifyObservers(arg);
		this.setChanged();
	}
	
	/**
	 * Adds an Menu item at the path name to the map of map of Menu Items.
	 * Also adds item to global items (used in every perspective) if global is true.
	 * 
	 * @param path
	 * @param item
	 * @param global
	 */
	@SuppressWarnings("null")
	private void addItem(String path, JMenuItem item, boolean global) {
		String[] split = path.split("/");
		if(!this.items.containsKey(split[0])) {
			this.items.put(split[0], new HashMap<>());
		}
		this.items.get(split[0]).put(split[1], item);
		if(global) {
			this.globalItems.add(path);
		}
	}
	
}











