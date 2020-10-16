package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.io.CSVReader;
import nz.ac.vuw.engr301.group9mcs.commons.io.CSVWriter;

/**
 * Tests the CSVReader and CSVWriter classes.
 *
 * @author Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestCSV {

	/**
	 * Tests creating a new CSVWriter with invalid files.
	 */
	@SuppressWarnings({ "static-method", "unused" })
	@Test
	public void testSavingInvalidCSV() {
		assertThrows(PreconditionViolationException.class, () -> {new CSVWriter(new File(""));});
		assertThrows(PreconditionViolationException.class, () -> {new CSVWriter(new File("/"));});
	}

	/**
	 * Tests creating a new CSVReader with invalid files.
	 */
	@SuppressWarnings({ "static-method", "unused" })
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
				//REMOVE COMMA FROM THE END OF THE SECOND LINE
				try (Scanner sc = new Scanner(csv);) {
					String line1 = sc.nextLine();
					String line2 = sc.nextLine();
					sc.close();
					line2 = line2.substring(0, line2.length() - 1);
					csv.createNewFile();
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv));) {
						bw.write(line1);
						bw.append('\n');
						bw.write(line2);
						bw.close();
						//NOW TEST LOADING CSV
						try (CSVReader reader = new CSVReader(csv);) {
							assertTrue(reader.hasNextLine());
							assertFalse(reader.hasNextValue());
							assertThrows(PreconditionViolationException.class,() -> {reader.nextValue();});
							reader.nextLine();
							assertTrue(reader.hasNextValue());
							assertEquals("this", reader.nextValue());
							assertEquals("is", reader.nextValue());
							assertEquals("a", reader.nextValue());
							assertEquals("CSV", reader.nextValue());
							assertEquals("file", reader.nextValue());
							assertFalse(reader.hasNextValue());
							reader.nextLine();
							assertEquals("this", reader.nextValue());
							assertEquals("is", reader.nextValue());
							assertEquals("the", reader.nextValue());
							assertEquals("second", reader.nextValue());
							assertEquals("row", reader.nextValue());
							assertFalse(reader.hasNextValue());
							assertFalse(reader.hasNextLine());
							assertThrows(PreconditionViolationException.class,() -> {reader.nextValue();});
							assertThrows(NoSuchElementException.class,() -> {reader.nextLine();});
						}}}}} catch (IOException e) {
							fail(e);
						}
	}


	/**
	 * Tests saving to and loading from a valid CSV file with blank values.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testValidCSVWithBlanks() {
		try {
			//TEST WRITING CSV
			File csv = new File("test.csv");
			csv.createNewFile();
			try (CSVWriter writer = new CSVWriter(csv);) {
				writer.writeValue("this");
				writer.writeValue("is");
				writer.writeValue("");
				writer.writeValue("CSV");
				writer.writeValue("");
				writer.nextRow();
				writer.writeValue("");
				writer.writeValue("");
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
					assertEquals("this", reader.nextValue());
					assertEquals("is", reader.nextValue());
					assertEquals("", reader.nextValue());
					assertEquals("CSV", reader.nextValue());
					assertEquals("", reader.nextValue());
					assertFalse(reader.hasNextValue());
					reader.nextLine();
					assertEquals("", reader.nextValue());
					assertEquals("", reader.nextValue());
					assertEquals("the", reader.nextValue());
					assertEquals("second", reader.nextValue());
					assertEquals("row", reader.nextValue());
					assertFalse(reader.hasNextValue());
					assertFalse(reader.hasNextLine());
					assertThrows(PreconditionViolationException.class,() -> {reader.nextValue();});
					assertThrows(NoSuchElementException.class,() -> {reader.nextLine();});
				}}} catch (IOException e) {
					fail(e);
				}
	}
}
