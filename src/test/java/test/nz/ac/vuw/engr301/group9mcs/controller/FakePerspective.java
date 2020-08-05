package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Perspective;
import nz.ac.vuw.engr301.group9mcs.view.ViewMenuItem;

/**
 * @author Bryony
 *
 */
public class FakePerspective extends Observable implements Perspective{

	/**
	 * 
	 */
	private HashSet<ViewMenuItem> menuItems;
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
		this.menuItems = new HashSet<>();
		if(panel != null) {
			this.panel = panel;
		}
		this.panel.setName(name);
	}

	@SuppressWarnings("null")
	@Override
	public JPanel enable(@NonNull MenuController menu) {
		String[] a = new String[this.menuItems.size()];
		int i = 0;
		for(ViewMenuItem v : this.menuItems) {
			a[i] = v.getPath();
			i++;
		}
		menu.enableItems(a);
		return this.panel;
	}

	@SuppressWarnings("null")
	@Override
	public void init(@NonNull MenuController menu, @NonNull Observer o) {
		for(ViewMenuItem i : this.menuItems) {
			menu.addMenuItem(i.getPath(), i.getName(), i.getListener());
		}
		this.addObserver(o);
	}

	/**
	 * @param path 
	 * @param name
	 * @param a
	 */
	@SuppressWarnings("null")
	public void add(String path, String name, ActionListener a) {
		this.menuItems.add(new ViewMenuItem(path, name, a));
	}

	@Override
	public @NonNull String name() {
		return "Fake Perspective";
	}

}
