package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents a set of data retrieved from the OSM Overpass API.
 *
 * @author Bailey Jewell
 */
public class OsmOverpassData {

    private final List<Node> NODES;
    private final List<Way> WAYS;

    OsmOverpassData(List<Node> nodes, List<Way> ways) {
        this.NODES = nodes;
        this.WAYS = ways;
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(NODES);
    }

    public List<Way> getWays() {
        return Collections.unmodifiableList(WAYS);
    }

    /**
     * Represents a single OpenStreetMap node. Every OSM entity comprises nodes.
     */
    public static final class Node {
        final long ID;
        final double LAT;
        final double LON;
        private final Map<String, String> TAGS;

        Node(long id, double lat, double lon, Map<String, String> tags) {
            this.ID = id;
            this.LAT = lat;
            this.LON = lon;
            this.TAGS = tags;
        }

        public Map<String, String> getTags() {
            return Collections.unmodifiableMap(TAGS);
        }
    }

    /**
     * Represents a single OpenStreetMap way. Ways comprise nodes.
     */
    public static final class Way {
        final long ID;
        private final List<Node> NODES;
        private final Map<String, String> TAGS;

        Way(long id, List<Node> nodes, Map<String, String> tags) {
            this.ID = id;
            this.NODES = nodes;
            this.TAGS = tags;
        }

        public List<Node> getNodes() {
            return Collections.unmodifiableList(NODES);
        }

        public Map<String, String> getTags() {
            return Collections.unmodifiableMap(TAGS);
        }
    }
}
