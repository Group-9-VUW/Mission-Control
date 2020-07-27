package nz.ac.vuw.engr301.group9mcs.externaldata;

/**
 * This class serves to retrieve pure map data for a specified region, including nodes, ways and the associated
 * metadata from OpenStreetMap Overpass.
 *
 * @author Bailey Jewell (jewellbail)
 */
public class OsmOverpassData {

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
        // TODO: Implement me!
        return null;
    }
}
