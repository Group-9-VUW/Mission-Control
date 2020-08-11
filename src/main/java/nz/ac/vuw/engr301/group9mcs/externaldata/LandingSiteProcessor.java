package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Determines acceptability of landing points.
 *
 * @author jewellbail
 */
public class LandingSiteProcessor {
    /**
     * Map data retrieved from OSM Overpass.
     */
    private final OsmOverpassData data;

    /**
     * Array of possible landing locations - [point][lat, lon].
     */
    private final double[][] points;

    /**
     * Class constructor. Retrieves the appropriate map data from the Overpass API.
     * @param points Array of points[lat, lon] representing potential landing locations.
     */
    public LandingSiteProcessor(double[][] points) {
        this.points = points;
        double[] boundingBox = calculatePointsBoundingBox();
        assert boundingBox != null;
        data = OsmOverpassGetter.getAreasInBox(boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);
    }

    /**
     * Filters the array of all points to a list of valid landing points.
     * @return Returns a list of valid landing points.
     */
    public List<double[]> getValidPoints() {
        return Arrays.stream(points)
                .filter(this::rayCast)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the bounding box containing all possible landing locations.
     * @return Returns an array representing the top latitude, left longitude, bottom latitude, right longitude.
     */
    private double[] calculatePointsBoundingBox() {
        // TODO: Implement me.
        return null;
    }

    /**
     * Implementation of the <a href="https://en.wikipedia.org/wiki/Point_in_polygon">PIP ray casting polygon membership
     * algorithm</a>.
     *
     * <br> This casts a ray through the polygon, counting the number of times it intersects an edge. If it intersects
     * an even number of times, the point is outside of the polygon. Therefore, if it intersects the polygon an odd
     * number of times, the point is insider of the polygon.
     *
     * @param point Latitude, longitude of point.
     * @return Returns false if the point is within a polygon, true otherwise.
     */
    private boolean rayCast(double[] point) {
        // TODO: Implement me.
        return true;
    }
}
