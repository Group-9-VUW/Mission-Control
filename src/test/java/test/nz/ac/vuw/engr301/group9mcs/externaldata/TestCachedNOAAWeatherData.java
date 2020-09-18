package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.engr301.group9mcs.externaldata.CachedNOAAWeatherData;
import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAWeatherData;

/**
 * Tests saving and loading NOAAWeatherData using the
 * CachedNOAAWeatherData class.
 * 
 * @author Joshua Hindley
 */
public class TestCachedNOAAWeatherData {

	/**
	 * The NOAAWeatherData to be cached.
	 */
	private final NOAAWeatherData[] dat = {
			new NOAAWeatherData(2.61, 21, 24.44, 1010),
			new NOAAWeatherData(14.95, 12, 8.39, 994),
			new NOAAWeatherData(4.38, 107, 14.8, 1015)
	};
	
	/**
	 * Tests caching NOAAWeatherData.
	 */
	@Test
	public void testCachingNOAAWeatherData() {
		try {
			//save the data
			JSONArray arr = new JSONArray(this.dat);
			CachedNOAAWeatherData noaaDat = new CachedNOAAWeatherData(arr);
			assertEquals(arr.toString(), noaaDat.getData().toString());
			//load the data
			CachedNOAAWeatherData loadedDat = new CachedNOAAWeatherData();
			assertEquals(noaaDat.getData().toString(), loadedDat.getData().toString());
		} catch (JSONException | IOException e) {
			fail(e);
		}
	}

	/**
	 * Tests caching an empty JSONArray.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEmptyJSON() {
		try {
			//try to save the data
			JSONArray arr = new JSONArray();
			CachedNOAAWeatherData noaaDat = new CachedNOAAWeatherData(arr);
			noaaDat.toString();
			fail("Expected a JSONException to be thrown.");
		} catch (JSONException e) {
			assertEquals("The provided JSON array is empty or not syntactically correct.", e.getMessage());
		} catch (IOException e) {
			fail(e);
		}
	}

	/**
	 * Tests loading cached NOAAWeatherData from a missing file.
	 */
	@Test
	public void testMissingFile() {
		try {
			//save the data
			JSONArray arr = new JSONArray(this.dat);
			CachedNOAAWeatherData cachedNOAAWeatherData = new CachedNOAAWeatherData(arr);
			assertEquals(arr.toString(), cachedNOAAWeatherData.getData().toString());
			//delete the data file
			File cachedFile = new File(CachedNOAAWeatherData.fileName);
			assertTrue(cachedFile.delete());
			//try to load the data
			CachedNOAAWeatherData load = new CachedNOAAWeatherData();
			load.toString();
			fail("Expected a FileNotFoundException to be thrown.");
		} catch (FileNotFoundException e) {
			assertTrue(e.getMessage().contains("The system cannot find the file specified"));
		} catch (IOException | JSONException e) {
			fail(e);
		}
	}

	/**
	 * Tests caching NOAAWeatherData with a missing folder.
	 */
	@Test
	public void testCreatingFolderNOAAWeatherData() {
		try {
			//deletes the folder
			File folder = new File(CachedNOAAWeatherData.fileName.split("/")[0]);
			File file = new File(CachedNOAAWeatherData.fileName);
			if (folder.exists()) {
				if (file.exists())
					assertTrue(file.delete());
				assertTrue(folder.delete());
			}
			//saves the data
			JSONArray arr = new JSONArray(this.dat);
			CachedNOAAWeatherData noaaDat = new CachedNOAAWeatherData(arr);
			assertEquals(arr.toString(), noaaDat.getData().toString());
			//loads the data
			CachedNOAAWeatherData loadedDat = new CachedNOAAWeatherData();
			assertEquals(noaaDat.getData().toString(), loadedDat.getData().toString());
		} catch (JSONException | IOException e) {
			fail(e);
		}
	}
}
