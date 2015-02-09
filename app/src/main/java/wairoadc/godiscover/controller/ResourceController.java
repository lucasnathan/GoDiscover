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
    public List<Resource> loadAllByType(Track track,Type type) {
        List<Resource> typeResources  = new ArrayList<>();
        List<Resource> tempResouces = new ArrayList<>();
        List<Spot> trackSpots = new ArrayList<>();
        SpotController spotController = new SpotController(context);
        ResourceDAO resourceDAO = new ResourceDAO(context);
        SQLiteDatabase db = null;
        try {
            trackSpots = spotController.loadAllSpots(track);
            if(null != trackSpots && 0 != trackSpots.size()) {
                for(Spot spot: trackSpots) {
                    db = resourceDAO.open();
                    db.beginTransaction();
                    tempResouces = resourceDAO.getAllSpotResources(spot);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                    if(null != tempResouces && 0 != tempResouces.size()) {
                        for(Resource resource : tempResouces) {
                            if(resource.getType().get_id() == type.get_id()) {
                                typeResources.add(resource);
                            }
                        }
                    }
                }
                return typeResources;
            } else return null;
        }catch(SQLException e) {
            e.printStackTrace();
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            return null;
        }
    }

    // Given a track, retrieves a list of all resources from that track.
    public List<Resource> loadAllRes(Track track) {
        List<Resource> typeResources  = new ArrayList<>();
        List<Resource> tempResouces = new ArrayList<>();
        List<Spot> trackSpots = new ArrayList<>();
        SpotController spotController = new SpotController(context);
        ResourceDAO resourceDAO = new ResourceDAO(context);
        SQLiteDatabase db = null;
        try {
            trackSpots = spotController.loadAllSpots(track);
            if(null != trackSpots && 0 != trackSpots.size()) {
                for(Spot spot: trackSpots) {
                    db = resourceDAO.open();
                    resourceDAO.setDatabase(db);
                    db.beginTransaction();
                    tempResouces = resourceDAO.getAllSpotResources(spot);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
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
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            return null;
        }
    }

    // Given a spot and a type retrieves a list of all the resources from that type on the spot.
    public List<Resource> loadByType(Spot spot, SQLiteDatabase transaction, Type type) {
        List<Resource> resources = new ArrayList<>();
        List<Resource> results = new ArrayList<>();
        ResourceDAO resourceDAO =  new ResourceDAO(context);
        resourceDAO.setDatabase(transaction);
        resources = resourceDAO.getAllSpotResources(spot);
        if(null != resources && 0 != resources.size()) {
            for(Resource resource : resources) {
                if(resource.getType().get_id() == type.get_id()) {
                    results.add(resource);
                }
            }
            return results;
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

    // Given a spot, retrieves a list of all the resources from that spot.
    //This is a self contained transaction
    public List<Resource> loadRes(Spot spot) {
        List<Resource> resources = new ArrayList<>();
        ResourceDAO resourceDAO =  new ResourceDAO(context);
        SQLiteDatabase db = null;
        try {
            db = resourceDAO.open();
            resourceDAO.setDatabase(db);
            db.beginTransaction();
            resources = resourceDAO.getAllSpotResources(spot);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            if(null != resources && 0 != resources.size()) return resources;
            else return null;
        } catch(SQLException e) {
            e.printStackTrace();
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            return null;
        }
    }

    // Given a spot, retrieves a list of all the resources from that spot.
    //This is a self contained transaction
    public List<Resource> loadResByType(Spot spot,Type type) {
        List<Resource> resources = new ArrayList<>();
        List<Resource> results = new ArrayList<>();
        ResourceDAO resourceDAO =  new ResourceDAO(context);
        SQLiteDatabase db = null;
        try {
            db = resourceDAO.open();
            resourceDAO.setDatabase(db);
            db.beginTransaction();
            resources = resourceDAO.getAllSpotResources(spot);
            for(Resource resource : resources) {
                if(resource.getType().get_id() == type.get_id()) {
                    results.add(resource);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            if(null != results && 0 != results.size()) return results;
            else return null;
        } catch(SQLException e) {
            e.printStackTrace();
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            return null;
        }
    }

    // Retrieves the number of unlocked resources on a track given a type.
    public int countUnlockedByType(Track track, Type type) {
        int countUnlocked = 0;
        SpotController spotController = new SpotController(context);
        ResourceDAO resourceDAO = new ResourceDAO(context);
        SQLiteDatabase db = null;
        try {
            List<Spot> spots = spotController.loadAllSpots(track);
            db = resourceDAO.open();
            db.beginTransaction();
            for(Spot spot : spots) {
                if(spot.getUnlocked() == 1) {
                    List<Resource>resources = this.loadByType(spot,db,type);
                    if(null != resources) {
                        countUnlocked += resources.size();
                    }
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return countUnlocked;
        } catch (SQLException e) {
            e.printStackTrace();
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            return -1;
        }
    }

    // Retrieves the number of unlocked resources on a track.
    public int countUnlocked(Track track) {
        int countUnlocked = 0;
        SpotController spotController = new SpotController(context);
        ResourceDAO resourceDAO = new ResourceDAO(context);
        SQLiteDatabase db = null;
        try {
            List<Spot> spots = spotController.loadAllSpots(track);
            db = resourceDAO.open();
            db.beginTransaction();
            for(Spot spot : spots) {
                if(spot.getUnlocked() == 1) {
                    List<Resource>resources = this.loadRes(spot,db);
                    if(null != resources) {
                        countUnlocked += resources.size();
                    }
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return countUnlocked;
        } catch (SQLException e) {
            e.printStackTrace();
            if(null != db) {
                db.endTransaction();
                db.close();
            }
            return -1;
        }
    }

    //Insert the resources in a given spot
    protected void insertResources(Spot spot, SQLiteDatabase transaction) {
        if(null != spot.getResources() && 0 != spot.getResources().size()) {
            ResourceDAO resourceDAO = new ResourceDAO(context);
            resourceDAO.setDatabase(transaction);
            for(Resource r : spot.getResources()) {
                resourceDAO.insertResource(r,spot);
            }
        }
    }
}
