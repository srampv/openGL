package com.example.shashank.opengl;

/**
 * Created by shashank on 6/12/16.
 */
// Data class to explicitly indicate that these bytes are the FFT of audio data
public class FFTData
{
    public FFTData(byte[] bytes)
    {
        this.bytes = bytes;
    }

    public byte[] bytes;
}