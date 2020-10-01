package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteStatistics;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSitesData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLandingSiteStatistics {
    @Test
    public void testPercentageValid() {
        List<Point> valid = new ArrayList<>();
        List<Point> all = new ArrayList<>();
        valid.add(new Point(1, 1));
        all.add(new Point(1, 1));
        all.add(new Point(0, 1));
        LandingSitesData landingSitesData = new LandingSitesData(new Point(0, 0), all, valid);

        assertEquals(50.0, LandingSiteStatistics.getPercentageValid(landingSitesData));

    }
}
