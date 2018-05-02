package com.example.casek.babysitter.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.casek.babysitter.R;

public class MenuActivity extends AppCompatActivity {

    private Button btnListen;
    private Button btnClientSettings;
    private Button btnServerSetting;
    private Button btnAbout;

    /**
     * Class represents menu for the App
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnListen = (Button) findViewById(R.id.btnListen);
        btnClientSettings = (Button) findViewById(R.id.btnClientSettings);
        btnServerSetting = (Button) findViewById(R.id.btnServerSetting);
        btnAbout = (Button) findViewById(R.id.btnAbout);

        ActivityCompat.requestPermissions(MenuActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},
                1);

        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to Listen Activity, where you can play sound over network
                Intent listenIntent = new Intent(MenuActivity.this, ListenActivity.class);
                MenuActivity.this.startActivity(listenIntent);
            }
        });

        btnClientSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to setting activty, where you can edit settings
                Intent clientSettingsIntent = new Intent(MenuActivity.this,SettingsActivity.class);
                MenuActivity.this.startActivity(clientSettingsIntent);
            }
        });

        btnServerSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to activity, which shows you how to run sound server
               // Intent serverSettingIntent = new Intent(MenuActivity.this, ServerSettingsActivity.class);
               // MenuActivity.this.startActivity(serverSettingIntent);
                Intent server = new Intent(MenuActivity.this,OnBoardingActivity.class);
                MenuActivity.this.startActivity(server);
            }
        });


        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to activity about the app itself
                Intent aboutIntent = new Intent(MenuActivity.this, AboutAppActivity.class);
                MenuActivity.this.startActivity(aboutIntent);
            }
        });

    }
}
