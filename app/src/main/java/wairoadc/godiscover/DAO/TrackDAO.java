package wairoadc.godiscover.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.database.MySQLiteHelper;
import wairoadc.godiscover.database.TrackTable;
import wairoadc.godiscover.model.Track;

/**
 * Created by Lucas on 8/01/2015.
 */
public class TrackDAO {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {TrackTable.COLUMN_ID_TRACK,TrackTable.COLUMN_TRACK_NAME,TrackTable.COLUMN_VERSION,TrackTable.COLUMN_DESCRIPTION,TrackTable.COLUMN_TRACK_MAP,TrackTable.COLUMN_TRACK_RESOURCE};

    //Initialize TrackDAO class
    public TrackDAO(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    //Open connection with the database
    public SQLiteDatabase open() throws SQLException{
        return database = dbHelper.getWritableDatabase();
    }

    //Close connection with the database
    public void close(){
        dbHelper.close();
    }

    //Transform Cursor in Track Object
    private Track cursorToTrack(Cursor cursor){
        Track track = new Track();
        track.setId(cursor.getInt(0));
        track.setName(cursor.getString(1));
        track.setVersion(cursor.getLong(2));
        track.setDescription(cursor.getString(3));
        track.setMapPath(cursor.getString(4));
        track.setResource(cursor.getString(5));
        return track;
    }
    //Insert a New Track on the database
    public Track insertTrack(Track track){
        ContentValues values = new ContentValues();
        values.put(TrackTable.COLUMN_TRACK_NAME, track.getName());
        values.put(TrackTable.COLUMN_VERSION, track.getVersion());
        values.put(TrackTable.COLUMN_DESCRIPTION, track.getDescription());
        values.put(TrackTable.COLUMN_TRACK_MAP, track.getMapPath());
        values.put(TrackTable.COLUMN_TRACK_RESOURCE, track.getResource());

        long insertId = database.insert(TrackTable.TRACK_TABLE,null,values);

        Cursor cursor = database.query(TrackTable.TRACK_TABLE,allColumns,TrackTable.COLUMN_ID_TRACK+"="+insertId,null,null,null,null,null);
        cursor.moveToFirst();
        Track newTrack = cursorToTrack(cursor);
        cursor.close();
        return newTrack;
    }
    public Track getById(Track track){
        Cursor cursor = database.query(TrackTable.TRACK_TABLE,allColumns,TrackTable.COLUMN_ID_TRACK+"="+track.getId(),null,null,null,null,null);
        cursor.moveToFirst();
        track = cursorToTrack(cursor);
        cursor.close();
        return track;
    }
    //Get all tracks stored on the database on the database
    public List<Track> getAllTracks(){
        List<Track> tracks = new ArrayList<Track>();
        Cursor cursor = database.query(TrackTable.TRACK_TABLE,allColumns,null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Track track = cursorToTrack(cursor);
            tracks.add(track);
            cursor.moveToNext();
        }
        //♥♦♣♠
        cursor.close();
        return tracks;
    }

    //Delete a track from the database(remember to store the ID on the Track model)
    public void deleteTrack(Track track){
        long id = track.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(TrackTable.TRACK_TABLE,TrackTable.COLUMN_ID_TRACK + "=" + id,null);
    }
}
