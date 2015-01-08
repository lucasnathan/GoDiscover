package wairoadc.godiscover.dao;

import java.util.ArrayList;

import wairoadc.godiscover.model.Resource;

/**
 * Created by Lucas on 7/01/2015.
 */

// Data Access Object for accessing the resources on the database.
public class ResourceDAO {

    // Returns a resource from the database by its id.
    public Resource ReadByID(int id) {
        return null;
    }

    // Returns a list of resources from the database by their name (path).
    public ArrayList<Resource> ReadByName(String name) {
        return null;
    }

    // Return all the resources from the database (for testing only)
    public ArrayList<Resource> ReadAll() {
        return null;
    }
}
