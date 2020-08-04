/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Allows the user to select a .ork file to load the rocket specifications. 
 * @author pandasai
 *
 */
public class SelectFileView extends JPanel {
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		SelectFileView view = new SelectFileView(); 
		frame.add(view);
		frame.setVisible(true);
	}
}
