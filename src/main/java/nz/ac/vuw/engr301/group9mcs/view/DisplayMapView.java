package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.externaldata.MapData;

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
   * Drop down menu for the view (how the objects are shown).
   */
  private JComboBox<String> viewDropDown;
  /**
   * Dropdown menu for Labels (if labels should be shown).
   */
  private JComboBox<String> labelDropDown;
  /**
   * The drawing panel.
   */
  private JComponent panel;
  /**
   * The menu bar across the top.
   */
  private JMenuBar bar;
  /**
   * The size of the dots in relation to screen size.
   */
  private int dotRatio = 30;

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
   * Colour of launch site.
   */
  private Color launchSiteColor = new Color(39, 0, 200);
  /**
   * Color of rocket.
   */
  private Color rocketColor = new Color(255, 0, 0);
  
  /**
   * The ratio of how much screen is shown around the objects.
   */
  private double zoom = 0.25;
  /**
   * UID.
   */
  private static final long serialVersionUID = 3810711176953194652L;
  
  /**
   * Provides the map image.
   */
  private MapData mapData;

  /**
   * Set up JPanel for Displaying Map of Rocket's Progress.
   * @param latLaunchSite The Latitude of where the launch site is
   * @param longLaunchSite The Longitude of where the launch site is
   * @param mapData The MapData class, provides the map image
   */
  public DisplayMapView(double latLaunchSite, double longLaunchSite, MapData mapData) {
    this.longLaunchSite = longLaunchSite;
    this.longRocket = longLaunchSite;
    this.latLaunchSite = latLaunchSite;
    this.latRocket = latLaunchSite;
    this.mapData = mapData;

    this.setPreferredSize(new Dimension(300, 300));

    this.setLayout(new BorderLayout());

    this.bar = new JMenuBar();//createMenuBar();

    @NonNull String[] strings = {"Images", "Dots"};
    this.viewDropDown = new JComboBox<>(strings);
    this.viewDropDown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(@Nullable ActionEvent e) {
        DisplayMapView.this.repaint();
      }
    });
    this.bar.add(this.viewDropDown);

    @NonNull String[] texts = {"Labels", "No Labels"};
    this.labelDropDown = new JComboBox<>(texts);
    this.labelDropDown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(@Nullable ActionEvent e) {
        DisplayMapView.this.repaint();
      }
    });
    this.bar.add(this.labelDropDown);

    this.panel = new JComponent() {
      /**
       * UID.
       */
      private static final long serialVersionUID = 1L;

      @Override
      protected void paintComponent(@Nullable Graphics g) {
        paintInsideComponent(g);
      }
    };
    this.panel.setPreferredSize(new Dimension(300, 300 - this.bar.getSize().height));
    this.add(this.bar, BorderLayout.NORTH);
    this.add(this.panel, BorderLayout.SOUTH);
  }

  @Override
  public void paintComponent(@Nullable Graphics g) {
    Dimension d = this.getSize();
    this.panel.setPreferredSize(new Dimension(d.width, d.height - this.bar.getSize().height));
  }

  /**
   * Paint the Component inside the Panel.
   * 
   * @param g Graphics drawer
   */
  public void paintInsideComponent(@Nullable Graphics g) {
    if (g == null) {
      return;
    }

    // box around launch and rocket locations (tight)
    Point2D[] corners = getCorners();

    Point2D launchCoordinates = new Point2D.Double(this.longLaunchSite, this.latLaunchSite);
    Point2D rocketCoordinates = new Point2D.Double(this.longRocket, this.latRocket);

    // calculate the x coordinates for rocket and launch site
    int xlaunch;
    int xrocket;
    int xscreen = (int)(this.panel.getSize().getWidth() 
        - (this.panel.getSize().getWidth() * this.zoom));
    // translate and scale x coordinates based on which site is north most
    // new coordinates are within the screen boundaries (0 - x)
    if (launchCoordinates.getY() > rocketCoordinates.getY()) {
      xlaunch = (int)(translateScale(launchCoordinates.getX(),
          corners[2].getX(), corners[3].getX(), xscreen)) 
          + (int)(this.panel.getSize().width * this.zoom / 2);
      xrocket = (int)(translateScale(rocketCoordinates.getX(),
          corners[0].getX(), corners[1].getX(), xscreen)) 
          + (int)(this.panel.getSize().width * this.zoom / 2);
    } else {
      xrocket = (int)(translateScale(rocketCoordinates.getX(),
          corners[2].getX(), corners[3].getX(), xscreen)) 
          + (int)(this.panel.getSize().width * this.zoom / 2);
      xlaunch = (int)(translateScale(launchCoordinates.getX(),
          corners[0].getX(), corners[1].getX(), xscreen)) 
          + (int)(this.panel.getSize().width * this.zoom / 2);
    }

    // calculate the y coordinates for rocket and launch site
    int ylaunch;
    int yrocket;
    int yscreen = (int)(this.panel.getSize().getHeight() 
        - this.panel.getSize().getHeight() * this.zoom);
    // translate and scale y coordinates based on which site is east most
    // new coordinates are within the screen boundaries (0 - x)
    if (launchCoordinates.getX() > rocketCoordinates.getX()) {
      ylaunch = (int)(translateScale(launchCoordinates.getY(),
          corners[1].getY(), corners[3].getY(), yscreen)) 
          + (int)(this.panel.getSize().getHeight() * this.zoom / 2);
      yrocket = (int)(translateScale(rocketCoordinates.getY(),
          corners[0].getY(), corners[2].getY(), yscreen)) 
          + (int)(this.panel.getSize().getHeight() * this.zoom / 2);
    } else {
      yrocket = (int)(translateScale(rocketCoordinates.getY(),
          corners[1].getY(), corners[3].getY(), yscreen)) 
          + (int)(this.panel.getSize().getHeight() * this.zoom / 2);
      ylaunch = (int)(translateScale(launchCoordinates.getY(),
          corners[0].getY(), corners[2].getY(), yscreen)) 
          + (int)(this.panel.getSize().getHeight() * this.zoom / 2);
    }

    // Calculate the four corners of the screen
    // Reverse translate/scale process to get lat/long
    int upperLX = (int)((0 + corners[0].getX()) / ((corners[1].getX() 
        - corners[0].getX()) / xscreen)) - (int)(this.panel.getSize().width * this.zoom / 2);
    int lowerRX = (int)((this.panel.getSize().width + corners[3].getX()) / ((corners[2].getX() 
        - corners[3].getX()) / xscreen)) - (int)(this.panel.getSize().width * this.zoom / 2);
    int upperLY = (int)((0 + corners[0].getY()) / ((corners[1].getY() 
        - corners[0].getY()) / yscreen)) - (int)(this.panel.getSize().height * this.zoom / 2);
    int lowerRY = (int)((this.panel.getSize().height + corners[3].getY()) / ((corners[2].getY() 
        - corners[3].getY()) / yscreen)) - (int)(this.panel.getSize().height * this.zoom / 2);

    // Get and Draw map
    BufferedImage image = (BufferedImage)this.mapData.get(upperLY, upperLX, lowerRY, lowerRX);
    g.drawImage(image, 0, 0, this.panel.getSize().width, this.panel.getSize().height, null);

    int width = this.panel.getSize().width;
    int height = this.panel.getSize().height;

    // draw the rocket and launch site on the map
    drawRocketPath(width - xlaunch, height - ylaunch, width - xrocket, height - yrocket, g);
    drawRocket(width - xrocket, height - yrocket, g, launchCoordinates, rocketCoordinates);
    drawLaunchSite(width - xlaunch, height - ylaunch, g);
  }

  /**
   * Draw a dashed line between the launch site and the rocket.
   * 
   * @param x1 X Coordinate of object 1
   * @param y1 Y Coordinate of object 1
   * @param x2 X Coordinate of object 2
   * @param y2 Y Coordinate of object 2
   * @param g The Graphics drawer
   */
  private static void drawRocketPath(int x1, int y1, int x2, int y2, Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, 
        BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    g2d.setStroke(dashed);
    g.drawLine(x1, y1, x2, y2);
  }

  /**
   * Draw the launch site at a given point on the screen.
   * 
   * @param xlocation X coordinate of the site location
   * @param ylocation Y coordinate of the site location
   * @param g The Graphics drawer
   */
  private void drawLaunchSite(int xlocation, int ylocation, Graphics g) {
    if (this.viewDropDown.getSelectedItem().equals("Images")) {
      BufferedImage image = null;
      try {
        image = ImageIO.read(new File("./src/main/resources/view/launchsite.png"));
        image = scaleDimensions(image, 7.0);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (image == null) {
        return;
      }
      int x = xlocation - image.getWidth() / 2;
      int y = ylocation - image.getHeight() / 2;
      setColour(image, this.launchSiteColor);
      g.drawImage(image, x, y, null);
      if (this.labelDropDown.getSelectedItem().equals("Labels")) {
        String s = String.format("%.5g%n", this.latLaunchSite) + ", " 
            + String.format("%.5g%n", this.longLaunchSite);
        centerText(s, (Graphics2D)g, x, y, image.getWidth(), image.getHeight());
      }
    } else {
      g.setColor(this.launchSiteColor);
      int size = this.panel.getSize().height / this.dotRatio;
      g.fillOval(xlocation - size / 2, ylocation - size / 2, size, size);
      if (this.labelDropDown.getSelectedItem().equals("Labels")) {
        String s = String.format("%.5g%n", this.latLaunchSite) + ", " 
            + String.format("%.5g%n", this.longLaunchSite);
        centerText(s, (Graphics2D)g, xlocation - size / 2, ylocation - size / 2, size, size);
      }
    }
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
    if (this.viewDropDown.getSelectedItem().equals("Images")) {
      BufferedImage image = null;
      try {
        image = ImageIO.read(new File("./src/main/resources/view/rocket.png")); 
        image = scaleDimensions(image, 6.0);
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
      setColour(image, this.rocketColor);
      g.drawImage(image, x, y, null);
      if (this.labelDropDown.getSelectedItem().equals("Labels")) {
        String s = String.format("%.5g%n", this.latLaunchSite) + ", " 
            + String.format("%.5g%n", this.longLaunchSite);
        centerText(s, (Graphics2D)g, x, y, image.getWidth(), image.getHeight());
      }
    } else {
      g.setColor(this.rocketColor);
      int size = this.panel.getSize().height / this.dotRatio;
      g.fillOval(xlocation - size / 2, ylocation - size / 2, size, size);
      if (this.labelDropDown.getSelectedItem().equals("Labels")) {
        String s = String.format("%.5g%n", this.latLaunchSite) + ", " 
            + String.format("%.5g%n", this.longLaunchSite);
        centerText(s, (Graphics2D)g, xlocation - size / 2, ylocation - size / 2, size, size);
      }
    }
  }

  /**
   * Center the coordinates below the site images on a white rectangle.
   * 
   * @param text The text to display (coordinates of site)
   * @param g The Graphics drawer
   * @param x The x position of the site image
   * @param y The y position of the site image
   * @param width The site image's width
   * @param height The site image's height
   */
  public void centerText(String text, Graphics2D g, int x, int y, int width, int height) {
    g.setFont(new Font("Serif", Font.PLAIN, this.panel.getSize().height / 30));
    FontMetrics fm = g.getFontMetrics(g.getFont());
    Rectangle2D rect = fm.getStringBounds(text, g);
    int xstring = (int)(x - (rect.getWidth() / 2) + (width / 2));
    int ystring = (int)(y + height + rect.getHeight());
    g.setColor(Color.white);
    g.fillRect(xstring - (int)rect.getWidth() / text.length() / 2, ystring - fm.getAscent(), 
        (int)rect.getWidth(), (int)rect.getHeight());
    g.setColor(Color.black);
    g.drawString(text, xstring, ystring);

  }

  /**
   * Assuming that the image is black with a transparent background.
   * Sets the image color to red (has a black outline).
   * 
   * @param image Image to be colored
   * @param color Color image will become
   * @return Colored image
   */
  private @Nullable static BufferedImage setColour(@Nullable BufferedImage image, Color color) {
    if (image == null) {
      return image;
    }
    // get width and height 
    int width = image.getWidth(); 
    int height = image.getHeight(); 

    int set = color.getRGB();

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
    double ratio = (this.panel.getSize().height / scale) / image.getHeight();
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
  public void updateRocketPosition(double latR, double longR) {
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
    // Earth Radius in KM
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
