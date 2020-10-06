package nz.ac.vuw.engr301.group9mcs.externaldata.map;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PostconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

import java.io.*;

import static nz.ac.vuw.engr301.group9mcs.externaldata.map.OsmOverpassGetter.getAreasInBox;

public class CachedOsmOverpassData {

    private static final String MAP_CACHE_DIRECTORY = "map_cache/";

    /**
     * Save an area defined by the two bounding boxes.
     * @param northWest North west border.
     * @param southEast South east border.
     * @param data OsmOverpassData object containing data in bounding box.
     */
    public static void saveArea(Point northWest, Point southEast, OsmOverpassData data) {
        save(String.format(MAP_CACHE_DIRECTORY + "%f.0000, %f.0000, %f.0000, %f.0000.osm",
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
        return load(String.format(MAP_CACHE_DIRECTORY + "%f.0000, %f.0000, %f.0000, %f.0000.osm",
                northWest.getLatitude(), northWest.getLongitude(),
                southEast.getLatitude(), southEast.getLongitude())
        );
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

    public static void main(String[] args) throws IOException {
        OsmOverpassData data = getAreasInBox(-41.29039, 174.76832, -41.29056, 174.76839);
        saveArea(new Point(-41.29039, 174.76832), new Point(-41.29056, 174.76839), data);
    }
}
