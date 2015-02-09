package wairoadc.godiscover.adapter;

/**
 * Created by Lucas on 5/02/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.view.fragments.AudioFragment;
import wairoadc.godiscover.view.fragments.PicturesFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    int type;
    private String tabTitles[] = new String[] { "Pictures", "Audio" };
    private Context context;
    private List<Resource> imageResources;
    private List<Resource> audioResources;
    //Type means gallery from spot or from the track
    public TabsPagerAdapter(FragmentManager fm, Context context,int type,List<Resource> imageResources,List<Resource> audioResources) {
        super(fm);
        this.context = context;
        this.type = type;
        this.imageResources = imageResources;
        this.audioResources = audioResources;
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return PicturesFragment.newInstance(imageResources);

            case 1:
                return AudioFragment.newInstance(audioResources);

            default:
                return PicturesFragment.newInstance(imageResources);
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}