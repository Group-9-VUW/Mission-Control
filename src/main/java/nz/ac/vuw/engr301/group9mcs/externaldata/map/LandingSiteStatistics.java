package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collection of static methods operating on LandingSitesData objects to calculate statistics around landing sites.
 *
 * @author jewellbail
 */
public class LandingSiteStatistics {

    /**
     * Returns the percentage of landing sites that are valid according to LandingSitesProcessor.
     * @param data LandingSitesData object containing all landing sites and launch site.
     * @return Returns the percentage of valid landing sites in the data object.
     */
    public static double getPercentageValid(LandingSitesData data) {
        return data.getLandingPointsValid().size() / data.getLandingPointsAll().size() * 100;
    }

    /**
     * Returns the average distance between the valid points and the launch site.
     * @param data LandingSitesData object containing all landing sites and launch site.
     * @return Returns the average distance in metres.
     */
    public static double getAverageValidDistanceFromLaunchSite(LandingSitesData data) {
        List<Double> distances = data.getLandingPointsValid().stream()
                .map(point -> getDistanceBetweenPoints(data.getLaunchSite(), point))
                .collect(Collectors.toList());

        double sum = distances.stream().reduce(0.0, Double::sum);
        return sum / distances.size();
    }

    /**
     * Returns the average distance between the valid points and the launch site.
     * @param data LandingSitesData object containing all landing sites and launch site.
     * @return Returns the average distance in metres.
     */
    public static double getAverageAllDistanceFromLaunchSite(LandingSitesData data) {
        List<Double> distances = data.getLandingPointsAll().stream()
                .map(point -> getDistanceBetweenPoints(data.getLaunchSite(), point))
                .collect(Collectors.toList());

        double sum = distances.stream().reduce(0.0, Double::sum);
        return sum / distances.size();
    }

    /**
     * Calculates the distance between points using the <a href="https://www.movable-type.co.uk/scripts/latlong.html"
     * Haversine Formula</a>.
     *
     * @param a First point.
     * @param b Second point.
     * @return Returns the distance between points in metres.
     */
    private static double getDistanceBetweenPoints(Point a, Point b) {
        final double EARTH_RADIUS = 6371e3;
        double pointALatRads = a.getLatitude() * Math.PI/180;
        double pointBLatRads = b.getLatitude() * Math.PI/180;
        double pointALonRads = a.getLongitude() * Math.PI/180;
        double pointBLonRads = b.getLongitude() * Math.PI/180;

        double deltaLat = pointALatRads - pointBLatRads;
        double deltaLon = pointALonRads - pointBLonRads;

        double squareHalfChord = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(pointALatRads) * Math.cos(pointALatRads) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double angularDistance = 2 * Math.atan2(Math.sqrt(squareHalfChord), Math.sqrt(1 - squareHalfChord));

        return EARTH_RADIUS * angularDistance;
    }
}
