package wairoadc.godiscover.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 7/01/2015.
 */

// Resource: Defines the structure for resources, which are the path for resources to be used by the app.
public class Resource {
    private int id; // Id for the resource on the database.
    private String path; // Path of the resource on the application.
    private Type type; // Type of the resource.

// Getters and setters for the id, path and type.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return path;
    }

    public void setResource(String resource) {
        this.path = resource;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", type=" + type +
                '}';
    }
}
