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
	
	protected final MapImage parentImage;
	protected final SimpleEventListener loadListener;
	
	protected Image cur = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
	
	protected double pixelsPerLat, pixelsPerLon;
	
	protected boolean loadingAlready = false;
	
	@Nullable
	protected PlanetaryArea area; 
	
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
		System.out.println(latUL + " : " + latBR);
		PlanetaryArea getArea = PlanetaryArea.fromCorners(latUL, longUL, latBR, lonBR);
		System.out.println(getArea.getUpperLeftLatitude() + " : " + getArea.getBottomRightLatitude());
		if(this.area == null || !this.area.containsArea(getArea)) {
			this.startBackgroundLoadIfIdle(getArea.scale(OVERREACH));
		} 
		BufferedImage image = loadingImage((int) Math.round(this.cur.getWidth(null) / OVERREACH), (int) Math.round(this.cur.getHeight(null) / OVERREACH));
		if(this.area != null) {
			PlanetaryArea clipped = this.area.clip(getArea);
			int xStart = (int) Math.round((clipped.getUpperLeftLongitude() - this.area.getUpperLeftLongitude()) * this.pixelsPerLon);
			int yStart = (int) Math.round((this.area.getUpperLeftLatitude() - clipped.getUpperLeftLatitude()) * this.pixelsPerLat);
			int xEnd = (int) Math.round((clipped.getBottomRightLongitude() - this.area.getBottomRightLongitude()) * this.pixelsPerLon);
			int yEnd = (int) Math.round((this.area.getBottomRightLatitude() - clipped.getBottomRightLatitude()) * this.pixelsPerLat);
			Graphics graphics = image.getGraphics();
			graphics.drawImage(this.cur, xStart, yStart, xEnd - xStart, yEnd - yStart, null);
			graphics.dispose();
		}
		return image;
	}
	
	private void startBackgroundLoadIfIdle(PlanetaryArea toLoad)
	{
		if(!this.loadingAlready)
			new GetImage(toLoad).start();
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
	
	private class GetImage extends Thread
	{
		private final PlanetaryArea toLoad;
		
		public GetImage(PlanetaryArea toLoad)
		{
			this.toLoad = toLoad;
		}
		
		public void run()
		{
			SmoothMapImage.this.loadingAlready = true;
			Image image = SmoothMapImage.this.parentImage.get(this.toLoad.getUpperLeftLatitude(), this.toLoad.getUpperLeftLongitude(), this.toLoad.getBottomRightLatitude(), this.toLoad.getBottomRightLongitude());
			SmoothMapImage.this.cur = image;
			SmoothMapImage.this.area = this.toLoad;
			SmoothMapImage.this.pixelsPerLat = image.getHeight(null) / (this.toLoad.getRadLat() * 2);
			SmoothMapImage.this.pixelsPerLon = image.getWidth(null) / (this.toLoad.getRadLon() * 2);
			SmoothMapImage.this.loadListener.event("image loaded");
			SmoothMapImage.this.loadingAlready = false;
		}
	}

}
