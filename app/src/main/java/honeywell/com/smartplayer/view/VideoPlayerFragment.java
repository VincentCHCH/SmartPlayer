package honeywell.com.smartplayer.view;

import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import honeywell.com.smartplayer.R;
import honeywell.com.smartplayer.entity.ControlModel;
import honeywell.com.smartplayer.network.TCPServer;
import honeywell.com.smartplayer.ui.base.BaseFragment;
import honeywell.com.smartplayer.ui.base.MessageTransfer;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by Vincent on 8/11/15.
 */
public class VideoPlayerFragment extends BaseFragment implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback,MessageTransfer {

    private static final String TAG = "MediaPlayerDemo";
    private int mVideoWidth;
    private int mVideoHeight;
    private MediaPlayer mMediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private String path;
    private Bundle extras;
    private static final String MEDIA = "media";
    private static final int LOCAL_AUDIO = 1;
    private static final int STREAM_AUDIO = 2;
    private static final int RESOURCES_AUDIO = 3;
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;
    private View view;
    private Button mControlLeftBtn;
    private ExecutorService exec ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.mediaplayer_2, container, false);
        mPreview = (SurfaceView) view.findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);
        extras = mActivity.getIntent().getExtras();
        mControlLeftBtn = (Button)view.findViewById(R.id.control_left);
        exec = Executors.newCachedThreadPool();

        return view;
    }
    public void doClick(View v) {
        TCPServer server = null;
        switch (v.getId()) {
            case R.id.control_top:
               server = new TCPServer(mActivity,new ControlModel("T10"));
                break;
            case R.id.control_down:
                server = new TCPServer(mActivity,new ControlModel("t10"));

                break;
            case R.id.control_left:
                server = new TCPServer(mActivity,new ControlModel("p10"));

                break;
            case R.id.control_right:
                server = new TCPServer(mActivity,new ControlModel("P10"));

                break;
        }
        exec.execute(server);
    }
    private void playVideo(Integer Media) {
        doCleanUp();
        try {

            switch (Media) {
                case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
                    path = "";
                    if (path == "") {
                        // Tell the user to provide a media file URL.
//                        Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity, " + "and set the path variable to your media file path." + " Your media file must be stored on sdcard.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or
				 * 3gpp format URL. Http protocol should be used.
				 * Mediaplayer can only play "progressive streamable
				 * contents" which basically means: 1. the movie atom has to
				 * precede all the media data atoms. 2. The clip has to be
				 * reasonably interleaved.
				 *
				 */
                    path = "rtsp://192.168.0.111/live1.sdp";
                    if (path == "") {
                        // Tell the user to provide a media file URL.
//                        Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity," + " and set the path variable to your media file URL.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    break;

            }

            // Create a new media player and set the listeners
            mMediaPlayer = new MediaPlayer(mContext);
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        // Log.d(TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");

    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
//        playVideo(extras.getInt(MEDIA));
        playVideo(5);


    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }
}
