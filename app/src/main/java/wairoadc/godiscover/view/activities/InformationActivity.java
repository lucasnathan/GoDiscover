package wairoadc.godiscover.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import wairoadc.godiscover.R;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.view.fragments.InformationFragment;

public class InformationActivity extends TrackDrawer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_information);
        super.onCreate(savedInstanceState);

        if(findViewById(R.id.content_frame) != null) {
            if(savedInstanceState != null)
                return;
            InformationFragment informationFragment = new InformationFragment();
            Bundle b = new Bundle();
            b.putParcelable(MainActivity.TRACK_EXTRA,super.currentTrack);
            informationFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.content_frame,informationFragment).commit();
        }
    }
}
