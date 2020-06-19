package nz.ac.vuw.engr301.group9mcs.externaldata;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStreamImpl;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Retrieves map images from the OpenStreetMap tile API.
 *
 * @author Bailey Jewell (jewellbail)
 */
public class InternetMapData implements MapData {

    /**
     * This is the basic format for the OSM tile URI.
     *
     * <br>The endpoint "a" is specified here, but two others ("b" and "c") are also provided.
     */
    private static String osmTileUriFormat = "http://a.tile.openstreetmap.org/%d/%d/%d.png";

    /**
     * Gets an image of a rectangular area defined by the top left and bottom right latitude and longitudes.
     *
     * <br>This involves retrieving multiple map tiles and stitching them together to form one map, before cropping to
     * fit the requested size.
     *
     * @param topLeftLat Top left latitude.
     * @param topLeftLong Top left longitude.
     * @param bottomRightLat Bottom right latitude.
     * @param bottomRightLong Bottom right longitude.
     * @return Returns an image of the rectangular area requested.
     */
    public BufferedImage get(double topLeftLat, double topLeftLong, double bottomRightLat, double bottomRightLong) {
        // FIXME: Zoom fixed at 16 for demo
        int zoom = 16;

        int[] topLeftXY = convertCoordsToXY(topLeftLat, topLeftLong, zoom);
        int[] bottomRightXY = convertCoordsToXY(bottomRightLat, bottomRightLong, zoom);

        double[] topLeftTileLatLong = convertXYtoLatLon(topLeftXY[0], topLeftXY[1], zoom);
        // Add one to get the actual coordinates, since origin is at top left corner.
        double[] bottomRightTileLatLong = convertXYtoLatLon(bottomRightXY[0] + 1, bottomRightXY[1] + 1, zoom);

        // Add one to these as well to avoid cutting off right/bottom point.
        int xTiles = bottomRightXY[0] - topLeftXY[0] + 1;
        int yTiles = bottomRightXY[1] - topLeftXY[1] + 1;

        BufferedImage[][] images = new BufferedImage[yTiles][xTiles];

        // Request the individual tiles and store them in the array.
        for (int i = 0; i < xTiles; ++i) {
            for (int j = 0; j < yTiles; ++j) {
                images[j][i] = get(topLeftXY[0] + i, topLeftXY[1] + j, zoom);
            }
        }

        int tileWidth = images[0][0].getWidth();
        int tileHeight = images[0][0].getHeight();

        BufferedImage fullImage = new BufferedImage(tileWidth * xTiles,
                tileHeight * yTiles, images[0][0].getType());

        // Stitch tiles together to create the full image.
        Graphics g = fullImage.getGraphics();
        for (int i = 0; i < xTiles; ++i) {
            for (int j = 0; j < yTiles; ++j) {
                g.drawImage(images[j][i], tileWidth * i, tileWidth * j, null);
            }
        }
        g.dispose();

        // Calculate the number of pixels per degree of latitude and longitude.
        double pixelsPerLatitude = fullImage.getHeight() / (bottomRightTileLatLong[0] - topLeftTileLatLong[0]);
        double pixelsPerLongitude = fullImage.getWidth() / (bottomRightTileLatLong[1] - topLeftTileLatLong[1]);

        // Apply the number of pixels to lat/long differential between requested area and received area.
        int pixelsToCropTop = (int) ((topLeftLat - topLeftTileLatLong[0]) * pixelsPerLatitude);
        int pixelsToCropLeft = (int) ((topLeftLong - topLeftTileLatLong[1]) * pixelsPerLongitude);
        int pixelsToCropBottom = (int) ((bottomRightTileLatLong[0] - bottomRightLat) * pixelsPerLatitude);
        int pixelsToCropRight = (int) ((bottomRightTileLatLong[1] - bottomRightLong) * pixelsPerLongitude);
        int newWidth = fullImage.getWidth() - pixelsToCropLeft - pixelsToCropRight;
        int newHeight = fullImage.getHeight() - pixelsToCropTop - pixelsToCropBottom;

        // Crop the image by returning a subimage of the full image.
        return fullImage.getSubimage(pixelsToCropLeft, pixelsToCropTop, newWidth, newHeight);
    }

    /**
     * Gets an image from the OpenStreetMap API.
     *
     * @param latitude Latitude (degrees) of image to be requested.
     * @param longitude Longitude (degrees) of image to be requested.
     * @param zoom Zoom level. Follows the formula 2^2n to determine the number of 256px tiles returned around
     *             the centre point.
     * @return Returns an Image object of the specified location.
     */
    public Image get(double latitude, double longitude, int zoom) {
        int[] location = convertCoordsToXY(latitude, longitude, zoom);
        try {
            // Get the image and return it. The zoom level and Cartesian coordinates are used as the string format
            // parameters.
            // URLConnection courtesy of @hindlejosh
            URL url = new URL(String.format(osmTileUriFormat, zoom, location[0], location[1]));
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mission Control 0.1 contact hindlejosh@ecs.vuw.ac.nz");
            connection.connect();
            return ImageIO.read(connection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Determine error handling.
            return null;
        }
    }

    /**
     * Gets an image from the OpenStreetMap API by X, Y.
     *
     * @param x X tile value.
     * @param y Y tile value.
     * @param zoom Zoom level. Follows the formula 2^2n to determine the number of 256px tiles returned around
     *             the centre point.
     * @return Returns an Image object of the specified location.
     */
    public BufferedImage get(int x, int y, int zoom) {
        try {
            // Get the image and return it. The zoom level and Cartesian coordinates are used as the string format
            // parameters.
            // URLConnection courtesy of @hindlejosh
            URL url = new URL(String.format(osmTileUriFormat, zoom, x, y));
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mission Control 0.1 contact hindlejosh@ecs.vuw.ac.nz");
            connection.connect();
            return ImageIO.read(connection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Determine error handling.
            return null;
        }
    }

    /**
     * Checks if the user can successfully connect to the OSM API.
     * @return true if the user can connect to the API, false otherwise.
     */
    public static boolean isAvailable() {
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
    private static boolean doIsAvailable(String URL) {
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
        int x = (int) (n * ((longitude + 180f) / 360f));
        int y = (int) (n * (1 - (Math.log(Math.tan(latitudeRadians) + 1 / Math.cos(latitudeRadians)) / Math.PI)) / 2);
        return new int[]{x, y};
    }

    /**
     * Converts the Cartesian tile coordinates to latitude and longitude.
     *
     * <br>This calculation is made from the formula available at the
     * <a href=https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Derivation_of_tile_names>OSM Wiki</a>.
     *
     * @param x X tile value.
     * @param y Y tile vale.
     * @param zoom Zoom level. Is used as a scalar when calculating XY coordinates.
     * @return Returns a double array containing the latitude and longitude of the top left point of the tile.
     */
    private static double[] convertXYtoLatLon(int x , int y, int zoom) {
        // Calculating latitude and longitude from tile coordinates.
        int n = (int) Math.pow(2, zoom);
        double longitude = (double) x / (double) n * 360f - 180f;
        double latitudeRadians = Math.atan(Math.sinh(Math.PI * (1 - 2 * (double) y / (double) n)));
        return new double[]{Math.toDegrees(latitudeRadians), longitude};
    }

}
