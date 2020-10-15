/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.commons;

import java.io.Serializable;

/**
 * A data class for the launch rod data
 * 
 * @author Claire
 */
public class LaunchRodData implements Serializable {
	
	/**
	 */
	private static final long serialVersionUID = 8665124166371337601L;

	/**
	 * Launch rod angle
	 */
	private final double angle;
	
	/**
	 * Launch rod direction
	 */
	private final double direction;
	
	/**
	 * Launch rod height
	 */
	private final double height;

	/**
	 * @param angle
	 * @param direction
	 * @param height
	 */
	public LaunchRodData(double angle, double direction, double height) {
		this.angle = angle;
		this.direction = direction;
		this.height = height;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return this.angle;
	}

	/**
	 * @return the direction
	 */
	public double getDirection() {
		return this.direction;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return this.height;
	}

	
}
