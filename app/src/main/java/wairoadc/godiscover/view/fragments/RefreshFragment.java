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

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.RefreshAdapter;
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


    private List<String> valuesArray = new ArrayList<>();

    private ProgressDialogFragment progressDialogFragment;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RefreshAdapter(getActivity(), R.layout.track_download_list_item,valuesArray);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
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

    private String stripZipExtensionName(String name) {
        return name.replace(".zip","");
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
            valuesArray.clear();
            valuesArray.addAll(toList(data));
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(),"There was an error while download, please try again",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Track>> loader) {
    }
}