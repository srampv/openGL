package com.example.shashank.opengl;

import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class AudioRecordActivity extends AppCompatActivity {
    MediaRecorder mRecorder;
    MediaPlayer mPlayer;
    private String mFileName=null;
    private boolean mStartPlaying=true;
    private boolean mStartRecording=true;
    private MediaPlayer mSilentPlayer;  /* to avoid tunnel player issue */
    private VisualizerView mVisualizerView;

    public AudioRecordActivity(){
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    private void cleanUp()
    {
        if (mPlayer != null)
        {
            mVisualizerView.release();
            mPlayer.release();
            mPlayer = null;
        }

        if (mSilentPlayer != null)
        {
            mSilentPlayer.release();
            mSilentPlayer = null;
        }
    }

    // Methods for adding renderers to visualizer
    private void addBarGraphRenderers()
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(50f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 56, 138, 252));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        mVisualizerView.addRenderer(barGraphRendererBottom);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.argb(200, 181, 111, 233));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
        mVisualizerView.addRenderer(barGraphRendererTop);
    }

    private void initTunnelPlayerWorkaround() {
        // Read "tunnel.decode" system property to determine
        // the workaround is needed
        if (TunnelPlayerWorkaround.isTunnelDecodeEnabled(this)) {
            mSilentPlayer = TunnelPlayerWorkaround.createSilentMediaPlayer(this);
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        initTunnelPlayerWorkaround();
        init();
    }
    @Override
    protected void onDestroy()
    {
        cleanUp();
        super.onDestroy();
    }

    private void init()
    {
       mPlayer = new MediaPlayer();
        mPlayer.setLooping(true);
        mPlayer.start();

        // We need to link the visualizer view to the media player so that
        // it displays something
        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        mVisualizerView.link(mPlayer);

        // Start with just line renderer
        addBarGraphRenderers();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton record = (FloatingActionButton) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    Snackbar.make(view, "Stop Recording...!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    Snackbar.make(view, "Start Recording...!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                mStartRecording = !mStartRecording;

            }
        });
        FloatingActionButton play = (FloatingActionButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    Snackbar.make(view, "Stop Playing...!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    Snackbar.make(view, "Start Playing...!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                mStartPlaying = !mStartPlaying;

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }
    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        mVisualizerView.link(mPlayer);

        // Start with just line renderer
        addBarGraphRenderers();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("AudioRecordTest", "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("AudioRecordTest", "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

}
