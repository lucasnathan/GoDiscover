package wairoadc.godiscover.view.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.TabsPagerAdapter;
import wairoadc.godiscover.view.activities.TrackDrawer;


/**
 * Created by Lucas on 5/02/2015.
 */
public class GalleryFragment extends TrackDrawer {

    // Tab titles
    private String[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab);
        super.onCreate(savedInstanceState);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(),
                GalleryFragment.this));

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
}
