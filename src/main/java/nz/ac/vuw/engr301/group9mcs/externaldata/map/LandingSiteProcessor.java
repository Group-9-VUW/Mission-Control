package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

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
    private final List<Point> points;

    /**
     * Class constructor. Retrieves the appropriate map data from the Overpass API.
     * @param points Array of points[lat, lon] representing potential landing locations.
     */
    public LandingSiteProcessor(double[][] points) {
        this.points = new ArrayList<>();
        for (double[] point : points) {
            this.points.add(new Point(point[0], point[1]));
        }
        double[] boundingBox = calculatePointsBoundingBox();
        System.out.println(Arrays.toString(boundingBox));
        assert boundingBox != null;
        try {
            this.data = OsmOverpassGetter.getAreasInBox(boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);
        } catch (IOException e) {
            throw new PreconditionViolationException(e);
        }
    }

    /**
     * Class constructor. Retrieves the appropriate map data from the Overpass API.ç
     * @param points List of Point objects representing potential landing locations.
     */
    public LandingSiteProcessor(List<Point> points) {
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
    public List<Point> getValidPoints() {
        return Null.nonNull(this.points.stream()
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

        for (Point point : this.points) {
            north = Math.max(point.getLatitude(), north);
            south = Math.min(point.getLatitude(), south);
            east = Math.max(point.getLongitude(), east);
            west = Math.min(point.getLongitude(), west);
        }

        return new double[]{north+0.0005, west-0.0005, south-0.0005, east+0.0005};
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
    private boolean rayCast(Point point) {
        // Return true if no polygons in data - this means there are no buildings in the area to check against.
        if (this.data.getWays().size() == 0) return true;
        Line2D ray = new Line2D.Double(point.getLongitude(), point.getLatitude(),
                point.getLongitude() + 0.05, point.getLatitude()-0.05);

        // Each building is represented by a way.
        for (OsmOverpassData.Way w : this.data.getWays()) {
            List<OsmOverpassData.Node> nodes = w.getNodes();
            int intersections = 0;
            for (int i = 0; i < nodes.size(); ++i) {
                @SuppressWarnings("null")
				Line2D edge = new Line2D.Double(
                        Null.nonNull(nodes.get(i).LON),
                        Null.nonNull(nodes.get(i).LAT),
                        // We must check against the first node again here.
                        Null.nonNull(nodes.get(i < nodes.size() - 1
                                ? i + 1
                                : 0).LON),
                        Null.nonNull(nodes.get(i < nodes.size() - 1
                                ? i + 1
                                : 0).LAT));
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
