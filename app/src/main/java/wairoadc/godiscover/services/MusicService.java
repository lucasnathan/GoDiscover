package wairoadc.godiscover.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.MediaController;

import java.io.IOException;

import wairoadc.godiscover.view.activities.AudioPlayer;

/**
 * Created by Xinxula on 9/02/2015.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,MediaController.MediaPlayerControl, MediaPlayer.OnCompletionListener {
    public static final String ACTION_PLAY = "wairoadc.godiscover.action.PLAY";
    public static final String TAG = "MusicService";
    private final IBinder mBinder = new LocalBinder();
    private static String audioFile;

    MediaPlayer mMediaPlayer = null;

    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(ACTION_PLAY)) {
            if(mMediaPlayer == null) {
                audioFile = intent.getStringExtra(AudioPlayer.AUDIO_FILE_NAME);
                initMediaPlayer(audioFile);
            } else {
                //Check if user has requested another song
                String audioFile2 = intent.getStringExtra(AudioPlayer.AUDIO_FILE_NAME);
                if(null != audioFile && !audioFile.equals(audioFile2)) {
                    Log.i("MusicService","Different Music");
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();
                    audioFile = audioFile2;
                    initMediaPlayer(audioFile2);
                }
            }
        }
        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MusicService","onBind");
        return mBinder;
    }

    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void initMediaPlayer(String audioFile) {
        // ...initialize the MediaPlayer here...
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(audioFile);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
            mMediaPlayer.setOnErrorListener(this);
        } catch (IOException e) {
            Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
        }
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
        mp.reset();
        return true;
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
