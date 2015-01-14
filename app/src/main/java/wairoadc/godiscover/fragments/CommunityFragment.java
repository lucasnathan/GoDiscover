package wairoadc.godiscover.fragments;

/**
 * Created by Lucas on 13/01/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wairoadc.godiscover.R;

public class CommunityFragment extends Fragment {

    public CommunityFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cmmunity, container, false);

        return rootView;
    }
}