package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.util.Collections;
import java.util.List;

/**
 * Class to represent a set of landing site data.
 *
 * @author jewellbail
 */
public class LandingSitesData {
    private final Point LAUNCH_SITE;
    private final List<Point> LANDING_POINTS_ALL;
    private final List<Point> LANDING_POINTS_VALID;

    /**
     * Creates a set of launch site data.
     *
     * @param launchSite Point representing the location at which the rocket is launched.
     * @param landingPointsAll List containing all possible landing points.
     * @param landingSitesValid List containing only the possible landing points that are deemed valid by LSP.
     */
    public LandingSitesData(Point launchSite, List<Point> landingPointsAll, List<Point> landingSitesValid) {
        this.LAUNCH_SITE = launchSite;
        this.LANDING_POINTS_ALL = landingPointsAll;
        this.LANDING_POINTS_VALID = landingSitesValid;
    }

    public Point getLaunchSite() {
        return LAUNCH_SITE;
    }

    public List<Point> getLandingPointsAll() {
        return Collections.unmodifiableList(LANDING_POINTS_ALL);
    }

    public List<Point> getLandingPointsValid() {
        return Collections.unmodifiableList(LANDING_POINTS_VALID);
    }
}
