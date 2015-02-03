package wairoadc.godiscover.view.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wairoadc.godiscover.R;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.view.activities.MainActivity;

/**
 * Created by Lucas on 13/01/2015.
 */
public class InformationFragment extends Fragment {

    private TextView descriptionField;
    private ImageView mainPicture;
    private Track track;


    public InformationFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle b = getArguments();
        track = b.getParcelable(MainActivity.TRACK_EXTRA);

        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        descriptionField = (TextView)rootView.findViewById(R.id.txt_description);
        mainPicture = (ImageView)rootView.findViewById(R.id.main_picture);
        descriptionField.setText(track.getDescription());
        String imageFullPath = getActivity().getFilesDir().getPath()+track.getResource();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFullPath);
        if(null != bitmap) {
            mainPicture.setImageBitmap(bitmap);
        }

        return rootView;
    }
}
