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
    //TODO calculate zoom level
    int zoom = 16;

    int[] topLeftXY = MapData.convertCoordsToXY(topLeftLat, topLeftLong, zoom);
    int[] bottomRightXY = MapData.convertCoordsToXY(bottomRightLat, bottomRightLong, zoom);
    int xToDraw = bottomRightXY[0] - topLeftXY[0];
    int yToDraw = bottomRightXY[1] - topLeftXY[1];
    
    
    
    
    BufferedImage[][] images = new BufferedImage[xToDraw][yToDraw];
    for (int x = 0; x < images.length; x++) {
      
    }
    double currentLat = ;
    double currentLong;
    
    BufferedImage topLeft = (BufferedImage) data.get(topLeftLat, topLeftLong, zoom);
    BufferedImage topRight = (BufferedImage) data.get(topLeftLat, bottomRightLong, zoom);
    BufferedImage bottomLeft = (BufferedImage) data.get(bottomRightLat, topLeftLong, zoom);
    BufferedImage bottomRight = (BufferedImage) data.get(bottomRightLat, bottomRightLong, zoom);
    
    this.img = new BufferedImage(topLeft.getWidth() + topRight.getWidth(), 
        topLeft.getHeight() + bottomLeft.getHeight(), bottomRight.getType());
    Graphics2D g = this.img.createGraphics();
    g.drawImage(topLeft, null, 0, 0);
    g.drawImage(topRight, null, topLeft.getWidth(), 0);
    g.drawImage(bottomLeft, null, 0, topLeft.getHeight());
    g.drawImage(bottomRight, null, topLeft.getWidth(), topLeft.getHeight());
    g.dispose();
    
    double centreLat = (topLeftLat + bottomRightLat) / 2;
    double centreLong = (topLeftLong + bottomRightLong) / 2;
    
    this.file = new File("src/main/resources/" + centreLat + "-" + centreLong + ".png"); 
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
