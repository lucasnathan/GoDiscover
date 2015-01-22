package wairoadc.godiscover;

import android.test.AndroidTestCase;

import java.io.InputStream;

import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.controller.TrackXMLParser;
import wairoadc.godiscover.model.Track;

/**
 * Created by Xinxula on 22/01/2015.
 */
public class TestXMLParser extends AndroidTestCase {

    public void testXMLParser() throws Throwable {
        InputStream in = getContext().getResources().openRawResource(R.raw.xml_track_example);
        Track track = TrackXMLParser.parse(in);
        TrackController controller = new TrackController(getContext());
        Track trackDb = controller.insertTrack(track);
        assertEquals(trackDb.getSpots().get(0).getName(),track.getSpots().get(0).getName());
    }

}
