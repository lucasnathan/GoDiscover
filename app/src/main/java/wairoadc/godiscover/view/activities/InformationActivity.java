package wairoadc.godiscover.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import wairoadc.godiscover.R;
import wairoadc.godiscover.view.fragments.InformationFragment;

public class InformationActivity extends TrackDrawer {

    private long track_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_information);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        track_id = intent.getLongExtra("TRACK_ID",-1);
        if(findViewById(R.id.content_frame) != null) {
            if(savedInstanceState != null)
                return;
            InformationFragment informationFragment = new InformationFragment();
            Bundle b = new Bundle();
            b.putLong("TRACK_ID",track_id);
            informationFragment.setArguments(b);
            getFragmentManager().beginTransaction().add(R.id.content_frame,informationFragment).commit();
        }
    }
}
