package main.java.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Bryony
 *
 */
public class Controller {

	/**
	 * The entire frame of the program
	 */
	private JFrame frame;
	
	/**
	 * 
	 */
	@SuppressWarnings("null")
	public Controller() {
		createScreen();
	}
	
	/**
	 * 
	 */
	private void createScreen() {
		this.frame = new JFrame("Mission Control");
		this.frame.setPreferredSize(new Dimension(300, 300));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
}
