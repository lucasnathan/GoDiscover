package wairoadc.godiscover.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lucas on 8/01/2015.
 */
public class MySQLiteHelper {
    //Database Table Type
    public static final String TYPE_TABLE = "type";
    public static final String COLUMN_ID_TYPE = "id";
    public static final String COLUMN_NAME = "name";

    //Database Table Resource
    public static final String RESOURCE_TABLE = "resource";
    public static final String COLUMN_ID_RESOURCE = "id";
    public static final String COLUMN_RESOURCE_NAME = "resource";
    public static final String COLUMN_FK_SPOT = "fk_spot";
    public static final String COLUMN_FK_TYPE = "fk_type";

    //Database Table Spot
    public static final String SPOT_TABLE = "spot";
    public static final String COLUMN_ID_SPOT = "id";
    public static final String COLUMN_INFORMATION = "information";
    public static final String COLUMN_UNLOCKED = "unlocked";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DATE_FOUND = "datefound";
    public static final String COLUMN_FK_TRACK = "fk_track";

    //Database Table Track
    public static final String TRACK_TABLE = "track";
    public static final String COLUMN_ID_TRACK = "id";
    public static final String COLUMN_TRACK_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TRACK_RESOURCE = "resource";
    public static final String COLUMN_TRACK_MAP = "map";

    //Type Table creation SQL statement
    private static final String CREATE_TYPE = "create table "
            + TYPE_TABLE
            + "("
            + COLUMN_ID_TYPE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL);";

    //Resource Table creation SQL statement
    private static final String CREATE_RESOURCE = "create table "
            + RESOURCE_TABLE
            + "("
            + COLUMN_ID_RESOURCE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESOURCE_NAME + " TEXT NOT NULL, "
            + COLUMN_FK_SPOT + " INTEGER, "
            + COLUMN_FK_TYPE + " INTEGER, "
            + "FOREIGN KEY ("+COLUMN_FK_SPOT+") REFERENCES " + SPOT_TABLE+" ("+COLUMN_ID_SPOT+"), "
            + "FOREIGN KEY ("+COLUMN_FK_TYPE+") REFERENCES " + TYPE_TABLE+" ("+COLUMN_ID_TYPE+")"
            +");";

    //Spot Table creation SQL statement
    private static final String CREATE_SPOT = "create table "
            + SPOT_TABLE
            + "("
            + COLUMN_ID_SPOT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_INFORMATION + " TEXT NOT NULL, "
            + COLUMN_UNLOCKED+ " INTEGER, "
            + COLUMN_X+ " INTEGER, "
            + COLUMN_Y+ " INTEGER, "
            + COLUMN_LATITUDE+ " REAL, "
            + COLUMN_LONGITUDE+ " REAL, "
            + COLUMN_DATE_FOUND+ " INTEGER, "
            + COLUMN_FK_TRACK + " INTEGER, "
            + "FOREIGN KEY ("+COLUMN_FK_TRACK+") REFERENCES " + TRACK_TABLE+" ("+COLUMN_ID_TRACK+")"
            +");";

    //Track Table creation SQL statement
    private static final String CREATE_TRACK = "create table "
            + TRACK_TABLE
            + "("
            + COLUMN_ID_TRACK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TRACK_NAME + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_TRACK_RESOURCE + " TEXT, "
            + COLUMN_TRACK_MAP + "TEXT"
            +");";

    public static void onCreate(SQLiteDatabase database) {

        //Creating the tables
        database.execSQL(CREATE_TRACK);
        database.execSQL(CREATE_SPOT);
        database.execSQL(CREATE_TYPE);
        database.execSQL(CREATE_RESOURCE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        Log.w(TypeTable.class.getName(), "Upgrading database from version"
                + oldVersion + " to " + newVersion
                + ", wich will destroy all old data");
        //Drop older versions when upgrading it
        database.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + TRACK_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + SPOT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + RESOURCE_TABLE);

        //create the new tables
        onCreate(database);
    }
}
