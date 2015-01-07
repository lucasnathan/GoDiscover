package wairoadc.godiscover.model;

/**
 * Created by Lucas on 7/01/2015.
 */

// Type: Defines the structure for the type of a resource.
public class Type {
    private int id; // Id for the type on the database.
    private String name; // Name of the type.

// Getters and setters for the Id and Name.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
