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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import wairoadc.godiscover.R;
import wairoadc.godiscover.services.BitmapWorkerTask;
import wairoadc.godiscover.utilities.GradientOverImageDrawable;

public class GalleryFocusAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagePaths;
    private LayoutInflater inflater;
    private List<String> imageStories;
    private static final String ADAPTER_TAG = "GalleryFocusAdapter";

    // constructor
    public GalleryFocusAdapter(Activity activity,List<String> imagePaths,List<String> imageInfo) {
        this.activity = activity;
        this.imageStories = imageInfo;
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
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imgDisplay;
        TextView imgInfo;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_focus, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        imgInfo = (TextView) viewLayout.findViewById(R.id.captionTextView);
        //Log.i("GalleryFocus","size: "+imgDisplay.getWidth()+" "+imgDisplay.getHeight());
        if(null != imagePaths.get(position)) {//unlocked spot show focus picture otherwise don't show
            imgInfo.setText(imageStories.get(position));
            loadBitmap(activity.getFilesDir() + imagePaths.get(position), imgDisplay);
            imgDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("GalleryFocus", "Image click: " + imageStories.get(position));
                }
            });
        } else { //locked image show locked icon
            imgInfo.setText("Go Discover to Unlock this Image");
            int gradientStartColor = Color.argb(0, 0, 0, 0);
            int gradientEndColor = Color.argb(155, 0, 0, 0);
            Bitmap lockedBitmap = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.locked_resource);
            GradientOverImageDrawable gradientDrawable = new GradientOverImageDrawable(this.activity.getResources(), lockedBitmap);
            gradientDrawable.setGradientColors(gradientStartColor, gradientEndColor);
            imgDisplay.setImageDrawable(gradientDrawable);
        }
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
        int gradientStartColor = Color.argb(0, 0, 0, 0);
        int gradientEndColor = Color.argb(155, 0, 0, 0);
        if(bitmap != null) {
            //Log.i(ADAPTER_TAG,"This image is in cache");
            GradientOverImageDrawable gradientDrawable = new GradientOverImageDrawable(this.activity.getResources(), bitmap);
            gradientDrawable.setGradientColors(gradientStartColor, gradientEndColor);
            imageView.setImageDrawable(gradientDrawable);
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
        container.removeView((RelativeLayout) object);
    }
}