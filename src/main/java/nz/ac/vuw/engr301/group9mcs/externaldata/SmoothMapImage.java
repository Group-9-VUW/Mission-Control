/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.PlanetaryArea;
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
	
	@Nullable
	private PlanetaryArea area; 
	
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
	public Image get(double latUL, double longUL, double latBR, double lonBR) {
		PlanetaryArea getArea = PlanetaryArea.fromCorners(latUL, longUL, latBR, lonBR);
		if(this.area == null || !this.area.containsArea(getArea)) {
			this.area = getArea.scale(OVERREACH);
			this.startBackgroundLoadIfIdle();
		} 
		
		return null;
	}
	
	private static BufferedImage loadingImage(int width, int height)
	{
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics graph = image.getGraphics();
		graph.setColor(Color.GRAY);
		graph.fillRect(0, 0, width, height);
		graph.setColor(Color.WHITE);
		graph.drawString("Loading", (int) (width - 1.0 * (width / 6.0)), (int) (height - 1.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 3.0 * (width / 6.0)), (int) (height - 1.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 5.0 * (width / 6.0)), (int) (height - 1.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 1.0 * (width / 6.0)), (int) (height - 3.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 3.0 * (width / 6.0)), (int) (height - 3.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 5.0 * (width / 6.0)), (int) (height - 3.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 1.0 * (width / 6.0)), (int) (height - 5.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 3.0 * (width / 6.0)), (int) (height - 5.0 * (height / 6.0)));
		graph.drawString("Loading", (int) (width - 5.0 * (width / 6.0)), (int) (height - 5.0 * (height / 6.0)));
		graph.dispose();
		return image;
	}
	
	private void startBackgroundLoadIfIdle()
	{
		//Unimplemented
	}

}
