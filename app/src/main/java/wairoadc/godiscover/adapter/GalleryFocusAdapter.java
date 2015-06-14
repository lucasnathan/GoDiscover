package wairoadc.godiscover.adapter;

/**
 * Created by Lucas on 8/02/2015.
 */
import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import wairoadc.godiscover.R;
import wairoadc.godiscover.services.BitmapWorkerTask;

public class GalleryFocusAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagePaths;
    private LayoutInflater inflater;
    private static final String ADAPTER_TAG = "GalleryFocusAdapter";

    // constructor
    public GalleryFocusAdapter(Activity activity,List<String> imagePaths) {
        this.activity = activity;
        this.imagePaths = imagePaths;

    }

    @Override
    public int getCount() {
        return this.imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_focus, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        Log.i("GalleryFocus","size: "+imgDisplay.getWidth()+" "+imgDisplay.getHeight());
        if(null != imagePaths.get(position))//unlocked spot show focus picture otherwise don't show
            loadBitmap(activity.getFilesDir()+imagePaths.get(position),imgDisplay);
        else //locked image show locked icon
            imgDisplay.setImageResource(R.drawable.locked_resource);
        container.addView(viewLayout);

        return viewLayout;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if(imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if(drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable)drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    private boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if(bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.getData();
            if(bitmapData == null || bitmapData == "" || !bitmapData.equals(data)) {
                Log.i("GalleryFocusAdapter","Image Load cancelled");
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    public void loadBitmap(String currentImage, ImageView imageView) {
        final Bitmap bitmap = BitmapWorkerTask.getBitmapFromMemCache(currentImage);
        if(bitmap != null) {
            Log.i(ADAPTER_TAG,"This image is in cache");
            imageView.setImageBitmap(bitmap);
        } else {
            if(cancelPotentialWork(currentImage,imageView)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable = new AsyncDrawable(this.activity.getResources(),null,task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(currentImage);
            }
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskWeakReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res,bitmap);
            bitmapWorkerTaskWeakReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskWeakReference.get();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}