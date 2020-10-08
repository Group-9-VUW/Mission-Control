package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PostconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.io.*;
import java.util.Optional;

/**
 * Class for saving and loading OsmOverpassData objects to the filesystem.
 * @author jewellbail
 */
public class CachedOsmOverpassData {

    private static final String MAP_CACHE_DIRECTORY = "map_cache/";

    /**
     * Save an area defined by the two bounding boxes.
     *
     * @param northWest North west border.
     * @param southEast South east border.
     * @param data      OsmOverpassData object containing data in bounding box.
     */
    public static void saveArea(Point northWest, Point southEast, OsmOverpassData data) {
        save(Null.nonNull(String.format(MAP_CACHE_DIRECTORY + "%f_%f_%f_%f.osm",
                Null.nonNull(northWest.getLatitude()), Null.nonNull(northWest.getLongitude()),
                Null.nonNull(southEast.getLatitude()), Null.nonNull(southEast.getLongitude()))),
                data);
    }

    /**
     * Load an area defined by the two bounding boxes.
     *
     * @param northWest North west border.
     * @param southEast South east border.
     * @return Returns the de-serialised OsmOverpassData object.
     */
    public static OsmOverpassData loadArea(Point northWest, Point southEast) {
        Optional<String> cacheFile = getCacheForArea(northWest, southEast);
        if (!cacheFile.isPresent()) {
            throw new PreconditionViolationException("No cache exists for this area.");
        }
        return load(MAP_CACHE_DIRECTORY + cacheFile.get());
    }

    /**
     * Save an OsmOverpassData object.
     *
     * @param fileName Filename to write to. Will typically be coordinates of bounding box.
     * @param data     OsmOverpassData object containing data in bounding box.
     */
    private static void save(String fileName, OsmOverpassData data) {
    	// First, ensure cache directory exists. If not, create it.
    	File cacheDir = new File(MAP_CACHE_DIRECTORY);
    	if (!cacheDir.isDirectory()) {
    		cacheDir.mkdir();
    	}
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(data);

        } catch (IOException e) {
            throw new PostconditionViolationException("Error serialising OsmOverpassData object", e);
        }
    }

    /**
     * @param fileName Filename to read from. Will typically be coordinates of bounding box.
     *                 Returns the de-serialised OsmOverpassData object.
     */
    private static OsmOverpassData load(String fileName) {
        try (
                FileInputStream fileInputStream = new FileInputStream(fileName);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)

        ) {
            return Null.nonNull((OsmOverpassData) objectInputStream.readObject());
        } catch (ClassNotFoundException | IOException e) {
            throw new PostconditionViolationException("Error de-serialising OsmOverpassData object", e);
        }
    }

    /**
     * Gets the cache file corresponding to a particular area. This checks whether the given area is encapsulated
     * by any cache file and returns the file name.
     *
     * @param northWest North west of area.
     * @param southEast South east of area.
     * @return Returns string if cache for area exists, Optional.empty() otherwise.
     */
    private static Optional<String> getCacheForArea(Point northWest, Point southEast) {
        File cacheDir = new File(MAP_CACHE_DIRECTORY);
        String[] cachedAreas = cacheDir.list();

        if (cachedAreas == null) {
            return Null.nonNull(Optional.empty());
        }

        for (String area : cachedAreas) {
            String pointString = area.replace(".osm", "");
            String[] points = pointString.split("_");

            if (doPointsEncapsulateArea(Null.nonNull(points), northWest, southEast)) {
                return Null.nonNull(Optional.of(area));
            }
        }
        return Null.nonNull(Optional.empty());
    }

    /**
     * Checks whether the area defined by points encapsulates the area defined by northWest and southEast.
     *
     * @param points    Array of points from cache filename.
     * @param northWest North west point of bounding box requested.
     * @param southEast South east point of bounding box requested.
     * @return Returns true if cache file encapsulates this area.
     */
    private static boolean doPointsEncapsulateArea(String[] points, Point northWest, Point southEast) {
        Point cacheNorthWest = new Point(Double.parseDouble(points[0]), Double.parseDouble(points[1]));
        Point cacheSouthEast = new Point(Double.parseDouble(points[2]), Double.parseDouble(points[3]));
        return cacheNorthWest.getLatitude() >= northWest.getLatitude()
                && cacheNorthWest.getLongitude() <= northWest.getLongitude()
                && cacheSouthEast.getLatitude() <= southEast.getLatitude()
                && cacheSouthEast.getLongitude() >= southEast.getLongitude();
    }
}


