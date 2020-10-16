package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.OsmOverpassData;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static nz.ac.vuw.engr301.group9mcs.externaldata.map.CachedOsmOverpassData.loadArea;
import static nz.ac.vuw.engr301.group9mcs.externaldata.map.CachedOsmOverpassData.saveArea;
import static nz.ac.vuw.engr301.group9mcs.externaldata.map.OsmOverpassGetter.getAreasInBox;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the locally cached OsmOverpassData objects.
 *
 * @author Bailey Jewell
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestCachedOsmOverpassData {

    /**
     * Tests loading the exact area saved.
     * @throws IOException Filesystem error.
     */
    @SuppressWarnings("static-method")
	@Test
    public void testExactAreaLoad() throws IOException {
        OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
        saveArea(new Point(-41.29039, 174.76832), new Point(-41.29056, 174.76839), data);
        OsmOverpassData data1 = loadArea(new Point(-41.29039, 174.76832), new Point(-41.29056, 174.76839));

        assertEquals(data, data1);
    }

    /**
     * Tests loading a bounding box inside of the area saved.
     * @throws IOException Filesystem error.
     */
    @SuppressWarnings("static-method")
	@Test
    public void testInsideAreaLoad() throws IOException {
        OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
        saveArea(new Point(-41.29039, 174.76832), new Point(-41.29056, 174.76839), data);
        OsmOverpassData data1 = loadArea(new Point(-41.29040, 174.76833), new Point(-41.29056, 174.76839));

        assertEquals(data, data1);
    }

    /**
     * Tries to load area outside of cached area. Should throw PreconditionViolationException.
     */
    @SuppressWarnings({ "static-method", "unused" })
	@Test
    public void testOutsideAreaLoadFail() {
        assertThrows(PreconditionViolationException.class, () -> {
            OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
            saveArea(new Point(-41.29039, 174.76832), new Point(-41.29056, 174.76839), data);
            OsmOverpassData data1 = loadArea(new Point(-41.29038, 174.76831), new Point(-41.29056, 174.76839));
        });
    }
}
