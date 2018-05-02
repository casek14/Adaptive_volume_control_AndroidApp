package com.example.casek.babysitter.model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.casek.babysitter.R;

/**
 * Created by casek on 2.5.18.
 */

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;


    public String[] pages ={"FIRST","SECOND","THIRD"};


    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);


        Log.i("~~~ HOVNO",position+"");
        TextView slide = view.findViewById(R.id.slide);
        slide.setText(position+"");

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
