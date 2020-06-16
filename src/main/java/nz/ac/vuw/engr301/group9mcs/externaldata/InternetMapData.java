package nz.ac.vuw.engr301.group9mcs.externaldata;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jdt.annotation.Nullable;

import java.awt.*;

public class InternetMapData {

    @Nullable
    HttpClient httpClient;

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
        double[] location = convertCoordsToXY(latitude, longitude, zoom);
        setupHttpClient();
        return null;
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
     * @return Returns a double array containing the X and Y coordinates of the given latitude and longitude.
     */
    private static double[] convertCoordsToXY(double latitude, double longitude, int zoom) {
        // Converting coordiantes to radians for use in tile formula.
        double latitudeRadians = Math.toRadians(latitude);
        double longitudeRadians = Math.toRadians(longitude);

        int n = (int) Math.pow(2, zoom);
        int x = (int) (n * ((longitude + 180f) / 380f));
        int y = (int) (n * (1 - (Math.log(Math.tan(latitudeRadians) + 1 / Math.cos(latitudeRadians)) / Math.PI)) / 2);
        return new double[]{x, y};
    }

    /**
     * Creates a HttpClientBuilder and builds a new default HttpClient.
     */
    private void setupHttpClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        httpClient = builder.build();
    }
}
