package wairoadc.godiscover.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.view.fragments.StoryFragment;

/**
 * Created by Ian on 04/02/2015.
 */
public class StoryPageAdapter extends FragmentPagerAdapter {

    private List<Spot> spots;
    private String mapPah;
    private String trackName;

    public StoryPageAdapter(FragmentManager fm,List<Spot> spots,String mapPath, String trackName) {
        super(fm);
        this.spots = spots;
        this.mapPah = mapPath;
        this.trackName = trackName;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(StoryFragment.CURRENT,spots.get(position));
        bundle.putString(StoryFragment.MAP_PATH,mapPah);
        bundle.putString(StoryFragment.TRACK_NAME,trackName);
        StoryFragment storyFragment = new StoryFragment();
        storyFragment.setArguments(bundle);
        return storyFragment;
    }

    @Override
    public int getCount() {
        return spots.size();
    }
}
