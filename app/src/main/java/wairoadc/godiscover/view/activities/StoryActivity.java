package wairoadc.godiscover.view.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.StoryPageAdapter;
import wairoadc.godiscover.view.fragments.StoryFragment;

public class StoryActivity extends TrackDrawer {
    StoryPageAdapter storyPageAdapter;
    ViewPager mViewPager;
    String[] spotNames;
    String[] spotInfos;
    String[] spotMaps;
    int spotsSize;

    protected void setTrackInfo() {
        spotsSize = 0;
        for (int i = 0; i < super.currentTrack.getSpots().size(); i++) {
            if (super.currentTrack.getSpots().get(i).getUnlocked() == 1) {
                spotsSize++;
            }
        }
        spotNames = new String[spotsSize];
        spotInfos = new String[spotsSize];
        spotMaps = new String[spotsSize];
        int it = 0;
        for (int i = 0; i < super.currentTrack.getSpots().size(); i++) {
            if (super.currentTrack.getSpots().get(i).getUnlocked() == 1) {
                spotNames[it] = super.currentTrack.getSpots().get(i).getName();
                spotInfos[it] = super.currentTrack.getSpots().get(i).getInformation();
                spotMaps[it] = super.currentTrack.getMapPath();
                it++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_story);
        super.onCreate(savedInstanceState);
        setTrackInfo();
        StoryFragment.setData(spotNames, spotInfos, spotMaps);
        storyPageAdapter = new StoryPageAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(storyPageAdapter);
    }

}