package wairoadc.godiscover;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import wairoadc.godiscover.controller.ResourceController;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.controller.TrackXMLParser;
import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.dao.TrackDAO;
import wairoadc.godiscover.dao.TypeDAO;
import wairoadc.godiscover.database.ResourceTable;
import wairoadc.godiscover.database.SpotTable;
import wairoadc.godiscover.database.TrackTable;
import wairoadc.godiscover.database.TypeTable;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Track;

/**
 * Created by Xinxula on 25/01/2015.
 */
public class ControllerTests  extends AndroidTestCase {



    public boolean wipeData() throws SQLException{

        ResourceDAO resourceDAO = new ResourceDAO(mContext);
        SpotDAO spotDAO = new SpotDAO(mContext);
        TypeDAO typeDAO = new TypeDAO(mContext);
        TrackDAO trackDAO = new TrackDAO(mContext);

        //Test if Database is opening
        SQLiteDatabase db = resourceDAO.open();
        SQLiteDatabase db1 = typeDAO.open();
        SQLiteDatabase db2 = spotDAO.open();
        SQLiteDatabase db3 = trackDAO.open();


        Log.i("ResourceDAO", "Database opens");
        //delete all the data
        db.delete(ResourceTable.RESOURCE_TABLE,null,null);
        db1.delete(TypeTable.TYPE_TABLE,null,null);
        db2.delete(SpotTable.SPOT_TABLE,null,null);
        db3.delete(TrackTable.TRACK_TABLE,null,null);

        return true;
    }

    public void testResourceController() throws Throwable {
        assertEquals(true,wipeData());

        InputStream in = getContext().getResources().openRawResource(R.raw.xml_track_example);
        Track track = TrackXMLParser.parse(in);
        in = getContext().getResources().openRawResource(R.raw.xml_track_example_2);
        Track track2 = TrackXMLParser.parse(in);
        TrackController controller = new TrackController(getContext());
        Track trackDb = controller.insertTrack(track);
        Track trackDb2 = controller.insertTrack(track2);
        ResourceController resourceController = new ResourceController(mContext);

        List<Resource> resoruces1  = resourceController.loadAllRes(trackDb);
        assertTrue(resoruces1.get(0).getName().equals(trackDb.getName()));



    }


}
