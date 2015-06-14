package wairoadc.godiscover.controller;

import android.content.Context;

import wairoadc.godiscover.model.Track;

/**
 * Created by Lucas on 7/01/2015.
 */

// Controller that manages the scan functionality of the application (scanning a QR Code and retrieving some value).
public class ScanController {

    private Context context;

    public ScanController(Context context) {
        this.context = context;
    }

    public Track scanCode(String trackName, int spotIndex) {
        TrackController trackController = new TrackController(context);
        Track resultTrack = new Track();
        resultTrack.setName(trackName);
        resultTrack = trackController.loadTrack(resultTrack);
        if(resultTrack != null) {
            try {
                SpotController spotController = new SpotController(context);
                spotController.setUnlocked(resultTrack.getSpots().get(spotIndex));
                return resultTrack;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
