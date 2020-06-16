package nz.ac.vuw.engr301.group9mcs.externaldata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class InternetMapData {

    /**
     * This is the basic format for the OSM tile URI.
     *
     * <br>The endpoint "a" is specified here, but two others ("b" and "c") are also provided.
     */
    private static final String osmTileUriFormat = "http://a.tile.openstreetmap.org/%d/%d/%d.png";

    /**
     * Gets an image from the OpenStreetMap API.
     *
     * @param latitude Latitude (degrees) of centre point of image to be requested.
     * @param longitude Longitude (degrees) of centre point of image to be requested.
     * @param zoom Zoom level. Follows the formula 2^2n to determine the number of 256px tiles returned around
     *             the centre point.
     * @return Returns an Image object of the specified location.
     */
    public Image get(double latitude, double longitude, int zoom) {
        int[] location = convertCoordsToXY(latitude, longitude, zoom);
        try {
            return ImageIO.read(new URL(String.format(osmTileUriFormat, zoom, location[0], location[1])));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Determine error handling.
            return null;
        }
    }

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
     * @return Returns an integer array containing the X and Y coordinates of the given latitude and longitude.
     */
    private static int[] convertCoordsToXY(double latitude, double longitude, int zoom) {
        // Converting coordiantes to radians for use in tile formula.
        double latitudeRadians = Math.toRadians(latitude);
        double longitudeRadians = Math.toRadians(longitude);

        int n = (int) Math.pow(2, zoom);
        int x = (int) (n * ((longitude + 180f) / 380f));
        int y = (int) (n * (1 - (Math.log(Math.tan(latitudeRadians) + 1 / Math.cos(latitudeRadians)) / Math.PI)) / 2);
        return new int[]{x, y};
    }
}
