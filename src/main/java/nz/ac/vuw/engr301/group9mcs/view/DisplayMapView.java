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
   * The longitude of where the rocket is.
   */
  private double longRocket;
  /**
   * The Latitude of where the rocket is.
   */
  private double latRocket;

  /**
   * The space between a point and the edge (any point).
   */
  private int zoom = 100;
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

    Point2D[] corners = getCorners();

    Point2D launchCoordinates = new Point2D.Double(this.longLaunchSite, this.latLaunchSite);
    Point2D rocketCoordinates = new Point2D.Double(this.longRocket, this.latRocket);

    // calculate the x coordinates for rocket and launch site
    int xlaunch;
    int xrocket;
    if (launchCoordinates.getY() > rocketCoordinates.getY()) {
      // the launch site is closer to the top of the screen
      xlaunch = (int)(translateScale(launchCoordinates.getX(),
          corners[2].getX(), corners[3].getX(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
      // the rocket is closer to the bottom of the screen
      xrocket = (int)(translateScale(rocketCoordinates.getX(),
          corners[0].getX(), corners[1].getX(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
    } else {
      // the rocket is closer to the top of the screen
      xrocket = (int)(translateScale(rocketCoordinates.getX(),
          corners[2].getX(), corners[3].getX(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
      // the launch is closer to the bottom of the screen
      xlaunch = (int)(translateScale(launchCoordinates.getX(),
          corners[0].getX(), corners[1].getX(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
    }

    // calculate the y coordinates for rocket and launch site
    int ylaunch;
    int yrocket;
    if (launchCoordinates.getX() > rocketCoordinates.getX()) {
      // the launch is closer to the 
      ylaunch = (int)(translateScale(launchCoordinates.getY(),
          corners[1].getY(), corners[3].getY(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
      yrocket = (int)(translateScale(rocketCoordinates.getY(),
          corners[0].getY(), corners[2].getY(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
    } else {
      yrocket = (int)(translateScale(rocketCoordinates.getY(),
          corners[1].getY(), corners[3].getY(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
      ylaunch = (int)(translateScale(launchCoordinates.getY(),
          corners[0].getY(), corners[2].getY(), this.getSize().getWidth() 
          - this.zoom)) + this.zoom / 2;
    }

    int width = this.getSize().width;
    int height = this.getSize().height;
    
    // Show a representation of the locations using rectangles
    g.fillRect(0, 0, width - xlaunch, height - ylaunch);
    g.setColor(Color.white);
    g.drawRect(0, 0, width - xrocket, height - yrocket);
  }

  /**
   * Create four corners around the launch site and rocket location.
   * NOTE: Latitude works opposite to x.
   * 
   * @return An array containing objects holding the coordinates for each corner
   */
  private Point2D[] getCorners() {
    // Find max/min x/y of rocket and launch site
    double maxX = Math.max(this.longLaunchSite, this.longRocket);
    double minX = Math.min(this.longLaunchSite, this.longRocket);
    double maxY = Math.max(this.latLaunchSite, this.latRocket);
    double minY = Math.min(this.latLaunchSite, this.latRocket);

    // Convert to a square of points
    Point2D bottomLeft = new Point2D.Double(minX, minY);
    Point2D bottomRight = new Point2D.Double(maxX, minY);
    Point2D topLeft = new Point2D.Double(minX, maxY);
    Point2D topRight = new Point2D.Double(maxX, maxY);

    // Find the width and height of square
    double distX = distance(bottomRight.getY(), bottomRight.getX(), 
        bottomLeft.getY(), bottomLeft.getX());
    double distY = distance(bottomRight.getY(), bottomRight.getX(), 
        topRight.getY(), topRight.getX());

    // Make both lengths equal
    if (distX > distY) {
      // Get a point in the middle of the left side
      Point2D midXLeft = getNewLocation(bottomLeft.getY(), bottomLeft.getX(), distY / 2, 0);
      // Move the left points to make a side the same length as the top
      topLeft = getNewLocation(midXLeft.getY(), midXLeft.getX(), distX / 2, 0);
      bottomLeft = getNewLocation(midXLeft.getY(), midXLeft.getX(), distX / 2, 180);
      // Get a point in the middle of the right side
      Point2D midXRight = getNewLocation(bottomRight.getY(), bottomRight.getX(), distY / 2, 0);
      // Move the right points to make a side the same length as the top
      topRight = getNewLocation(midXRight.getY(), midXRight.getX(), distX / 2, 0);
      bottomRight = getNewLocation(midXRight.getY(), midXRight.getX(), distX / 2, 180);
    } else if (distY > distX) {
      // Get a point in the middle of the bottom
      Point2D midYBottom = getNewLocation(bottomLeft.getY(), bottomLeft.getX(), distX / 2, 90);
      // Move the bottom points to make the bottom the same length as the side
      bottomRight = getNewLocation(midYBottom.getY(), midYBottom.getX(), distX / 2, 90);
      bottomLeft = getNewLocation(midYBottom.getY(), midYBottom.getX(), distX / 2, 270);
      // Get a point in the middle of the top
      Point2D midYTop = getNewLocation(bottomRight.getY(), bottomRight.getX(), distX / 2, 90);
      // Move the top points to make the top the same length as the side
      topRight = getNewLocation(midYTop.getY(), midYTop.getX(), distX / 2, 90);
      topLeft = getNewLocation(midYTop.getY(), midYTop.getX(), distX / 2, 270);
    }

    // Return all corners
    Point2D[] corners = {topLeft, topRight, bottomLeft, bottomRight};
    return corners;
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
  private static double translateScale(double coordinate, double max, double min, double screen) {
    return (coordinate - min) / ((max - min) / screen);
  }

  /**
   * Find a point. This point is X distance from the original point at y angle.
   * 
   * @param lat The Latitude of original point
   * @param lon The Longitude of original point
   * @param dist The distance between points
   * @param angle The angle between original point and new point
   * @return The coordinates of the new point
   */
  private static Point2D getNewLocation(double lat, double lon, double dist, double angle) {
    // Earth Radious in KM
    double radiusEarth = 6378.14;

    // Degree to Radian
    double latRadian = lat * (Math.PI / 180);
    double lonRadian = lon * (Math.PI / 180);
    double direction = angle * (Math.PI / 180);

    double latitude2 = Math.asin(Math.sin(latRadian) * Math.cos(dist / radiusEarth) 
        + Math.cos(latRadian) * Math.sin(dist / radiusEarth) * Math.cos(direction));
    double longitude2 = lonRadian + Math.atan2(Math.sin(direction) * Math.sin(dist / radiusEarth)
        * Math.cos(latRadian), Math.cos(dist / radiusEarth) - Math.sin(latRadian) 
        * Math.sin(latitude2));

    // back to degrees
    latitude2 = latitude2 * (180 / Math.PI);
    longitude2 = longitude2 * (180 / Math.PI);

    return new Point2D.Double(longitude2, latitude2);
  }

  /**
   * Calculate distance between two points.
   * 
   * @param lat1 Latitude of First point
   * @param lon1 Longitude of First point
   * @param lat2 Latitude of Second point
   * @param lon2 Longitude of Second point
   * @return The distance
   */
  private static double distance(double lat1, double lon1, double lat2, double lon2) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
      return 0;
    }
    double theta = lon1 - lon2;
    double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) 
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) 
        * Math.cos(Math.toRadians(theta));
    dist = Math.acos(dist);
    dist = Math.toDegrees(dist);
    dist = dist * 60 * 1.1515;
    // convert to kilometers
    dist = dist * 1.609344;
    return (dist);
  }

}
