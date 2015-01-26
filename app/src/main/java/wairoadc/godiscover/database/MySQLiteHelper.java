package wairoadc.godiscover.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import wairoadc.godiscover.dao.TypeDAO;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 8/01/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    //Database Name & Version
    public static final String DATABASE_NAME="goDataBase";
    public static final int DATABASE_VERSION=2;



    public void onCreate(SQLiteDatabase database) {

        //Creating the tables
        database.execSQL(TrackTable.CREATE_TRACK);
        database.execSQL(SpotTable.CREATE_SPOT);
        database.execSQL(TypeTable.CREATE_TYPE);
        database.execSQL(ResourceTable.CREATE_RESOURCE);
        database.execSQL("INSERT INTO "+TypeTable.TYPE_TABLE+
                         "("+TypeTable.COLUMN_ID_TYPE+","+
                             TypeTable.COLUMN_NAME+") "+
                           "VALUES ("+1+",'"+Type.IMAGE_TYPE+"')");
        database.execSQL("INSERT INTO "+TypeTable.TYPE_TABLE+
                "("+TypeTable.COLUMN_ID_TYPE+","+
                TypeTable.COLUMN_NAME+") "+
                "VALUES ("+2+",'"+Type.SOUND_TYPE+"')");
        database.execSQL("INSERT INTO "+TypeTable.TYPE_TABLE+
                "("+TypeTable.COLUMN_ID_TYPE+","+
                TypeTable.COLUMN_NAME+") "+
                "VALUES ("+3+",'"+Type.VIDEO_TYPE+"')");
    }
    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        Log.w(TypeTable.class.getName(), "Upgrading database from version"
                + oldVersion + " to " + newVersion
                + ", wich will destroy all old data");
        //Drop older versions when upgrading it
        database.execSQL("DROP TABLE IF EXISTS " + TypeTable.TYPE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + TrackTable.TRACK_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + SpotTable.SPOT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + ResourceTable.RESOURCE_TABLE);

        //create the new tables
        onCreate(database);
    }
}
