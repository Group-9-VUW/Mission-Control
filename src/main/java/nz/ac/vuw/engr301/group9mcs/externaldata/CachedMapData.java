package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A class to save and load map data to files (i.e. cache the map data).
 * 
 * @author Joshua Hindley, hindlejosh, 300438963
 */
public class CachedMapData implements MapData {
  /**
   * Holds this CachedMapData's file. That is, the file where 
   * this class will save an image to, or load an image from.
   */
  private final File file;
  /**
   * Holds this CachedMapData's image. Initialises as a one-width, 
   * one-height black BufferedImage to avoid null errors.
   */
  private BufferedImage img = new BufferedImage(1, 1, 1); 

  private double topLeftLat;
  private double topLeftLong;
  private double bottomRightLat;
  private double bottomRightLong;



  //public CachedMapData() {} TODO maybe implement this to automatically load a file

  /**
   * Creates a new CachedMapData given an InternetMapData instance and relevant image parameters.
   * @param data The instance of InternetMapData to get the image from.
   * @param topLeftLat The latitude of the top left of the image to get.
   * @param topLeftLong The longitude of the top left of the image to get.
   * @param bottomRightLat The latitude of the bottom right of the image to get.
   * @param bottomRightLong The longitude of the bottom right of the image to get.
   */
  public CachedMapData(InternetMapData data, double topLeftLat, double topLeftLong, 
      double bottomRightLat, double bottomRightLong) {
    //TODO maybe remove the data parameter and create new instance here.

    this.topLeftLat = topLeftLat;
    this.topLeftLong = topLeftLong;
    this.bottomRightLat = bottomRightLat;
    this.bottomRightLong = bottomRightLong;


    //** TODO this should be moved to the InternetDataClass


    //FIXME this is almost certainly FULL of bugs (e.g. lang/lat, x/y confusion)
    //TODO calculate zoom level
    int zoom = 16;

    int[] topLeftXY = MapData.convertCoordsToXY(topLeftLat, topLeftLong, zoom);
    int[] bottomRightXY = MapData.convertCoordsToXY(bottomRightLat, bottomRightLong, zoom);
    int numXToDraw = bottomRightXY[0] - topLeftXY[0];
    int numYToDraw = bottomRightXY[1] - topLeftXY[1];

    //TODO check that x and y are correct at these locations
    BufferedImage[][] images = new BufferedImage[numXToDraw][numYToDraw];
    double[] latitudesToDo;
    double[] longitudesToDo;
    //TODO this is hardcoded for now
    //It should be changed in the future to be a general solution
    //determine which latitudes should be used
    switch (images.length) {
      case 1:
        latitudesToDo = new double[] {topLeftLat};
        break;
      case 2:
        latitudesToDo = new double[] {topLeftLat, bottomRightLat};
        break;
      case 3:
        latitudesToDo = new double[] {topLeftLat, 
            (topLeftLat + bottomRightLat) / 2, bottomRightLat};
        break;
      case 4:
        latitudesToDo = new double[] {topLeftLat, topLeftLat * 2 / 3 + bottomRightLat * 1 / 3, 
            topLeftLat * 1 / 3 + bottomRightLat * 2 / 3, bottomRightLat};
        break;
      case 5:
        latitudesToDo = new double[] {topLeftLat, topLeftLat * 3 / 4 + bottomRightLat * 1 / 4, 
            (topLeftLat + bottomRightLat) / 2, topLeftLat * 1 / 4 + bottomRightLat * 3 / 4, 
            bottomRightLat};
        break;
      default:
        System.out.println("This case has not been implemented.");
        latitudesToDo = new double[] {topLeftLat};
        break;
    }
    //determine which longitudes should be used
    switch (images[0].length) {
      case 1:
        longitudesToDo = new double[] {topLeftLong};
        break;
      case 2:
        longitudesToDo = new double[] {topLeftLong, bottomRightLong};
        break;
      case 3:
        longitudesToDo = new double[] {topLeftLong, 
            (topLeftLong + bottomRightLong) / 2, bottomRightLong};
        break;
      case 4:
        longitudesToDo = new double[] {topLeftLong, topLeftLong * 2 / 3 + bottomRightLong * 1 / 3, 
            topLeftLong * 1 / 3 + bottomRightLong * 2 / 3, bottomRightLong};
        break;
      case 5:
        longitudesToDo = new double[] {topLeftLong, topLeftLong * 3 / 4 + bottomRightLong * 1 / 4, 
            (topLeftLong + bottomRightLong) / 2, topLeftLong * 1 / 4 + bottomRightLong * 3 / 4, 
            bottomRightLong};
        break;
      default:
        System.out.println("This case has not been implemented.");
        longitudesToDo = new double[] {topLeftLong};
        break;
    }

    //TODO check this is actually working
    for (int x = 0; x < images.length && x < latitudesToDo.length; x++) {
      for (int y = 0; y < images[0].length && y < longitudesToDo.length; y++) {
        images[x][y] = (BufferedImage) data.get(latitudesToDo[x], longitudesToDo[y], zoom);
      }
    }

    this.img = new BufferedImage(images.length * images[0][0].getWidth(),  
        images[0].length * images[0][0].getHeight(), images[0][0].getType());
    Graphics2D g = this.img.createGraphics();
    int currX = 0;
    int currY = 0;
    for (int x = 0; x < images.length; x++) {
      for (int y = 0; y < images[0].length; y++) {
        g.drawImage(images[x][y], null, currX, currY);
        currY += images[x][y].getHeight();
      }
      currX += images[x][0].getWidth();
      currY = 0;
    }
    g.dispose();

    //** END OF SHOULD BE MOVED

    double centreLat = (topLeftLat + bottomRightLat) / 2;
    double centreLong = (topLeftLong + bottomRightLong) / 2;

    this.file = new File("src/main/resources/" + centreLat + "-" + centreLong + ".png"); 
    //TODO save metadata
    saveMapToFile();
  }

