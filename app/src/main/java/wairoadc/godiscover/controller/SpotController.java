package wairoadc.godiscover.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

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
        SpotDAO spotDAO = new SpotDAO(context);
        SQLiteDatabase db = null;
        try {
            List<Spot> spots = new ArrayList<>();
            ResourceController controller = new ResourceController(context);
            db = spotDAO.open();
            db.beginTransaction();
            spotDAO.setDatabase(db);
            spots = spotDAO.getAllTrackSpots(track);
            for(Spot spot : spots) {
                List<Resource> resources = new ArrayList<>();
                resources = controller.loadRes(spot,db);
                spot.setResources(resources);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            if(null != spots && 0 != spots.size()) return spots;
            else return null;
        }catch (SQLException e) {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllSpotPathsByType(Spot spot, Type type) {
        if(null != spot) {
            List<String> resourcesPath = new ArrayList<>();
            for(Resource resource : spot.getResources()) {
                if(resource.getType().equals(type)) {
                    resourcesPath.add(resource.getPath());
                }
            }
            return resourcesPath;
        } else return null;
    }

    // Retrieves all information and resources from a spot given its name or id.
    public Spot loadSpot(Spot spot) {
        SpotDAO spotDAO = new SpotDAO(context);
        SQLiteDatabase db = null;
        try {
            db = spotDAO.open();
            db.beginTransaction();
            spotDAO.setDatabase(db);
            if(spot.get_id() != 0) {
                spot = spotDAO.getById(spot);
            } else if (null != spot.getName() && !spot.getName().equals("")) {
                spot = spotDAO.getByName(spot);
            }
            ResourceController controller = new ResourceController(context);
            List<Resource> resources = new ArrayList<>();
            resources = controller.loadRes(spot,db);
            spot.setResources(resources);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return spot;
        } catch (SQLException e) {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            e.printStackTrace();
            return  null;
        }
    }

    // Set the status of a spot to unlocked, given its id.
    public void setUnlocked(Spot spot) {
        SpotDAO spotDAO = new SpotDAO(context);
        SQLiteDatabase db = null;
        try {
            db = spotDAO.open();
            db.beginTransaction();
            spotDAO.setDatabase(db);
            spotDAO.updateUnlocked(spot);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch(SQLException e) {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            e.printStackTrace();
        }
    }

    //Insert the spots of a given track
    //This method receives the transaction from insertTrack, so it shouldn't be called
    //alone.
    protected void insertSpots(Track track, SQLiteDatabase transaction) {
        if(null != track.getSpots() && 0 != track.getSpots().size()) {
            SpotDAO spotDAO = new SpotDAO(context);
            spotDAO.setDatabase(transaction);
            for(Spot s : track.getSpots()) {
                Spot spot = spotDAO.insertSpot(s,track);
                s.set_id(spot.get_id());
                ResourceController resController = new ResourceController(context);
                resController.insertResources(s,transaction);
            }
        }
    }

    //Returns the number of unlocked spots in a given track
    public int unlockedSpots(Track track) {
        SpotDAO spotDAO = new SpotDAO(context);
        SQLiteDatabase db = null;
        int unlockedSpots = 0;
        try {
            db = spotDAO.open();
            spotDAO.setDatabase(db);
            db.beginTransaction();
            List<Spot> spots = spotDAO.getAllTrackSpots(track);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            for(Spot spot : spots) {
                if(spot.getUnlocked() == 1) {
                    unlockedSpots++;
                }
            }
            return unlockedSpots;
        } catch (SQLException e) {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            e.printStackTrace();
            return -1;
        }
    }
}
