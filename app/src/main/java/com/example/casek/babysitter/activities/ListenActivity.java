package com.example.casek.babysitter.activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.casek.babysitter.R;
import com.example.casek.babysitter.model.PlaySound;
import com.example.casek.babysitter.model.SettingsManager;

import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ListenActivity extends AppCompatActivity {


    private TextView txtPhoneVolume;
    private ProgressBar prgbPhoneVolume;
    private TextView txtSourceLoudness;
    private Button btnPlay;
    private Button btnStop;
    PlaySound playSound = null;
    private String ipAddress;
    private String port;
    private boolean isPlaying = false;
    private SettingsManager settingsManager = new SettingsManager();
    private Thread thread;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtPhoneVolume = (TextView) findViewById(R.id.txtListenVolumeValue);
        prgbPhoneVolume = (ProgressBar) findViewById(R.id.progressBarVolume);
        txtSourceLoudness = (TextView) findViewById(R.id.txtLoudnessOfSourceValue);
        btnPlay = (Button) findViewById(R.id.btnPlaySound);
        btnStop = (Button) findViewById(R.id.btnStopSound);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioManager m = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                Log.i("Phone volume is ",""+m.getStreamVolume(AudioManager.STREAM_MUSIC));
                Log.i("Max volume is ",""+m.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                String settingsResult = checkSettings();

                if(settingsResult.equals("ok")){
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Playing sound from "+ipAddress+":"+port,
                            duration);
                    toast.show();

                    if(playSound == null) {
                        playSound = new PlaySound(ipAddress, port,txtSourceLoudness,getApplicationContext());
                    }

                    thread = new Thread(playSound);

                    if(isPlaying == false){
                        btnPlay.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        btnPlay.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        btnPlay.setText(getResources().getText(R.string.playButtonActive));

                       // new Thread(playSound).start();
                        thread.start();
                        isPlaying = true;
                    }




                }else{
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No settings provided, go to settings !",
                            duration);
                    toast.show();
                }








            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thread != null){
                    thread.interrupt();
                }
                playSound.stopSound();
                isPlaying = false;
                playSound = null;

                btnPlay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnPlay.setTextColor(getResources().getColor(R.color.white_frame));
                btnPlay.setText(getResources().getString(R.string.playButton));
                txtSourceLoudness.setText("- dB");


            }
        });

    }




    private String checkSettings(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                "app_settings", Context.MODE_PRIVATE);

        try {


            ipAddress = sharedPreferences.getString(settingsManager.SETTING_TYPE_IP_ADDRESS, null);
            port = sharedPreferences.getString(settingsManager.SETTING_TYPE_PORT, null);
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return "error";
        }
        if (ipAddress == null || port == null){
            return "error";
        }

        return "ok";
    }

}