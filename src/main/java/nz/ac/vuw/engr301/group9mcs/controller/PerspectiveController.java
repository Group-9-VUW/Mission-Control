package nz.ac.vuw.engr301.group9mcs.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import org.eclipse.jdt.annotation.Nullable;
import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;

/**
 * Controls what view elements are shown on the screen for the different perspectives.
 *
 * @author Bryony
 *
 */
public class PerspectiveController implements Observer{

	/**
	 * A list of Perspectives and their names.
	 * Perspectives can be looked up by their name.
	 */
	private Map<String, Perspective> perspectives;
	/**
	 * The menu controller.
	 */
	private MenuController menu;
	/**
	 * The Panel.
	 */
	private JPanel panel;

	/**
	 * Constructor.
	 * Updates the Menu through the passed MenuController over lifetime.
	 * @param menu 
	 */
	public PerspectiveController(MenuController menu) {
		this.menu = menu;
		this.panel = new JPanel();
		this.perspectives = new HashMap<>();
	}

	/**
	 * Adds a Perspective to the Map.
	 * Name will lead to the Perspective everytime.
	 *
	 * @param name
	 * @param p
	 */
	@SuppressWarnings("null")
	public void addPerspective(String name, Perspective p) {
		p.init(this.menu, this);
		this.perspectives.put(name.toLowerCase(), p);
	}

	/**
	 * Returns the Panel for the MainController.
	 * @return Returns the Perspective Controller Panel
	 */
	public JPanel getPanel() {
		return this.panel;
	}

	/**
	 * Changes the Perspective to the one indicated by the passed name.
	 * If name isn't connected to a perspective (doesn't exist in list) an Error is thrown.
	 *
	 * @param name
	 */
	@SuppressWarnings("null")
	public void changePerspective(String name) {
		if(!this.perspectives.containsKey(name.toLowerCase())) {
			throw new PreconditionViolationException(name + " isn't a valid Perspective");
		}
		this.panel.removeAll();
		this.panel.add(this.perspectives.get(name.toLowerCase()).enable(this.menu));
	}

	/**
	 * Should only be used by Perspectives changing Perspective.
	 * Requires a string array = ["switch view", name.of.perspective]
	 */
	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if(arg instanceof String[]) {
			String[] args = (String[]) arg;
			if(args[0].toLowerCase().equals("switch view") && args.length == 2) {
				@Nullable String s = args[1];
				if(s != null) {
					changePerspective(s);
				}
			}
		}
	}

}
