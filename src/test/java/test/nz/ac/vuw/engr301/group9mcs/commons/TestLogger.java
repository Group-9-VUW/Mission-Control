package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.commons.FileAppender;
import nz.ac.vuw.engr301.group9mcs.commons.LoggerLayout;

/**
 * Test cases for testing the default logger, logger layout and appenders
 * @author August Bolter
 *
 */
public class TestLogger {

	/**
	 * Testing that the default logger can be created and can hold a static logger.
	 */
	@Test
	public void testCreatingDefaultLogger() {
		DefaultLogger logger = new DefaultLogger();
		assertNotNull(logger); //Testing that it has been created
		assertNotNull(logger.logger); //Testing that it holds a static logger
	}
	
	/**
	 * Testing that the logger layout doesn't work if the event is null
	 */
	@Test
	public void testLoggerLayoutNull() {
		LoggerLayout layout = new LoggerLayout();
		assertNull(layout.format(null));
	}
	
	/**
	 * Testing that the logger layout formats the logging event correctly (log includes throwable).
	 */
	@Test
	public void testLoggerLayoutCorrect() {
		DefaultLogger logger = new DefaultLogger();
		LoggerLayout layout = new LoggerLayout();
		assertTrue(!layout.ignoresThrowable()); //Layout should always consider throwable
		/* Creating the throwable information */
		String[] info = new String[1];
		info[0] = "An exception has occured";
		ThrowableInformation throwable = new ThrowableInformation(info);
		long time = 5000; //Creating the time stamp
		LoggingEvent event = new LoggingEvent("foo", logger.logger, time, Level.ERROR, "Error has occured", "main", throwable, null, null, null); //Creating the event
		/* Checking that the format is correct */
		assertEquals("Logger: foo\n" + 
				"Date: 1970-01-01T00:00:05Z\n" + 
				"Level: ERROR\n" + 
				"Thread: main\n" + 
				"Message: Error has occured\n" + 
				"Throwable Info: An exception has occured\n" +
				"\n", layout.format(event));
	}
	
	/**
	 * Testing that the file appender can be added to the logger and that it appends the formatted 
	 * log events (log includes throwable).
	 */
	@Test 
	public void testFileAppender() {
		DefaultLogger logger = new DefaultLogger();
		LoggerLayout layout = new LoggerLayout();
		FileAppender appender = new FileAppender(layout, "file_appender"); //Creating the appender with a layout and name
		assertNotNull(appender);
		assertTrue(appender.requiresLayout()); //The appender always requires a layout
		logger.logger.addAppender(appender); //Add the appender to the logger
		assertNotNull(logger.logger.getAppender("file_appender")); //Check it exists
		/* Creating the throwable information */
		String[] info = new String[1];
		info[0] = "An exception has occured";
		ThrowableInformation throwable = new ThrowableInformation(info);
		long time = 5000; //Creating the time stamp
		LoggingEvent event = new LoggingEvent("foo", logger.logger, time, Level.ERROR, "Error has occured", "main", throwable, null, null, null); //Creating the event
		logger.logger.getAppender("file_appender").doAppend(event); //Append the log to the event
		logger.logger.getAppender("file_appender").close(); //Close the appender
		File file = new File("log_" + appender.getCurrentTime() + ".log");
		assertTrue(file.exists()); //Checking the file exists
		try {
			/* Checking the content of the file is correct */
			Scanner reader = new Scanner(file);
			assertEquals("Logger: foo", reader.nextLine());
			assertEquals("Date: 1970-01-01T00:00:05Z", reader.nextLine());
			assertEquals("Level: ERROR", reader.nextLine());
			assertEquals("Thread: main", reader.nextLine());
			assertEquals("Message: Error has occured", reader.nextLine());
			assertEquals("Throwable Info: An exception has occured", reader.nextLine());
			assertEquals("", reader.nextLine());
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testing that the console appender prints/outputs the formatted log to console (log doesn't include throwable)
	 */
	@Test
	public void testConsoleAppender() {
		DefaultLogger logger = new DefaultLogger();
		LoggerLayout layout = new LoggerLayout();
		/* Stores the console output in PrintStream of outContent */
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		ConsoleAppender appender = new ConsoleAppender(layout); //Creating the console appender
		assertNotNull(appender); //Checking it isn't null
		assertTrue(appender.requiresLayout()); //And that it requires a layout
		logger.logger.addAppender(appender); //Adding it to the logger
		assertNotNull(logger.logger.getAllAppenders().nextElement()); //Checking that it has been added to the logger
		long time = 5000; //Creating the time stamp
		LoggingEvent event = new LoggingEvent("foo", logger.logger, time, Level.ERROR, "Error has occured", "main", null, null, null, null); //Creating the event without throwable.
		((AppenderSkeleton) logger.logger.getAllAppenders().nextElement()).doAppend(event); //Append the log to the appender
		((AppenderSkeleton) logger.logger.getAllAppenders().nextElement()).close(); //Close the appender
		/* Checking the content of the console output is correct */
		assertEquals("Logger: foo\n" + 
				"Date: 1970-01-01T00:00:05Z\n" + 
				"Level: ERROR\n" + 
				"Thread: main\n" + 
				"Message: Error has occured\n" + 
				"\n", outContent.toString());
	}
}