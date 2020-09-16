package nz.ac.vuw.engr301.group9mcs.commons;

import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.jdt.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.apache.log4j.AppenderSkeleton;

/**
 * A log4j appender which appends the details of the log to a file. Each time the program is run the logs will be
 * appended to a new file. The files are named by the date in which they were created.
 * 
 * @author August Bolter
 * @editor Joshua Hindley
 */
public class FileAppender extends AppenderSkeleton {

	/** The layout the appender will use */
	private LoggerLayout loggerLayout;
	/** The file writer. Used for appending log events to the file */
	private FileWriter writer;
	/** Stores the time of when the FileAppender was created */
	private final String currentTime;

	/**
	 * Creates the appender and sets it's layout and (AppenderSkeleton) name.
	 * @param layout The Appender's Layout
	 * @param name The Appender's name
	 * @throws IOException Thrown when creating the FileWriter if an issue occurs
	 */
	public FileAppender(LoggerLayout layout, String name) throws IOException {
		/* Formatting the current time to the specified date format */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String time = formatter.format(now);
		this.currentTime = time == null ? "" : time;
		this.loggerLayout = layout;
		super.name = name;
		Path path = Paths.get("logs");
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		//Creating a file and its writer
		this.writer = new FileWriter(new File("logs/log_" + this.currentTime + ".log")); 
	}

	@Override
	public void close() {
		try {
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean requiresLayout() {
		return true; //FileAppender always requires a layout so the logging event being appended is formatted correctly.
	}

	@Override
	protected void append(@Nullable LoggingEvent event) {
		try {
			this.writer.append(this.loggerLayout.format(event)); //Formatting the log and appending it to the file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Gets the current time (used for testing purposes).
	 * @return the current time
	 */
	public String getCurrentTime() {
		return this.currentTime;
	}

}
