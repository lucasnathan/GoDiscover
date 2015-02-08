package wairoadc.godiscover.services;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;


public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private String data;

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
            return BitmapFactory.decodeFile(data);
        }
        //draw points on map
        else {
            data = params[0];
            int x = Integer.parseInt(params[1]);
            int y = Integer.parseInt(params[2]);
            Bitmap bitmap = BitmapFactory.decodeFile(data);
            Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(tempBitmap);
            canvas.drawBitmap(bitmap, 0, 0, null);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, 5, paint);
            return tempBitmap;
        }
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}