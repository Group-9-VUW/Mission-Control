package nz.ac.vuw.engr301.group9mcs.avionics;

import jssc.SerialPortList;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;

/**
 * A helper class for accessing serial COM ports
 * 
 * @author Claire
 */
public class COMHelper {
	
	/**
	 * @return A list of all the available COM ports on the system.
	 */
	public static final String[] getAvailablePorts()
	{
		return Null.nonNull(SerialPortList.getPortNames());
	}
	
	/**
	 * @param port The port to check
	 * @return True if it's available, false otherwise
	 */
	public static final boolean comPortExists(String port)
	{
		for(String s : getAvailablePorts())
			if(s.equals(port))
				return true;
		return false;
	}

}
