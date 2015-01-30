package wairoadc.godiscover.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.mobileconnectors.s3.transfermanager.*;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.utilities.TrackXMLParser;
import wairoadc.godiscover.utilities.Utility;
import wairoadc.godiscover.view.activities.MainActivity;
import wairoadc.godiscover.view.activities.RefreshActivity;

//Based on:
//http://www.vogella.com/tutorials/AndroidServices/article.html
public class DownloadService extends IntentService implements ProgressListener {

    private int result = Activity.RESULT_CANCELED;

    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "wairoadc.godiscover.services";
    private static final String LOG_TAG = "DownloadService";
    private static final String BUCKET_NAME = "godiscover";
    public static String SERVICE = "ID";
    public static  String PROGRESS = "Progress";
    private int serviceIdValue;



    // Notification ID to allow for future updates
    private static final int MY_NOTIFICATION_ID = 1;

    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    // Notification Text Elements
    private final CharSequence tickerText = "Track Downloaded!";
    private final CharSequence contentTitle = "Download done";
    private final CharSequence contentText = "The file has been downloaded";


    private File output;

    public static final int MSG_REGISTER_CLIENT = 1;
    private TransferManager transferManager;
    private Download download;
    private Messenger messenger;


    public DownloadService() {
        super("DownloadService");
    }

    private void sendMessageToUI(int id, long progress) {

        try {
            Bundle b = new Bundle();
            b.putInt(SERVICE, id);
            b.putLong(PROGRESS, progress);
            Message msg = Message.obtain();
            msg.setData(b);
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean downloadFile(String fileName) throws Utility.NotAuthenticatedException,InterruptedException {
        Log.i(LOG_TAG,"downloadFile with filename: "+fileName);
        output = new File(getApplicationContext().getFilesDir(), fileName);
        //Deletes file if it already exists
        if(output.exists()) {
            Log.i(LOG_TAG, "Output exists" + output.getAbsolutePath());
            output.delete();
        }
        try {
            transferManager = Utility.connectToAmazon(getApplicationContext());
            download = transferManager.download(BUCKET_NAME, fileName, output);
            download.addProgressListener(this);
            download.waitForCompletion();
            transferManager.shutdownNow();
            if(Utility.unpackZip(getFilesDir().getPath()+"/",fileName)) {
                //delete the zip file once it's contents has been placed
                output.delete();
                Log.i(LOG_TAG,"File Unzipped: "+fileName);
                String folderName = Utility.stripZipExtensionName(fileName);
                InputStream in = new FileInputStream(getFilesDir().getPath()+"/"+folderName+"/"+folderName+".xml");
                if(in != null) {
                    Log.i(LOG_TAG,"XML Found!");
                    Track newTrack = TrackXMLParser.parse(in);
                    TrackController controller = new TrackController(getApplicationContext());
                    controller.insertTrack(newTrack);
                    Log.i(LOG_TAG,"Saved on the database!");
                    return true;
                }
                return false;
            } else return false;
        } catch (Utility.NotAuthenticatedException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG,"onHandleIntent");
        String fileName = intent.getStringExtra(FILENAME);
        serviceIdValue = intent.getIntExtra(SERVICE,-1);
        messenger = (Messenger)intent.getExtras().get("MESSENGER");
        try {
            boolean outPut = downloadFile(fileName);
            if(outPut) {
                result = Activity.RESULT_OK;
            } else {
                result = Activity.RESULT_CANCELED;
            }
            if(RefreshActivity.IS_RUNNING)
                publishResults(result);
            else
                notifyUser();
        } catch (InterruptedException e) {
            e.getMessage();
        } catch (Utility.NotAuthenticatedException e) {
            e.printStackTrace();
        }
    }

    private void notifyUser() {
        Uri soundURI= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] mVibratePattern = { 0, 200, 200, 300 };
        mNotificationIntent = new Intent(getApplicationContext(),MainActivity.class);
        mContentIntent = PendingIntent.getActivity(getApplicationContext(), 0, mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setTicker(tickerText)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(mContentIntent)
                .setSound(soundURI)
                .setVibrate(mVibratePattern);
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
    }

    private void publishResults( int result) {
        Intent intent = new Intent(NOTIFICATION);

        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        if(Math.round(download.getProgress().getPercentTransferred()) % 5 == 0) {
            sendMessageToUI(serviceIdValue, Math.round(download.getProgress().getPercentTransferred()));
        }
    }
}