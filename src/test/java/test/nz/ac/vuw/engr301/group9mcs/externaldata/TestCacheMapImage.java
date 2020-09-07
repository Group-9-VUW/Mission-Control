package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.externaldata.CachedMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.InternetMapImage;

/**
 * Tests for CacheMapImage
 *
 * @author Claire
 * @editor Joshua Hindley
 */
public final class TestCacheMapImage {

	/**
	 * Tests that writing and then reading from cache matches data from the network.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReadWrite() {
		try {
			//the latitudes and longitudes of the map image to get
			double latUL = -41.291257 + 0.01;
			double lonUL = 174.776879 - 0.01;
			double latBR = -41.291257 - 0.01;
			double lonBR = 174.776879 + 0.01;
			//gets the map image
			InternetMapImage mapdata = new InternetMapImage();
			CachedMapImage cache = new CachedMapImage(mapdata, latUL, lonUL, latBR, lonBR);
			CachedMapImage fromfile = new CachedMapImage(cache.getFile());
			//removes the cached map image from memory
			cache = null;
			assertTrue(Math.abs(fromfile.centerLat() - (-41.291257)) < 0.000001);
			assertTrue(Math.abs(fromfile.centerLon() - (174.776879)) < 0.000001);
			//tests that the cached and non-cached images are the same
			Image image1 = mapdata.get(latUL, lonUL, latBR, lonBR);
			Image image2 = fromfile.get(latUL, lonUL, latBR, lonBR);
			assertEquals(image1.getWidth(null), image2.getWidth(null));
			assertEquals(image1.getHeight(null), image2.getHeight(null));
			final int width = image1.getWidth(null);
			final int height = image1.getWidth(null);
			BufferedImage bimage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			BufferedImage bimage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graph1 = bimage1.createGraphics();
			graph1.drawImage(image1, 0, 0, null);
			graph1.dispose();
			Graphics2D graph2 = bimage2.createGraphics();
			graph2.drawImage(image2, 0, 0, null);
			graph2.dispose();
			for(int i = 0; i < width; i++)
				for(int j = 0; j < height; j++)
					assertEquals(bimage1.getRGB(i, j), bimage2.getRGB(i, j));
		} catch (IOException | NullPointerException e) {
			fail(e); 
		}
	}
}
