package com.example.casek.babysitter;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.BufferedInputStream;
import java.net.Socket;

/**
 * This class is connecting to the sound server
 * via socket on specified ip address and port
 * and play the sound
 * Created by casek on 31.1.18.
 */

public class PlaySound implements Runnable {

    private String serverAddress;
    private int serverPort;
    private boolean playing = true;
    private int sampleRate = 48000;

    public PlaySound(String serverAddress, String serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = Integer.parseInt(serverPort);
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedInputStream data = null;

        try {
            // try to create socket, if fails throw error
            socket = new Socket(serverAddress,serverPort);
        }catch (Exception e){
            Log.e("ERROR",e.getMessage());
        }

        if(playing) {
            try {
                data = new BufferedInputStream(socket.getInputStream());

            }catch (Exception e){
                Log.e("ERROR",e.getMessage());
            }


            //get min buffer size for AudioTrack to be created in
            //stream mode
            int lenght = AudioTrack.getMinBufferSize(sampleRate,
                    AudioFormat.CHANNEL_OUT_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);

            //create audio track with appropriate values
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    lenght,
                    AudioTrack.MODE_STREAM);
            //start playing
            audioTrack.play();

            //buffer array
            byte[] audioBuffer = new byte[lenght * 8];

            while (playing) {
                try {
                    //read data
                    int sizeRead = data.read(audioBuffer, 0, lenght * 8);

                    //write the data to the audioTrack for playback
                    int sizeWrite = audioTrack.write(audioBuffer, 0, sizeRead);
                    // TODO print audioTrack.getMaxVolume
                    Log.i("Music-Data",audioBuffer.toString());
                }catch (Exception e){
                    Log.e("ERROR",e.getMessage());
                }
            }


            audioTrack.stop();
            socket = null;
            data = null;


        }
    }

    /**
     * Stop playing
     */
    public void stopSound(){
        playing = false;
    }

    /**
     * Start playing
     */
    public void startPlaying(){
        playing = true;
    }
}
