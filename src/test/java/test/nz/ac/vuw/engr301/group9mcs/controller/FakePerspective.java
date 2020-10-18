package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;
import nz.ac.vuw.engr301.group9mcs.controller.perspectives.Perspective;
import nz.ac.vuw.engr301.group9mcs.view.ViewMenuItem;

/**
 * A Fake Perspective used in the Test PerspectiveController Class.
 *
 * @author Bryony Gatehouse
 * Copyright (C) 2020, Mission Control Group 9
 */
public class FakePerspective extends Observable implements Perspective{

	/**
	 * List of Required Menu Items.
	 */
	private HashSet<ViewMenuItem> menuItems;

	/**
	 * The Default Panel.
	 */
	private JPanel panel = new JPanel();

	/**
	 * Resources.
	 */
	@SuppressWarnings("unused")
	private @Nullable Resources res;

	/**
	 * If Panel isn't null, Default Panel will be replaced.
	 * The Panel's name (default or new) will be set as the passed name.
	 *
	 * @param name
	 * @param panel
	 */
	public FakePerspective(String name, @Nullable JPanel panel) {
		this.menuItems = new HashSet<>();
		if(panel != null) {
			this.panel = panel;
		}
		this.panel.setName(name);
	}

	@Override
	public @NonNull JPanel enable(@NonNull MenuController menu, @Nullable Resources resource) {
		String[] a = new String[this.menuItems.size()];
		int i = 0;
		for(ViewMenuItem v : this.menuItems) {
			a[i] = v.getPath();
			i++;
		}
		menu.enableItems(a);
		this.res = resource;
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
	public void add(String path, String name, ActionListener a) {
		this.menuItems.add(new ViewMenuItem(Null.nonNull(path), Null.nonNull(name), a));
	}

	@Override
	public @NonNull String name() {
		return "Fake Perspective";
	}

	@Override
	public void releaseResources() { /**/ }

}
