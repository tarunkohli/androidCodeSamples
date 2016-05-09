package com.example.ajay.animationswithopengl.Activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.example.ajay.animationswithopengl.Adapter.ViewAnimationsAdapter;
import com.example.ajay.animationswithopengl.R;

public class ViewAnimations extends AppCompatActivity {

    private ListView mListView;
    private String[] mDataList;
    private AnimationSet mSet;
    private int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.rsz_ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get screen height
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        mScreenHeight = size.y;

        //initialize list view
        mListView = (ListView) findViewById(R.id.view_animations_list);
        mDataList = getResources().getStringArray(R.array.view_animation_elements);
        ViewAnimationsAdapter lAdapter = new ViewAnimationsAdapter(this,mDataList);
        mListView.setAdapter(lAdapter);

        //animation set on list view
        mSet = new AnimationSet(false);
        mSet.addAnimation(new AlphaAnimation(0f,1f));
        mSet.addAnimation(new TranslateAnimation(0f, 0f, mScreenHeight, 0f));
        mSet.setDuration(1500);
        mListView.startAnimation(mSet);
    }
}