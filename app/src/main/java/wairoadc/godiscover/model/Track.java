package wairoadc.godiscover.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 7/01/2015.
 */

// Track: Defines the structure for a track which consists of a series of spots, and some other basic information.
public class Track {
    private long id; // Id for the track on the database.
    private String name; // Name or title of the track.
    private String description; // Some basic information about the track.
    private String mapPath; // Path to the base map of the track.
    private ArrayList<Spot> Spots; // List of the spots that make up the track.
    private long version; //Version number of the track itself
    private String resource; //base resource for the track
    // Getters and setters for the attributes of the track.

    public String getMapPath() {
        return mapPath;
    }

    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public ArrayList<Spot> getSpots() {
        return Spots;
    }

    public void setSpots(ArrayList<Spot> spots) {
        Spots = spots;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mapPath='" + mapPath + '\'' +
                ", Spots=" + Spots +
                ", version=" + version +
                ", resource='" + resource + '\'' +
                '}';
    }
}
