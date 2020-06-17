package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class to save and load map data to files (i.e. cache the map data).
 * 
 * @author Joshua Hindley, hindlejosh, 300438963
 */
public class CachedMapData /*TODO implements MapData*/ {
  private File file;
  private Image img;

  //public CachedMapData() {} TODO maybe implement this

  /**
   * Creates a new CachedMapData given an InternetMapData instance and relevant image parameters.
   * @param data The instance of InternetMapData to get the image from.
   * @param lat The latitude of the centre of the image to get.
   * @param lon The longitude of the centre of the image to get.
   * @param radius The radius of the image to get.
   */
  public CachedMapData(InternetMapData data, double lat, double lon, double radius) {
    this.img = data.get(lat, lon, radius);
    //TODO change this pathname
    this.file = new File("maps/" + lat + "-" + lon + "-" + radius + ".png"); 
    saveMapToFile();
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
    
  }

  /**
   * Gets the image stored in this CachedMapData's file.
   * @return
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

  //TODO add constructor from String/File -> loads from file and analyses 
  //the file contents to ensure it is valid

  //TODO maybe add a default constructor that loads a "default" file name, 
  //if a file with that name exists at the expected location

  //TODO the MapData interface (or both InternetMapData and CachedMapData) 
  //should contain a hashCode method and an equals method. It is VITAL that:

  // CachedMapData data1 = new CachedMapData(data, long, lat, radius);
  // CachedMapData data2 = new CachedMapData(data1.getFile());
  // assert data1.hashCode().equals(data2.hashCode());
  // assert data1.equals(data2);

  //or similar for whatever constructors are used is valid
}
