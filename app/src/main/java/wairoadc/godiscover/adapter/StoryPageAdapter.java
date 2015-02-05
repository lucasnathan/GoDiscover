package wairoadc.godiscover.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wairoadc.godiscover.view.fragments.StoryFragment;

/**
 * Created by Ian on 04/02/2015.
 */
public class StoryPageAdapter extends FragmentPagerAdapter {

    public StoryPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        Resources resources = context.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(StoryFragment.NameKey, StoryFragment.getNames()[position]);
        bundle.putString(StoryFragment.InfoKey, StoryFragment.getInfos()[position]);
        bundle.putString(StoryFragment.MapKey, StoryFragment.getMap()[position]);
        StoryFragment storyFragment = new StoryFragment();
        storyFragment.setArguments(bundle);
        return storyFragment;
    }

    @Override
    public int getCount() {
        return StoryFragment.getNames().length;
    }
}
