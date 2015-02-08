package wairoadc.godiscover.view.fragments;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.MyArrayAdapter;
import wairoadc.godiscover.utilities.UtilsGrid;
import wairoadc.godiscover.view.activities.GalleryActivity;
import wairoadc.godiscover.view.models.Audio;

/**
 * Created by Lucas on 5/02/2015.
 */
public class AudioFragment extends Fragment {
    public static final String ARG_PAGE = "Audio";


    private int galleryMode;


    public static AudioFragment newInstance() {
        Bundle args = new Bundle();

        AudioFragment fragment = new AudioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_audio, container, false);
        ListView listview = (ListView) view.findViewById(R.id.list);
        UtilsGrid utilsGrid = new UtilsGrid(getActivity());

        final ArrayList<String> list = utilsGrid.getFilePaths();
        ArrayList<String> dur = this.getMusicDuration(list);

        ArrayList<Audio> audios = new ArrayList<Audio>();
        for (int i = 0;i<list.size() && i<dur.size();i++){
            Audio audio = new Audio();
            audio.setPath(list.get(i));
            audio.setName(list.get(i));
            audio.setDuration(dur.get(i));
            audios.add(audio);
        }
        MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),audios);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(list.get(position));
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
            }
        });
        return view;
    }

    private ArrayList<String> getMusicDuration(ArrayList<String> filePaths){
        ArrayList<String> durations = new ArrayList<>();
        for (String filePath :filePaths){
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(filePath);

            String out = "";
            // get mp3 info
            String txtTime = "";
            // convert duration to minute:seconds
            String duration =
                    metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            Log.v("time", duration);
            long dur = Long.parseLong(duration);
            String seconds = String.valueOf((dur % 60000) / 1000);

            Log.v("seconds", seconds);
            String minutes = String.valueOf(dur / 60000);
            out = minutes + ":" + seconds;
            if (seconds.length() == 1) {
                txtTime = "0" + minutes + ":0" + seconds;
            }else {
                txtTime = "0" + minutes + ":" + seconds;
            }
            Log.v("minutes", minutes);
            // close object
            durations.add(txtTime);
            metaRetriever.release();
        }
        return durations;
    }
}