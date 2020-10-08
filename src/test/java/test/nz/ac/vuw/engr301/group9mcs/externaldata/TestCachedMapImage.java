package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.externaldata.map.CachedMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.InternetMapImage;

/**
 * Tests for CachedMapImage
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
			BufferedImage image1 = mapdata.get(latUL, lonUL, latBR, lonBR);
			BufferedImage image2 = fromfile.getImage();
			assertEquals(image1.getWidth(null), image2.getWidth(null));
			assertEquals(image1.getHeight(null), image2.getHeight(null));
			for (int i = 0; i < image1.getWidth(null); i++) {
				for (int j = 0; j < image1.getHeight(null); j++) {
					assertEquals(image1.getRGB(i, j), image2.getRGB(i, j));
				}
			}
		} catch (IOException | NullPointerException e) {
			fail(e);
		}
	}

	/**
	 * Tests trying to load a file with no name (i.e. a blank string).
	 */
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

	/**
	 * Tests trying to load a file with a name that is too short.
	 * (i.e. less than 5 characters as the file should end with ".png").
	 */
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

	/**
	 * Tests trying to load a non-PNG file.
	 */
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

	/**
	 * Tests trying to load an image with no corresponding .dat file.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingImageWithNoCorrespondingData() {
		try {
			CachedMapImage img = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "thisfileshouldnotexist.png"));
			img.toString();
			fail("Expected an IOException to be thrown.");
		} catch (IOException e) {
			assertTrue(e.getMessage().contains("No such file or directory") || e.getMessage().contains("The system cannot find the file specified"));
		} catch (NullPointerException e) {
			fail(e);
		}
	}

	/**
	 * Tests trying to load an image file that does not exist, but its data file does.
	 */
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

	/**
	 * Tests trying to load an image with an invalid path.
	 * (i.e. the image should be within CachedMapImage.IMG_CACHE_FOLDER).
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingWithInvalidPath() {
		try {
			CachedMapImage img = new CachedMapImage(new File("invalidPath.png"));
			img.toString();
			fail("Expected an IOException to be thrown.");
		} catch (IOException e) {
			fail(e);
		} catch (NullPointerException e) {
			assertEquals("The image file must be in the " + CachedMapImage.IMG_CACHE_FOLDER + " folder.", e.getMessage());
		}
	}

	/**
	 * Tests loading a valid image and checking its hashcode.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGettingHashCode() {
		try {
			CachedMapImage img = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "black-dot.png"));
			//was the image loaded
			assertNotEquals(new BufferedImage(1, 1, 1), img.getImage());
			//checks the hashcode
			Color centrePixel = new Color(img.hashCode());
			assertEquals(1, centrePixel.getRed());
			assertEquals(1, centrePixel.getGreen());
			assertEquals(1, centrePixel.getBlue());
		} catch (IOException | NullPointerException e) {
			fail(e);
		}
	}

	/**
	 * Tests the equals method on a number of different images and .dat files.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testImageEquals() {
		try {
			CachedMapImage img = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "black-dot.png"));
			assertFalse(img.equals(null));
			assertFalse(img.equals(new Object()));
			assertTrue(img.equals(new CachedMapImage(img.getFile())));
			//tests that invalid get() calls return the original image
			assertEquals(img.getImage(), img.get(-41.3, 174.74, -41.31, 174.76));
			assertEquals(img.getImage(), img.get(-41.2, 174.75, -41.31, 174.76));
			assertEquals(img.getImage(), img.get(-41.3, 174.75, -41.305, 174.761));
			assertEquals(img.getImage(), img.get(-41.3, 174.75, -41.311, 174.755));
			//tests that a valid get() call returns a subimage
			assertNotEquals(img.getImage(), img.get(-41.304, 174.754, -41.306, 174.756));

			BufferedImage bufImg = img.getImage();
			//the base latitudes and longitudes of the image
			double topLeftLat = -41.3;
			double topLeftLong = 174.75;
			double bottomRightLat = -41.31;
			double bottomRightLong = 174.76;
			//the image and data files to save to
			File blackDotImg = new File(CachedMapImage.IMG_CACHE_FOLDER + "black-dot-2.png");
			File blackDotDat = new File(CachedMapImage.IMG_CACHE_FOLDER + blackDotImg.getName().substring(0, blackDotImg.getName().length() - 4) + ".dat");
			blackDotDat.createNewFile();
			for (int i = 1; i < 7; i++) {
				try (BufferedWriter out = new BufferedWriter(new FileWriter(blackDotDat));) {
					//save image
					RenderedImage renderedImg = bufImg.getSubimage(0, 0, (i % 6 == 5 ? 499 : 500), (i % 6 == 0 ? 499 : 500));
					ImageIO.write(renderedImg, "png", blackDotImg);
					//save data
					out.write("" + topLeftLat + (i % 6 == 1 ? 1 : 0));
					out.newLine();
					out.write("" + topLeftLong + (i % 6 == 2 ? 1 : 0));
					out.newLine();
					out.write("" + bottomRightLat + (i % 6 == 3 ? 1 : 0));
					out.newLine();
					out.write("" + bottomRightLong + (i % 6 == 4 ? 1 : 0));
					out.close();
					//test that images do not match
					CachedMapImage nonEqualImg = new CachedMapImage(blackDotImg);
					assertFalse(img.equals(nonEqualImg));
				}}} catch (IOException | NullPointerException e) {
					fail(e);
				}
	}
}
