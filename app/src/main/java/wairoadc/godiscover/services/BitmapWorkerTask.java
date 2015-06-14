package wairoadc.godiscover.services;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private static LruCache<String,Bitmap> mMemoryCache;
    private static final String ASYNC_TASK_TAG= "BitmapWorkerTask";

    private String data;

    public String getData() { return this.data;}

    static {
        Log.i(ASYNC_TASK_TAG, "new LRU cache Created");
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/4;
        BitmapWorkerTask.mMemoryCache = new LruCache<String,Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount()/1024;
            }
        };
    }

    public BitmapWorkerTask(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        //Normal draw
        if(params.length == 1) {
            data = params[0];
            final Bitmap bitmap = this.decodeSampledBitmapFromFile(data, 200, 200);
            BitmapWorkerTask.addBitmapToMemoryCache(data,bitmap);
            return bitmap;
        }
        //draw points on map
        else {
            data = params[0];
            int x = Integer.parseInt(params[1]);
            int y = Integer.parseInt(params[2]);
            final Bitmap bitmap = this.decodeSampledBitmapforSize(data);
            BitmapWorkerTask.addBitmapToMemoryCache(data, bitmap);
            final Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
            final Canvas canvas = new Canvas(tempBitmap);
            canvas.drawBitmap(bitmap, 0, 0, null);
            final Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, 10, paint);
            return tempBitmap;
        }
    }

    private static Bitmap decodeSampledBitmapFromFile(String file,int reqWidth,int reqHeight) {
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file,options);
    }

    private static Bitmap decodeSampledBitmapforSize(String file) {
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file,options);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file,options);
    }

    public static LruCache<String,Bitmap> getmMemoryCache() {
        return BitmapWorkerTask.mMemoryCache;
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        Log.i(ASYNC_TASK_TAG,"Adding Image "+key+" to cache");
        if(getBitmapFromMemCache(key)==null) {
            mMemoryCache.put(key,bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}