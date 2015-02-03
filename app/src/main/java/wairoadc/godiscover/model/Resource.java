package wairoadc.godiscover.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 7/01/2015.
 */

// Resource: Defines the structure for resources, which are the path for resources to be used by the app.
public class Resource implements Parcelable {
    private long _id; // Id for the resource on the database.
    private String name; // Name of the resource to be seen by the user.
    private String story; // Story of that piece of resource.
    private String path; // Path of the resource on the application.
    private Type type; // Type of the resource.

// Getters and setters for the _id, path and type.

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this._id);
        dest.writeString(this.name);
        dest.writeString(this.story);
        dest.writeString(this.path);
        dest.writeParcelable(this.type, 0);
    }

    public Resource() {
    }

    private Resource(Parcel in) {
        this._id = in.readLong();
        this.name = in.readString();
        this.story = in.readString();
        this.path = in.readString();
        this.type = in.readParcelable(Type.class.getClassLoader());
    }

    public static final Parcelable.Creator<Resource> CREATOR = new Parcelable.Creator<Resource>() {
        public Resource createFromParcel(Parcel source) {
            return new Resource(source);
        }

        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };
}
