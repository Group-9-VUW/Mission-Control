package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.awt.Image;

public interface MapData {

  // Gets image 
  public Image get(double longUL, double latUL, double longBR, double latBR);
  
}
