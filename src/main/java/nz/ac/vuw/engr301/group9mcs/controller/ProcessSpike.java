package nz.ac.vuw.engr301.group9mcs.controller;

import nz.ac.vuw.engr301.group9mcs.controller.perspectives.StartPerspective;

/**
 * Should show the main process.
 * 
 * @author Bryony
 *
 */
public class ProcessSpike {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainController controller = new MainController();
		controller.addPerspective("start", new StartPerspective());
		controller.setPerspective("start");
	}
	
}
