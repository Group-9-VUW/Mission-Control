package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Perspective;

public class FakePerspective extends Observable implements Perspective{

	private Map<String, ActionListener> menuItems;
	private JPanel panel;

	@Override
	public JPanel enable(MenuController menu) {
		menu.enableItems((String[])this.menuItems.keySet().toArray());
		return this.panel;
	}

	@Override
	public void init(MenuController menu, Observer o) {
		for(String s : this.menuItems.keySet()) {
			menu.addMenuItem(s, this.menuItems.get(s));
		}
		this.addObserver(o);
	}

	public void add(String name, ActionListener a) {
		this.menuItems.put(name, a);
	}

}
