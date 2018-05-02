package com.example.casek.babysitter.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.casek.babysitter.R;
import com.example.casek.babysitter.model.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private TextView[] mdots;

    private SliderAdapter sliderAdapter;

    private Button btnNext, btnBack;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);



        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutOnBoard);
        sliderAdapter = new SliderAdapter(this);

        btnNext = (Button) findViewById(R.id.btnSettingsNext);
        btnBack = (Button) findViewById(R.id.btnSettingsBack);



        viewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentPage == mdots.length - 1){
                    Intent goToMenu = new Intent(OnBoardingActivity.this,MenuActivity.class);
                    OnBoardingActivity.this.startActivity(goToMenu);
                }else {
                    viewPager.setCurrentItem(mCurrentPage + 1);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position){
        mdots = new TextView[3];
        linearLayout.removeAllViews();

        for (int i=0;i < mdots.length;i++){
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colorDotsSelected));


            linearLayout.addView(mdots[i]);
        }


        if (mdots.length > 0){
            mdots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;


            if (position == 0){
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setText("next");

            }else if (position == mdots.length - 1){
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("finish");
                btnBack.setText("back");
            }else{
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("next");
                btnBack.setText("back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
