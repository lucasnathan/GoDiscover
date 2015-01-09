package wairoadc.godiscover.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.database.MySQLiteHelper;
import wairoadc.godiscover.database.TypeTable;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 8/01/2015.
 */
public class TypeDAO {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {TypeTable.COLUMN_ID_TYPE,TypeTable.COLUMN_NAME};

    //Initialize TypeDAO class
    public TypeDAO(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    //Open connection with the database
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    //Close connection with the database
    public void close(){
        dbHelper.close();
    }

    //Transform Cursor in Type Object
    private Type cursorToType(Cursor cursor){
        Type type = new Type();
        type.setId(cursor.getInt(0));
        type.setName(cursor.getString(1));
        return type;
    }

    public Type insertType(Type type){
        ContentValues values = new ContentValues();
        values.put(TypeTable.COLUMN_NAME, type.getName());
        long insertId = database.insert(TypeTable.TYPE_TABLE,null,values);

        Cursor cursor = database.query(TypeTable.TYPE_TABLE,allColumns,TypeTable.COLUMN_ID_TYPE+"="+insertId,null,null,null,null,null);
        cursor.moveToFirst();
        Type newType = cursorToType(cursor);
        cursor.close();
        return newType;
    }
    public List<Type> getAllTypes(){
        List<Type> types = new ArrayList<Type>();
        Cursor cursor = database.query(TypeTable.TYPE_TABLE,allColumns,null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Type type = cursorToType(cursor);
            types.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        return types;
    }
    public void deleteType(Type type){
        long id = type.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(TypeTable.TYPE_TABLE,TypeTable.COLUMN_ID_TYPE + "=" + id,null);
    }
}
