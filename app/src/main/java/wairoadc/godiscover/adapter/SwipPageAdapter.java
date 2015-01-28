package wairoadc.godiscover.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lucas on 27/01/2015.
 */
public class SwipPageAdapter extends PagerAdapter{

    Activity activity;
    int imageArray[];

    public SwipPageAdapter(Activity activity,int [] imageArray){
        imageArray = imageArray;
        activity = activity;
    }
    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
