package nz.ac.vuw.engr301.group9mcs.commons;
import org.apache.log4j.Logger;

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

}
