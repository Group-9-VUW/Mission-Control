package nz.ac.vuw.engr301.group9mcs.controller;

import javax.swing.JFrame;

import nz.ac.vuw.engr301.group9mcs.view.ViewController;

/**
 * Controls what view elements are shown on the screen for the different perspectives.
 * 
 * @author Bryony
 *
 */
public class PerspectiveController {

	/**
	 * The view controller.
	 */
	private ViewController view;
	/**
	 * The three perspectives.
	 */
	private enum States {
	/**
	 * Initial Opening State.
	 */
	start, 
	/**
	 * Selecting the Launch Site.
	 */
	selectLaunch, 
	/**
	 * At the Launch Site.
	 */
	preLaunch, 
	/**
	 * Launching.
	 */
	launch}
	/**
	 * Current Perspective
	 */
	private States state;
	
	/**
	 * Constructor.
	 */
	public PerspectiveController() {
		this.view = new ViewController();
		this.state = States.launch;
	}
	
	/**
	 * Change to a new State(perspective) and setup that new state.
	 * 
	 * @param name
	 * @param frame
	 * @param menu
	 */
	public void changeState(String name, JFrame frame, MenuController menu) {
		if(name.toLowerCase().contains("select")) {
			setUpSelectLaunch(frame, menu);
		} else if (name.toLowerCase().contains("pre")) {
			return;
		} else {
			return;
		}
	}
	
	/**
	 * Update the Current State with information from controller/logic.
	 */
	public void updateState(/* ??? */) {
		switch(this.state) {
			case selectLaunch:
				updateSelectLaunch(/* ??? */);
				break;
			case launch:
				break;
			case preLaunch:
				break;
			case start:
				break;
			default:
				break;
		}
	}
	
	/**
	 * Create the Perspective for Select Launch.
	 * 
	 * @param frame
	 * @param menu
	 */
	private void setUpSelectLaunch(JFrame frame, MenuController menu) {
		String[] paths = {"File/Quit", "File/Setup For Launch"};
		menu.enableItems(paths);
		frame.add(this.view.getCurrentView("select"));
		this.state = States.selectLaunch;
	}
	
	/**
	 * Receive information to update SelectLaunch
	 */
	private void updateSelectLaunch(/* ??? */) {
		int i = this.state.ordinal();
		i = i + 1;
		return;
	}
	
}
