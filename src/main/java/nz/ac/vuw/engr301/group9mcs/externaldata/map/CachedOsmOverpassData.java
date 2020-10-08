package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PostconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.io.*;
import java.util.Arrays;
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
        save(String.format(MAP_CACHE_DIRECTORY + "%f_%f_%f_%f.osm",
                northWest.getLatitude(), northWest.getLongitude(),
                southEast.getLatitude(), southEast.getLongitude()),
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
        // TODO: If area is contained by larger area, load this dataset.
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
            return (OsmOverpassData) objectInputStream.readObject();
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
            return Optional.empty();
        }

        for (String area : cachedAreas) {
            String pointString = area.replace(".osm", "");
            String[] points = pointString.split("_");
            System.out.println(pointString);

            if (doPointsEncapsulateArea(points, northWest, southEast)) {
                return Optional.of(area);
            }
        }
        return Optional.empty();
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
        System.out.println(Arrays.toString(points));
        Point cacheNorthWest = new Point(Double.parseDouble(points[0]), Double.parseDouble(points[1]));
        Point cacheSouthEast = new Point(Double.parseDouble(points[2]), Double.parseDouble(points[3]));
        return cacheNorthWest.getLatitude() >= northWest.getLatitude()
                && cacheNorthWest.getLongitude() <= northWest.getLongitude()
                && cacheSouthEast.getLatitude() <= southEast.getLatitude()
                && cacheSouthEast.getLongitude() >= southEast.getLongitude();
    }
}


