package nz.ac.vuw.engr301.group9mcs.commons.logging;
import org.apache.log4j.Logger;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;

/**
 * The default logger of the program. Whenever an error occurs, or
 * something needs to be logged, this class should be used to log
 * the information to a file or to the console.
 *
 * @author Joshua Hindley (hindlejosh)
 */
public class DefaultLogger {

	/**
	 * The default logger.
	 */
	public static final Logger logger = Null.nonNull(Logger.getLogger(DefaultLogger.class));

}
