package wairoadc.godiscover.dao;

import java.util.ArrayList;

import wairoadc.godiscover.model.Spot;

/**
 * Created by Lucas on 7/01/2015.
 */

// Data Access Object for accessing the spots on the database.
public class SpotDAO {

    // Returns a spot from the database by its id.
    public Spot ReadById(int id) {
        return null;
    }

    // Returns a list of spots from the database by their name.
    public ArrayList<Spot> ReadByName(String name) {
        return null;
    }

    // Returns all the spots from the database (for testing only).
    public ArrayList<Spot> ReadAll () {
        return null;
    }

    // Set if the spot is unlocked or not.
    public void SetSpotStatus (Spot spot) {
    }
}
