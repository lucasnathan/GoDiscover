package wairoadc.godiscover.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 7/01/2015.
 */

// Spot: Defines the structure of a spot, which is part of a track.
public class Spot implements Parcelable {
    private long _id; // Id for the spot on the database.
    private String name; // Name or title of the spot on a track.
    private String information; // Some basic information about the spot.
    private int x; // X (image coordinate) to draw where on the map the spot is.
    private int y; // Y (image coordinate) to draw where on the map the spot is.
    private int unlocked; // Defines if the spot has been unlocked already or not, usually 0 (false) by default, always 1 (true) for the first spot on a track.
    private double latitude; // Latitude of the spot on real coordinates, to be plotted on Google Maps.
    private double longitude; // Longitude of the spot on real coordinates, to be plotted on Google Maps.
    private List<Resource> resources; // List of the resources that are related and unlocked by this spot.
    private Date date;
    public static int UNLOCKED = 1;
    public static int LOCKED = 0;

// Getters and setters for the attributes of the spot.

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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", unlocked=" + unlocked +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", resources=" + resources +
                ", date=" + date +
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
        dest.writeString(this.information);
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeInt(this.unlocked);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeLong(date != null ? date.getTime() : -1);
        if(null != this.resources) {
            dest.writeTypedList(resources);
        }
    }

    public Spot() {
    }

    private Spot(Parcel in) {
        this._id = in.readLong();
        this.name = in.readString();
        this.information = in.readString();
        this.x = in.readInt();
        this.y = in.readInt();
        this.unlocked = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        resources = new ArrayList<>();
        in.readTypedList(resources, Resource.CREATOR);
    }

    public static final Parcelable.Creator<Spot> CREATOR = new Parcelable.Creator<Spot>() {
        public Spot createFromParcel(Parcel source) {
            return new Spot(source);
        }

        public Spot[] newArray(int size) {
            return new Spot[size];
        }
    };
}
