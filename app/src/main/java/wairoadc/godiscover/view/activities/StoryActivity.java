package wairoadc.godiscover.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.StoryPageAdapter;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.view.fragments.StoryFragment;

public class StoryActivity extends TrackDrawer {
    StoryPageAdapter storyPageAdapter;
    ViewPager mViewPager;
    int startingItem;

    public static String CURRENT_SPOT = "currentSpot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_story);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        startingItem = intent.getIntExtra(CURRENT_SPOT,0);
        storyPageAdapter = new StoryPageAdapter(getSupportFragmentManager(),currentTrack.getSpots(),currentTrack.getMapPath(), currentTrack.getName());
        storyPageAdapter.notifyDataSetChanged();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(storyPageAdapter);
        mViewPager.setCurrentItem(startingItem);
    }
}