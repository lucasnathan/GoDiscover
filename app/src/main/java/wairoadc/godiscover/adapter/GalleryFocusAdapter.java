package wairoadc.godiscover.adapter;

/**
 * Created by Lucas on 8/02/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import wairoadc.godiscover.R;
import wairoadc.godiscover.services.BitmapWorkerTask;

public class GalleryFocusAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagePaths;
    private LayoutInflater inflater;
    private ArrayList<String> _story;

    // constructor
    public GalleryFocusAdapter(Activity activity,List<String> imagePaths) {
        this.activity = activity;
        //this._story = story;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this.imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        TextView storyText;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_focus, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        Log.i("GalleryFocus","size: "+imgDisplay.getWidth()+" "+imgDisplay.getHeight());

        loadBitmap(activity.getFilesDir()+imagePaths.get(position),imgDisplay);
        container.addView(viewLayout);

        return viewLayout;
    }

    public void loadBitmap(String currentImage, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(currentImage);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}