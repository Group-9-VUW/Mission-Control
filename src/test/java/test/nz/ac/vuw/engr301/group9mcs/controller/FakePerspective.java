package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observer;
import javax.swing.JPanel;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Perspective;

public class FakePerspective implements Perspective{

	private Map<String, ActionListener> menuItems;

	@Override
	public JPanel enable(MenuController menu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(MenuController menu, Observer o) {

	}

	public void add(String name, ActionListener a) {
		menuItems.put(name, a);
	}

}
