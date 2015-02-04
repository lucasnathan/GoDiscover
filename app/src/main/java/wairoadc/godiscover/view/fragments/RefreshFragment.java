package wairoadc.godiscover.view.fragments;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;

import android.app.ListFragment;
import android.app.LoaderManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.RefreshAdapter;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.services.DownloadIndexTask;
import wairoadc.godiscover.services.DownloadService;
import wairoadc.godiscover.view.activities.RefreshActivity;


/**
 * Created by Xinxula on 27/01/2015.
 */
public class RefreshFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Track>> {

    public RefreshFragment() {}

    private RefreshAdapter adapter;

    private static boolean IS_RUNNING;
    static final String STATE_SCORE = "playerScore";


    private List<String> valuesArray = new ArrayList<>();

    private ProgressDialogFragment progressDialogFragment;

    private Map<Integer,Long> progressValues = new HashMap<>();

    private Handler loadHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(IS_RUNNING) {
                    Bundle b = msg.getData();
                    int serverId = b.getInt("SERVICE");
                    long progress = b.getLong(DownloadService.PROGRESS);
                    //Toast.makeText(getActivity(),"got message",Toast.LENGTH_LONG).show();
                    ListView listView = getListView();
                    View view = listView.getChildAt(serverId);
                    ProgressBar pb = (ProgressBar)(getListView().getChildAt(serverId)).findViewById(R.id.track_item_download_progress);
                    if(null != pb) {
                        //Toast.makeText(getActivity(),"yay!",Toast.LENGTH_SHORT).show();
                        if(pb.getVisibility() == ProgressBar.GONE)
                            pb.setVisibility(ProgressBar.VISIBLE);
                        pb.setProgress((int)progress);
                        progressValues.put(serverId,progress);
                    }
                }
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SCORE,56);
        super.onSaveInstanceState(outState);
    }

    private Handler handler = null;

    @Override
    public void onResume() {
        super.onResume();
        IS_RUNNING = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        IS_RUNNING = false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RefreshAdapter(getActivity(), R.layout.track_download_list_item,valuesArray);
        setListAdapter(adapter);
        if(savedInstanceState == null) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            int i = savedInstanceState.getInt(STATE_SCORE);
            handler = loadHandler();
            Log.i("REfresh fragment","test: "+i);
        }
    }

    private void startAnimation() {
        FragmentManager fm = getFragmentManager();
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(fm, "fragment_progress_track");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        Toast.makeText(getActivity(),valuesArray.get(position),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),DownloadService.class);
        intent.putExtra(DownloadService.FILENAME,valuesArray.get(position));
        intent.putExtra(DownloadService.SERVICE,position);

        //intent.putExtra("MESSENGER",new Messenger(handler));
        ProgressBar pb = (ProgressBar)(getListView().getChildAt(position)).findViewById(R.id.track_item_download_progress);
        pb.setVisibility(ProgressBar.VISIBLE);
        getActivity().startService(intent);

    }

    private void dismissDialog(){
        Fragment prev = getFragmentManager().findFragmentByTag("fragment_progress_track");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismissAllowingStateLoss();
        }
    }

    private List<String> toList(List<Track> tracks) {
        ArrayList<String> tracksString = new ArrayList<>();
        for(Track track : tracks) {
            tracksString.add(track.getName());
        }
        return tracksString;
    }

    @Override
    public Loader<List<Track>> onCreateLoader(int id, Bundle args) {
        startAnimation();
        return (new DownloadIndexTask(getActivity()));
    }

    @Override
    public void onLoadFinished(Loader<List<Track>> loader, List<Track> data) {
        dismissDialog();
        if(null != data) {
            TrackController trackController = new TrackController(getActivity());
            valuesArray.clear();
            valuesArray.addAll(toList(trackController.getNewTracksToDownload(data)));
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(),"There was an error while download, please try again",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Track>> loader) {
    }
}