package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Perspective;

/**
 * @author Bryony
 *
 */
public class FakePerspective extends Observable implements Perspective{

	/**
	 * 
	 */
	private Map<String, ActionListener> menuItems;
	/**
	 * 
	 */
	private JPanel panel = new JPanel();

	/**
	 * @param name 
	 * @param panel 
	 * 
	 */
	public FakePerspective(String name, JPanel panel) {
		this.menuItems = new HashMap<>();
		if(panel != null) {
			this.panel = panel;
		}
		this.panel.setName(name);
	}

	@SuppressWarnings("null")
	@Override
	public JPanel enable(@NonNull MenuController menu) {
		String[] a = new String[this.menuItems.size()];
		menu.disableAll();
		menu.enableItems(this.menuItems.keySet().toArray(a));
		return this.panel;
	}

	@SuppressWarnings("null")
	@Override
	public void init(@NonNull MenuController menu, @NonNull Observer o) {
		for(String s : this.menuItems.keySet()) {
			menu.addMenuItem(s, this.menuItems.get(s));
		}
		this.addObserver(o);
	}

	/**
	 * @param name
	 * @param a
	 */
	public void add(String name, ActionListener a) {
		this.menuItems.put(name, a);
	}

}
