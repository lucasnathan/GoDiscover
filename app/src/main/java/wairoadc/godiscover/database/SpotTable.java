package wairoadc.godiscover.database;

/**
 * Created by Lucas on 8/01/2015.
 */
public class SpotTable {
    //Database Table Spot
    public static final String SPOT_TABLE = "spot";
    public static final String COLUMN_ID_SPOT = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INFORMATION = "information";
    public static final String COLUMN_UNLOCKED = "unlocked";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DATE_FOUND = "datefound";
    public static final String COLUMN_FK_TRACK = "fk_track";

    //Spot Table creation SQL statement
    public static final String CREATE_SPOT = "create table "
            + SPOT_TABLE
            + "("
            + COLUMN_ID_SPOT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_INFORMATION + " TEXT NOT NULL, "
            + COLUMN_UNLOCKED+ " INTEGER, "
            + COLUMN_X+ " INTEGER, "
            + COLUMN_Y+ " INTEGER, "
            + COLUMN_LATITUDE+ " REAL, "
            + COLUMN_LONGITUDE+ " REAL, "
            + COLUMN_DATE_FOUND+ " TEXT, "
            + COLUMN_FK_TRACK + " INTEGER, "
            + "FOREIGN KEY ("+COLUMN_FK_TRACK+") REFERENCES " + TrackTable.TRACK_TABLE+" ("+TrackTable.COLUMN_ID_TRACK+")"
            +");";
}
