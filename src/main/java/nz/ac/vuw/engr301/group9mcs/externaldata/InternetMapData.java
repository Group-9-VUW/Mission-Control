package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * A placeholder InternetMapData class.
 * Will be replaced by the actual class written by @author Bailey Jewell.
 */
@SuppressWarnings(value = { "all" })
public class InternetMapData {

  public InternetMapData() { }

  /**
   * This is a placeholder.
   * @param latitude lat
   * @param longitude long
   * @param zoom zoom
   * @return
   */
  public Image get(double latitude, double longitude, double zoom) {
    if (latitude > 0 && longitude > 0 && zoom > 0) {
      return new BufferedImage(0, 0, 0);
    }
    return new BufferedImage(0, 0, 0);
  }
}
