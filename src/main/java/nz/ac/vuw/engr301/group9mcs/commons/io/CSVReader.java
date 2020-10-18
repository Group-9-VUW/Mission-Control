package nz.ac.vuw.engr301.group9mcs.commons.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;

/**
 * A reader for csv files
 *
 * @author Claire Chambers
 * @editor Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class CSVReader implements Closeable {

	/**
	 * The underlying reader for this reader
	 */
	private final BufferedReader reader;

	/**
	 * An Iterator of all the lines in the file
	 */
	private final Iterator<String> lines;

	/**
	 * The current line
	 */
	private String @Nullable[] currentLine;

	/**
	 * The current value
	 */
	private int value = 0;

	/**
	 * @param file The file to read from
	 * @throws PreconditionViolationException if the file doesn't exist
	 * @throws IOException if there's an error reading from the file
	 */
	@SuppressWarnings("null")
	public CSVReader(File file) throws IOException {
		if(!file.exists() || !file.isFile())
			throw new PreconditionViolationException("File to read doesn't exist");

		this.reader = new BufferedReader(new FileReader(file));
		this.lines = this.reader.lines().iterator();
	}

	/**
	 * @return Whether a next line is available
	 */
	public boolean hasNextLine() {
		return this.lines.hasNext();
	}

	/**
	 * @return Whether a next value is available
	 */
	public boolean hasNextValue() {
		if(this.currentLine == null)
			return false;
		return this.value < Null.nonNull(this.currentLine).length;
	}

	/**
	 * Moves this reader to the next line
	 */
	public void nextLine() {
		String str = this.lines.next();
		this.currentLine = str.split(",", count(str, ',') + (str.charAt(str.length() - 1) == ',' ? 0 : 1));
		String last = Null.nonNull(this.currentLine)[Null.nonNull(this.currentLine).length - 1];
		if(last.endsWith(",")) {
			Null.nonNull(this.currentLine)[Null.nonNull(this.currentLine).length - 1] = last.substring(0, last.length() - 1);
		}
		this.value = 0;
	}

	/**
	 * @return The next value on the current line
	 */
	public String nextValue() {
		if(this.currentLine == null || !this.hasNextValue())
			throw new PreconditionViolationException("No line in memory, or hasNextValue() is false");
		return Null.nonNull(Null.nonNull(this.currentLine)[this.value++]);
	}

	/**
	 * @param str The string to check
	 * @param c the character to count
	 * @return The number of c instances in str
	 */
	public static int count(String str, char c) {
		int lastindex = 0;
		int count = 0;
		while(lastindex != -1)  {
			lastindex = str.indexOf(c, lastindex);
			if(lastindex != -1) {
				count++;
				lastindex++;
			}
		}
		return count;
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}

}
