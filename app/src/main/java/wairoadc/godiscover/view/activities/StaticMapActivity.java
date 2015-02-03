package wairoadc.godiscover.view.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;
import wairoadc.godiscover.R;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;

public class StaticMapActivity extends TrackDrawer {

    private PhotoViewAttacher mAttacher;

    private Track track;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_static_map);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        track = intent.getParcelableExtra(MainActivity.TRACK_EXTRA);

        ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);

        String imageFullPath = getFilesDir().getPath()+track.getMapPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFullPath);
        if(null != bitmap) {
            mImageView.setImageBitmap(bitmap);
        }

        // The MAGIC happens here!
        mAttacher = new PhotoViewAttacher(mImageView);
        mImageView.measure(0,0);
        float centerX = (float) mImageView.getMeasuredWidth()/2;
        float centerY =(float) mImageView.getMeasuredHeight()/2;
        //Write Center of image
        //Toast.makeText(this, centerX+"/"+centerY,Toast.LENGTH_LONG).show();
        mAttacher.setScale(1.8f,centerX ,centerY,true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Need to call clean-up
        mAttacher.cleanup();
    }
}

