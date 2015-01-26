package wairoadc.godiscover.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.dao.TrackDAO;
import wairoadc.godiscover.model.Track;

/**
 * Created by Lucas on 7/01/2015.
 */

// Controller that manages the track interactions.
public class TrackController {

    private Context context;

    public TrackController(Context context) {
        this.context = context;
    }

    // Retrieves all the tracks on the database.
    public List<Track> loadAllTracks() {
        return null;
    }

    // Retrieves basic information of all the tracks, for displaying on the home page.
    //Which Include all the information in the Track table. See the design for more information.
    public List<Track> loadHomeTracks() {
        TrackDAO trackDAO = new TrackDAO(context);
        SQLiteDatabase db = null;
        try {
            List<Track> tracks = new ArrayList<>();
            db = trackDAO.open();
            db.beginTransaction();
            tracks = trackDAO.getAllTracks();
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            if(tracks.size() > 0) return tracks;
            else return null;
        }catch(SQLException e) {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves a track by its name or id.
    //Loads the Track's full features
    public Track loadTrack(Track track) {
        if(null != track) {
            TrackDAO trackDAO = new TrackDAO(context);
            SpotController spotController = new SpotController(context);
            SQLiteDatabase db = null;
            try {
                db = trackDAO.open();
                db.beginTransaction();
                if(0 != track.get_id()) {
                    track = trackDAO.getById(track);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                }
                else if(null != track.getName()) {
                    track = trackDAO.getByName(track);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                } else return null;
                if(null != track) {
                    track.setSpots(spotController.loadAllSpots(track));
                }
                return track;
            } catch(SQLException e) {
                if (null != db) {
                    db.endTransaction();
                    db.close();
                }
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    // Retrieves the map of a track given the track id or name.
    public List<String> loadTrackMap(Track track) {
        return null;
    }

    //Records a Track into the database
    public Track insertTrack(Track track) {
        TrackDAO trackDAO = new TrackDAO(context);
        SQLiteDatabase db = null;
        try {
            db = trackDAO.open();
            db.beginTransaction();
            if(null != track) {
                Track nTrack = trackDAO.insertTrack(track);
                track.set_id(nTrack.get_id());
                SpotController spotController = new SpotController(context);
                spotController.insertSpots(track,db);
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
                return track;
            } else return null;
        }catch(SQLException e) {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            e.printStackTrace();
            return null;
        }
    }
}
