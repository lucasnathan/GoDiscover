package wairoadc.godiscover.view.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;

import wairoadc.godiscover.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }
}
