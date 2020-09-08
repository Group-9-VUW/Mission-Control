package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.externaldata.CachedMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.MapImage;

/**
 * Tests for CacheMapImage
 *
 * @author Claire
 * @author Joshua Hindley
 * @editor Joshua Hindley
 */
public final class TestCachedMapImage {

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
	
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingNoNameFile() {
		try {
			CachedMapImage img = new CachedMapImage(new File(""));
			img.toString();
			fail("Expected a NullPointerException to be thrown.");
		} catch (IOException e) {
			fail(e);
		} catch (NullPointerException e) {
			assertEquals("\"\" is not a valid file name for a .png file.", e.getMessage());
		}
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingTooShortNameFile() {
		try {
			CachedMapImage img = new CachedMapImage(new File("test"));
			img.toString();
			fail("Expected a NullPointerException to be thrown.");
		} catch (IOException e) {
			fail(e);
		} catch (NullPointerException e) {
			assertEquals("\"test\" is not a valid file name for a .png file.", e.getMessage());
		}
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingNonPNGFile() {
		try {
			CachedMapImage img = new CachedMapImage(new File("file.jpg"));
			img.toString();
			fail("Expected a NullPointerException to be thrown.");
		} catch (IOException e) {
			fail(e);
		} catch (NullPointerException e) {
			assertEquals("\"file.jpg\" is not a valid file name for a .png file.", e.getMessage());
		}
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingImageWithNoCorrespondingData() {
		try {
			CachedMapImage img = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "thisfileshouldnotexist.png"));
			img.toString();
			fail("Expected an IOException to be thrown.");
		} catch (IOException e) {
			assertEquals("img_cache\\thisfileshouldnotexist.dat (The system cannot find the file specified)", e.getMessage());
		} catch (NullPointerException e) {
			fail(e);
		}
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingMissingImageWithValidData() {
		try {
			CachedMapImage img = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "testData.png"));
			img.toString();
			fail("Expected an IOException to be thrown.");
		} catch (IOException e) {
			assertEquals("Can't read input file!", e.getMessage());
		} catch (NullPointerException e) {
			fail(e);
		}
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testGettingHashCode() {
		try {
			MapImage img = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "black-dot.png"));
			CachedMapImage cached = (CachedMapImage) img;
			assertNotEquals(new BufferedImage(1, 1, 1), cached.getImage());
			Color centrePixel = new Color(cached.hashCode());			
			assertEquals(1, centrePixel.getRed());
			assertEquals(1, centrePixel.getGreen());
			assertEquals(1, centrePixel.getBlue());
		} catch (IOException | NullPointerException e) {
			fail(e);
		}
	}
}
