package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;

/**
 * Determines acceptability of landing points.
 *
 * @author jewellbail
 */
public class LandingSiteProcessor {
    /**
     * Map data retrieved from OSM Overpass.
     */
    private OsmOverpassData data;

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
        try {
            this.data = OsmOverpassGetter.getAreasInBox(boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);
        } catch (IOException e) {
            throw new PreconditionViolationException(e);
        }
    }

    /**
     * Filters the array of all points to a list of valid landing points.
     * @return Returns a list of valid landing points.
     */
    @SuppressWarnings("null")
    public List<double[]> getValidPoints() {
        return Null.nonNull(Arrays.stream(this.points)
                .filter(this::rayCast)
                .collect(Collectors.toList()));
    }

    /**
     * Calculates the bounding box containing all possible landing locations.
     * @return Returns an array representing the top latitude, left longitude, bottom latitude, right longitude.
     */
    private double[] calculatePointsBoundingBox() {
        // Note the use of -Double.MAX_VALUE to determine the minimum float value. This is because Double.MIN_VALUE
        // represents the smallest magnitude of a double (a very small positive number), not the lowest value.
        double north = -Double.MAX_VALUE, south = Double.MAX_VALUE, east = -Double.MAX_VALUE, west = Double.MAX_VALUE;

        for (double[] point : this.points) {
            north = Math.max(point[0], north);
            south = Math.min(point[0], south);
            east = Math.max(point[1], east);
            west = Math.min(point[1], west);
        }

        return new double[]{north, west, south, east};
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
        assert this.data.getWays().size() > 0;
        Line2D ray = new Line2D.Double(point[1], point[0], point[1] + 1, point[0]);

        // Each building is represented by a way.
        for (OsmOverpassData.Way w : this.data.getWays()) {
            List<OsmOverpassData.Node> nodes = w.getNodes();
            int intersections = 0;
            for (int i = 0; i < nodes.size() - 1; ++i) {
                Line2D edge = new Line2D.Double(nodes.get(i).LON, nodes.get(i).LAT, nodes.get(i + 1).LON, nodes.get(i + 1).LAT);
                if (ray.intersectsLine(edge)) {
                    intersections++;
                }
            }
            // If inside a polygon, we can return false, as this point does not represent a suitable landing location.
            if (intersections % 2 != 0) {
                return false;
            }
        }
        // If we get here, then the point is not within any polygons.
        return true;
    }
}
