package wairoadc.godiscover.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 7/01/2015.
 */
public class Resource {
    private int id;
    private String resource;
    private Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
