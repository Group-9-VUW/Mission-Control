/**
 *
 */
package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;
import nz.ac.vuw.engr301.group9mcs.commons.map.PlanetaryArea;

/**
 * A MapImage that doesn't block while retrieving data from file.
 *
 * @author Claire Chambers
 * @editor Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class SmoothMapImage implements MapImage {

	/**
	 * The proportion by which to load around the requested area. 1.5 = a 50% increased radius
	 */
	private static final double OVERREACH = 1.5;

	/**
	 * Parent MapImage, used for all requests
	 */
	protected final MapImage parentImage;

	/**
	 * For when the loading thread terminates
	 */
	protected final SimpleEventListener loadListener;

	/**
	 * The latest image loaded
	 */
	protected volatile Image cur = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);

	/**
	 * Pixels per latitude
	 */
	protected volatile double pixelsPerLat;

	/**
	 * Pixels per longitude
	 */
	protected volatile double pixelsPerLon;

	/**
	 * Whether we already are loading an image. Used to prevent multiple redundant loading operations
	 */
	protected volatile boolean loadingAlready = false;

	/**
	 * The PlanetaryArea the current image covers. Null if no image has been loaded
	 */
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
	public SmoothMapImage(MapImage parent, SimpleEventListener loadListener) {
		this.loadListener = loadListener;
		this.parentImage = parent;
	}

	@SuppressWarnings("null")
	@Override
	public Image get(double latUL, double lonUL, double latBR, double lonBR) {
		PlanetaryArea getArea = PlanetaryArea.fromCorners(latUL, lonUL, latBR, lonBR);
		if (this.area == null || !this.area.containsArea(getArea)) {
			this.startBackgroundLoadIfIdle(getArea.scale(OVERREACH));
		}

		BufferedImage image = loadingImage((int) Math.round(this.cur.getWidth(null) / OVERREACH),
				                           (int) Math.round(this.cur.getHeight(null) / OVERREACH));

		double newPixelsPerLon = image.getWidth(null) / (getArea.getRadLon() * 2);
		double newPixelsPerLat = image.getHeight(null) / (getArea.getRadLat() * 2);

		if (this.area != null && this.area.overlapsWithArea(getArea)) {
			PlanetaryArea clipped = this.area.clip(getArea);
			int xStartFrom = (int) Math.round((clipped.getUpperLeftLongitude() - this.area.getUpperLeftLongitude()) * this.pixelsPerLon);
			int yStartFrom = (int) Math.round((this.area.getUpperLeftLatitude() - clipped.getUpperLeftLatitude()) * this.pixelsPerLat);
			int xEndFrom = (int) Math.round((clipped.getBottomRightLongitude() - this.area.getUpperLeftLongitude()) * this.pixelsPerLon);
			int yEndFrom = (int) Math.round((this.area.getUpperLeftLatitude() - clipped.getBottomRightLatitude()) * this.pixelsPerLat);

			int xStartTo = (int) Math.round((clipped.getUpperLeftLongitude() - getArea.getUpperLeftLongitude()) * newPixelsPerLon);
			int yStartTo = (int) Math.round((getArea.getUpperLeftLatitude() - clipped.getUpperLeftLatitude()) * newPixelsPerLat);
			int xEndTo = (int) Math.round((clipped.getBottomRightLongitude() - getArea.getUpperLeftLongitude()) * newPixelsPerLon);
			int yEndTo = (int) Math.round((getArea.getUpperLeftLatitude() - clipped.getBottomRightLatitude()) * newPixelsPerLat);

			Graphics graphics = image.getGraphics();
			graphics.drawImage(this.cur, xStartTo, yStartTo, xEndTo, yEndTo, xStartFrom, yStartFrom, xEndFrom, yEndFrom, null);
			graphics.dispose();
		}

		return image;
	}

	/**
	 * Loads a given area if we're not already loading.
	 *
	 * @param toLoad The area to load
	 */
	private void startBackgroundLoadIfIdle(PlanetaryArea toLoad) {
		if (!this.loadingAlready) {
			this.loadingAlready = true;
			new GetImage(toLoad).start();
		}
	}

	/**
	 * Produces a background loading image as a placeholder for not-yet-loaded content
	 *
	 * @param width The width of the image in pixels
	 * @param height The height of the image in pixels
	 * @return The image
	 */
	private static BufferedImage loadingImage(int width, int height) {
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

	/**
	 * A image loading thread. Loads an image from the parent image and then assigns it
	 * to SmoothMapImage.cur
	 *
	 * @author Claire Chambers
	 * Copyright (C) 2020, Mission Control Group 9
	 */
	private class GetImage extends Thread {
		/**
		 * Area to load
		 */
		private final PlanetaryArea toLoad;

		/**
		 * @param toLoad
		 */
		public GetImage(PlanetaryArea toLoad) {
			this.toLoad = toLoad;
		}

		@Override
		public void run() {
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
