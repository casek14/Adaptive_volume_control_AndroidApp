package com.example.casek.babysitter.model;

import android.content.Context;
import android.media.AudioManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * This class takes dB as input and change phone volume
 * based on the level of incoming sound
 * Created by casek on 30.3.18.
 */

public class SoundManager {

    private ProgressBar prgBarPhoneVolume;
    private TextView txtPhoneVolume;
    private TextView txtLoudness;
    private int[] volumeSet;
    public int lowSoundLevel = 30;
    public int mediumSoundLevel = 50;
    public int largeSoundLevel = 70;
    Context context;
    AudioManager audioManager;


    public SoundManager(ProgressBar prgBarPhoneVolume,
                        TextView txtPhoneVolume,
                        TextView txtLoudness,
                        Context context) {
        this.prgBarPhoneVolume = prgBarPhoneVolume;
        this.txtPhoneVolume = txtPhoneVolume;
        this.txtLoudness = txtLoudness;
        this.context = context;
        audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Function update phone volume progress bar,
     * phone volume text view and source volume
     */
    private void updateUI(){

    }

    public void adjustPhoneVolume(double votage){

    }




    /**
     * Compute decibel value from given voltage
     * @param voltage
     * @return
     */
    private int computeDecibel(double voltage){
        double db = 20*Math.log10((0.000002+((0.6325-0.00002)/32767.0)*Math.abs(voltage))/0.00002);

        return (int) db;
    }


    /**
     * This function will set volume of the phone based on the given parameter
     * @param sourceLoudnes
     */
    private void setPhoneVolume(int sourceLoudnes){
        // min volume is 0 and max volume is 15
        int index = 5;
        if(sourceLoudnes <= 30){
            // sound of source is very low raise the volume to 95%
            index = returnVolumeLevel(95);
        }else if (sourceLoudnes > 30 && sourceLoudnes <= 55){
            // sound source is medium set volume to 65%
            index = returnVolumeLevel(65);
        }else if (sourceLoudnes > 55 && sourceLoudnes <= 70){
            // sound source is big set volume to 40%
            index = returnVolumeLevel(40);
        }else if (sourceLoudnes > 70){
            // sound source is very big set volume to 10%
            index = returnVolumeLevel(10);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,index,0);
    }

    /**
     * Function returns volume value for the specific %
     * This need to be configured on each device, because each device
     * have different max level, for example Nexus 5X have max 15, others
     * can have for example 11
     * @param percentage
     * @return
     */
    private int returnVolumeLevel(int percentage){
        int maxLevel = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int level = Math.round(maxLevel * percentage);
        return level;
    }
}
