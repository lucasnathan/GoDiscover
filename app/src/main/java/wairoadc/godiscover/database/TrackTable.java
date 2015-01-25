package wairoadc.godiscover.database;

/**
 * Created by Lucas on 8/01/2015.
 */
public class TrackTable {
    //Database Table Track
    public static final String TRACK_TABLE = "track";
    public static final String COLUMN_ID_TRACK = "_id";
    public static final String COLUMN_TRACK_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TRACK_RESOURCE = "resource";
    public static final String COLUMN_TRACK_MAP = "map";
    public static final String COLUMN_VERSION = "version";

    //Track Table creation SQL statement
    public static final String CREATE_TRACK = "create table "
            + TRACK_TABLE
            + "("
            + COLUMN_ID_TRACK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TRACK_NAME + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_TRACK_RESOURCE + " TEXT, "
            + COLUMN_TRACK_MAP + " TEXT, "
            + COLUMN_VERSION + " INTEGER NOT NULL);";


}
