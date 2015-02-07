package wairoadc.godiscover.view.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wairoadc.godiscover.R;


/**
 * Created by Ian on 03/02/2015.
 */
public class StoryFragment extends Fragment {

    public static final String NameKey = "nameKey";
    public static final String InfoKey = "infoKey";
    public static final String MapKey = "mapKey";
    public static final String XKey = "xKey";
    public static final String YKey = "yKey";

    static String[] spotNames;
    static String[] spotInfos;
    static String[] spotMaps;
    static int[] spotX;
    static int[] spotY;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString(NameKey);
            String info = bundle.getString(InfoKey);
            String map = bundle.getString(MapKey);
            Integer x = bundle.getInt(XKey);
            Integer y = bundle.getInt(YKey);
            setValues(view, name, info, map, x, y);
        }
        return view;
    }

    public static void setData(String[] names, String[] infos, String[] maps, int[] x, int[] y) {
        spotNames = names;
        spotInfos = infos;
        spotMaps = maps;
        spotX = x;
        spotY = y;
    }

    public static String[] getNames() {
        return spotNames;
    }

    public static String[] getInfos() {
        return spotInfos;
    }

    public static String[] getMap() {
        return spotMaps;
    }

    public static int[] getX() {
        return spotX;
    }

    public static int[] getY() {
        return spotY;
    }

    private void setValues(View view, String name, String info, String map, int x, int y) {
        TextView nameTV = (TextView) view.findViewById(R.id.spotNameTV);
        nameTV.setText(name);
        TextView infoTV = (TextView) view.findViewById(R.id.spotInfoTV);
        infoTV.setText(info);
        ImageView mapIV = (ImageView) view.findViewById(R.id.spotMapIV);
        String imageFullPath = getActivity().getFilesDir().getPath()+map;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFullPath);
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, 5, paint);
        if(null != bitmap) {
            mapIV.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
        }
    }
}
