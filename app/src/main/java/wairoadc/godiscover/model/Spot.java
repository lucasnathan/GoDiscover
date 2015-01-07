package wairoadc.godiscover.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 7/01/2015.
 */

// Spot: Defines the structure of a spot, which is part of a track.
public class Spot {
    private int id; // Id for the spot on the database.
    private String name; // Name or title of the spot on a track.
    private String information; // Some basic information about the spot.
    private String x; // X (image coordinate) to draw where on the map the spot is.
    private String y; // Y (image coordinate) to draw where on the map the spot is.
    private int unlocked; // Defines if the spot has been unlocked already or not, usually 0 (false) by default, always 1 (true) for the first spot on a track.
    private float latitude; // Latitude of the spot on real coordinates, to be plotted on Google Maps.
    private float longitude; // Longitude of the spot on real coordinates, to be plotted on Google Maps.
    private ArrayList<Resource> resources; // List of the resources that are related and unlocked by this spot.

// Getters and setters for the attributes of the spot.

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public int getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
