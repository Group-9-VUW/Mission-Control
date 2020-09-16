package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;
import nz.ac.vuw.engr301.group9mcs.externaldata.CachedMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.SmoothMapImage;

/**
 * Tests the SmoothMapImage class.
 * 
 * @author Joshua Hindley
 */
public class TestSmoothMapImage {

	/**
	 * Tests getting a smooth map image. That is, tests that the get() method
	 * of the SmoothMapImage class returns a new and different image.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetImage() {
		try {
			CachedMapImage cache = new CachedMapImage(new File(CachedMapImage.IMG_CACHE_FOLDER + "black-dot.png"));
			SmoothMapImage map = new SmoothMapImage(cache, new SimpleEventListener() {			
				@Override
				public void event(@NonNull String type) {/*This event does not need to be handled.*/}
			});
			BufferedImage img = (BufferedImage) map.get(-41.3, 174.75, -41.31, 174.76);
			assertNotEquals(img, cache.getImage());
			BufferedImage img2 = (BufferedImage) map.get(-41.28, 174.73, -41.29, 174.74);
			assertNotEquals(img.getWidth(), img2.getWidth());
			assertNotEquals(img.getHeight(), img2.getHeight());
			assertNotEquals(img, img2);
		} catch (NullPointerException | IOException e) {
			fail(e);
		}
	}
}
