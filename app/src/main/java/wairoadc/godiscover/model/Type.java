package wairoadc.godiscover.model;

/**
 * Created by Lucas on 7/01/2015.
 */

// Type: Defines the structure for the type of a resource.
public class Type {
    private long _id; // Id for the type on the database.
    private String name; // Name of the type.

    //Just to make it more uniform, this constants can be used when you're setting a type name for
    // the type object you created
    public static final String IMAGE_TYPE = "Image";
    public static final String SOUND_TYPE = "Sound";
    public static final String VIDEO_TYPE = "Video";

// Getters and setters for the Id and Name.

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

    @Override
    public String toString() {
        return "Type{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }
}
