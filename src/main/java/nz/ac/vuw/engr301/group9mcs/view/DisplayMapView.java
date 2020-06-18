package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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

    // draw the rocket and launch site on the map
    drawRocket(width - xrocket, height - yrocket, g, launchCoordinates, rocketCoordinates);
    drawLaunchSite(width - xlaunch, height - ylaunch, g);
  }

  /**
   * Draw the launch site at a given point on the screen.
   * 
   * @param xlocation X coordinate of the site location
   * @param ylocation Y coordinate of the site location
   * @param g The Graphics drawer
   */
  private void drawLaunchSite(int xlocation, int ylocation, Graphics g) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("./src/main/resources/view/launchsite.png"));
      image = scaleDimensions(image, 5.0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (image == null) {
      return;
    }
    int x = xlocation - image.getWidth() / 2;
    int y = ylocation - image.getHeight() / 2;
    setColour(image);
    g.drawImage(image, x, y, null);
  }
  
  /**
   * Draw a rocket in the direction given at a point on the screen.
   * The direction could be 1 (NorthEast), 2 (NorthWest), 3 (SouthEast), 4 (SouthWest)
   * 
   * @param xlocation X coordinate of the rocket Location
   * @param ylocation Y coordinate of the rocket Location
   * @param g The graphic drawer
   * @param launch The Launch Coordinates (lon & lat)
   * @param rocket The Rocket Coordinates (lon & lat)
   */
  private void drawRocket(int xlocation, int ylocation, Graphics g, 
      Point2D launch, Point2D rocket) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("./src/main/resources/view/rocket.png")); 
      image = scaleDimensions(image, 4.0);
      double angle = angleOf(launch, rocket);
      image = rotateImage(image, angle);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (image == null) {
      return;
    }
    int x = xlocation - image.getWidth(null) / 2;
    int y = ylocation - image.getHeight(null) / 2;
    setColour(image);
    g.drawImage(image, x, y, null);
  }

  /**
   * Assuming that the image is black with a transparent background.
   * Sets the image color to red (has a black outline).
   * 
   * @param image Image to be colored
   * @return Colored image
   */
  private @Nullable static BufferedImage setColour(@Nullable BufferedImage image) {
    if (image == null) {
      return image;
    }
    // get width and height 
    int width = image.getWidth(); 
    int height = image.getHeight(); 

    int set = new Color(255,0,0).getRGB();

    int[] result = new int[height * width];
    image.getRGB(0, 0, width, height, result, 0, width);

    for (int y = 0; y < height; y++) { 
      for (int x = 0; x < width; x++) { 
        Color c = new Color(image.getRGB(x, y), true);
        int p = c.getAlpha();

        if (p > 20 && c.equals(Color.black)) {
          image.setRGB(x, y, set); 
        }
      } 
    }
    return image;
  }

  /**
   * Scale the image to 1/4 of the screen.
   * Scales to the shortest length (height/width).
   * 
   * @param image The image to be scaled
   * @param scale The scale of the image (side = screen dimension / scale)
   * @return The scaled image
   */
  private @Nullable BufferedImage scaleDimensions(@Nullable BufferedImage image, double scale) {
    if (image == null) {
      return image;
    }
    double ratio = 0.0;
    if (this.getSize().height > this.getSize().width) {
      ratio = (this.getSize().width / scale) / image.getWidth();
    } else {
      ratio = (this.getSize().height / scale) / image.getHeight();
    }
    int w = (int)(image.getWidth() * ratio);
    int h = (int)(image.getHeight() * ratio);
    BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    at.scale(ratio, ratio);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    return scaleOp.filter(image, after);
  }

  /**
   * Rotate the image to the given angle.
   * 
   * @param image The image to be rotated
   * @param angle The angle to rotate to
   * @return The rotated image
   */
  private static @Nullable BufferedImage rotateImage(@Nullable BufferedImage image, double angle) {
    if (image == null) {
      return image;
    }
    final double rads = Math.toRadians(angle);
    final double sin = Math.abs(Math.sin(rads));
    final double cos = Math.abs(Math.cos(rads));
    final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
    final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
    final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
    final AffineTransform at = new AffineTransform();
    at.translate(w / 2, h / 2);
    at.rotate(rads,0, 0);
    at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
    final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    return rotateOp.filter(image,rotatedImage);
  }

  /**
   * Get the angle between two points.
   * Converts angle to against the vertical line.
   * 
   * @param p1 Point where the angle starts
   * @param p2 Point where the angle goes
   * @return The angle between p1 and p2
   */
  private static double angleOf(Point2D p1, Point2D p2) {
    // NOTE: Remember that most math has the Y axis as positive above the X.
    // However, for screens we have Y as positive below. For this reason, 
    // the Y values are inverted to get the expected results.
    int result = (int)(Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()) * 180 / Math.PI);
    result -= 90;
    result = result * -1;
    /*final double deltaY = (p1.getY() - p2.getY());
    final double deltaX = (p2.getX() - p1.getX());
    final double result = Math.toDegrees(Math.atan2(deltaY, deltaX)); 
    return (result < 0) ? (360d + result) : result;*/
    return result;
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
