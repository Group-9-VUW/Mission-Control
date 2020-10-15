package nz.ac.vuw.engr301.group9mcs.controller;

import javax.swing.JFrame;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.avionics.LORADriver;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.montecarlo.MonteCarloBridge;

/**
 * Holds resources needed to pass to Perspectives
 * 
 * @author Bryony
 *
 */
public class Resources {

	/**
	 * The root JFrame
	 */
	private final JFrame frame;
	
	/**
	 * The LoRA driver
	 */
	private final LORADriver driver;
	
	/**
	 * The Longitude (launch site?)
	 */
	private double longitude;
	
	/**
	 * The Latitude (launch site?)
	 */
	private double latitude;
	
	/**
	 * The saved launch, if one exists
	 */
	private @Nullable SavedLaunch savedLaunch;
	
	/**
	 * The Monte Carlo Bridge
	 */
	private final MonteCarloBridge bridge;
	
	/**
	 * @param frame The root JFrame
	 * @throws Exception If there's an error initializing any resources
	 */
	public Resources(JFrame frame) throws Exception
	{
		this.frame = frame;
		this.driver = new LORADriver();
		this.bridge = new MonteCarloBridge();
	}
	
	/**
	 * Returns the Longitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * 
	 * @return Returns a Double
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Sets the Longitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * Not sure if it's needed
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Returns the Latitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * 
	 * @return Returns a Double
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Sets the Latitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * Not sure if it's needed
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() 
	{
		return this.frame;
	}

	/**
	 * @return the driver
	 */
	public LORADriver getDriver() 
	{
		return this.driver;
	}
	
	/**
	 * This method checks whether a launch exists, and loads it if so
	 * 
	 * @return Whether a launch has been saved
	 * @throws Exception If there is an error in checking/loading the launch state
	 */
	public boolean hasSavedLaunch() throws Exception
	{
		if(this.savedLaunch == null) {
			if(!SavedLaunch.hasLaunch()) {
				return false;
			}
			this.savedLaunch = SavedLaunch.loadLaunch();
		}
		return true;
	}
	
	/**
	 * Requires <code>hasSavedLaunch() == true</code>
	 * @return The saved launch
	 * @throws PreconditionViolationException if hasSavedLaunch() == false
	 */
	public SavedLaunch getSavedLaunch()
	{
		return Null.nonNull(this.savedLaunch);
	}

	/**
	 * @return the bridge
	 */
	public MonteCarloBridge getBridge() {
		return this.bridge;
	}
	
}
