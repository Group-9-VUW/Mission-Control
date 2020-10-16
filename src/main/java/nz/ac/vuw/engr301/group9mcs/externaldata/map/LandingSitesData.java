package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.util.Collections;
import java.util.List;

/**
 * Class to represent a set of landing site data.
 *
 * @author Bailey Jewell
 * Copyright (C) 2020, Mission Control Group 9
 */
public class LandingSitesData {
	/**
	 * Point of rocket launch.
	 */
    private final Point LAUNCH_SITE;
    /**
     * List of all possible landing sites.
     */
    private final List<Point> LANDING_POINTS_ALL;
    /**
     * List of only valid landing sites.
     */
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

    /**
     * @return Returns the point of rocket launch.
     */
    public Point getLaunchSite() {
        return this.LAUNCH_SITE;
    }

    /**
     * @return Returns an unmodifiable collection of all landing sites.
     */
    public List<Point> getLandingPointsAll() {
        return Null.nonNull(Collections.unmodifiableList(this.LANDING_POINTS_ALL));
    }

    /**
     * @return Returns an unmodifiable collection of only valid landing sites.
     */
    public List<Point> getLandingPointsValid() {
        return Null.nonNull(Collections.unmodifiableList(this.LANDING_POINTS_VALID));
    }
}
