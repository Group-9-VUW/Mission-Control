package main.java.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Bryony
 *
 */
public class MenuController {

	/**
	 * 
	 */
	private JMenu selectLaunch;
	/**
	 * 
	 */
	private JMenu preLaunch;
	/**
	 * 
	 */
	private JMenu launch;
	
	/**
	 * @param frame
	 */
	@SuppressWarnings("null")
	public MenuController(JFrame frame) {
		createMenu();
	}
	
	/**
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
		
		this.selectLaunch = new JMenu("Select Launch");
		this.selectLaunch.setEnabled(false);
		
		this.preLaunch = new JMenu("Pre-Launch");
		this.preLaunch.setEnabled(false);
		
		this.launch = new JMenu("Launch");
		this.launch.setEnabled(false);
		
		menu.add(file);
		menu.add(this.launch);
		menu.add(this.preLaunch);
		menu.add(this.selectLaunch);
		return menu;
	}
	
	/**
	 * @param name
	 * @return Returns the JMenu that matches the name (launch is default)
	 */
	private JMenu getMenuFromString(String name) {
		if((name.toLowerCase()).contains("select")) {
			return this.selectLaunch;
		} else if ((name.toLowerCase()).contains("pre")) {
			return this.preLaunch;
		} else {
			return this.launch;
		}
	}
	
	/**
	 * @param name 
	 * @param items
	 * @param total
	 * @return Returns true if the total is smaller or equal to item.length
	 */
	public boolean setMenuItems(String name, JMenuItem[] items, int total) {
		if(items.length > total) {
			return false;
		}
		JMenu menu = getMenuFromString(name);
		menu.removeAll();
		for(int i = 0; i < total; i++) {
			menu.add(items[i]);
		}
		return true;
	}
	
	/**
	 * @param name 
	 * @return Returns true if there are already Menu Items in the Menu
	 */
	public boolean menuContainsOptions(String name) {
		return getMenuFromString(name).getMenuComponentCount() > 0;
	}
	
	/**
	 * @param name
	 */
	public void enableMenu(String name) {
		disableMenu("pre");
		disableMenu("select");
		disableMenu("");
		
		getMenuFromString(name).setEnabled(true);
	}
	
	/**
	 * @param name
	 */
	public void disableMenu(String name) {
		getMenuFromString(name).setEnabled(false);
	}
	
}











