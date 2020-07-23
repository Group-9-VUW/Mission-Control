package main.java.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.Dimension;

import javax.swing.JFrame;

import main.java.nz.ac.vuw.engr301.group9mcs.view.ViewController;

/**
 * Controller class.
 * Creates the screen.
 * 
 * @author Bryony
 *
 */
public class Controller {

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
		this.view = new ViewController();
		this.frame.add(this.view.getCurrentView("select"));
		
		this.frame.setPreferredSize(new Dimension(300, 300));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private void changePanel() {
		this.frame.remove(this.view.getCurrentView("select"));
		this.frame.add(this.view.getCurrentView("pre"));
	}
	
}
