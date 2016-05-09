package com.example.ajay.animationswithopengl.Activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.ajay.animationswithopengl.Adapter.CustomAnimationsAdapter;
import com.example.ajay.animationswithopengl.R;

public class CustomAnimations extends AppCompatActivity {

    private String[] mDataList;
    private ListView mList;
    private FragmentManager mFragmentManager;
    private FrameLayout mFrame;
    private int mScreenHeight;
    private AnimationSet mSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.rsz_ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFrame.getVisibility() == View.VISIBLE) {
                    mFrame.setVisibility(View.GONE);
                    mList.setVisibility(View.VISIBLE);
                    return;
                }

                finish();
            }
        });

        //get screen height
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        mScreenHeight = size.y;

        //initialize list view
        mDataList = getResources().getStringArray(R.array.custom_animation_elements);
        mList = (ListView) findViewById(R.id.custom_animation_list);
        mFrame = (FrameLayout) findViewById(R.id.fragment_frame);
        mFragmentManager = getSupportFragmentManager();
        CustomAnimationsAdapter adapter = new CustomAnimationsAdapter(this,mDataList,
                mFragmentManager,mFrame,mList);
        mList.setAdapter(adapter);

        //animation set on list view
        mSet = new AnimationSet(false);
        mSet.addAnimation(new AlphaAnimation(0f,1f));
        mSet.addAnimation(new TranslateAnimation(0f, 0f, mScreenHeight, 0f));
        mSet.setDuration(1500);
        mList.startAnimation(mSet);
    }

    @Override
    public void onBackPressed() {
        if(mFrame.getVisibility() == View.VISIBLE){
            mFrame.setVisibility(View.GONE);
            mList.setVisibility(View.VISIBLE);
            return;
        }

        finish();
    }
}