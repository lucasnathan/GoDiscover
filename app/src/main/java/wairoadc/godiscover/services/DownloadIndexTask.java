package wairoadc.godiscover.services;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;

import java.io.File;

/**
 * Created by Xinxula on 27/01/2015.
 */
public class DownloadIndexTask extends AsyncTaskLoader<String[]> {

    private String[] tracks = null;
    private String INDEX_FILE_NAME = "index.xml";

    public DownloadIndexTask(Context context) {
        super(context);
    }

    private void downloadIndex() {
        File output = new File(getContext().getFilesDir(), INDEX_FILE_NAME);
        //Deletes file if it already exists
        if(output.exists())
            output.delete();

    }


    @Override
    public String[] loadInBackground() {
        String test[] = {"funcionou"};
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return test;
    }

    @Override
    protected void onStartLoading() {
        if(tracks != null) {
            deliverResult(tracks);
        }
        if(takeContentChanged() || tracks == null) {
            forceLoad();
        }
    }
}
