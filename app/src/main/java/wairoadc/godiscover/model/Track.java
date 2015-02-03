package wairoadc.godiscover.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 7/01/2015.
 */

// Track: Defines the structure for a track which consists of a series of spots, and some other basic information.
public class Track implements Parcelable {
    private long _id; // Id for the track on the database.
    private String name; // Name or title of the track.
    private String description; // Some basic information about the track.
    private String mapPath; // Path to the base map of the track.
    private List<Spot> spots; // List of the spots that make up the track.
    private long version; //Version number of the track itself
    private String resource; //base resource for the track
    // Getters and setters for the attributes of the track.

    public String getMapPath() {
        return mapPath;
    }

    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public long get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Track{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mapPath='" + mapPath + '\'' +
                ", spots=" + spots +
                ", version=" + version +
                ", resource='" + resource + '\'' +
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
        dest.writeString(this.description);
        dest.writeString(this.mapPath);
        dest.writeLong(this.version);
        dest.writeString(this.resource);
        dest.writeTypedList(spots);
    }

    public Track() {
    }

    private Track(Parcel in) {
        this._id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.mapPath = in.readString();
        this.version = in.readLong();
        this.resource = in.readString();
        spots = new ArrayList<>();
        in.readTypedList(spots, Spot.CREATOR);
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
