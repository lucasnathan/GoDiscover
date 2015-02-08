package wairoadc.godiscover.adapter;

/**
 * Created by Lucas on 8/02/2015.
 */


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import wairoadc.godiscover.R;

import wairoadc.godiscover.utilities.Utility;
import wairoadc.godiscover.view.activities.GalleryFocusActivity;
import wairoadc.godiscover.view.fragments.PicturesFragment;

public class GalleryGridAdapter extends BaseAdapter{

    private Activity activity;
    private List<String> filePaths = new ArrayList<String>();
    private int imageWidth;
    public int position;
    public GalleryGridAdapter(Activity activity, List<String> filePaths,
                                int imageWidth, int type) {
        this.activity = activity;
        this.filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this.filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(activity);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));

        loadBitmap(activity.getFilesDir()+filePaths.get(position),imageView);
        this.position = position;
        imageView.setFocusable(true);
        imageView.setFocusableInTouchMode(true);
        imageView.setClickable(true);

        imageView.setOnClickListener(new OnImageClickListener(position));
        imageView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    v.performClick();
                }
            }
        });

        return imageView;
    }

    class OnImageClickListener implements OnClickListener {

        int position;

        // constructor
        public OnImageClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Log.i("GalleryAdapter", "Entered onClick method");
            Intent i = new Intent(activity, GalleryFocusActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            i.putExtra("position", position);
            i.putStringArrayListExtra(PicturesFragment.IMAGE_LIST,(ArrayList<String>)filePaths);
            activity.startActivity(i);
        }

    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == null || bitmapData.equals(data)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    public void loadBitmap(String resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            Bitmap mPlaceHolderBitmap = BitmapFactory.decodeResource(activity.getResources(),R.drawable.btn_cab_done_default_go_theme);

            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(activity.getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }


    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            return Utility.decodeSampledBitmapFromResource(data, 100, 100);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}