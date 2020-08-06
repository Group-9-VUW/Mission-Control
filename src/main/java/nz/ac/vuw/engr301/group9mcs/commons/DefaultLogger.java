package nz.ac.vuw.engr301.group9mcs.commons;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * The default logger of the program. Whenever an error occurs, or
 * something needs to be logged, this class should be used to log
 * the information to a file or to the console.
 *
 * @author Joshua Hindley (hindlejosh)
 */
@SuppressWarnings("null")
public class DefaultLogger {

	/**
	 * The default logger.
	 */
	public static final Logger logger = Logger.getLogger(DefaultLogger.class); //TODO fix null warning

	/**
	 * Initializes the logger by adding a console appender to it.
	 * This method should only be called once.
	 */
	public DefaultLogger() {
		//TODO change appender (especially the layout).
		logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n"), "System.out"));
	}

}
