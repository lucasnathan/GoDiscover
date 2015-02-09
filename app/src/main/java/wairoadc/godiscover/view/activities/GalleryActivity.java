package wairoadc.godiscover.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.TabsPagerAdapter;
import wairoadc.godiscover.controller.SpotController;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Type;
import wairoadc.godiscover.view.fragments.SlidingTabLayout;
import wairoadc.godiscover.view.fragments.StoryFragment;


/**
 * Created by Lucas on 5/02/2015.
 */
public class GalleryActivity extends TrackDrawer {

    private int galleryMode;
    private List<String> imagePaths;

    public static final String GALLERY_TYPE = "galleryType";

    public static final int TRACK_MODE = 0;
    public static final int SPOT_MODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_tab);
        super.onCreate(savedInstanceState);
        Intent intent  = getIntent();
        galleryMode = intent.getIntExtra(GALLERY_TYPE,TRACK_MODE);
        if(galleryMode == TRACK_MODE) {
            TrackController trackController = new TrackController(this);
            imagePaths = trackController.getAllTrackPathsByType(currentTrack,new Type(1));
        } else if (galleryMode == SPOT_MODE) {
            Spot spot = intent.getParcelableExtra(StoryFragment.SPOT_EXTRA);
            SpotController spotController = new SpotController(this);
            imagePaths = spotController.getAllSpotPathsByType(spot,new Type(1));
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(),GalleryActivity.this, galleryMode,imagePaths));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        // Customize tab color
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.green);
            }
        });
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }
    private boolean enabled = false;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}