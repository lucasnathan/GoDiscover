package wairoadc.godiscover.view.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.controller.ResourceController;
import wairoadc.godiscover.controller.SpotController;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Type;

public class ProgressActivity extends TrackDrawer {

    private ProgressBar audioBar,pictureBar,checkpointBar;
    private int statusAudio,statusCheckpoints,statusPictures = 0;
    private TextView picturesLabel,picturesRatio,audioLabel,audioRatio,checkpointRatio;
    private int audioMax, pictureMax, checkpointMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_progress);
        super.onCreate(savedInstanceState);
        ResourceController resourceController = new ResourceController(this);
        SpotController spotController = new SpotController(this);
        Type type = new Type();

        TextView progressTV = (TextView) findViewById(R.id.progressTrackTV);
        progressTV.setText(super.currentTrack.getName());

        type.set_id(1);
        List<Resource> imageResources = resourceController.loadAllByType(super.currentTrack,type,false);
        pictureMax = imageResources.size();
        statusPictures = resourceController.countResoucesByType(super.currentTrack,type,false);

        type.set_id(2);
        List<Resource> soundResources = resourceController.loadAllByType(super.currentTrack,type,false);
        audioMax = soundResources.size();
        statusAudio = resourceController.countResoucesByType(super.currentTrack,type,false);

        checkpointMax = currentTrack.getSpots().size();
        statusCheckpoints= spotController.unlockedSpots(super.currentTrack);

        int progressBarColor = Color.parseColor("#669900");

        checkpointBar = (ProgressBar) findViewById(R.id.checkpoint_progress_bar);
        checkpointBar.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
        checkpointBar.setMax(checkpointMax);
        checkpointBar.setProgress(statusCheckpoints);
        checkpointRatio =(TextView) findViewById(R.id.checkpoint_ratio);
        checkpointRatio.setText(statusCheckpoints+"/"+checkpointMax);

        if(audioMax > 0) {
            audioBar = (ProgressBar) findViewById(R.id.audio_progress_bar);
            audioBar.setVisibility(ProgressBar.VISIBLE);
            audioBar.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
            audioBar.setMax(audioMax);
            audioBar.setProgress(statusAudio);
            audioLabel = (TextView) findViewById(R.id.audio_name_progress);
            audioLabel.setVisibility(TextView.VISIBLE);
            audioRatio = (TextView) findViewById(R.id.audio_ratio);
            audioRatio.setVisibility(TextView.VISIBLE);
            audioRatio.setText(statusAudio+"/"+audioMax);
        }

        if(pictureMax > 0) {
            pictureBar = (ProgressBar) findViewById(R.id.pictures_progress_bar);
            pictureBar.setVisibility(ProgressBar.VISIBLE);
            pictureBar.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
            pictureBar.setMax(pictureMax);
            pictureBar.setProgress(statusPictures);
            picturesLabel = (TextView) findViewById(R.id.pictures_name_progress);
            picturesLabel.setVisibility(TextView.VISIBLE);
            picturesRatio = (TextView) findViewById(R.id.pictures_ratio);
            picturesRatio.setVisibility(TextView.VISIBLE);
            picturesRatio.setText(statusPictures+"/"+pictureMax);
        }
    }
}