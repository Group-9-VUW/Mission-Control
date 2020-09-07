package nz.ac.vuw.engr301.group9mcs.commons;

import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.jdt.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	private LoggerLayout loggerLayout; //The layout the appender will use
	private FileWriter writer;
	private final String currentTime;

	/**
	 * Creates the appender and sets it's layout and (AppenderSkeleton) name.
	 */
	@SuppressWarnings("null") //TODO fix this
	public FileAppender(LoggerLayout layout, String name) {
		/* Formatting the current time to the specified date format */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String time = formatter.format(now);
		this.currentTime = time == null ? "" : time;
		this.loggerLayout = layout;
		super.name = name;
		try {
			//Creating a file and its writer
			this.writer = new FileWriter(new File("log_" + this.currentTime + ".log")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
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
