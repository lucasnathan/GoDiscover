package wairoadc.godiscover.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 7/01/2015.
 */

// Controller that manages the interactions with the resources.
public class ResourceController {

    private Context context;

    public ResourceController(Context context) {
        this.context = context;
    }

    // Given a track and a type of resource retrieves a list of all resources of that type from that track.
    public ArrayList<Resource> loadAllByType(Type type, Track track) {
        return null;
    }

    // Given a track, retrieves a list of all resources from that track.
    public ArrayList<Resource> loadAllRes(Track track) {
        return null;
    }

    // Given a spot and a type retrieves a list of all the resources from that type on the spot.
    public ArrayList<Resource> loadByType(Spot spot, Type type) {
        return null;
    }

    // Given a spot, retrieves a list of all the resources from that spot.
    public ArrayList<Resource> loadRes(Spot spot) {
        return null;
    }

    // Retrieves the number of unlocked resources on a track given a type.
    public int countUnlockedByType(Track track, Type type) {
        return 0;
    }

    // Retrieves the number of unlocked resources on a track.
    public int countUnlocked(Track track) {
        return 0;
    }

    //Insert the resources in a given spot
    public void insertResources(Spot spot, SQLiteDatabase transaction) {
        if(null != spot.getResources() && 0 != spot.getResources().size()) {
            ResourceDAO resourceDAO = new ResourceDAO(context);
            resourceDAO.setDatabase(transaction);
            for(Resource r : spot.getResources()) {
                resourceDAO.insertResource(r,spot);
            }
        }
    }

}
