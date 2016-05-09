package com.example.ajay.animationswithopengl.Fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 29/2/16.
 */
public class AnimatorSetFragment extends Fragment {

    private LinearLayout mBallLayout;
    private LinearLayout mBallLayout2;
    private ImageView mBall;
    private ImageView mBall2;
    private ValueAnimator mTransformAnimator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.animator_set_fragment,null);

        mBall = (ImageView) view.findViewById(R.id.ball);
        mBallLayout = (LinearLayout) view.findViewById(R.id.ball_layout);
        mBall2 = (ImageView) view.findViewById(R.id.ball2);
        mBallLayout2 = (LinearLayout) view.findViewById(R.id.ball_layout2);

        applyAnimations(mBall,mBallLayout);
        applyAnimations(mBall2,mBallLayout2);

        return view;
    }

    /**
     * Method to apply animations
     * @param ball
     * @param ballLayout
     */
    private void applyAnimations(View ball,View ballLayout){

        AnimatorSet animatorSet = new AnimatorSet();

        Animator animator = ObjectAnimator.ofFloat(ball,"alpha",1,0,1)
                .setDuration(1000);
        Animator animator2 = ObjectAnimator.ofFloat(ballLayout, "scaleX", 0, 0.25f,0.5f, 0.75f, 1)
                .setDuration(1000);
        Animator animator3 = ObjectAnimator.ofFloat(ballLayout, "scaleY", 0, 0.25f, 0.5f, 0.75f, 1)
                .setDuration(1000);

        //To create a transform animation
        if(ball.getId() == R.id.ball)
            createTransformDownAnimation(ballLayout);
        else
            createTransformUpAnimation(ballLayout);

        animatorSet.play(animator)
                .with(animator2)
                .with(animator3)
                .after(mTransformAnimator);

        animatorSet.start();
    }

    /**
     * Method to create a downward transformation animation
     */
    private void createTransformDownAnimation(final View ballLayout){

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ballLayout.getLayoutParams();
        mTransformAnimator = ValueAnimator.ofInt(params.topMargin, params.topMargin +
                (int) getResources().getDimension(R.dimen.dp_250))
                .setDuration(1000);
        mTransformAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                params.topMargin = (int) animation.getAnimatedValue();
                ballLayout.requestLayout();
            }
        });
    }

    /**
     * Method to create a upward transformation animation
     */
    private void createTransformUpAnimation(final View ballLayout){

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ballLayout.getLayoutParams();
        mTransformAnimator = ValueAnimator.ofInt(params.bottomMargin, params.bottomMargin +
                (int) getResources().getDimension(R.dimen.dp_250))
                .setDuration(1000);
        mTransformAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                params.bottomMargin = (int) animation.getAnimatedValue();
                ballLayout.requestLayout();
            }
        });
    }
}