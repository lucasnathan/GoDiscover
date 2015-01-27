package wairoadc.godiscover.view.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.services.DownloadIndexTask;

/**
 * Created by Xinxula on 27/01/2015.
 */
public class RefreshFragment extends ListFragment implements LoaderManager.LoaderCallbacks<String[]> {

    public RefreshFragment() {}

    private ArrayAdapter<String> adapter;

    private String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };
    private List<String> valuesArray = new ArrayList<>(Arrays.asList(values));

    private ProgressDialogFragment progressDialogFragment;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, valuesArray);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
    }

    private void startAnimation() {
        FragmentManager fm = getFragmentManager();
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(fm,"fragment_progress_track");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        Toast.makeText(getActivity(),values[position],Toast.LENGTH_SHORT).show();
    }

    private void dismissDialog(){
        Fragment prev = getFragmentManager().findFragmentByTag("fragment_progress_track");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismissAllowingStateLoss();
        }
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        startAnimation();
        return (new DownloadIndexTask(getActivity()));
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        dismissDialog();
        valuesArray.clear();
        valuesArray.addAll(Arrays.asList(data));
        adapter.notifyDataSetChanged();
    }





    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

}