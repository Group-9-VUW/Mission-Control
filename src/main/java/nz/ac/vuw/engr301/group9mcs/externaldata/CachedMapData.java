package nz.ac.vuw.engr301.group9mcs.externaldata;

//import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
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
  
    
    double centreLat = (topLeftLat + bottomRightLat) / 2;
    double centreLong = (topLeftLong + bottomRightLong) / 2;
    //TODO change this method call
    this.img = data.get(topLeftLat, topLeftLong, bottomRightLat, bottomRightLong);

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
    String fileName = this.file.getName();
    File dat = new File("src/main/resources/" 
        + fileName.substring(0, fileName.length() - 4) + ".dat");
    try (BufferedWriter out = new BufferedWriter(new FileWriter(dat))) {
      //save image
      RenderedImage renderedImg = this.img;
      ImageIO.write(renderedImg, "png", this.file);
      //save data
      out.write("" + this.topLeftLat);
      out.newLine();
      out.write("" + this.topLeftLong);
      out.newLine();
      out.write("" + this.bottomRightLat);
      out.newLine();
      out.write("" + this.bottomRightLong);
      out.close();
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
    //get data file
    String fileName = this.file.getName();
    File dat = new File("src/main/resources/" 
        + fileName.substring(0, fileName.length() - 4) + ".dat");
    try (Scanner sc = new Scanner(dat);) {
      //get image
      @Nullable BufferedImage image = ImageIO.read(this.file);
      if (image == null) {
        throw new NullPointerException();
      }
      this.img = image;
      //get the data
      this.topLeftLat = sc.nextDouble();
      this.topLeftLong = sc.nextDouble();
      this.bottomRightLat = sc.nextDouble();
      this.bottomRightLong = sc.nextDouble();
      sc.close();
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
  @Override
  public Image get(double latUL, double lonUL, 
      double latBR, double lonBR) {

    double pixelsPerDegreeX = (this.bottomRightLong - this.topLeftLong) / this.img.getWidth();
    double pixelsPerDegreeY = (this.bottomRightLat - this.topLeftLat) / this.img.getHeight();

    double topLeftX = (lonUL - this.topLeftLong) / pixelsPerDegreeX;
    double topLeftY = (latUL - this.topLeftLat) / pixelsPerDegreeY;
    double bottomRightX = (lonBR - this.topLeftLong) / pixelsPerDegreeX;
    double bottomRightY = (latBR - this.topLeftLat) / pixelsPerDegreeY;
    double width = bottomRightX - topLeftX;
    double height = bottomRightY - topLeftY;

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
    //TODO check lats and longs here
    
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
