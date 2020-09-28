package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.externaldata.LandingSiteProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Tests for determining whether a point lands within a building or not.
 *
 * @author jewellbail
 */
@SuppressWarnings("static-method")
public class LandingSiteProcessorTests {

    @Test
    void testSingleClearPoint() {
        // Clear point on Kelburn park.
        double[][] landingPoints = new double[][]{
                {-41.28602, 174.77032}
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(1, validPoints.size());
        Assertions.assertEquals(landingPoints[0], validPoints.get(0));
    }

    @Test
    void testSingleNotClearPoint() {
        // Point on Cotton building.
        double[][] landingPoints = new double[][]{
                {-41.29020, 174.76825},
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(0, validPoints.size());
    }

    @Test
    void testOneClearPoint() {
        // One clear point in Kelburn park, one point on Cotton building.
        double[][] landingPoints = new double[][]{
                {-41.29020, 174.76825},
                {-41.28602, 174.77032}
        };

        LandingSiteProcessor landingSiteProcessor = new LandingSiteProcessor(landingPoints);

        List<double[]> validPoints = landingSiteProcessor.getValidPoints();

        Assertions.assertEquals(1, validPoints.size());
        Assertions.assertEquals(landingPoints[1], validPoints.get(0));
    }

    @Test
    void testAllClearPoints() {
        // All clear points in Kelburn park.
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

    @Test
    void testNoClearPoints() {
        // No clear points, all small buildings.
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
