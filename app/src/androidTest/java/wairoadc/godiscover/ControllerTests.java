package wairoadc.godiscover;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.controller.ResourceController;
import wairoadc.godiscover.controller.SpotController;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.utilities.TrackXMLParser;
import wairoadc.godiscover.dao.ResourceDAO;
import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.dao.TrackDAO;
import wairoadc.godiscover.dao.TypeDAO;
import wairoadc.godiscover.database.ResourceTable;
import wairoadc.godiscover.database.SpotTable;
import wairoadc.godiscover.database.TrackTable;
import wairoadc.godiscover.database.TypeTable;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

/**
 * Created by Xinxula on 25/01/2015.
 */
public class ControllerTests  extends AndroidTestCase {



    public void testGetAllTracksPath() throws Throwable{
        TrackController trackController = new TrackController(getContext());
        List<Track> homeTracks = trackController.loadHomeTracks();
        Track secondTrack = new Track();
        secondTrack.setName(homeTracks.get(0).getName());
        secondTrack = trackController.loadTrack(secondTrack);
        List<String> allPaths = trackController.getAllTrackPathsByType(secondTrack,new Type(1));
        SpotController spotController = new SpotController(mContext);
        List<String> allPathsSpot =  spotController.getAllSpotPathsByType(secondTrack.getSpots().get(0),new Type(1));
    }

    public void atestWipeData() throws Throwable{

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

        //return true;
    }

    public void atestResourceController() throws Throwable {
        //assertEquals(true,wipeData());

        InputStream in = getContext().getResources().openRawResource(R.raw.xml_track_example);
        Track track = TrackXMLParser.parse(in);
        in = getContext().getResources().openRawResource(R.raw.xml_track_example_2);
        Track track2 = TrackXMLParser.parse(in);
        TrackController controller = new TrackController(getContext());
        Track trackDb = controller.insertTrack(track);
        Track trackDb2 = controller.insertTrack(track2);
        //ResourceController resourceController = new ResourceController(mContext);

        //List<Resource> resoruces1  = resourceController.loadAllRes(trackDb);
        //assertTrue(resoruces1.get(0).getName().equals(trackDb.getSpots().get(0).getResources().get(0).getName()));
        //Type type = new Type();
        //type.set_id(1);
        //List<Resource> resources2 = resourceController.loadAllByType(trackDb,type);


        //int unlocked2 = resourceController.countUnlockedByType(trackDb,type);
    }

    public void atestControllers() throws Throwable {

        //Testing track Controllers
        TrackController trackController = new TrackController(getContext());
        List<Track> homeTracks = trackController.loadHomeTracks();
        Track secondTrack = new Track();
        secondTrack.setName(homeTracks.get(0).getName());
        secondTrack = trackController.loadTrack(secondTrack);


        //Testing spot Controllers
        SpotController spotController = new SpotController(getContext());
        List<Spot> spots = spotController.loadAllSpots(secondTrack);

        Spot spot = new Spot();
        spot.set_id(spots.get(0).get_id());
        spot = spotController.loadSpot(spot);


        spotController.setUnlocked(secondTrack.getSpots().get(0));
        spotController.setUnlocked(secondTrack.getSpots().get(2));
        int unlocked = spotController.unlockedSpots(secondTrack);

        //Testing Resource Controller
        Type type = new Type();
        type.set_id(1);
        ResourceController resourceController = new ResourceController(getContext());
        //int resUnlocked = resourceController.countUnlocked(secondTrack);
        int resUnlockedByType = resourceController.countUnlockedByType(secondTrack,type);
        int resUnlocked = resourceController.countUnlocked(secondTrack);
        List<Resource> resources = resourceController.loadRes(secondTrack.getSpots().get(0));

        List<Resource> resourceImages = resourceController.loadAllByType(secondTrack,type);
        List<Resource> allRes = resourceController.loadAllRes(homeTracks.get(1));

    }

    public void atestGetNewTracksToDownload() throws Throwable {
        Track t1 = new Track();
        t1.setName("Track 1");
        t1.setVersion(1);

        Track t2 = new Track();
        t2.setName("Track 2");
        t2.setVersion(1);

        Track t3 = new Track();
        t3.setName("Track 3");
        t3.setVersion(2);

        List<Track> tracksOnServerSample = new ArrayList<>();
        tracksOnServerSample.add(t1);
        tracksOnServerSample.add(t2);
        tracksOnServerSample.add(t3);


        TrackController trackController = new TrackController(mContext);
        List<Track> results = trackController.getNewTracksToDownload(tracksOnServerSample);

    }

}
