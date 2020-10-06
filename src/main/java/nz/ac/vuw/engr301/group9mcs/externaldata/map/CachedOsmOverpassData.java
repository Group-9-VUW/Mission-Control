package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PostconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.io.*;

public class CachedOsmOverpassData {

    private static final String MAP_CACHE_DIRECTORY = "map_cache/";

    /**
     * Save an area defined by the two bounding boxes.
     * @param northWest North west border.
     * @param southEast South east border.
     * @param data OsmOverpassData object containing data in bounding box.
     */
    public static void saveArea(Point northWest, Point southEast, OsmOverpassData data) {
        save(String.format(MAP_CACHE_DIRECTORY + "%f.000, %f.000, %f.000, %f.000.osm",
                northWest.getLatitude(), northWest.getLongitude(),
                southEast.getLatitude(), southEast.getLongitude()),
                data);
    }

    /**
     * Load an area defined by the two bounding boxes.
     * @param northWest North west border.
     * @param southEast South east border.
     * @return Returns the de-serialised OsmOverpassData object.
     */
    public static OsmOverpassData loadArea(Point northWest, Point southEast) {
        // TODO: If area is contained by larger area, load this dataset.
        return load(String.format(MAP_CACHE_DIRECTORY + "%f.000, %f.000, %f.000, %f.000.osm",
                northWest.getLatitude(), northWest.getLongitude(),
                southEast.getLatitude(), southEast.getLongitude()));
    }

    /**
     * Save an OsmOverpassData object.
     * @param fileName Filename to write to. Will typically be coordinates of bounding box.
     * @param data OsmOverpassData object containing data in bounding box.
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
     *
     * @param fileName Filename to read from. Will typically be coordinates of bounding box.
     * Returns the de-serialised OsmOverpassData object.
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
}
