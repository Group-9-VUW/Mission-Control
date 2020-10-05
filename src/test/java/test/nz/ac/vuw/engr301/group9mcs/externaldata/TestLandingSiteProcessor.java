package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteProcessor;

import java.util.List;

/**
 * Tests for determining whether a point lands within a building or not.
 *
 * @author jewellbail
 */
@SuppressWarnings("static-method")
public class TestLandingSiteProcessor {

	/**
	 * Clear point on Kelburn park.
	 */
    @Test
    public void testSingleClearPoint() {
        double[][] landingPoints = new double[][]{
                {-41.28602, 174.77032}
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(1, validPoints.size());
        Assertions.assertEquals(landingPoints[0], validPoints.get(0));
    }

    /**
     * Not clear point on Cotton building.
     */
    @Test
    public void testSingleNotClearPoint() {
        double[][] landingPoints = new double[][]{
                {-41.29020, 174.76825},
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(0, validPoints.size());
    }

    /**
     * One clear point in Kelburn park, one unclear point on Cotton building.
     */
    @Test
    public void testOneClearPoint() {
        double[][] landingPoints = new double[][]{
                {-41.29020, 174.76825},
                {-41.28602, 174.77032}
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(1, validPoints.size());
        Assertions.assertEquals(landingPoints[1], validPoints.get(0));
    }

    /**
     * All clear points in Kelburn park.
     */
    @Test
    public void testAllClearPoints() {
        double[][] landingPoints = new double[][]{
                {-41.28582, 174.77002},
                {-41.28608, 174.76966},
                {-41.28675, 174.76983},
                {-41.28632, 174.77088},
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(landingPoints.length, validPoints.size());
    }

    /**
     * No clear points, all small buildings.
     */
    @Test
    public void testNoClearPoints() {
        double[][] landingPoints = new double[][]{
                {-41.28571, 174.76878},
                {-41.28593, 174.76893},
                {-41.28589, 174.76864}
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(0, validPoints.size());
    }
}
