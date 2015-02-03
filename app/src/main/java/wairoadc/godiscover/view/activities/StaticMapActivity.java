package wairoadc.godiscover.view.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;
import wairoadc.godiscover.R;

public class StaticMapActivity extends TrackDrawer {

    private PhotoViewAttacher mAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_static_map);
        super.onCreate(savedInstanceState);


        ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);

        Drawable bitmap = getResources().getDrawable(R.drawable.big_map);
        mImageView.setImageDrawable(bitmap);

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

