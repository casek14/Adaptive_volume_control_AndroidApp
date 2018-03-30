package com.example.casek.babysitter.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.casek.babysitter.R;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

         Button back = (Button) findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutAppActivity.this.finish();
            }
        });
    }
}
