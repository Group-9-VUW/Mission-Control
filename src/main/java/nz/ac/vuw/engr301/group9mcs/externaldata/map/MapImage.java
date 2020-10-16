package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import java.awt.Image;

/**
 * Empty Interface for MapData.
 *
 * @author Bryony Gatehouse
 * Copyright (C) 2020, Mission Control Group 9
 */
public interface MapImage {

  // Gets image
  /**
   * Returns a map of specified coordinates.
   *
   * @param longUL Upper Left Longitude
   * @param latUL Upper Left Latitude
   * @param longBR Bottom Right Longitude
   * @param latBR Bottom Right Latitude
   * @return Image
   */
  public Image get(double longUL, double latUL, double longBR, double latBR);

}
