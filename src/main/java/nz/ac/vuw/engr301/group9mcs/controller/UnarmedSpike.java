/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

/**
 * Spike for unarmed perspective 
 * 
 * @author Claire 
 * @author Bryony
 */
public class UnarmedSpike {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainController controller = new MainController();
		controller.addPerspective("unarmed", new UnarmedPerspective());
		controller.setPerspective("unarmed");
	}

}
