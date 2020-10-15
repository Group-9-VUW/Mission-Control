package nz.ac.vuw.engr301.group9mcs.controller;

import nz.ac.vuw.engr301.group9mcs.controller.perspectives.ArmedPerspective;
import nz.ac.vuw.engr301.group9mcs.controller.perspectives.SelectSitePerspective;
import nz.ac.vuw.engr301.group9mcs.controller.perspectives.StartPerspective;
import nz.ac.vuw.engr301.group9mcs.controller.perspectives.UnarmedPerspective;

/**
 * Should show the main process.
 * 
 * @author Bryony
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainController controller = new MainController();
		controller.addPerspective("start", new StartPerspective());
		controller.addPerspective("unarmed", new UnarmedPerspective());
		controller.addPerspective("armed", new ArmedPerspective());
		controller.addPerspective("site", new SelectSitePerspective());
		controller.setPerspective("start");
	}
	
}
