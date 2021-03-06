package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import org.json.JSONArray;
import org.json.JSONObject;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This class serves to retrieve pure map data for a specified region, including nodes, ways and the associated
 * metadata from OpenStreetMap Overpass.
 *
 * @author Bailey Jewell
 * Copyright (C) 2020, Mission Control Group 9
 */
public class OsmOverpassGetter {

    /**
     * URL for OSM Overpass interpreter.
     */
    private static final URL OVERPASS_URL;

    static {
        URL overpassUrl;
        try {
            overpassUrl = new URL("https://lz4.overpass-api.de/api/interpreter");
        } catch (MalformedURLException e) {
            // This should NEVER be thrown, as URL is hardcoded.
            throw new RuntimeException("Error parsing URL: " + e);
        }
        OVERPASS_URL = overpassUrl;
    }

    /**
     * Gets the building data of any building with a node within the specified bounding box.
     *
     * @param latTop Latitude of northernmost point.
     * @param lonLeft Longitude of westernmost point.
     * @param latBot Latitude of southernmost point.
     * @param lonRight Longitude of easternmost point.
     * @return Returns an OsmOverpassData object created from the JSON response from Overpass.
     * @throws IOException Throws IOException on network/disk error.
     */
    public static OsmOverpassData getAreasInBox(double latTop, double lonLeft, double latBot, double lonRight)
            throws IOException {

        try {
            // First, we check whether this data is cached. If so, load from cache.
            return CachedOsmOverpassData.loadArea(
                    new Point(latTop, lonLeft),
                    new Point(latBot, lonRight)
            );
        } catch (PreconditionViolationException e) {
            // Exception will be thrown if data is not cached. In this case, load from internet.
            // Order of parameters is switched to suit API call.
        	System.err.println(e.getMessage());
            return parseData(getAreasInBoxJson(latBot, lonLeft, latTop, lonRight));
        }
    }

    /**
     * Gets the building data of any building with a node within the specified bounding box.
     *
     * @param south Latitude of southernmost point.
     * @param west Longitude of westernmost point.
     * @param north Latitude of northernmost point.
     * @param east Longitude of easternmost point.
     * @return Returns the JSON response from Overpass.
     * @throws IOException Throws IOException on network/disk error.
     */
    private static String getAreasInBoxJson(double south, double west, double north, double east) throws IOException {
        // Firstly, we must generate the query.
        String queryBase = "data=" +
                "[out:json][timeout:25];\n" +
                "(\n" +
                "  way(%f, %f, %f, %f)[building];\n" +
                ");\n" +
                "(._;>;);\n" +
                "out;";

        String query = String.format(queryBase, new Double(south), new Double(west),
        		new Double(north), new Double(east));

        // Setup connection.
        HttpURLConnection connection = (HttpURLConnection) OVERPASS_URL.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mission Control 0.1 contact jewellbail@ecs.vuw.ac.nz");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // Setup output stream and write query.
        try (
        		OutputStream out = connection.getOutputStream();
        	 	OutputStreamWriter outWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {

	        outWriter.write(query);
	        outWriter.flush();
        }

        // Setup the input stream and a buffer to store the response.

        try (
		        BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
		        ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

	        // Reading response byte by byte.
	        int nextByte = in.read();
	        while (nextByte != -1) {
	            buffer.write((byte) nextByte);
	            nextByte = in.read();
	        }
	        return Null.nonNull(buffer.toString());
        }
    }

    /**
     * Parses the JSON data retrieved from Overpass into an OsmOverpassData object consisting of Nodes and Ways.
     *
     * @param json Result of Overpass API call.
     * @return Returns a complete OsmOverpassData object.
     */
    @SuppressWarnings("null") // Used to suppress a null warning Null.nonNull() can't handle
		private static OsmOverpassData parseData(String json) {
        JSONArray data = new JSONObject(json).getJSONArray("elements");

        // We map to the node ID here to simplify adding node references to ways later on.
        Map<Long, OsmOverpassData.Node> nodes = new HashMap<>();
        List<OsmOverpassData.Way> ways = new ArrayList<>();

        for (int i = 0; i < data.length(); ++i) {
            JSONObject elem = data.getJSONObject(i);
            List<Long> wayNodeIds = new ArrayList<>();

            switch (elem.getString("type")) {
                case "node":
                    nodes.put(new Long(elem.getLong("id")), new OsmOverpassData.Node(
                            elem.getLong("id"),
                            elem.getDouble("lat"),
                            elem.getDouble("lon"),
                            elem.has("tags") ? parseTags(Null.nonNull(elem.getJSONObject("tags"))) : null
                    ));

                    break;
                case "way":
                    // Store node IDs first, to preserve order and ensure that all references will be present.
                    for (int j = 0; j < elem.getJSONArray("nodes").length(); ++j) {
                        wayNodeIds.add(new Long(elem.getJSONArray("nodes").getLong(j)));
                    }

                    ways.add(new OsmOverpassData.Way(
                            elem.getInt("id"),
                            wayNodeIds,
                            elem.has("tags") ? parseTags(elem.getJSONObject("tags")) : null
                    ));
                    break;
			default:
				// Encountered malformed object - precondition requires valid map object.
                throw new PreconditionViolationException("Malformed map object received.");
            }
        }

        // Set proper node references to ways after all elements are parsed.
        ways.forEach(way -> way.setNodeRefs(nodes));

        // Return the OSM data with nodes reduced to a list.
        return new OsmOverpassData(new ArrayList<>(nodes.values()), ways);
    }

    /**
     * Parses the tags of a node or way into a String:String map.
     * @param tags JSON object containing the element's tags.
     * @return Returns a map containing all tags belonging to the element.
     */
    private static Map<String, String> parseTags(JSONObject tags) {
        Map<String, String> tagMap = new HashMap<>();
        for (String key : JSONObject.getNames(tags)) {
            tagMap.put(Null.nonNull(key), Null.nonNull(tags.getString(key)));
        }
        return tagMap;
    }
}
