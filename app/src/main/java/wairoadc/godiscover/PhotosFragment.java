package wairoadc.godiscover;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lucas on 13/01/2015.
 */
public class PhotosFragment extends Fragment {

    public PhotosFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);

        return rootView;
    }
}
