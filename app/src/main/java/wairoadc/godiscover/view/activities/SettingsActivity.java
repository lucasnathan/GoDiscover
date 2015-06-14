package wairoadc.godiscover.view.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import wairoadc.godiscover.view.fragments.SettingsFragment;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
