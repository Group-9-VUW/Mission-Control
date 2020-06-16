package nz.ac.vuw.engr301.group9mcs.externaldata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Retrieves map images from the OpenStreetMap tile API.
 *
 * @author Bailey Jewell (jewellbail)
 */
public class InternetMapData /*TODO: implements MapData*/ {

    /**
     * This is the basic format for the OSM tile URI.
     *
     * <br>The endpoint "a" is specified here, but two others ("b" and "c") are also provided.
     */
    private static String osmTileUriFormat = "http://a.tile.openstreetmap.org/%d/%d/%d.png";

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
            // Get the image and return it. The zoom level and Cartesian coordinates are used as the string format
            // parameters.
            return ImageIO.read(new URL(String.format(osmTileUriFormat, zoom, location[0], location[1])));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Determine error handling.
            return null;
        }
    }

    /**
     * Checks if the user can succesfully connect to the OSM API.
     * @return true if the user can connect to the API, false otherwise.
     */
    public boolean isAvailable() {
        // Multiple endpoints are available so we should test them all.
        String[] endpoints = new String[]{
                "a", "b", "c"
        };
        for (String endpoint : endpoints) {
            String endpointUrl = osmTileUriFormat.substring(osmTileUriFormat.length() - 14).replaceFirst("a", endpoint);
            if (doIsAvailable(endpointUrl)) {
                // If this endpoint is reachable, modify the base URI and return.
                osmTileUriFormat = osmTileUriFormat.replaceFirst("a", endpoint);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the user can succesfully connect to the given URL.
     * @return Returns true if the user can connect to the URL, false otherwise.
     */
    private boolean doIsAvailable(String URL) {
        try {
            URL url = new URL(osmTileUriFormat.substring(osmTileUriFormat.length() - 14));
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
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
        // Converting coordinates to radians for use in tile formula.
        double latitudeRadians = Math.toRadians(latitude);
        double longitudeRadians = Math.toRadians(longitude);

        // Calculating cartesian coordinates.
        int n = (int) Math.pow(2, zoom);
        int x = (int) (n * ((longitude + 180f) / 380f));
        int y = (int) (n * (1 - (Math.log(Math.tan(latitudeRadians) + 1 / Math.cos(latitudeRadians)) / Math.PI)) / 2);
        return new int[]{x, y};
    }
}
