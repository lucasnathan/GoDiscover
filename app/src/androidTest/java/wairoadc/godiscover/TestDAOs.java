package wairoadc.godiscover;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wairoadc.godiscover.dao.SpotDAO;
import wairoadc.godiscover.dao.TrackDAO;
import wairoadc.godiscover.dao.TypeDAO;
import wairoadc.godiscover.database.SpotTable;
import wairoadc.godiscover.database.TrackTable;
import wairoadc.godiscover.database.TypeTable;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 14/01/2015.
 */
public class TestDAOs extends AndroidTestCase {

    public void testTypeDAO() throws Throwable{
        TypeDAO typeDAO = new TypeDAO(mContext);

        //Test if Database is opening
        SQLiteDatabase db = typeDAO.open();
        assertEquals(true,db.isOpen());
        //delete all the data
        db.delete(TypeTable.TYPE_TABLE,null,null);

        //Test insertType()
        Type type = new Type();
        type.setName("audio");
        Type nType = typeDAO.insertType(type);
        assertEquals(nType.getName(),"audio");

        //Testing getById()
        type = typeDAO.getById(nType);
        assertEquals(nType.toString(),type.toString());

        //Test getALL()
        List<Type> types = new ArrayList<Type>();
        List<Type> ntypes;
        types.add(type);
        type = new Type();
        type.setName("pictures");
        types.add(typeDAO.insertType(type));
        ntypes = typeDAO.getAllTypes();
        assertEquals(ntypes.toString(),types.toString());

        //Test deleteType()
        types = new ArrayList<Type>();
        types.add(nType);
        typeDAO.deleteType(ntypes.get(1));
        ntypes = typeDAO.getAllTypes();
        assertEquals(ntypes.toString(),types.toString());

        db.close();
    }
    public void testTrackDAO() throws Throwable{
        TrackDAO trackDAO = new TrackDAO(mContext);

        //Test if Database is opening
        SQLiteDatabase db = trackDAO.open();
        assertEquals(true,db.isOpen());
        Log.i("TrackDAO","Database opens");
        //delete all the data
        db.delete(TrackTable.TRACK_TABLE,null,null);

        //Test insertTrack
        Track track = new Track();
        //inserting in the model
        track.setName("wairoaWalks");
        track.setDescription("Beautiful place");
        track.setMapPath("www.map.nz");
        track.setResource("www.images.nz");
        track.setVersion(1);

        Track nTrack = trackDAO.insertTrack(track);
        track.setId(nTrack.getId());
        assertEquals(track.toString(), nTrack.toString());
        Log.i("TrackDAO","track inserted\n" + "ntrack = " + nTrack + "\ntrack = " + track);

        //Test getById()
        track = trackDAO.getById(nTrack);
        assertEquals(nTrack.toString(),track.toString());

        //Test getAll()
        List<Track> tracks = new ArrayList<Track>();
        List<Track> nTracks;
        tracks.add(track);
        track = new Track();
        track.setName("MahiaWalks");
        track.setDescription("Beaches");
        track.setMapPath("www.map.nz");
        track.setResource("www.images.nz");
        track.setVersion(1);
        tracks.add(trackDAO.insertTrack(track));
        nTracks = trackDAO.getAllTracks();
        assertEquals(nTracks.toString(),tracks.toString());

        //Test deleteTrack()
        tracks = new ArrayList<Track>();
        tracks.add(nTrack);
        trackDAO.deleteTrack(nTracks.get(1));
        nTracks = trackDAO.getAllTracks();
        assertEquals(nTracks.toString(),tracks.toString());
        
        db.close();
        Log.i("TrackDAO","Database closes");
    }
    public void testSpotDAO() throws Throwable{
        SpotDAO spotDAO = new SpotDAO(mContext);
        TrackDAO trackDAO = new TrackDAO(mContext);
        
        //Test if Database is opening
        SQLiteDatabase db = spotDAO.open();
        SQLiteDatabase db1 = trackDAO.open();
        assertEquals(true,db.isOpen());
        Log.i("SpotDAO","Database opens");
        //delete all the data
        db.delete(SpotTable.SPOT_TABLE,null,null);
        db1.delete(TrackTable.TRACK_TABLE,null,null);

        //Test insertSpot
        //insertTrack
        Track track = new Track();
        //inserting in the model
        track.setName("wairoaWalks");
        track.setDescription("Beautiful place");
        track.setMapPath("www.map.nz");
        track.setResource("www.images.nz");
        track.setVersion(1);

        Track nTrack = trackDAO.insertTrack(track);
        track.setId(nTrack.getId());
        assertEquals(track.toString(), nTrack.toString());
        Log.i("TrackDAO","track inserted\n" + "ntrack = " + nTrack + "\ntrack = " + track);

        //inserting Spot
        Spot spot = new Spot();
        //inserting in the model
        spot.setName("spot1");
        spot.setInformation("many things happened here");
        spot.setUnlocked(0);
        spot.setX(123);
        spot.setY(254);
        spot.setLatitude(15545.55);
        spot.setLongitude(5544.55);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse("2015-01-16 12:50:23");
            spot.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Spot nSpot = spotDAO.insertSpot(spot,nTrack);
        spot.setId(nSpot.getId());
        assertEquals(spot.toString(),nSpot.toString());

        db.close();
    }
}
