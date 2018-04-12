package com.example.casek.babysitter.model;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casek.babysitter.R;
import com.jjoe64.graphview.GraphView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

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
    private int sampleRate = 44100;
    private Context context;
    private GraphView graph;
    TextView txtdB;
    private SoundManager soundManager;

    public PlaySound(String serverAddress, String serverPort,
                     TextView txtSourceLoudness, Context context, ProgressBar progressBar,
                     TextView txtPhoneVolume,
                     GraphView graph) {
        this.serverAddress = serverAddress;
        this.serverPort = Integer.parseInt(serverPort);
        txtdB = txtSourceLoudness;
        this.context = context;
        soundManager = new SoundManager(progressBar,txtPhoneVolume,
                txtSourceLoudness,this.context, graph);

    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedInputStream data = null;

        try {
            // try to create socket, if fails throw error
            socket = new Socket(serverAddress,serverPort);
        }catch (Exception e) {
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
            int length = AudioTrack.getMinBufferSize(sampleRate,
                    AudioFormat.CHANNEL_OUT_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);

            //create audio track with appropriate values
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    length,
                    AudioTrack.MODE_STREAM);
            //start playing
            audioTrack.play();

            //buffer array
            byte[] audioBuffer = new byte[length * 8];
            double value;
            while (playing) {
                try {
                    //read data
                    int sizeRead = data.read(audioBuffer, 0, length * 8);
      //write the data to the audioTrack for playback
                    int sizeWrite = audioTrack.write(audioBuffer, 0, sizeRead);

                    value = bytesToShort(audioBuffer);
                    double db = 20*Math.log10((0.000002+((0.6325-0.00002)/32767.0)*Math.abs(value))/0.00002);
                    soundManager.adjustPhoneVolume(db);
                   // txtdB.setText((int) db+" dB");
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

    public short bytesToShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public void resetGraph(){
        soundManager.setSequence(0d);
    }
}
