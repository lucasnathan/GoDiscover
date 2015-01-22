package wairoadc.godiscover.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 7/01/2015.
 */

// Spot: Defines the structure of a spot, which is part of a track.
public class Spot {
    private long id; // Id for the spot on the database.
    private String name; // Name or title of the spot on a track.
    private String information; // Some basic information about the spot.
    private int x; // X (image coordinate) to draw where on the map the spot is.
    private int y; // Y (image coordinate) to draw where on the map the spot is.
    private int unlocked; // Defines if the spot has been unlocked already or not, usually 0 (false) by default, always 1 (true) for the first spot on a track.
    private double latitude; // Latitude of the spot on real coordinates, to be plotted on Google Maps.
    private double longitude; // Longitude of the spot on real coordinates, to be plotted on Google Maps.
    private List<Resource> resources; // List of the resources that are related and unlocked by this spot.
    private Date date;

// Getters and setters for the attributes of the spot.


    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", unlocked=" + unlocked +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", resources=" + resources +
                ", date=" + date +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
