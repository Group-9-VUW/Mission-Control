package nz.ac.vuw.engr301.group9mcs.commons;

import java.time.Instant;

import org.apache.log4j.spi.LoggingEvent;

/**
 * The layout for the logger. 
 * Whenever an event is logged using default logger it will use this layout to format the details of the log event. 
 * @author August Bolter
 *
 */
public class LoggerLayout extends org.apache.log4j.Layout {

	@Override
	public void activateOptions() {}

	@Override
	public String format(LoggingEvent event) {
		if (event == null) {
			return null;
		}
		
		Instant time = Instant.ofEpochMilli(event.getTimeStamp()); //Converts the time stamp into a date.
		StringBuilder output = new StringBuilder();
		/* Appends logger details */
		output.append("Logger: " + event.fqnOfCategoryClass + "\n");
		output.append("Date: " + time.toString() + "\n");
		output.append("Level: " + event.getLevel().toString() + "\n"); 
		output.append("Thread: " + event.getThreadName() + "\n");
		output.append("Message: " + event.getRenderedMessage() + "\n");
		if (event.getThrowableInformation() != null) { //Throwable information is optional, depends on the log event
            String[] errorInfo = event.getThrowableInformation().getThrowableStrRep();
            output.append("Throwable Info: " + errorInfo[0] + "\n");
		}
		output.append("\n");
		return output.toString();
	}

	@Override
	public boolean ignoresThrowable() {
		return false; //Always looks for a throwable (shown by if statement in format())
	}

}
