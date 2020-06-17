package nz.ac.vuw.engr301.group9mcs.externaldata;

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
public class CachedMapData /*TODO implements MapData*/ {
  /**
   * Holds this CachedMapData's file. That is, the file where 
   * this class will save an image to, or load an image from.
   */
  private final File file;
  /**
   * Holds this CachedMapData's image. Initialises as a zero-width, 
   * zero-height black BufferedImage to avoid null errors.
   */
  private BufferedImage img = new BufferedImage(1, 1, 1); 

  //public CachedMapData() {} TODO maybe implement this to automatically load a file

  /**
   * Creates a new CachedMapData given an InternetMapData instance and relevant image parameters.
   * @param data The instance of InternetMapData to get the image from.
   * @param lat The latitude of the centre of the image to get.
   * @param lon The longitude of the centre of the image to get.
   * @param radius The radius of the image to get.
   */
  public CachedMapData(InternetMapData data, double lat, double lon, double radius) {
    //TODO ensure this returns a BufferedImage no matter what
    this.img = (BufferedImage) data.get(lat, lon, radius);

    //TODO change this pathname
    this.file = new File(lat + "-" + lon + "-" + radius + ".png"); 
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
      System.exit(-1);
    } catch (IOException e) {
      System.err.println("The map image could not be saved to " + this.file.getName());
      e.printStackTrace();
      System.exit(-1);
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
      System.exit(-1);
    } catch (NullPointerException e) {
      System.err.println("The image in the file: " + this.file.getName() + " could not be read.");
      e.printStackTrace();
      System.exit(-1);
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
