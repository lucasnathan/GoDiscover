package wairoadc.godiscover.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.database.MySQLiteHelper;
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

    // Retrieves all the spots and their resources from a track.
    public List<Spot> loadAllSpots(Track track) {
        try {
            List<Spot> spots = new ArrayList<>();
            SpotDAO spotDAO = new SpotDAO(context);
            ResourceDAO resourceDAO = new ResourceDAO(context);
            ResourceController controller = new ResourceController(context);

            SQLiteDatabase db = spotDAO.open();
            db.beginTransaction();
            spots = spotDAO.getAllTrackSpots(track);
            for(Spot spot : spots) {
                List<Resource> resources = new ArrayList<>();
                resources = controller.loadRes(spot);
                spot.setResources(resources);
            }
            db.endTransaction();
            if(null != spots && 0 != spots.size()) return spots;
            else return null;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves all information and resources from a spot given its name or id.
    public Spot loadSpot(Spot spot) {
        SpotDAO spotDAO = new SpotDAO(context);
        try {
            SQLiteDatabase db = spotDAO.open();
            db.beginTransaction();
            if(spot.getId() != 0) {
                spot = spotDAO.getById(spot);
            } else if (null != spot.getName() && !spot.getName().equals("")) {
                spot = spotDAO.getByName(spot);
            }
            ResourceController controller = new ResourceController(context);
            List<Resource> resources = new ArrayList<>();
            resources = controller.loadRes(spot);
            spot.setResources(resources);
            db.endTransaction();
            return spot;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
    }

    // Set the status of a spot to unlocked, given its id.
    public void setUnlocked(Spot spot) {
        try {
            SpotDAO spotDAO = new SpotDAO(context);
            SQLiteDatabase db = spotDAO.open();
            db.beginTransaction();
            spotDAO.updateUnlocked(spot);
            db.endTransaction();
        } catch(SQLException e) {
            e.printStackTrace();
        }
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
