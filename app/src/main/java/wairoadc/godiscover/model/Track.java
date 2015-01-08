package wairoadc.godiscover.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 7/01/2015.
 */

// Track: Defines the structure for a track which consists of a series of spots, and some other basic information.
public class Track {
    private int id; // Id for the track on the database.
    private String name; // Name or title of the track.
    private String description; // Some basic information about the track.
    private String mapPath; // Path to the base map of the track.
    private ArrayList<Spot> Spots; // List of the spots that make up the track.

// Getters and setters for the attributes of the track.

    public ArrayList<Spot> getSpots() {
        return Spots;
    }

    public void setSpots(ArrayList<Spot> spots) {
        Spots = spots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMap() {
        return mapPath;
    }

    public void setMap(String map) {
        this.mapPath = map;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mapPath='" + mapPath + '\'' +
                ", Spots=" + Spots +
                '}';
    }
}
