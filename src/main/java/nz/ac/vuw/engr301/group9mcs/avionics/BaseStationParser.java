package nz.ac.vuw.engr301.group9mcs.avionics;

import nz.ac.vuw.engr301.group9mcs.commons.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.commons.RocketData;

/**
 * This will parse incoming data from the rocket and will create objects contained the
 * parsed information. The incoming data is in CSV format (without headers).
 */
public class BaseStationParser {

    /**
     * Default constructor.
     */
    public BaseStationParser(){}

    /**
     * This will parse one instance of data from the rocket.
     * @param data the data to parse
     * @return RocketData with all information from data
     * @throws NumberFormatException if the supplied data is not a double. 
     */
    @SuppressWarnings("static-method")
	public RocketData parse(String data) throws NumberFormatException{
        String[] separated = data.split(",");

        //Length of the converted array is 1 less than separated as
        //the rocket state will be converted on its own.
        double[] converted = new double[separated.length-1];
        RocketData.ROCKET_STATE state = null; 
        
        for(int i = 0; i < converted.length; i++){
        	try {
        		converted[i] = Double.parseDouble(separated[i]);
        	} catch(NumberFormatException e) {
            	DefaultLogger.logger.error("Invalid input for RocketData: " + separated[i] + " is not a double.");
            	throw new NumberFormatException(separated[i] + " is not a double."); 
        	}
        }
        
	    try {
	        state = RocketData.ROCKET_STATE.valueOf(Null.nonNull(separated[separated.length-1])); 
        } catch(IllegalArgumentException e) {
        	DefaultLogger.logger.error("Invalid rocket state");
        	throw new IllegalArgumentException(separated[separated.length-1] + " is not a valid rocket state.");
        }

        return new RocketData(converted[0], converted[1], converted[2], converted[3], converted[4], converted[5],
                converted[6], converted[7], converted[8], converted[9], converted[10], converted[11], converted[12],
                converted[13], state);
    }
}
