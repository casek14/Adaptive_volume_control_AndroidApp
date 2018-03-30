package com.example.casek.babysitter.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.casek.babysitter.R;
import com.example.casek.babysitter.model.PlaySound;
import com.example.casek.babysitter.model.SettingsManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {


    private SettingsManager settingsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        settingsManager = new SettingsManager();
        final JSONObject settingJSON = settingsManager.readSettings(this.getApplicationContext());
        final EditText txtIpAddress = (EditText) findViewById(R.id.txtIpAddress);
        final EditText txtPort = (EditText) findViewById(R.id.txtPort);
        final Spinner spinnerSampleRate = (Spinner) findViewById(R.id.spinnerSampleRate);
        final Spinner spinnerMonoStereo = (Spinner) findViewById(R.id.spinnerMonoStereo);
        Button btnLoadSettings = (Button) findViewById(R.id.btnLoadSettings);
        Button btnSaveSettings = (Button) findViewById(R.id.btnSave);
        String ip =  "";
        String port = "";


        if(settingJSON != null){

            try {
                ip = settingJSON.getString(settingsManager.SETTING_TYPE_IP_ADDRESS);
                port = settingJSON.getString(settingsManager.SETTING_TYPE_PORT);
            }catch (JSONException e){
                e.printStackTrace();
            }

            txtIpAddress.setText(ip);
            txtPort.setText(port);
        }


        btnLoadSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = settingsManager.readSettings(getApplicationContext());
                if(jsonObject != null){
                    try {
                        txtIpAddress.setText(jsonObject.getString(settingsManager.SETTING_TYPE_IP_ADDRESS));
                        txtPort.setText(jsonObject.getString(settingsManager.SETTING_TYPE_PORT));

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(),"No saved settings",duration);
                    toast.show();
                }
            }
        });

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = txtIpAddress.getText().toString();
                String port = txtPort.getText().toString();
                String answerStatus = settingsManager.manageSettings(getApplicationContext(),
                                                ip,
                                                port,
                                                "",
                                                "");
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(),answerStatus,duration);
                toast.show();
            }
        });


    }
}
