package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.event.ActionListener;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Bryony
 *
 */
public class ViewMenuItem {

	/**
	 * 
	 */
	private String filePath;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private @Nullable ActionListener ls;
	
	/**
	 * @param path
	 * @param n
	 * @param l
	 */
	public ViewMenuItem(String path, String n, ActionListener l) {
		this.filePath = path;
		this.name = n;
		this.ls = l;
	}
	
	/**
	 * @return The Entire Path
	 */
	public String getPath() {
		return this.filePath;
	}
	
	/**
	 * @return The Name Displayed
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return The Listener
	 */
	public @Nullable ActionListener getListener() {
		return this.ls;
	}
	
}
