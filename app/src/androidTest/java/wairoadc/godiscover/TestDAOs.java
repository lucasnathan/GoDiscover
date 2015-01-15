package wairoadc.godiscover;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.dao.TrackDAO;
import wairoadc.godiscover.dao.TypeDAO;
import wairoadc.godiscover.database.TrackTable;
import wairoadc.godiscover.database.TypeTable;
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
}
