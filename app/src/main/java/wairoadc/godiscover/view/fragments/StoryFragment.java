package wairoadc.godiscover.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.services.BitmapWorkerTask;
import wairoadc.godiscover.utilities.Utility;
import wairoadc.godiscover.view.activities.GalleryActivity;
import wairoadc.godiscover.view.activities.MainActivity;
import wairoadc.godiscover.view.activities.StoryActivity;
import wairoadc.godiscover.view.activities.TrackDrawer;


/**
 * Created by Ian on 03/02/2015.
 */
public class StoryFragment extends Fragment {

    public static final String CURRENT = "current";
    public static final String MAP_PATH = "map_path";
    public static final String SPOT_EXTRA = "spot_extra";
    public static final String TRACK_NAME = "track_name";

    private Spot spot;
    private String trackName;
    private String mapPath;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            spot  = bundle.getParcelable(CURRENT);
            mapPath = bundle.getString(MAP_PATH);
            trackName = bundle.getString(TRACK_NAME);
            setValues(view);
        }
        return view;
    }

    public void loadBitmap(String currentImage, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(currentImage,String.valueOf(spot.getX()),String.valueOf(spot.getY()));
    }

    private void setValues(View view) {
        TextView nameTV = (TextView) view.findViewById(R.id.spotNameTV);
        TextView infoTV = (TextView) view.findViewById(R.id.spotInfoTV);
        TextView trackTV = (TextView) view.findViewById(R.id.spotTitleTV);
        trackTV.setText(trackName);
        if(spot.getUnlocked() == Spot.LOCKED) {
            nameTV.setText("Undiscovered Spot");
            infoTV.setText("Keep exploring to find this spot.");
        } else {
            nameTV.setText(spot.getName());
            infoTV.setText(spot.getInformation());

            ImageView mapIV = (ImageView) view.findViewById(R.id.spotMapIV);
            String imageFullPath = getActivity().getFilesDir().getPath() + mapPath;
            loadBitmap(imageFullPath,mapIV);
            ImageButton imageButton = (ImageButton)view.findViewById(R.id.spotGalleryImgBtn);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),GalleryActivity.class);
                    TrackDrawer activity = (TrackDrawer)getActivity();
                    if(null != activity.getCurrentTrack())
                        intent.putExtra(MainActivity.TRACK_EXTRA,activity.getCurrentTrack());
                    intent.putExtra(SPOT_EXTRA,spot);
                    intent.putExtra(GalleryActivity.GALLERY_TYPE,GalleryActivity.SPOT_MODE);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }
}
