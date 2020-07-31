package nz.ac.vuw.engr301.group9mcs.externaldata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
     * Gets the building data of any building with a node within the specified bounding box.
     *
     * @param south Latitude of southernmost point.
     * @param west Longitude of westernmost point.
     * @param north Latitude of northernmost point.
     * @param east Longitude of easternmost point.
     * @return Returns the JSON response from Overpass.
     */
    public static String getAreasInBox(double south, double west, double north, double east) {
        // Firstly, we must generate the query.
        String queryBase = "data=" +
                "[out:json][timeout:25];\n" +
                "(\n" +
                "  way(%f, %f, %f, %f)[building];\n" +
                ");\n" +
                "(._;>;);\n" +
                "out;";

        String query = String.format(queryBase, south, west, north, east);

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

    /**
     * Parses the JSON data retrieved from Overpass into an OsmOverpassData object consisting of Nodes and Ways.
     *
     * @param json Result of Overpass API call.
     * @return Returns a complete OsmOverpassData object.
     */
    public static OsmOverpassData parseData(String json) {
        JSONArray data = new JSONObject(json).getJSONArray("elements");

        // We map to the node ID here to simplify adding node references to ways later on.
        Map<Integer, OsmOverpassData.Node> nodes = new HashMap<>();
        List<OsmOverpassData.Way> ways = new ArrayList<>();

        for (int i = 0; i < data.length(); ++i) {
            JSONObject elem = data.getJSONObject(i);

            switch (elem.getString("type")) {
                case "node":
                    nodes.put(elem.getInt("id"), new OsmOverpassData.Node(
                            elem.getInt("id"),
                            elem.getDouble("lat"),
                            elem.getDouble("lon"),
                            elem.has("tags") ? parseTags(elem.getJSONObject("tags")) : null
                    ));

                    break;
                case "way":
                    ways.add(new OsmOverpassData.Way(
                            elem.getInt("id"),
                            elem.getJSONArray("nodes").toList().stream().map(nodes::get).filter(Objects::nonNull).collect(Collectors.toList()),
                            elem.has("tags") ? parseTags(elem.getJSONObject("tags")) : null
                    ));
                    break;
            }
        }
        // Return the OSM data with nodes reduced to a list.
        return new OsmOverpassData(new ArrayList<>(nodes.values()), ways);
    }

    private static Map<String, String> parseTags(JSONObject tags) {
        Map<String, String> tagMap = new HashMap<>();
        for (String key : JSONObject.getNames(tags)) {
            tagMap.put(key, tags.getString(key));
        }
        return tagMap;
    }

    public static void main(String[] args) {
        System.out.println(parseData(getAreasInBox(-41.29056, 174.76832, -41.29039, 174.76839)).getWays());
    }
}
