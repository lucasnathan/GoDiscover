package wairoadc.godiscover.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wairoadc.godiscover.R;

/**
 * Created by Lucas on 13/01/2015.
 */
public class FindPeopleFragment extends Fragment {

    public FindPeopleFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find, container, false);

        return rootView;
    }
}
