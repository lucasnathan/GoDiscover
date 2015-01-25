package wairoadc.godiscover.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lucas on 8/01/2015.
 */
public class TypeTable {

    //Database Table Type
    public static final String TYPE_TABLE = "type";
    public static final String COLUMN_ID_TYPE = "_id";
    public static final String COLUMN_NAME = "name";

    //Type Table creation SQL statement
    public static final String CREATE_TYPE = "create table "
            + TYPE_TABLE
            + "("
            + COLUMN_ID_TYPE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL);";
}
