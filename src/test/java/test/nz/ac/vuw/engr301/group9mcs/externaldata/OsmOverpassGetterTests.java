package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.externaldata.OsmOverpassData;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static nz.ac.vuw.engr301.group9mcs.externaldata.OsmOverpassGetter.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Overpass API.
 *
 * Note that I have kept the number of test cases and query size
 * here small to prevent undue load on the Overpass endpoints.
 *
 * @author jewellbail
 * @editor hindlejosh
 */
@SuppressWarnings("static-method")
class OsmOverpassGetterTests {

	/**
	 * This test retrieves a small area around VUW and
	 * checks whether nodes have been retrieved.
	 */
	@Test
	void testNodeRetrieval_01() {
		try {
			OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
			assertFalse(data.getNodes().isEmpty());
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}

	/**
	 * This test retrieves a small area around VUW and
	 * checks whether ways have been retrieved.
	 */
	@Test
	void testWayRetrieval_01() {
		try {
			OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
			assertFalse(data.getWays().isEmpty());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * This test retrieves a small area around VUW and
	 * checks the names of the buildings retrieved.
	 */
	@Test
	void testWayTags_01() {
		try {
			OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
			assertEquals("Cotton Building", Null.nonNull(data.getWays().get(0)).getTags().get("name"));
			assertEquals("Alan MacDiarmid Building", Null.nonNull(data.getWays().get(1)).getTags().get("name"));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * This test retrieves a small area around VUW and
	 * checks the names of the buildings retrieved.
	 *
     * It then checks whether:
     *  1. Node set contains all nodes in each building
     *  2. Each building node set does NOT contain each other's nodes.
	 */
	@Test
	void testNodeReferencesInWay_01() {
		try {
			OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
			assertEquals("Cotton Building", Null.nonNull(data.getWays().get(0)).getTags().get("name"));
			assertEquals("Alan MacDiarmid Building", Null.nonNull(data.getWays().get(1)).getTags().get("name"));
			assertTrue(data.getNodes().containsAll(Null.nonNull(data.getWays().get(0)).getNodes()));
			assertTrue(data.getNodes().containsAll(Null.nonNull(data.getWays().get(1)).getNodes()));
			assertFalse(Null.nonNull(data.getWays().get(0)).getNodes().containsAll(Null.nonNull(data.getWays().get(1)).getNodes()));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
