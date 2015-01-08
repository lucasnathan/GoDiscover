package wairoadc.godiscover.controller;

import java.util.ArrayList;

import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 7/01/2015.
 */

// Controller that manages the interactions with the resources.
public class ResourceController {

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
}
