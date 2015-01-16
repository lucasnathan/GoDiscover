package wairoadc.godiscover.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wairoadc.godiscover.database.MySQLiteHelper;
import wairoadc.godiscover.database.SpotTable;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;

/**
 * Created by Lucas on 8/01/2015.
 */
public class SpotDAO {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {SpotTable.COLUMN_ID_SPOT, SpotTable.COLUMN_NAME, SpotTable.COLUMN_INFORMATION, SpotTable.COLUMN_UNLOCKED, SpotTable.COLUMN_X,
            SpotTable.COLUMN_Y, SpotTable.COLUMN_LATITUDE, SpotTable.COLUMN_LONGITUDE, SpotTable.COLUMN_DATE_FOUND, SpotTable.COLUMN_FK_TRACK};

    //Initialize SpotDAO class
    public SpotDAO(Context context){
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

    //Transform Cursor in Spot Object
    private Spot cursorToSpot(Cursor cursor){
        Spot spot = new Spot();
        spot.setId(cursor.getInt(0));
        spot.setName(cursor.getString(1));
        spot.setInformation(cursor.getString(2));
        spot.setUnlocked(cursor.getInt(3));
        spot.setX(cursor.getInt(4));
        spot.setY(cursor.getInt(5));
        spot.setLatitude(cursor.getFloat(6));
        spot.setLongitude(cursor.getFloat(7));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(cursor.getString(8));
            spot.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return spot;
    }
    //Insert a New Spot on the database
    public Spot insertSpot(Spot spot, Track trackId){
        ContentValues values = new ContentValues();
        values.put(SpotTable.COLUMN_NAME, spot.getName());
        values.put(SpotTable.COLUMN_INFORMATION, spot.getInformation());
        values.put(SpotTable.COLUMN_UNLOCKED, spot.getUnlocked());
        values.put(SpotTable.COLUMN_X, spot.getX());
        values.put(SpotTable.COLUMN_Y, spot.getY());
        values.put(SpotTable.COLUMN_LATITUDE, spot.getLatitude());
        values.put(SpotTable.COLUMN_LONGITUDE, spot.getLongitude());
        values.put(SpotTable.COLUMN_DATE_FOUND, getDateTime(spot.getDate()));
        values.put(SpotTable.COLUMN_FK_TRACK, trackId.getId());

        long insertId = database.insert(SpotTable.SPOT_TABLE,null,values);

        Cursor cursor = database.query(SpotTable.SPOT_TABLE,allColumns,SpotTable.COLUMN_ID_SPOT+"="+insertId,null,null,null,null,null);
        cursor.moveToFirst();
        Spot newSpot = cursorToSpot(cursor);
        cursor.close();
        return newSpot;
    }
    public Spot getById(Spot spot){
        Cursor cursor = database.query(SpotTable.SPOT_TABLE,allColumns,SpotTable.COLUMN_ID_SPOT+"="+spot.getId(),null,null,null,null,null);
        cursor.moveToFirst();
        spot = cursorToSpot(cursor);
        cursor.close();
        return spot;
    }
    //Get all spots stored on the database on the database
    public List<Spot> getAllSpots(){
        List<Spot> spots = new ArrayList<Spot>();
        Cursor cursor = database.query(SpotTable.SPOT_TABLE,allColumns,null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Spot spot = cursorToSpot(cursor);
            spots.add(spot);
            cursor.moveToNext();
        }
        //♥♦♣♠
        cursor.close();
        return spots;
    }

    //Delete a spot from the database(remember to store the ID on the Spot model)
    public void deleteSpot(Spot spot){
        long id = spot.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SpotTable.SPOT_TABLE,SpotTable.COLUMN_ID_SPOT + "=" + id,null);
    }
    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
}
