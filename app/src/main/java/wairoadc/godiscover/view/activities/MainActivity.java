package wairoadc.godiscover.view.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.GridViewAdapter;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.view.models.ImageItem;

public class MainActivity extends HomeDrawer {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    private TextView emptyGrid;
    // constant for identifying the dialog
    private static final int DIALOG_ALERT = 10;



    // adjust this method if you have more than
    // one button pointing to this method
    public void onClick(View view) {
        showDialog(DIALOG_ALERT);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        this.gridView = (GridView) findViewById(R.id.gridView);
        this.emptyGrid = (TextView) LayoutInflater.from(this).inflate(R.layout.empty,null);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.grid_container);
        relativeLayout.addView(emptyGrid,0);
        this.gridView.setEmptyView(emptyGrid);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, position + "#Selected",Toast.LENGTH_LONG).show();
                showDialog(DIALOG_ALERT);
            }

        });
    }

    //Will get Track information from the database
    private ArrayList getData() {
        TrackController trackController = new TrackController(this);
        List<Track> listHomeTracks = trackController.loadHomeTracks();
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
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
        gridView.setAdapter(customGridAdapter);
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want directions?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes, I do", new RedirectToMaps());
                builder.setNegativeButton("No, thanks", new GoToInfo());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }
//Negative Answer
    private final class RedirectToMaps implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            openPreferredLocationInMap();
        }
    }
//Positive answer
    private final class GoToInfo implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            //MainActivity.this.finish();
            Intent intent= new Intent(MainActivity.this,InformationActivity.class);
            startActivity(intent);

        }
    }
    private void openPreferredLocationInMap() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(
                "location",
                "-39.033064, 177.419115");

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
}