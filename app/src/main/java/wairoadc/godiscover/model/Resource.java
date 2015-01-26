package wairoadc.godiscover.model;

/**
 * Created by Lucas on 7/01/2015.
 */

// Resource: Defines the structure for resources, which are the path for resources to be used by the app.
public class Resource {
    private long _id; // Id for the resource on the database.
    private String name; // Name of the resource to be seen by the user.
    private String story; // Story of that piece of resource.
    private String path; // Path of the resource on the application.
    private Type type; // Type of the resource.

// Getters and setters for the _id, path and type.


    @Override
    public String toString() {
        return "Resource{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", story='" + story + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                '}';
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
