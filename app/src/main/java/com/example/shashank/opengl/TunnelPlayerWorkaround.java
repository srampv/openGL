package com.example.shashank.opengl;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by shashank on 6/12/16.
 */

public class TunnelPlayerWorkaround {
    private static final String TAG = "TunnelPlayerWorkaround";

    private static final String SYSTEM_PROP_TUNNEL_DECODE_ENABLED = "tunnel.decode";
    private static String mFileName=null;

    private TunnelPlayerWorkaround()
    {
    }

    /**
     * Obtain "tunnel.decode" system property value
     *
     * @param context Context
     * @return Whether tunnel player is enabled
     */
    public static boolean isTunnelDecodeEnabled(Context context)
    {
        return SystemPropertiesProxy.getBoolean(
                context, SYSTEM_PROP_TUNNEL_DECODE_ENABLED, false);
    }

    /**
     * Create silent MediaPlayer instance to avoid tunnel player issue
     *
     * @param context Context
     * @return MediaPlayer instance
     */
    public static MediaPlayer createSilentMediaPlayer(Context context)
    {
        boolean result = false;

        MediaPlayer mp = null;
        try {
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/audiorecordtest.3gp";
            mp = new MediaPlayer();
            try {
                mp.setDataSource(mFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // NOTE: start() is no needed
            // mp.start();

            result = true;
        } catch (RuntimeException e) {
            Log.e(TAG, "createSilentMediaPlayer()", e);
        } finally {
            if (!result && mp != null) {
                try {
                    mp.release();
                } catch (IllegalStateException e) {
                }
            }
        }

        return mp;
    }
}
