package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * This class serves to retrieve pure map data for a specified region, including nodes, ways and the associated
 * metadata from OpenStreetMap Overpass.
 *
 * @author Bailey Jewell (jewellbail)
 */
public class OsmOverpassGetter {

    private static final URL OVERPASS_URL;

    static {
        URL overpassUrl;
        try {
            overpassUrl = new URL("https://lz4.overpass-api.de/api/interpreter");
        } catch (MalformedURLException e) {
            overpassUrl = null;
            e.printStackTrace();
        }
        OVERPASS_URL = overpassUrl;
    }


    /**
     * Determines whether or not a given location is on a forbidden area.
     *
     * Forbidden areas include:
     *  - Buildings
     *  - Forests
     *
     * @param latitude Latitude of point.
     * @param longitude Longitude of point.
     * @return Returns true if on a building, false otherwise.
     */
    public static boolean isOnForbiddenArea(double latitude, double longitude) {
        // TODO: Implement me!
        return false;
    }

    /**
     * Gets the building data around a point.
     *
     * @param latitude Latitude of point.
     * @param longitude Longitude of point.
     * @return Returns the JSON response from Overpass.
     */
    private static String getAreasAtPoint(double radius, double latitude, double longitude) {
        // Firstly, we must generate the query.
        String queryBase = "data=" +
                "[out:json][timeout:25];\n" +
                "(\n" +
                "  way(around:%f, %f, %f)[building];\n" +
                ");\n" +
                "(._;>;);\n" +
                "out;";

        String query = String.format(queryBase, radius, latitude, longitude);

        try {
            // Setup connection.
            HttpURLConnection connection = (HttpURLConnection) OVERPASS_URL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mission Control 0.1 contact jewellbail@ecs.vuw.ac.nz");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Setup output stream and write query.
            OutputStream out = connection.getOutputStream(); // We keep a reference to output stream so we can close it!
            OutputStreamWriter outWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            outWriter.write(query);
            outWriter.flush();
            outWriter.close();
            out.close();

            // Setup the input stream and a buffer to store the response.
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            // Reading response byte by byte.
            int nextByte = in.read();
            while(nextByte != -1) {
                buffer.write((byte) nextByte);
                nextByte = in.read();
            }

            String response = buffer.toString();
            buffer.close();
            in.close();
            return response;

        } catch (IOException e) {
            // Connection issue.
            e.printStackTrace();
            return null;
        }
    }
}
