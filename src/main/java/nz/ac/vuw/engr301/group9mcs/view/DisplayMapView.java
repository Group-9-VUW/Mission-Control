package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Display map view is for showing the position of the rocket. 
 * Its viewing zoom and position should be automatically decided 
 * based upon the position of the launch site and the position of 
 * the rocket. It's purely output, the user shouldn't be able to engage 
 * with it at all
 * There will be a MapData interface that allows you to get a map 
 * image for a certain area specified in longitude and latitude 
 * (I'm thinking upper-left, lower-right corner notation, as it 
 * allows the caller to choose aspect ratio in only two arguments). 
 * 
 * @author Bryony
 *
 */

public class DisplayMapView extends JPanel {
  
  /**
   * The Longitude of where the launch site is. 
   * Longitude changes value along the horizontal axis, the X axis.
   */
  private double longLaunchSite;
  /**
   * The Latitude of where the launch site is.
   * Latitude values increase or decrease along the vertical axis, the Y axis.
   */
  private double latLaunchSite;
  /**
   * The Altitude of where the launch site is.
   */
  private double altLaunchSite;
  /**
   * The longitude of where the rocket is.
   */
  private double longRocket;
  /**
   * The Latitude of where the rocket is.
   */
  private double latRocket;
  /**
   * The Altitude of where the rocket is.
   */
  private double altRocket;
  
  /**
   * UID.
   */
  private static final long serialVersionUID = 3810711176953194652L;

  /**
   * Set up JPanel for Displaying Map of Rocket's Progress.
   * @param latLaunchSite The Latitude of where the launch site is
   * @param longLaunchSite The Longitude of where the launch site is
   */
  public DisplayMapView(double latLaunchSite, double longLaunchSite) {
    this.longLaunchSite = longLaunchSite;
    this.longRocket = longLaunchSite;
    this.latLaunchSite = latLaunchSite;
    this.latRocket = latLaunchSite;
  }
  
  @Override
  public void paintComponent(@Nullable Graphics g) {
    if (g == null) {
      return;
    }
    
    Point2D launchCoordinates = coordinateToXY(this.longLaunchSite, 
        this.latLaunchSite, this.altLaunchSite);
    Point2D rocketCoordinates = coordinateToXY(this.longRocket, 
        this.latRocket, this.altRocket);
    
    // Launch coordinates in an x,y format
    int xlaunch;
    int ylaunch;
    
    // Rocket coordinates in an x,y format
    int xrocket;
    int yrocket;
    
    if (launchCoordinates.getX() > rocketCoordinates.getX()) {
      xrocket = 100;
      xlaunch = 100 + (int)(launchCoordinates.getX() - rocketCoordinates.getX());
    } else {
      xlaunch = 100;
      xrocket = 100 + (int)(rocketCoordinates.getX() - launchCoordinates.getX());
    }
    
    if (launchCoordinates.getY() > rocketCoordinates.getY()) {
      yrocket = 100;
      ylaunch = 100 + (int)(launchCoordinates.getY() - rocketCoordinates.getY());
    } else {
      ylaunch = 100;
      yrocket = 100 + (int)(rocketCoordinates.getY() - launchCoordinates.getY());
    }
    
    g.fillRect(0, 0, xlaunch, ylaunch);
    g.setColor(Color.white);
    g.drawRect(0, 0, xrocket, yrocket);
  }
  
  /**
   * Update the location of the rocket.
   * 
   * @param longR Longitude of the rocket's location
   * @param latR Latitude of the rocket's location
   */
  public void updateRocketPosition(double longR, double latR) {
    this.longRocket = longR;
    this.latRocket = latR;
  }
  
  /**
   * Translate and Scale a coordinate to fit on the Screen.
   * Everything is translated so that the min value is at 0.
   * Everything is scaled so that the max value is at the screen value.
   * 
   * @param coordinate X or Y position of an object
   * @param max Largest value of range
   * @param min Smallest value of range
   * @param screen Size of screen to fit range to
   * @return X or Y position inside new range
   */
  private double translateScale(double coordinate, double max, double min, double screen) {
    return (coordinate - min) / ((max - min) / screen);
  }
  
  /**
   * Convert the coordinates from Longitude and Latitude to x, y coordinates.
   * NOTE: this method gives you X and Y positions related to the globe
   * 
   * @param lon Longitude of Object
   * @param lat Latitude of Object
   * @param alt Altitude of Object
   * @return An object holding the x and y coordinates of the Object
   */
  private static Point2D coordinateToXY(double lon, double lat, double alt) {
    double radiusMajor = 6378137;
    double radiusMinor = 6356752.31424518;

    double latrad = lat / 180.0 * Math.PI;
    double lonrad = lon / 180.0 * Math.PI;

    double coslat = Math.cos(latrad);
    double sinlat = Math.sin(latrad);
    double coslon = Math.cos(lonrad);
    double sinlon = Math.sin(lonrad);

    double term1 = (radiusMajor * radiusMajor * coslat) / Math.sqrt(radiusMajor 
        * radiusMajor * coslat * coslat + radiusMinor * radiusMinor * sinlat * sinlat);

    double term2 = alt * coslat + term1;

    double x = coslon * term2;
    double y = sinlon * term2;
    
    return new Point2D.Double(x, y);
  }
  
}
