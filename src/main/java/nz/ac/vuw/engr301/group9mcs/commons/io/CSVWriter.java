package nz.ac.vuw.engr301.group9mcs.commons.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;

/**
 * A writer for writing to CSV files
 *
 * @author Claire
 */
public class CSVWriter implements Closeable {

	/**
	 * The underlying writer for this CSV writer
	 */
	private final PrintWriter writer;

	/**
	 * @param file The file to write to
	 * @throws PreconditionViolationException if the file doesn't exist
	 * @throws IOException if there's an error writing to the file
	 */
	public CSVWriter(File file) throws IOException {
		if(!file.exists() || !file.isFile())
			throw new PreconditionViolationException("File to write doesn't exist");

		this.writer = new PrintWriter(new FileWriter(file));
	}

	/**
	 * Writes a value to the CSV file
	 *
	 * @param str The value to write
	 */
	public void writeValue(String str) {
		this.writer.write(str);
		this.writer.write(',');
	}

	/**
	 * Writes a line terminator to the file, signifying a new row
	 */
	public void nextRow() {
		this.writer.write('\n');
	}

	@Override
	public void close() throws IOException {
		this.writer.close();
	}

}
