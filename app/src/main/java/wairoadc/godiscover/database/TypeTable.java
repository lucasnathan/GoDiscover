package wairoadc.godiscover.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lucas on 8/01/2015.
 */
public class TypeTable {

    //Database Table
    public static final String TYPE_TABLE ="type";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    //Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TYPE_TABLE
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(TypeTable.class.getName(), "Upgrading database from version"
                + oldVersion + " to " + newVersion
                + ", wich will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE);
        onCreate(database);
    }
}
