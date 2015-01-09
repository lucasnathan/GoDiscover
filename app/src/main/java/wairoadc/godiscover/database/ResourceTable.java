package wairoadc.godiscover.database;

/**
 * Created by Lucas on 8/01/2015.
 */
public class ResourceTable {

    //Database Table Resource
    public static final String RESOURCE_TABLE = "resource";
    public static final String COLUMN_ID_RESOURCE = "id";
    public static final String COLUMN_RESOURCE_NAME = "resource";
    public static final String COLUMN_FK_SPOT = "fk_spot";
    public static final String COLUMN_FK_TYPE = "fk_type";

    //Resource Table creation SQL statement
    public static final String CREATE_RESOURCE = "create table "
            + RESOURCE_TABLE
            + "("
            + COLUMN_ID_RESOURCE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESOURCE_NAME + " TEXT NOT NULL, "
            + COLUMN_FK_SPOT + " INTEGER, "
            + COLUMN_FK_TYPE + " INTEGER, "
            + "FOREIGN KEY ("+COLUMN_FK_SPOT+") REFERENCES " + SpotTable.SPOT_TABLE+" ("+SpotTable.COLUMN_ID_SPOT+"), "
            + "FOREIGN KEY ("+COLUMN_FK_TYPE+") REFERENCES " + TypeTable.TYPE_TABLE+" ("+TypeTable.COLUMN_ID_TYPE+")"
            +");";
}
