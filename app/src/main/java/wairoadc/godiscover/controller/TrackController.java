package wairoadc.godiscover.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.dao.TrackDAO;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
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
        try {
            List<Track> tracks = new ArrayList<>();
            TrackDAO trackDAO = new TrackDAO(context);
            SQLiteDatabase db = trackDAO.open();
            db.beginTransaction();
            tracks = trackDAO.getAllTracks();
            db.endTransaction();
            if(tracks.size() > 0) return tracks;
            else return null;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves a track by its name or id.
    //Loads the Track's full features
    public Track loadTrack(Track track) {
        try {
            if(null != track) {
                TrackDAO trackDAO = new TrackDAO(context);
                SQLiteDatabase transaction = trackDAO.open();
                SpotController spotController = new SpotController(context);
                if(0 != track.getId()) {
                    track = trackDAO.getById(track);
                }
                else if(null != track.getName()) {
                    track = trackDAO.getByName(track);
                } else return null;
                if(null != track) {
                    track.setSpots(spotController.loadAllSpots(track));
                }
                return track;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves the map of a track given the track id or name.
    public List<String> loadTrackMap(Track track) {
        return null;
    }

    //Records a Track into the database
    public Track insertTrack(Track track) {
        try {
            if(null != track) {
                TrackDAO trackDAO = new TrackDAO(context);
                SQLiteDatabase db = trackDAO.open();
                db.beginTransaction();
                Track nTrack = trackDAO.insertTrack(track);
                track.setId(nTrack.getId());
                SpotController spotController = new SpotController(context);
                spotController.insertSpots(track,db);
                db.endTransaction();
                return track;
            } else return null;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
