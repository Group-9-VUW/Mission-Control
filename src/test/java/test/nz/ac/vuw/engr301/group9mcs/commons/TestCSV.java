package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.io.CSVReader;
import nz.ac.vuw.engr301.group9mcs.commons.io.CSVWriter;

/**
 * Tests the CSVReader and CSVWriter classes.
 *
 * @author Joshua
 */
public class TestCSV {

	/**
	 * Tests creating a new CSVWriter with invalid files.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSavingInvalidCSV() {
		assertThrows(PreconditionViolationException.class, () -> {new CSVWriter(new File(""));});
		assertThrows(PreconditionViolationException.class, () -> {new CSVWriter(new File("/"));});
	}

	/**
	 * Tests creating a new CSVReader with invalid files.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLoadingInvalidCSV() {
		assertThrows(PreconditionViolationException.class, () -> {new CSVReader(new File(""));});
		assertThrows(PreconditionViolationException.class, () -> {new CSVReader(new File("/"));});
	}


	/**
	 * Tests saving to and loading from a valid CSV file.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testValidCSV() {
		try {
			//TEST WRITING CSV
			File csv = new File("test.csv");
			csv.createNewFile();
			try (CSVWriter writer = new CSVWriter(csv);) {
				writer.writeValue("this");
				writer.writeValue("is");
				writer.writeValue("a");
				writer.writeValue("CSV");
				writer.writeValue("file");
				writer.nextRow();
				writer.writeValue("this");
				writer.writeValue("is");
				writer.writeValue("the");
				writer.writeValue("second");
				writer.writeValue("row");
				writer.close();
				//NOW TEST LOADING CSV
				try (CSVReader reader = new CSVReader(csv);) {
					assertTrue(reader.hasNextLine());
					assertFalse(reader.hasNextValue());
					assertThrows(PreconditionViolationException.class,() -> {reader.nextValue();});
					reader.nextLine();
					assertTrue(reader.hasNextValue());
				}}} catch (IOException e) {
					fail(e);
				}
	}
}
