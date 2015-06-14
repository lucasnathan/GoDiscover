package wairoadc.godiscover.view.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;

import android.app.ListFragment;
import android.app.LoaderManager;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.RefreshAdapter;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.services.DownloadIndexTask;
import wairoadc.godiscover.services.DownloadService;


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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SCORE,56);
        super.onSaveInstanceState(outState);
    }

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
            setEmptyText("Congratulations, you have all tracks on you device!");
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(),"There was an error while download, please try again",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Track>> loader) {
    }
}