package com.example.ajay.animationswithopengl.CustomAnimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.example.ajay.animationswithopengl.Interpolators.CustomInterpolator;

/**
 * Created by ajay on 7/4/16.
 */
public class MyAnimation extends Animation {

    private View mView;
    private Long DURATION;
    private Interpolator mInterpolator;

    public MyAnimation(View view){
        mView = view;
        DURATION = 500L;
        mInterpolator = new CustomInterpolator();
    }

    public void setDURATION(Long DURATION) {
        this.DURATION = DURATION;
    }

    public Long getDURATION() {
        return DURATION;
    }

    public void setmInterpolator(Interpolator mInterpolator) {
        this.mInterpolator = mInterpolator;
    }

    public Interpolator getmInterpolator() {
        return mInterpolator;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public View getmView() {
        return mView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animate(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView,"alpha",1f,0f,1f);

        int backgroundColor = getBackgroundColor();
        ObjectAnimator animator2 = ObjectAnimator.ofArgb(mView, "background", backgroundColor,
                android.R.color.holo_orange_light, backgroundColor);

        AnimatorSet myAnimation = new AnimatorSet();
        myAnimation.playSequentially(new Animator[]{animator,animator2});
        myAnimation.setInterpolator(mInterpolator);
        myAnimation.setDuration(DURATION);
        myAnimation.start();
    }
}