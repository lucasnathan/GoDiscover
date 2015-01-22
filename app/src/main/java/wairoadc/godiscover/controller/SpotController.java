package wairoadc.godiscover.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;

/**
 * Created by Lucas on 7/01/2015.
 */

// Controller that manages the interactions with spots of the tracks.
public class SpotController {

    private Context context;

    public SpotController(Context context) {
        this.context = context;
    }

    // Retrieves all the spots from a track.
    public ArrayList<Spot> loadAllSpots(Track track) {
        return null;
    }

    // Retrieves information from a spot given its name or id.
    public ArrayList<Spot> loadSpot(Spot spot) {
        return null;
    }

    // Set the status of a spot to unlocked, given its id.
    public void setUnlocked(Spot spot) {
    }

    //Insert the spots of a given track
    public void insertSpots(Track track, SQLiteDatabase transaction) {
        if(null != track.getSpots() && 0 != track.getSpots().size()) {
            SpotDAO spotDAO = new SpotDAO(context);
            spotDAO.setDatabase(transaction);
            for(Spot s : track.getSpots()) {
                Spot spot = spotDAO.insertSpot(s,track);
                s.setId(spot.getId());
                ResourceController resController = new ResourceController(context);
                resController.insertResources(s,transaction);
            }
        }
    }

}
