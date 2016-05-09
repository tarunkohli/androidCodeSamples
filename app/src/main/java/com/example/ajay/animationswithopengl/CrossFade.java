package com.example.ajay.animationswithopengl;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class CrossFade extends AppCompatActivity {

    private Transition mFadeTransition;
    private Scene mInitialScene;
    private Scene mSecondScene;
    private FrameLayout mFrame;
    private Button mButton;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_fade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.rsz_ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButton = (Button) findViewById(R.id.button);
        mFadeTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.fade_transition);

        mFrame = (FrameLayout) findViewById(R.id.frame);

        mInitialScene = Scene.getSceneForLayout(mFrame,R.layout.initial_scene_crossfade,this);
        mSecondScene = Scene.getSceneForLayout(mFrame, R.layout.second_scene_crossfade, this);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(mSecondScene, mFadeTransition);
            }
        });
    }
}