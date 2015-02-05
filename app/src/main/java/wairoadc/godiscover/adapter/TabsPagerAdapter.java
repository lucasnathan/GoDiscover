package wairoadc.godiscover.adapter;

/**
 * Created by Lucas on 5/02/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wairoadc.godiscover.view.fragments.AudioFragment;
import wairoadc.godiscover.view.fragments.PicturesFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {




    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Pictures", "Audio" };
    private Context context;
    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PicturesFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}