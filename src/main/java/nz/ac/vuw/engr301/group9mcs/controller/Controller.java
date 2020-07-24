package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.view.ViewController;

/**
 * Controller class.
 * Creates the screen.
 * 
 * @author Bryony
 *
 */
public class Controller implements Observer{

	/**
	 * The entire frame of the program
	 */
	private JFrame frame;
	/**
	 * The menu controller.
	 */
	private MenuController menu;
	/**
	 * The view controller.
	 */
	private ViewController view;
	
	/**
	 * Creates the screen.
	 */
	@SuppressWarnings("null")
	public Controller() {
		createScreen();
	}
	
	/**
	 * Create the screen.
	 */
	private void createScreen() {
		this.frame = new JFrame("Mission Control");
		
		this.menu = new MenuController(this.frame);
		this.menu.addObserver(this);
		// this.menu.enableItems( {"Path/To/Item"} );
		this.view = new ViewController();
		this.frame.add(this.view.getCurrentView("select"));
		
		this.frame.setPreferredSize(new Dimension(300, 300));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	/**
	 * Change to named panel.
	 * 
	 * @param name
	 */
	private void changePanel(String name) {
		this.frame.remove(this.view.getCurrentView("select"));
		this.frame.add(this.view.getCurrentView(name));
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if(arg instanceof String[]) {
			String[] args = (String[]) arg;
			if(args[0].equals("Switch View") && args.length == 2) {
				@Nullable String s = args[1];
				if(s != null) {
					changePanel(s);
				}
			}
		}
	}
	
}










