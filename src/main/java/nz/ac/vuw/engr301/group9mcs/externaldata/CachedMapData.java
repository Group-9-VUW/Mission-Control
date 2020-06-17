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
  private Image img = new BufferedImage(0, 0, 0); 

  //public CachedMapData() {} TODO maybe implement this to automatically load a file

  /**
   * Creates a new CachedMapData given an InternetMapData instance and relevant image parameters.
   * @param data The instance of InternetMapData to get the image from.
   * @param lat The latitude of the centre of the image to get.
   * @param lon The longitude of the centre of the image to get.
   * @param radius The radius of the image to get.
   */
  public CachedMapData(InternetMapData data, double lat, double lon, double radius) {
    //TODO ensure this returns correctly if an error occurs while retrieving data
    this.img = data.get(lat, lon, radius);

    //TODO change this pathname
    this.file = new File("maps/" + lat + "-" + lon + "-" + radius + ".png"); 
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
      RenderedImage renderedImg = (RenderedImage) this.img;
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
      @Nullable Image image = ImageIO.read(this.file);
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
    //TODO override the hashCode method
    return super.hashCode();
  }
  
  @Override
  public boolean equals(@Nullable Object obj) {
    //check if obj is an instance of CachedMapData
    if (obj == null) {
      return false;
    } else if (!(obj instanceof CachedMapData)) {
      return false;
    }
    
    CachedMapData cmd = (CachedMapData) obj;
    //compare this and cmd's files
    if (!cmd.file.getName().equals(this.file.getName())) {
      return false;
    } 
    //compare this and cmd's images
    if (cmd.img.getHeight(null) != this.img.getHeight(null)) {
      return false;
    } else if (cmd.img.getWidth(null) != this.img.getWidth(null)) {
      return false;
    }
    //TODO compare every pixel?
    //this.img and cmd.img must be BufferedImages to do this
    /*for (int y = 0; y < this.img.getHeight(null); y++) {
      for (int x = 0; x < this.img.getWidth(null); x++) {
        if (this image rgb != other image rgb) {
          return false;
        }
      }
    }*/
    
    //TODO use hashcode?
    return true;
  }
  
  //TODO create a hashCode method and an equals method. It is VITAL that:

  // CachedMapData data1 = new CachedMapData(data, long, lat, radius);
  // CachedMapData data2 = new CachedMapData(data1.getFile());
  // assert data1.hashCode() == data2.hashCode();
  // assert data1.equals(data2);

  //or similar for whatever constructors are used is valid
}
