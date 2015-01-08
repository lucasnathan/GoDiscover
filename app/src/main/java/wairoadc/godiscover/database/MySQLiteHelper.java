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
    public static final String COLUMN_RESOURCENAME = "resource";
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
    public static final String COLUMN_DATEFOUND = "datefound";
    public static final String COLUMN_FK_TRACK = "fk_track";

    //Database Table Track
    public static final String TRACK_TABLE = "track";
    public static final String COLUMN_ID_TRACK = "id";
    public static final String COLUMN_TRACKNAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TRACK_RESOURCE = "resource";
    public static final String COLUMN_TRACKMAP = "map";

    //Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TYPE_TABLE
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(TypeTable.class.getName(), "Upgrading database from version"
                + oldVersion + " to " + newVersion
                + ", wich will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE);
        onCreate(database);
    }
}
