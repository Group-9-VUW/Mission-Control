package nz.ac.vuw.engr301.group9mcs.externaldata;

/**
 * A class to save and load map data to files (i.e. cache the map data).
 * 
 * @author Joshua Hindley, hindlejosh, 300438963
 */
public class CachedMapData /*TODO implements MapData*/ {

  public CachedMapData() {
    
  }

  //TODO add constructor from internetMapData, lang, long, radius -> saves to file
  //TODO add constructor from String/File -> loads from file and analyses 
  //the file contents to ensure it is valid

  //TODO maybe add a default constructor that loads a "default" file name, 
  //if a file with that name exists at the expected location
  
  //TODO the MapData interface (or both InternetMapData and CachedMapData) 
  //should contain a hashCode method and an equals method. It is VITAL that:
  
  // CachedMapData data1 = new CachedMapData(data, long, lat, radius);
  // CachedMapData data2 = new CachedMapData(data1.getFile());
  // assert data1.hashCode().equals(data2.hashCode());
  // assert data1.equals(data2);
  
  //or similar for whatever constructors are used is valid
}
