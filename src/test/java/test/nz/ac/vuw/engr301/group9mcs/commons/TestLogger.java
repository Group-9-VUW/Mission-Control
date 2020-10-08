package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.commons.logging.FileAppender;
import nz.ac.vuw.engr301.group9mcs.commons.logging.LoggerLayout;

/**
 * Test cases for testing the default logger, logger layout and appenders.
 * 
 * @author August Bolter
 * @editor Joshua Hindley
 */
public class TestLogger {

	/**
	 * Testing that the default logger exists by default.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDefaultLogger() {
		assertNotNull(DefaultLogger.logger); //Testing that it holds a static logger
		DefaultLogger log = new DefaultLogger();
		assertNotNull(log);
	}

	/**
	 * Testing that the logger layout doesn't work if the event is null
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLoggerLayoutNull() {
		LoggerLayout layout = new LoggerLayout();
		layout.activateOptions();
		assertEquals("", layout.format(null));
	}

	/**
	 * Testing that the logger layout formats the logging event correctly (log includes throwable).
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLoggerLayoutCorrect() {
		LoggerLayout layout = new LoggerLayout();
		assertTrue(!layout.ignoresThrowable()); //Layout should always consider throwable
		/* Creating the throwable information */
		String[] info = new String[1];
		info[0] = "An exception has occured";
		ThrowableInformation throwable = new ThrowableInformation(info);
		long time = 5000; //Creating the time stamp
		LoggingEvent event = new LoggingEvent("foo", DefaultLogger.logger, time, Level.ERROR, "Error has occured", "main", throwable, null, null, null); //Creating the event
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
	@SuppressWarnings("static-method")
	@Test 
	public void testFileAppender() {
		LoggerLayout layout = new LoggerLayout();
		try {
			FileAppender appender = new FileAppender(layout, "file_appender"); //Creating the appender with a layout and name
			assertNotNull(appender);
			assertTrue(appender.requiresLayout()); //The appender always requires a layout
			DefaultLogger.logger.addAppender(appender); //Add the appender to the logger
			assertNotNull(DefaultLogger.logger.getAppender("file_appender")); //Check it exists
			/* Creating the throwable information */
			String[] info = new String[1];
			info[0] = "An exception has occured";
			ThrowableInformation throwable = new ThrowableInformation(info);
			long time = 5000; //Creating the time stamp
			LoggingEvent event = new LoggingEvent("foo", DefaultLogger.logger, time, Level.ERROR, "Error has occured", "main", throwable, null, null, null); //Creating the event
			DefaultLogger.logger.getAppender("file_appender").doAppend(event); //Append the log to the event
			DefaultLogger.logger.getAppender("file_appender").close(); //Close the appender
			File file = new File("logs/log_" + appender.getCurrentTime() + ".log");
			assertTrue(file.exists()); //Checking the file exists
			try (Scanner reader = new Scanner(file);) {
				/* Checking the content of the file is correct */
				assertEquals("Logger: foo", reader.nextLine());
				assertEquals("Date: 1970-01-01T00:00:05Z", reader.nextLine());
				assertEquals("Level: ERROR", reader.nextLine());
				assertEquals("Thread: main", reader.nextLine());
				assertEquals("Message: Error has occured", reader.nextLine());
				assertEquals("Throwable Info: An exception has occured", reader.nextLine());
				assertEquals("", reader.nextLine());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				DefaultLogger.logger.removeAllAppenders();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}

	/**
	 * Testing that the console appender prints/outputs the formatted log to console (log doesn't include throwable)
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConsoleAppender() {
		try {
			LoggerLayout layout = new LoggerLayout();
			/* Stores the console output in PrintStream of outContent */
			final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outContent));
			ConsoleAppender appender = new ConsoleAppender(layout); //Creating the console appender
			assertNotNull(appender); //Checking it isn't null
			assertTrue(appender.requiresLayout()); //And that it requires a layout
			DefaultLogger.logger.addAppender(appender); //Adding it to the logger
			assertNotNull(DefaultLogger.logger.getAllAppenders().nextElement()); //Checking that it has been added to the logger
			long time = 5000; //Creating the time stamp
			LoggingEvent event = new LoggingEvent("foo", DefaultLogger.logger, time, Level.ERROR, "Error has occured", "main", null, null, null, null); //Creating the event without throwable.
			((AppenderSkeleton) DefaultLogger.logger.getAllAppenders().nextElement()).doAppend(event); //Append the log to the appender
			((AppenderSkeleton) DefaultLogger.logger.getAllAppenders().nextElement()).close(); //Close the appender
			/* Checking the content of the console output is correct */
			assertEquals("Logger: foo\n" + 
					"Date: 1970-01-01T00:00:05Z\n" + 
					"Level: ERROR\n" + 
					"Thread: main\n" + 
					"Message: Error has occured\n" + 
					"\n", outContent.toString());
		} finally {
			DefaultLogger.logger.removeAllAppenders();
		}
	}
}
