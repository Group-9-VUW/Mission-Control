package nz.ac.vuw.engr301.group9mcs.externaldata;

/**
 * An interface to represent a MapData class.
 * In the code, the concrete classes CachedMapData and 
 * InternetMapData should always be used in place of this interface.
 * This interface provides implementations of useful methods that
 * are required for both the CachedMapData and InternetMapData classes.
 * 
 * @author Joshua Hindley, hindlejosh, 300438963
 *
 */
public interface MapData {
  
  /**
   * Converts the latitude and longitude coordinates (degrees) to Cartesian tile coordinates using
   * <a href=https://en.wikipedia.org/wiki/Mercator_projection>Mercator Projection</a>.
   *
   * <br>This calculation is made from the formula available at the
   * <a href=https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Derivation_of_tile_names>OSM Wiki</a>.
   *
   * @param latitude Latitude (degrees) of centre point of image to be requested.
   * @param longitude Longitude (degrees) of centre point of image to be requested.
   * @param zoom Zoom level. Is used as a scalar when calculating XY coordinates.
   * @return An int array containing the X and Y coordinates of the given latitude and longitude.
   * 
   * @author Bailey Jewell, (jewellbail)
   */
  static int[] convertCoordsToXY(double latitude, double longitude, int zoom) {
    // Converting latitude to radians for use in tile formula.
    double latitudeRadians = Math.toRadians(latitude);

    // Calculating cartesian coordinates.
    int n = (int) Math.pow(2, zoom);
    int x = (int) (n * ((longitude + 180f) / 360f));
    int y = (int) (n * (1 - (Math.log(Math.tan(latitudeRadians) + 1 
        / Math.cos(latitudeRadians)) / Math.PI)) / 2);
    return new int[]{x, y};
  }
}
