package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents a set of data retrieved from the OSM Overpass API.
 *
 * @author Bailey Jewell
 */
public class OsmOverpassData implements Serializable {

	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = -6080618954952030556L;
	/**
	 * List of nodes from Overpass.
	 */
	private final List<Node> NODES;
	/**
	 * List of ways from Overpass.
	 */
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
		return Null.nonNull(Collections.unmodifiableList(this.NODES));
	}

	/**
	 * Gets an unmodifiable list of ways from the dataset.
	 * @return Returns an unmodifiable list of ways from the dataset.
	 */
	public List<Way> getWays() {
		return Null.nonNull(Collections.unmodifiableList(this.WAYS));
	}

	/**
	 * Represents a single OpenStreetMap node. Every OSM entity comprises nodes.
	 */
	public static final class Node {
		/**
		 * Node ID. This is the node's global OSM ID.
		 */
		final long ID;
		/**
		 * Latitude of the node.
		 */
		final double LAT;
		/**
		 * Longitude of the node.
		 */
		final double LON;
		/**
		 * Tags associated with the node, describing the node's purpose.
		 */
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
			this.TAGS = tags != null ? tags : Null.nonNull(Collections.emptyMap());
		}

		/**
		 * Gets an unmodifiable list of tags associated with the node. Tags describe the purpose of
		 * a node. Nodes that are members of ways will generally not have their own tags, preferring
		 * to defer to their parent way.
		 *
		 * @return Returns unmodifiable map of tags associated with the way.
		 */
		public Map<String, String> getTags() {
			return Null.nonNull(Collections.unmodifiableMap(this.TAGS));
		}
	}

    /**
     * Represents a single OpenStreetMap way. Ways comprise nodes.
     */
    public static final class Way {
        /**
         * Way ID. This is the way's global OSM ID.
         */
        final long ID;
        /**
         * List of nodes composing the way.
         */
        private final List<Node> NODES;
        /**
         * Tags associated with the node, describing the node's purpose.
         */
        private final Map<String, String> TAGS;
        /**
         * List of IDs of nodes composing the way. Used for populating node list.
         */
        private final List<Long> NODE_IDS;

        /**
         * Creates a single way object from the given OSM data.
         *
         * @param id Way ID. This is the way's global OSM ID.
		 * @param nodeIds List of IDs of nodes comprising the way.
         * @param tags Tags associated with the node, describing the node's purpose.
         */
        Way(long id, List<Long> nodeIds, @Nullable Map<String, String> tags) {
            this.ID = id;
            this.NODE_IDS = nodeIds;
            this.NODES = new ArrayList<>();
            this.TAGS = tags != null ? tags : Null.nonNull(Collections.emptyMap());
        }

		/**
		 * Gets an unmodifiable list of nodes composing the way.
		 * @return Returns unmodifiable list of nodes composing the way.
		 */
		public List<Node> getNodes() {
			return Null.nonNull(Collections.unmodifiableList(this.NODES));
		}

        /**
         * Sets the node refences from IDs.
         * @param nodes Map of ID to node reference.
         */
        void setNodeRefs(Map<Long, Node> nodes) {
            for (Long id : this.NODE_IDS) {
                this.NODES.add(nodes.get(id));
            }
        }

		/**
		 * Gets an unmodifiable list of tags associated with the way.
		 * @return Returns unmodifiable map of tags associated with the way.
		 */
		public Map<String, String> getTags() {
			return Null.nonNull(Collections.unmodifiableMap(this.TAGS));
		}
	}
}
