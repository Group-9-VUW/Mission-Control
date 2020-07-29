package nz.ac.vuw.engr301.group9mcs.externaldata;

/**
 * This class represents a set of data retrieved from the OSM Overpass API.
 *
 * @author Bailey Jewell
 */
public class OsmOverpassData {

    final Node[] nodes;
    final Way[] ways;

    public OsmOverpassData(Node[] nodes, Way[] ways) {
        this.nodes = nodes;
        this.ways = ways;
    }

    /**
     * Represents a single OpenStreetMap node. Every OSM entity comprises nodes.
     */
    static final class Node {
        final int id;
        final double lat;
        final double lon;
        final String[] tags;

        public Node(int id, double lat, double lon, String[] tags) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.tags = tags;
        }
    }

    /**
     * Represents a single OpenStreetMap way. Ways comprise nodes.
     */
    static final class Way {
        final Node[] nodes;
        final String[] tags;

        public Way(Node[] nodes, String[] tags) {
            this.nodes = nodes;
            this.tags = tags;
        }
    }
}