  /**
   * Creates a new CachedMapData given a file to load a map image from.
   * @param file The file to load the map image from.
   */
  public CachedMapData(File file) {
    this.file = file;
    loadMapFromFile();
  }

  /**
   * Saves the current map image to a file.
   */
  private void saveMapToFile() {
    try {
      RenderedImage renderedImg = this.img;
      ImageIO.write(renderedImg, "png", this.file);
    } catch (ClassCastException e) {
      //TODO deal with errors
      System.err.println("The map image obtained cannot be cast to a RenderedImage.");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("The map image could not be saved to " + this.file.getName());
      e.printStackTrace();
    }
  }

  /**
   * Loads the current map image from a file.
   */
  private void loadMapFromFile() {
    try {
      @Nullable BufferedImage image = ImageIO.read(this.file);
      if (image == null) {
        throw new NullPointerException();
      }
      this.img = image;
    } catch (IOException e) {
      //TODO deal with errors
      System.err.println("The file: " + this.file.getName() + " could not be found or loaded.");
      e.printStackTrace();
    } catch (NullPointerException e) {
      System.err.println("The image in the file: " + this.file.getName() + " could not be read.");
      e.printStackTrace();
    }
  }

  /**
   * Gets the image stored in this CachedMapData's file.
   * @return the image.
   */
  public Image getImage() {
    return this.img;
  }

  /**
   * Gets a subset of the image stored by this CachedMapData instance.
   * @param latUL the top left latitude of the image to return.
   * @param lonUL the top left longitude of the image to return.
   * @param latBR the bottom right latitude of the image to return.
   * @param lonBR the bottom right longitude of the image to return.
   * @return the subimage.
   */
  public Image get(double latUL, double lonUL, 
      double latBR, double lonBR) {
    //TODO check that this works

    double pixelsPerDegreeX = (this.bottomRightLong - this.topLeftLong) / this.img.getWidth();
    double pixelsPerDegreeY = (this.bottomRightLat - this.topLeftLat) / this.img.getWidth();

    double topLeftX = pixelsPerDegreeX * lonUL;
    double topLeftY = pixelsPerDegreeY * latUL;
    double width = pixelsPerDegreeX * lonBR - topLeftX;
    double height = pixelsPerDegreeY * latBR - topLeftY;

    //parameters were invalid
    if (topLeftX < 0 || topLeftY < 0 || width > this.img.getWidth() 
        || height > this.img.getHeight()) {
      return this.img;
    }

    BufferedImage subImage = this.img.getSubimage((int) topLeftX, 
        (int) topLeftY, (int) width, (int) height);
    assert subImage != null;
    return subImage;
  }

  /**
   * Gets this CachedMapData's file.
   * @return the file.
   */
  public File getFile() {
    return this.file;
  }

  @Override
  public int hashCode() {
    int x = this.img.getWidth() / 2;
    int y = this.img.getHeight() / 2;
    return this.img.getRGB(x, y);
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    //check if obj is an instance of CachedMapData
    if (obj == null || !(obj instanceof CachedMapData)) {
      return false;
    }
    CachedMapData cmd = (CachedMapData) obj;
    //compare this and cmd's files
    if (!cmd.file.getName().equals(this.file.getName())) {
      return false;
    } 
    //compare this and cmd's images
    if (cmd.img.getHeight() != this.img.getHeight()) {
      return false;
    } else if (cmd.img.getWidth() != this.img.getWidth()) {
      return false;
    }
    //compare every pixel
    for (int y = 0; y < this.img.getHeight(); y++) {
      for (int x = 0; x < this.img.getWidth(); x++) {
        if (this.img.getRGB(x, y) != cmd.img.getRGB(x, y)) {
          return false;
        }
      }
    }
    return true;
  }
}
