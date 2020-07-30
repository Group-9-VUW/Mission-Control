package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.externaldata.OsmOverpassData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static nz.ac.vuw.engr301.group9mcs.externaldata.OsmOverpassGetter.*;
import static nz.ac.vuw.engr301.group9mcs.externaldata.OsmOverpassGetter.parseData;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Overpass API.
 *
 * Note that I have kept the number of test cases and query size here small to reduce load on the Overpass endpoints.
 *
 * @author Bailey Jewell (@jewellbail)
 */
class OsmOverpassGetterTests {
    @Test
    void testNodeRetrieval_01() {
        /*
        This test retrieves a small area around VUW and checks whether nodes have been retrieved.
         */
        OsmOverpassData data = parseData(getAreasInBox(-41.29056, 174.76832, -41.29039, 174.76839));
        assertFalse(data.getNodes().isEmpty());
    }

    @Test
    void testWayRetrieval_01() {
        /*
        This test retrieves a small area around VUW and checks whether ways have been retrieved.
         */
        OsmOverpassData data = parseData(getAreasInBox(-41.29056, 174.76832, -41.29039, 174.76839));
        assertFalse(data.getNodes().isEmpty());
    }

    @Test
    void testWayTags_01() {
        /*
        This test retrieves a small area around VUW and checks the names of the buildings retrieved.
         */
        OsmOverpassData data = parseData(getAreasInBox(-41.29056, 174.76832, -41.29039, 174.76839));
        assertEquals("Cotton Building", data.getWays().get(0).getTags().get("name"));
        assertEquals("Alan MacDiarmid Building", data.getWays().get(1).getTags().get("name"));
    }

    @Test
    void testNodeReferencesInWay_01() {
        /*
        This test retrieves a small area around VUW and checks the names of the buildings retrieved.

        It then checks whether:
          1. Node set contains all nodes in each building
          2. Each building node set does NOT contain each other's nodes.
         */
        OsmOverpassData data = parseData(getAreasInBox(-41.29056, 174.76832, -41.29039, 174.76839));
        assertEquals("Cotton Building", data.getWays().get(0).getTags().get("name"));
        assertEquals("Alan MacDiarmid Building", data.getWays().get(1).getTags().get("name"));
        assertTrue(data.getNodes().containsAll(data.getWays().get(0).getNodes()));
        assertTrue(data.getNodes().containsAll(data.getWays().get(1).getNodes()));
        assertFalse(data.getWays().get(0).getNodes().containsAll(data.getWays().get(1).getNodes()));
    }
}
