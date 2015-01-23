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

// Controller that manages the interactions with the resources.
public class ResourceController {

    private Context context;

    public ResourceController(Context context) {
        this.context = context;
    }

    // Given a track and a type of resource retrieves a list of all resources of that type from that track.
    public List<Resource> loadAllByType(Type type, Track track) {
        List<Resource> typeResources  = new ArrayList<>();
        List<Resource> tempResouces = new ArrayList<>();
        List<Spot> trackSpots = new ArrayList<>();
        SpotDAO spotDAO = new SpotDAO(context);
        ResourceDAO resourceDAO = new ResourceDAO(context);
        try {
            SQLiteDatabase transaction = spotDAO.open();
            spotDAO.setDatabase(transaction);
            transaction.beginTransaction();
            trackSpots = spotDAO.getAllTrackSpots(track);
            if(null != trackSpots && 0 != trackSpots.size()) {
                for(Spot spot: trackSpots) {
                    resourceDAO.setDatabase(transaction);
                    tempResouces = resourceDAO.getAllSpotResources(spot);
                    if(null != tempResouces && 0 != tempResouces.size()) {
                        for(Resource resource : tempResouces) {
                            if(resource.getType().getId() == type.getId()) {
                                typeResources.add(resource);
                            }
                        }
                    }
                }
                return typeResources;
            } else return null;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Given a track, retrieves a list of all resources from that track.
    public List<Resource> loadAllRes(Track track) {
        List<Resource> typeResources  = new ArrayList<>();
        List<Resource> tempResouces = new ArrayList<>();
        List<Spot> trackSpots = new ArrayList<>();
        SpotDAO spotDAO = new SpotDAO(context);
        ResourceDAO resourceDAO = new ResourceDAO(context);
        try {
            SQLiteDatabase transaction = spotDAO.open();
            spotDAO.setDatabase(transaction);
            transaction.beginTransaction();
            trackSpots = spotDAO.getAllTrackSpots(track);
            if(null != trackSpots && 0 != trackSpots.size()) {
                for(Spot spot: trackSpots) {
                    resourceDAO.setDatabase(transaction);
                    tempResouces = resourceDAO.getAllSpotResources(spot);
                    if(null != tempResouces && 0 != tempResouces.size()) {
                        for(Resource resource : tempResouces) {
                            typeResources.add(resource);
                        }
                    }
                }
                return typeResources;
            } else return null;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Given a spot and a type retrieves a list of all the resources from that type on the spot.
    public List<Resource> loadByType(Spot spot, SQLiteDatabase transaction, Type type) {
        List<Resource> resources = new ArrayList<>();
        ResourceDAO resourceDAO =  new ResourceDAO(context);
        resourceDAO.setDatabase(transaction);
        resources = resourceDAO.getAllSpotResources(spot);
        if(null != resources && 0 != resources.size()) {
            for(Resource resource : resources) {
                if(resource.getType().getId() != type.getId()) {
                    resources.remove(resource);
                }
            }
            return resources;
        }
        else return null;
    }

    // Given a spot, retrieves a list of all the resources from that spot.
    public List<Resource> loadRes(Spot spot, SQLiteDatabase transaction) {
        List<Resource> resources = new ArrayList<>();
        ResourceDAO resourceDAO =  new ResourceDAO(context);
        resourceDAO.setDatabase(transaction);
        resources = resourceDAO.getAllSpotResources(spot);
        if(null != resources && 0 != resources.size()) return resources;
        else return null;
    }

    // Retrieves the number of unlocked resources on a track given a type.
    public int countUnlockedByType(Track track, Type type) {
        int countUnlocked = 0;
        SpotDAO spotsDao = new SpotDAO(context);
        try {
            SQLiteDatabase transacion = spotsDao.open();
            spotsDao.setDatabase(transacion);
            transacion.beginTransaction();
            List<Spot> spots = spotsDao.getAllTrackSpots(track);
            for(Spot spot : spots) {
                if(spot.getUnlocked() == 1) {
                    List<Resource>resources = this.loadByType(spot,transacion,type);
                    if(null != resources) {
                        countUnlocked += resources.size();
                    }
                }
            }
            return countUnlocked;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Retrieves the number of unlocked resources on a track.
    public int countUnlocked(Track track) {
        int countUnlocked = 0;
        SpotDAO spotsDao = new SpotDAO(context);
        try {
            SQLiteDatabase transacion = spotsDao.open();
            spotsDao.setDatabase(transacion);
            transacion.beginTransaction();
            List<Spot> spots = spotsDao.getAllTrackSpots(track);
            for(Spot spot : spots) {
                if(spot.getUnlocked() == 1) {
                    List<Resource>resources = this.loadRes(spot,transacion);
                    if(null != resources) {
                        countUnlocked += resources.size();
                    }
                }
            }
            return countUnlocked;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
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
