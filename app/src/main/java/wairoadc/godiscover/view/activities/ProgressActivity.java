package wairoadc.godiscover.view.activities;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.view.models.ProgressModel;

public class ProgressActivity extends TrackDrawer {

    private ProgressBar audioBar,pictureBar,checkpointBar;
    private int statusAudio,statusCheckpoints,statusPictures = 0;
    private TextView picturesRatio,audioRatio,checkpointRatio;
    private int audioMax, pictureMax, checkpointMax;

    //private ArrayList<ProgressModel> progressModels = new ArrayList<ProgressModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_progress);
        super.onCreate(savedInstanceState);
        //Max
        audioMax = 100;
        pictureMax = 100;
        checkpointMax = 100;
        //status
        statusAudio = 58;
        statusCheckpoints= 80;
        statusPictures = 20;

        //Linking the Progress Bars
        audioBar = (ProgressBar) findViewById(R.id.audio_progress_bar);
        pictureBar = (ProgressBar) findViewById(R.id.pictures_progress_bar);
        checkpointBar = (ProgressBar) findViewById(R.id.checkpoint_progress_bar);

        //Linking the Text views
        picturesRatio = (TextView) findViewById(R.id.pictures_ratio);
        audioRatio = (TextView) findViewById(R.id.audio_ratio);
        checkpointRatio =(TextView) findViewById(R.id.checkpoint_ratio);

        audioBar.setMax(audioMax);
        audioBar.setProgress(statusAudio);

        pictureBar.setMax(pictureMax);
        pictureBar.setProgress(statusPictures);

        checkpointBar.setMax(checkpointMax);
        checkpointBar.setProgress(statusCheckpoints);

        audioRatio.setText(statusAudio+"/"+audioMax);
        picturesRatio.setText(statusPictures+"/"+pictureMax);
        checkpointRatio.setText(statusCheckpoints+"/"+checkpointMax);
    }
}