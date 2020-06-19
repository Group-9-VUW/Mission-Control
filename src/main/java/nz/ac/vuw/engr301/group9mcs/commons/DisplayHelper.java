/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.commons;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * A helper class for swing
 * 
 * @author Claire
 */
public final class DisplayHelper {
	
	/**
	 * Centers a window on the screen
	 * 
	 * @param window The window to center
	 */
	public static void center(final Window window)
	{
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		final int X = (int) ((screen.getWidth() - window.getWidth()) / 2);
		final int Y = (int) ((screen.getHeight() - window.getHeight()) / 2);
		window.setLocation(X, Y);
	}	

}
