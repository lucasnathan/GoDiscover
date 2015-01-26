package wairoadc.godiscover.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.*;
import com.amazonaws.regions.Regions;

import java.io.File;

import wairoadc.godiscover.controller.Utility;
import wairoadc.godiscover.view.activities.MainActivity;

public class DownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.vogella.android.service.receiver";
    private static final String LOG_TAG = "DownloadService";
    private static final String BUCKET_NAME = "godiscover";


    // Notification ID to allow for future updates
    private static final int MY_NOTIFICATION_ID = 1;

    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    // Notification Text Elements
    private final CharSequence tickerText = "This is a Really, Really, Super Long Notification Message!";
    private final CharSequence contentTitle = "Download done";
    private final CharSequence contentText = "The file has been downloaded";



    public DownloadService() {
        super("DownloadService");
    }

    private CognitoCachingCredentialsProvider initializeCognitoProvider() {
        CognitoCachingCredentialsProvider credentials;
        credentials = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "865405818235", /* AWS Account ID */
                "us-east-1:12788d27-e277-47f5-bb9b-a15095395055", /* Identity Pool ID */
                "arn:aws:iam::865405818235:role/Cognito_GoDiscoverUnauth_Role", /* Unauthenticated Role ARN */
                "arn:aws:iam::865405818235:role/Cognito_GoDiscoverAuth_Role", /* Authenticated Role ARN */
                Regions.US_EAST_1 /* Region */
        );

        return credentials;

    }

    private File downloadFile(String fileName) throws NotAuthenticatedException,InterruptedException {
        Log.i(LOG_TAG,"downloadFile with filename: "+fileName);
        CognitoCachingCredentialsProvider credentialsProvider = initializeCognitoProvider();
        if(null == credentialsProvider.getIdentityId() || "" == credentialsProvider.getIdentityId())
            throw new NotAuthenticatedException("Not Authenticated!");
        File output = new File(getFilesDir(),fileName);
        //Deletes file if it already exists
        if(output.exists())
            output.delete();
        //Performs download operation from the bucket
        TransferManager transferManager = new TransferManager(credentialsProvider);
        Download download = transferManager.download(BUCKET_NAME, fileName, output);
        TransferProgress transferred = download.getProgress();
        if(download.isDone())
            Log.i(LOG_TAG,"download successful at: "+output.getAbsolutePath());

        Log.i(LOG_TAG,"unpacking zip:");
        if(Utility.unpackZip(getFilesDir().getPath() + "/", fileName)) {
            //delete the zip file once it's contents has been placed
            output.delete();
        }
        String files[] = fileList();
        for (String s : files) {
            Log.i(LOG_TAG,"file: "+s);
        }
        return output;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG,"onHandleIntent");
        try {
            File outPut = downloadFile("track_dummy2.zip");
            result = Activity.RESULT_OK;
            if(MainActivity.IS_RUNNING)
                publishResults(outPut.getAbsolutePath(),result);
            else
                notifyUser();
        } catch (NotAuthenticatedException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
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



    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);

        sendBroadcast(intent);
    }

    public static class NotAuthenticatedException extends Exception {
        public NotAuthenticatedException(String message) {
            super(message);
        }
    }




}
