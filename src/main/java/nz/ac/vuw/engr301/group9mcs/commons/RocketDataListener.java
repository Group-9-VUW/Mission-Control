package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * A listener for rocket data
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public interface RocketDataListener {

	/**
	 * Called when new data is received
	 *
	 * @param data The data that's been received
	 */
	void receiveRocketData(RocketData data);

}
