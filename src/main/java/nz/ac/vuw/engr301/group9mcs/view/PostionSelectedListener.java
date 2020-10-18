package nz.ac.vuw.engr301.group9mcs.view;

/**
 * If a user selects a position on a GUI, this is what you use to listen for it.
 *
 * @author August Bolter
 * Copyright (C) 2020, Mission Control Group 9
 */
public interface PostionSelectedListener {
	/**
	 * @param lat Latitude of chosen Position
	 * @param lon Longitude of chosen Position
	 */
	void onLaunchSelected(double lat, double lon);
}
