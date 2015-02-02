package wairoadc.godiscover.view.activities;


import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import wairoadc.godiscover.R;

public class StaticMapActivity extends TrackDrawer {

    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
    static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";

    private TextView mCurrMatrixTv;

    private PhotoViewAttacher mAttacher;

    private Toast mCurrentToast;

    private Matrix mCurrentDisplayMatrix = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_static_map);
        super.onCreate(savedInstanceState);


        ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);
        mCurrMatrixTv = (TextView) findViewById(R.id.tv_current_matrix);

        Drawable bitmap = getResources().getDrawable(R.drawable.big_map);
        mImageView.setImageDrawable(bitmap);

        // The MAGIC happens here!
        mAttacher = new PhotoViewAttacher(mImageView);

        // Lets attach some listeners, not required though!
        mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
        mAttacher.setOnPhotoTapListener(new PhotoTapListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Need to call clean-up
        mAttacher.cleanup();
    }

    private class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }

    private void showToast(CharSequence text) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(StaticMapActivity.this, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    private class MatrixChangeListener implements OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
            mCurrMatrixTv.setText(rect.toString());
        }
    }
}

