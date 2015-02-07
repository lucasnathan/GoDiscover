package wairoadc.godiscover.view.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.view.activities.StoryActivity;


/**
 * Created by Ian on 03/02/2015.
 */
public class StoryFragment extends Fragment {

    public static final String CURRENT = "current";
    public static final String MAP_PATH = "map_path";

    private Spot spot;
    private String mapPath;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            spot  = bundle.getParcelable(CURRENT);
            mapPath = bundle.getString(MAP_PATH);
            setValues(view);
        }
        return view;
    }

    private void setValues(View view) {
        TextView nameTV = (TextView) view.findViewById(R.id.spotNameTV);
        TextView infoTV = (TextView) view.findViewById(R.id.spotInfoTV);
        if(spot.getUnlocked() == Spot.LOCKED) {
            nameTV.setText("Undiscovered Spot");
            infoTV.setText("Keep exploring to find this spot.");
        } else {
            nameTV.setText(spot.getName());
            infoTV.setText(spot.getInformation());
            int x = spot.getX();
            int y = spot.getY();

            ImageView mapIV = (ImageView) view.findViewById(R.id.spotMapIV);
            String imageFullPath = getActivity().getFilesDir().getPath() + mapPath;
            Bitmap bitmap = BitmapFactory.decodeFile(imageFullPath);
            Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(tempBitmap);
            canvas.drawBitmap(bitmap, 0, 0, null);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, 5, paint);
            if (null != bitmap) {
                mapIV.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
            }
        }
    }
}
