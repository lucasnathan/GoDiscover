package wairoadc.godiscover.adapter;

/**
 * Created by Lucas on 8/02/2015.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import wairoadc.godiscover.R;
import wairoadc.godiscover.view.activities.GalleryFocusActivity;

public class GalleryGridAdapter extends BaseAdapter{

    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;
    public int _position;
    public GalleryGridAdapter(Activity activity, ArrayList<String> filePaths,
                                int imageWidth, int type) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setId(R.id.my_image_view);
        // get screen dimensions
        Bitmap image = decodeFile(_filePaths.get(position), imageWidth,
                imageWidth);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));
        imageView.setImageBitmap(image);
        //imageView.setOnClickListener(this,position);
        // image view click listener
        _position = position;
        imageView.setOnTouchListener(new OnTouchImage(position));
        imageView.setOnClickListener(new OnImageClickListener(position));
        imageView.setFocusable(false);
        return imageView;
    }
    class OnTouchImage implements View.OnTouchListener{

        int _position;

        public OnTouchImage(int position){
            this._position = position;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_POINTER_DOWN == event.getAction()){
                Intent i = new Intent(_activity, GalleryFocusActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                i.putExtra("position", _position);
                _activity.startActivity(i);
            }
            return false;
        }
    }
    class OnImageClickListener implements OnClickListener {

        int _position;

        // constructor
        public OnImageClickListener(int position) {
            this._position = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(_activity, GalleryFocusActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            i.putExtra("position", _position);
            _activity.startActivity(i);
        }

    }

    /*
     * Resizing image size
     */
    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {

            File f = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}