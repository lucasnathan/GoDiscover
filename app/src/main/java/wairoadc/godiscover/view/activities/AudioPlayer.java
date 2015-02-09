package wairoadc.godiscover.view.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import android.media.MediaPlayer.OnPreparedListener;
import android.view.MotionEvent;
import android.widget.MediaController;

import wairoadc.godiscover.R;
import wairoadc.godiscover.services.MusicService;

//Reference
//http://www.wenda.io/questions/1276827/android-how-to-use-mediacontroller-in-service-class.html
//http://stackoverflow.com/questions/3747139/how-can-i-show-a-mediacontroller-while-playing-audio-in-android/5265629#5265629
public class AudioPlayer extends Activity {
    private static final String TAG = "AudioPlayer";

    public static final String AUDIO_FILE_NAME = "audioFileName";

    private MediaPlayer mediaPlayer;
    private MediaController mc;
    private String audioFile;
    private MusicService mService;
    private boolean first;

    private boolean mBound;

    private Handler handler = new Handler();

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_player);
        audioFile = this.getIntent().getStringExtra(AUDIO_FILE_NAME);
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_PLAY);
        intent.putExtra(AUDIO_FILE_NAME,audioFile);
        startService(intent);
        first = true;
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            Log.i(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void showMediaControllerHere(){
        if (mBound){
            mc = new MediaController(this);
            mc.setAnchorView(findViewById(R.id.main_audio_view));
            mc.setMediaPlayer(mService);
            mc.setEnabled(true);
            mc.setVisibility(MediaController.VISIBLE);
            mc.show(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        if (first){
            if (mBound){
                showMediaControllerHere();
                first = false;
            }
        }
        else {
            if (mBound){
                mc.show(0);
            }
        }
        return false;
    }
    //--------------------------------------------------------------------------------
}