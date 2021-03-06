package wairoadc.godiscover.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.GridViewAdapter;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.view.models.ImageItem;

public class MainActivity extends Activity{

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public final static String TRACK_EXTRA = "track_extra";

    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    private ArrayList<Track> listHomeTracks;
    private ArrayList imageItems;
    private boolean startUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gridView = (GridView) findViewById(R.id.gridView);

        if (savedInstanceState == null) {
            TrackController trackController = new TrackController(this);
            listHomeTracks = trackController.loadHomeTracks();
            imageItems = getData();
            startUp = false;
        } else {
            listHomeTracks = savedInstanceState.getParcelableArrayList("listHomeTracks");
            imageItems = savedInstanceState.getParcelableArrayList("imageItems");
            startUp = false;
        }

        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, imageItems);

        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                makeDialog(listHomeTracks.get(position));
            }
        });
        Log.i(LOG_TAG, "onCreate");
    }

    public void makeDialog(Track track) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Do you want directions?");

        // set dialog message
        alertDialogBuilder
                .setMessage("If so, show me on google maps")
                .setCancelable(false)
                .setPositiveButton("Yes, to maps",new RedirectToMaps(track))
                .setNegativeButton("No, open the track",new GoToInfo(track));

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    //Will get Track information from the database
    private ArrayList getData() {
        final ArrayList imageItems = new ArrayList();
        if(null != listHomeTracks && listHomeTracks.size() > 0) {
            // retrieve String drawable array
            for(Track track : listHomeTracks) {
                String imageFullPath = getFilesDir().getPath()+track.getResource();
                Log.i(LOG_TAG,imageFullPath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageFullPath);
                if(null != bitmap) {
                    imageItems.add(new ImageItem(bitmap, track.getName()));
                }
            }
        }
        return imageItems;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (startUp) {
            TrackController trackController = new TrackController(this);
            listHomeTracks = trackController.loadHomeTracks();
            imageItems = getData();
        }

        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, imageItems);
        gridView.setAdapter(customGridAdapter);
        Log.i(LOG_TAG, "onResume");
    }


    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG, "onDestroy");
        super.onStop();
        listHomeTracks = null;
        imageItems = null;
        System.gc();
    }

    //Positive Answer
    private final class RedirectToMaps implements DialogInterface.OnClickListener {
        private Track track;
        public RedirectToMaps(Track track) { this.track = track; }
        public void onClick(DialogInterface dialog, int which) {
            Intent intent= new Intent(MainActivity.this,InformationActivity.class);
            TrackController trackController = new TrackController(getApplicationContext());
            track = trackController.loadTrack(track);
            openLocationInMap(track);
        }
    }

    //Negative answer
    private final class GoToInfo implements DialogInterface.OnClickListener {
        private Track track;
        public GoToInfo(Track track) {
            this.track = track;
        }
        public void onClick(DialogInterface dialog, int which) {
            Intent intent= new Intent(MainActivity.this,InformationActivity.class);
            TrackController trackController = new TrackController(getApplicationContext());
            track = trackController.loadTrack(track);
            //Creates an empty list for spots and resources in order to not break the parcel
            intent.putExtra(TRACK_EXTRA, track);
            startActivity(intent);
        }
    }
    private void openLocationInMap(Track track) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);

        String location = sharedPrefs.getString(
                "location",
                track.getSpots().get(0).getLatitude() + ", " + track.getSpots().get(0).getLongitude());

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("google.navigation:q=").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_map),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.action_settings:
                if (!this.getClass().getSimpleName().equals("SettingsActivity")){
                    intent = new Intent(this.getBaseContext(),SettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                return true;
            case R.id.action_refresh:
                if (!this.getClass().getSimpleName().equals("RefreshActivity")){
                    intent = new Intent(this.getBaseContext(),RefreshActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("listHomeTracks", listHomeTracks);
        outState.putParcelableArrayList("imageItems", imageItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}