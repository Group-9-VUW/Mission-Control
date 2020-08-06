/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

/**
 * @author Claire
 */
public final class SelectSiteSpike {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainController controller = new MainController();
		controller.addPerspective("site", new SelectSitePerspective());
	}

}
