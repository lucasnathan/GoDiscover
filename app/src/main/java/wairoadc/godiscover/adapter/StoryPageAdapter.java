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

    public StoryPageAdapter(FragmentManager fm,List<Spot> spots,String mapPath) {
        super(fm);
        this.spots = spots;
        this.mapPah = mapPath;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(StoryFragment.CURRENT,spots.get(position));
        bundle.putString(StoryFragment.MAP_PATH,mapPah);
        StoryFragment storyFragment = new StoryFragment();
        storyFragment.setArguments(bundle);
        return storyFragment;
    }

    @Override
    public int getCount() {
        return spots.size();
    }
}
