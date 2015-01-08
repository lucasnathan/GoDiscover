package wairoadc.godiscover.dao;

import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import wairoadc.godiscover.database.MySQLiteHelper;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 8/01/2015.
 */
public class TypeDAO {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID_TYPE,MySQLiteHelper.COLUMN_NAME};

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
}
