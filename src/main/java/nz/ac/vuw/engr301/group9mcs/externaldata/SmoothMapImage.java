/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.Image;

import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;

/**
 * A MapImage that doesn't block while retrieving data from file.
 * 
 * @author Claire
 */
public class SmoothMapImage implements MapImage {

	private static final double OVERREACH = 1.2;
	
	private final MapImage parentImage;
	private final SimpleEventListener loadListener;
	
	/**
	 * Creates a SmoothMapImage from a parent Map Image and a listener.
	 * The parent map image is used to load all the data that this MapImage
	 * caches, and the loadListener is called whenever the image is 
	 * asynchronously updated.
	 * 
	 * @param parent The parent MapImage
	 * @param loadListener The listener for asynchronous loading
	 */
	public SmoothMapImage(MapImage parent, SimpleEventListener loadListener)
	{
		this.loadListener = loadListener;
		this.parentImage = parent;
	}
	
	@SuppressWarnings("null")
	@Override
	public Image get(double longUL, double latUL, double longBR, double latBR) {
		// TODO Auto-generated method stub
		return null;
	}

}
