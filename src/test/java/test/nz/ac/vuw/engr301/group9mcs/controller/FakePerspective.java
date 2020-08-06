package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Perspective;
import nz.ac.vuw.engr301.group9mcs.view.ViewMenuItem;

/**
 * A Fake Perspective used in the Test PerspectiveController Class.
 *
 * @author Bryony
 *
 */
public class FakePerspective extends Observable implements Perspective{

	/**
	 * List of Required Menu Items.
	 */
	private HashSet<ViewMenuItem> menuItems;
	/**
	 * The Default Panel.
	 */
	private @NonNull JPanel panel = new JPanel();

	/**
	 * If Panel isn't null, Default Panel will be replaced.
	 * The Panel's name (default or new) will be set as the passed name.
	 *
	 * @param name
	 * @param panel
	 */
	public FakePerspective(String name, JPanel panel) {
		this.menuItems = new HashSet<>();
		if(panel != null) {
			this.panel = panel;
		}
		this.panel.setName(name);
	}

	@Override
	public @NonNull JPanel enable(@NonNull MenuController menu) {
		String[] a = new String[this.menuItems.size()];
		int i = 0;
		for(ViewMenuItem v : this.menuItems) {
			a[i] = v.getPath();
			i++;
		}
		menu.enableItems(a);
		return this.panel;
	}

	@Override
	public void init(@NonNull MenuController menu, @NonNull Observer o) {
		for(ViewMenuItem i : this.menuItems) {
			menu.addMenuItem(i.getPath(), i.getName(), i.getListener());
		}
		this.addObserver(o);
	}

	/**
	 * Add a Menu Item to the List
	 *
	 * @param path
	 * @param name
	 * @param a
	 */
	public void add(String path, String name, @NonNull ActionListener a) {
		this.menuItems.add(new ViewMenuItem(Null.nonNull(path), Null.nonNull(name), a));
	}

}
