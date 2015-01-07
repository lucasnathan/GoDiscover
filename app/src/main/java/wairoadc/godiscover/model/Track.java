package wairoadc.godiscover.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 7/01/2015.
 */
public class Track {
    private int id;
    private String name;
    private String description;
    private String map;
    private ArrayList<Spot> Spots;

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
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
