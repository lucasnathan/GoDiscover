package wairoadc.godiscover.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wairoadc.godiscover.database.MySQLiteHelper;
import wairoadc.godiscover.database.ResourceTable;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 8/01/2015.
 */
public class ResourceDAO {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {ResourceTable.COLUMN_ID_RESOURCE, ResourceTable.COLUMN_RESOURCE_NAME, ResourceTable.COLUMN_STORY, ResourceTable.COLUMN_PATH, ResourceTable.COLUMN_FK_SPOT,
            ResourceTable.COLUMN_FK_TYPE};

    //Initialize ResourceDAO class
    public ResourceDAO(Context context){
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

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    //Transform Cursor in Resource Object
    private Resource cursorToResource(Cursor cursor){
        Resource resource = new Resource();
        resource.set_id(cursor.getInt(0));
        resource.setName(cursor.getString(1));
        resource.setStory(cursor.getString(2));
        resource.setPath(cursor.getString(3));

        Type type = new Type();
        type.set_id(cursor.getLong(5));
        resource.setType(type);
        return resource;
    }
    //Insert a New Resource on the database
    public Resource insertResource(Resource resource, Spot spot){
        ContentValues values = new ContentValues();
        values.put(ResourceTable.COLUMN_RESOURCE_NAME, resource.getName());
        values.put(ResourceTable.COLUMN_STORY, resource.getStory());
        values.put(ResourceTable.COLUMN_PATH, resource.getPath());
        values.put(ResourceTable.COLUMN_FK_SPOT, spot.get_id());
        values.put(ResourceTable.COLUMN_FK_TYPE, resource.getType().get_id());

        long insertId = database.insert(ResourceTable.RESOURCE_TABLE,null,values);

        Cursor cursor = database.query(ResourceTable.RESOURCE_TABLE,allColumns,ResourceTable.COLUMN_ID_RESOURCE+"="+insertId,null,null,null,null,null);
        cursor.moveToFirst();
        Resource newResource = cursorToResource(cursor);
        cursor.close();
        return newResource;
    }
    public Resource getById(Resource resource){
        Cursor cursor = database.query(ResourceTable.RESOURCE_TABLE,allColumns,ResourceTable.COLUMN_ID_RESOURCE+"="+resource.get_id(),null,null,null,null,null);
        cursor.moveToFirst();
        resource = cursorToResource(cursor);
        cursor.close();
        return resource;
    }

    //Get all resources stored on the database on the database
    public List<Resource> getAllResources(){
        List<Resource> resources = new ArrayList<Resource>();
        Cursor cursor = database.query(ResourceTable.RESOURCE_TABLE,allColumns,null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Resource resource = cursorToResource(cursor);
            resources.add(resource);
            cursor.moveToNext();
        }
        //♥♦♣♠
        cursor.close();
        return resources;
    }

    //Get all resources stored on the database for the given spot
    public List<Resource> getAllSpotResources(Spot spot){
        List<Resource> resources = new ArrayList<Resource>();
        String args[] = {String.valueOf(spot.get_id())};
        Cursor cursor = database.query(ResourceTable.RESOURCE_TABLE,allColumns,ResourceTable.COLUMN_FK_SPOT+"= ?",args,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Resource resource = cursorToResource(cursor);
            resources.add(resource);
            cursor.moveToNext();
        }
        //♥♦♣♠
        cursor.close();
        return resources;
    }

    //Delete a resource from the database(remember to store the ID on the Resource model)
    public void deleteResource(Resource resource){
        long id = resource.get_id();
        System.out.println("Comment deleted with id: " + id);
        database.delete(ResourceTable.RESOURCE_TABLE,ResourceTable.COLUMN_ID_RESOURCE + "=" + id,null);
    }
    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
}
