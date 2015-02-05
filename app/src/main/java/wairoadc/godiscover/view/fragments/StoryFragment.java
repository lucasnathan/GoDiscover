package wairoadc.godiscover.view.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import wairoadc.godiscover.R;


/**
 * Created by Ian on 03/02/2015.
 */
public class StoryFragment extends Fragment {

    public static final String NameKey = "namekey";
    public static final String InfoKey = "infokey";
    public static final String MapKey = "mapKey";

    static String[] spotNames;
    static String[] spotInfos;
    static String[] spotMaps;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString(NameKey);
            String info = bundle.getString(InfoKey);
            String map = bundle.getString(MapKey);
            setValues(view, name, info, map);
        }
        return view;
    }

    public static void setData(String[] names, String[] infos, String[] maps) {
        spotNames = names;
        spotInfos = infos;
        spotMaps = maps;
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

    private void setValues(View view, String name, String info, String map) {
        TextView nameTV = (TextView) view.findViewById(R.id.spotNameTV);
        nameTV.setText(name);
        TextView infoTV = (TextView) view.findViewById(R.id.spotInfoTV);
        infoTV.setText(info);
        ImageView mapIV = (ImageView) view.findViewById(R.id.spotMapIV);
        String imageFullPath = getActivity().getFilesDir().getPath()+map;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFullPath);
        if(null != bitmap) {
            mapIV.setImageBitmap(bitmap);
        }
    }
}
