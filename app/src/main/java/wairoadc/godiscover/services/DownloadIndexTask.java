package wairoadc.godiscover.services;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transfermanager.Download;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.utilities.IndexParser;
import wairoadc.godiscover.utilities.Utility;

/**
 * Created by Xinxula on 27/01/2015.
 */
public class DownloadIndexTask extends AsyncTaskLoader<List<Track>>  {

    private List<Track> tracks = null;
    private String INDEX_FILE_NAME = "index.xml";
    private static final String LOG_TAG = "DownloadIndexTask";
    private static final String BUCKET_NAME = "godiscover";
    private Context context;
    private File output;

    public DownloadIndexTask(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public List<Track> loadInBackground() {
        output = new File(getContext().getFilesDir(), INDEX_FILE_NAME);
        //Deletes file if it already exists
        if(output.exists()) {
            Log.i(LOG_TAG, "Output exists" + output.getAbsolutePath());
            output.delete();
        }

        try {
            TransferManager transferManager = Utility.connectToAmazon(context);
            Download download = transferManager.download(BUCKET_NAME, INDEX_FILE_NAME, output);
            download.waitForCompletion();
            transferManager.shutdownNow();
            InputStream in = new FileInputStream(output);
            tracks = IndexParser.parse(in);
            return tracks;
        } catch (Utility.NotAuthenticatedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG,e.getMessage());
            return null;
        }
        return null;
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
