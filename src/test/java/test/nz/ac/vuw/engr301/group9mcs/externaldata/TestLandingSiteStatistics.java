package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteStatistics;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSitesData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLandingSiteStatistics {

	/**
     * Checks half valid points.
     */
    @Test
    public void testPercentageValid_01() {
        List<Point> valid = new ArrayList<>();
        List<Point> all = new ArrayList<>();
        valid.add(new Point(1, 1));
        all.add(new Point(1, 1));
        all.add(new Point(0, 1));
        LandingSitesData landingSitesData = new LandingSitesData(new Point(0, 0), all, valid);

        assertEquals(50.0, LandingSiteStatistics.getPercentageValid(landingSitesData));
    }

    /**
     * Checks all valid points.
     */
    @Test
    public void testPercentageValid_02() {
        List<Point> valid = new ArrayList<>();
        List<Point> all = new ArrayList<>();
        valid.add(new Point(1, 1));
        all.add(new Point(1, 1));
        LandingSitesData landingSitesData = new LandingSitesData(new Point(0, 0), all, valid);

        assertEquals(100.0, LandingSiteStatistics.getPercentageValid(landingSitesData));
    }

    /**
     * Checks no valid points.
     */
    @Test
    public void testPercentageValid_03() {
        List<Point> valid = new ArrayList<>();
        List<Point> all = new ArrayList<>();
        all.add(new Point(1, 1));
        LandingSitesData landingSitesData = new LandingSitesData(new Point(0, 0), all, valid);

        assertEquals(0.0, LandingSiteStatistics.getPercentageValid(landingSitesData));
    }

    /**
     * Checks one point approximately 111km from launch.
     */
    @Test
    public void testDistanceValid_01() {
        List<Point> valid = new ArrayList<>();
        List<Point> all = new ArrayList<>();
        all.add(new Point(2, 1));
        valid.add(new Point(2, 1));

        LandingSitesData landingSitesData = new LandingSitesData(new Point(1, 1), all, valid);

        assertEquals(111194.0 , LandingSiteStatistics.getAverageValidDistanceFromLaunchSite(landingSitesData), 1);
    }

    /**
     * Checks one point approximately 111km from launch.
     */
    @Test
    public void testDistanceValid_02() {
        List<Point> valid = new ArrayList<>();
        List<Point> all = new ArrayList<>();
        all.add(new Point(1, 2));
        valid.add(new Point(1, 2));

        LandingSitesData landingSitesData = new LandingSitesData(new Point(1, 1), all, valid);

        assertEquals(111177.0 , LandingSiteStatistics.getAverageValidDistanceFromLaunchSite(landingSitesData), 1);
    }
}
