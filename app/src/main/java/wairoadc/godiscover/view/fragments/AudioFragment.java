package wairoadc.godiscover.view.fragments;


import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.MyArrayAdapter;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.utilities.UtilsGrid;
import wairoadc.godiscover.view.activities.AudioPlayer;
import wairoadc.godiscover.view.models.Audio;

/**
 * Created by Lucas on 5/02/2015.
 */
public class AudioFragment extends ListFragment {
    public static final String ARG_PAGE = "Audio";
    private List<Resource> audioResources;
    public static final String AUDIO_LIST = "audioList";
    private int galleryMode;
    private final ArrayList<String> audioPaths = new ArrayList<>();
    private final ArrayList<String> audioTitles = new ArrayList<>();

    public static AudioFragment newInstance(List<Resource> audioResources) {
        Bundle args = new Bundle();

        AudioFragment fragment = new AudioFragment();
        args.putParcelableArrayList(AUDIO_LIST, (ArrayList<Resource>) audioResources);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            if(null != audioPaths.get(position)) {
                Intent intent = new Intent(getActivity(), AudioPlayer.class);
                intent.putExtra(AudioPlayer.AUDIO_FILE_NAME,getActivity().getFilesDir()+audioPaths.get(position));
                startActivity(intent);
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioResources = getArguments().getParcelableArrayList(AUDIO_LIST);
        if(null ==audioResources)
            audioResources = new ArrayList<Resource>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_audio, container, false);
        ListView listview = (ListView) view.findViewById(android.R.id.list);
        UtilsGrid utilsGrid = new UtilsGrid(getActivity());

        final ArrayList<Resource> list = (ArrayList<Resource>) audioResources;

        //Verify if the spot has at lest one audio file in order to render the list

            for(int i = 0; i < audioResources.size(); i++) {
                //check if audio resource is null(it means that this audio resource is blocked)
                final Resource resource = audioResources.get(i);
                if(null != resource) {
                    audioPaths.add(resource.getPath());
                    audioTitles.add(resource.getName());
                } else {
                    audioPaths.add(null);
                    audioTitles.add("Locked Audio "+(i+1));
                }

            }
            ArrayList<String> dur = this.getMusicDuration(audioPaths);


            ArrayList<Audio> audios = new ArrayList<Audio>();
            for (int i = 0;i<list.size() && i<dur.size();i++){
                Audio audio = new Audio();
                audio.setPath(audioPaths.get(i));
                audio.setName(audioTitles.get(i));
                audio.setDuration(dur.get(i));
                audios.add(audio);
            }
            MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),audios);
            listview.setAdapter(adapter);
            /*
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    try {
                        Intent intent = new Intent(getActivity(), AudioPlayer.class);
                        intent.putExtra(AudioPlayer.AUDIO_FILE_NAME,getActivity().getFilesDir()+audioPaths.get(position));
                        startActivity(intent);
                    } catch (Exception e) {e.printStackTrace();}

                }
            });*/

        return view;
    }

    private ArrayList<String> getMusicDuration(ArrayList<String> filePaths){
        ArrayList<String> durations = new ArrayList<>();

        for (String filePath :filePaths){
            if(null != filePath) {
                MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                metaRetriever.setDataSource(getActivity().getFilesDir().getAbsolutePath() + filePath);

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
                } else {
                    txtTime = "0" + minutes + ":" + seconds;
                }
                Log.v("minutes", minutes);
                // close object
                durations.add(txtTime);
                metaRetriever.release();
            } else {
                durations.add("???");
            }

        }
        return durations;
    }
}