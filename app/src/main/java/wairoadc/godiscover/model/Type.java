package wairoadc.godiscover.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 7/01/2015.
 */

// Type: Defines the structure for the type of a resource.
public class Type implements Parcelable {
    private long _id; // Id for the type on the database.
    private String name; // Name of the type.

    //Just to make it more uniform, this constants can be used when you're setting a type name for
    // the type object you created
    public static final String IMAGE_TYPE = "image";
    public static final String SOUND_TYPE = "sound";
    public static final String VIDEO_TYPE = "video";

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this._id);
        dest.writeString(this.name);
    }

    public Type() {
    }

    private Type(Parcel in) {
        this._id = in.readLong();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };
}
