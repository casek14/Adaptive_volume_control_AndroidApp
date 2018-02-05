package com.example.casek.babysitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private boolean isPlaying = false;
    PlaySound playSound = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtIpAddress = (EditText) findViewById(R.id.txtIpAddress);
        final EditText txtPort = (EditText) findViewById(R.id.txtPort);
        final Spinner spinnerSampleRate = (Spinner) findViewById(R.id.spinnerSampleRate);
        final Spinner spinnerMonoStereo = (Spinner) findViewById(R.id.spinnerMonoStereo);
        final Button btnPlay = (Button) findViewById(R.id.btnPlay);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(playSound == null) {
                    playSound = new PlaySound(txtIpAddress.getText().toString(),
                            txtPort.getText().toString());
                }

                if(isPlaying == false){
                    btnPlay.setText(R.string.stopButton);

                    new Thread(playSound).start();
                    isPlaying = true;
                }else{
                    btnPlay.setText(R.string.playButton);
                    playSound.stopSound();
                    isPlaying = false;
                    playSound = null;
                }

            }
        });
    }
}
