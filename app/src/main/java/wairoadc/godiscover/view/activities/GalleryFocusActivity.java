package wairoadc.godiscover.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.GalleryFocusAdapter;
import wairoadc.godiscover.utilities.UtilsGrid;

/**
 * Created by Lucas on 8/02/2015.
 */
public class GalleryFocusActivity extends TrackDrawer {

    private UtilsGrid utils;
    private GalleryFocusAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gallery_focus);
        super.onCreate(savedInstanceState);


        viewPager = (ViewPager) findViewById(R.id.pager);

        utils = new UtilsGrid(getApplicationContext());

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        adapter = new GalleryFocusAdapter(GalleryFocusActivity.this,
                utils.getFilePaths());

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}