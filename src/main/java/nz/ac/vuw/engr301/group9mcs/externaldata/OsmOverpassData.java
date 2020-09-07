package nz.ac.vuw.engr301.group9mcs.externaldata;

import org.eclipse.jdt.annotation.Nullable;

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

    /**
     * Creates an Overpass dataset from lists of nodes and ways.
     *
     * @param nodes List of nodes from Overpass.
     * @param ways List of ways from Overpass.
     */
    OsmOverpassData(List<Node> nodes, List<Way> ways) {
        this.NODES = nodes;
        this.WAYS = ways;
    }

    /**
     * Gets an unmodifiable list of nodes from the dataset.
     * @return Returns an unmodifiable list of nodes from the dataset.
     */
    public List<Node> getNodes() {
        return Collections.unmodifiableList(this.NODES);
    }

    /**
     * Gets an unmodifiable list of ways from the dataset.
     * @return Returns an unmodifiable list of ways from the dataset.
     */
    public List<Way> getWays() {
        return Collections.unmodifiableList(this.WAYS);
    }

    /**
     * Represents a single OpenStreetMap node. Every OSM entity comprises nodes.
     */
    public static final class Node {
        final long ID;
        final double LAT;
        final double LON;
        private final Map<String, String> TAGS;

        /**
         * Creates a single node object from the given OSM data.
         *
         * @param id Node ID. This is the node's global OSM ID.
         * @param lat Latitude of the node.
         * @param lon Longitude of the node.
         * @param tags Tags associated with the node, describing the node's purpose.
         */
        Node(long id, double lat, double lon, @Nullable Map<String, String> tags) {
            this.ID = id;
            this.LAT = lat;
            this.LON = lon;
            this.TAGS = tags != null ? tags : Collections.emptyMap();
        }

        /**
         * Gets an unmodifiable list of tags associated with the node. Tags describe the purpose of
         * a node. Nodes that are members of ways will generally not have their own tags, preferring
         * to defer to their parent way.
         *
         * @return Returns unmodifiable map of tags associated with the way.
         */
        public Map<String, String> getTags() {
            return Collections.unmodifiableMap(this.TAGS);
        }
    }

    /**
     * Represents a single OpenStreetMap way. Ways comprise nodes.
     */
    public static final class Way {
        final long ID;
        private final List<Node> NODES;
        private final Map<String, String> TAGS;

        /**
         * Creates a single way object from the given OSM data.
         *
         * @param id Way ID. This is the way's global OSM ID.
		 * @param nodes List of nodes composing the way.
         * @param tags Tags associated with the node, describing the node's purpose.
         */
        Way(long id, List<Node> nodes, @Nullable Map<String, String> tags) {
            this.ID = id;
            this.NODES = nodes;
            this.TAGS = tags != null ? tags : Collections.emptyMap();
        }

        /**
         * Gets an unmodifiable list of nodes composing the way.
         * @return Returns unmodifiable list of nodes composing the way.
         */
        public List<Node> getNodes() {
            return Collections.unmodifiableList(this.NODES);
        }

        /**
         * Gets an unmodifiable list of tags associated with the way.
         * @return Returns unmodifiable map of tags associated with the way.
         */
        public Map<String, String> getTags() {
            return Collections.unmodifiableMap(this.TAGS);
        }
    }
}
