package com.example.ajay.animationswithopengl.Fragments;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 5/4/16.
 */
public class CircularRevealFragment extends Fragment {

    private RelativeLayout mRevealLayout;
    private FloatingActionButton mFab;
    private boolean isReveal;
    private TextView mClickText;
    private TextView mNormalText;
    private FrameLayout mMainFrame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.circular_reveal_layout,container,false);
        mRevealLayout = (RelativeLayout) view.findViewById(R.id.reveal_layout);
        mMainFrame = (FrameLayout) view.findViewById(R.id.main_frame);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_button);
        mClickText = (TextView) view.findViewById(R.id.click_button_text);
        mNormalText = (TextView) view.findViewById(R.id.normal_text);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isReveal) {
                    exitReveal(mRevealLayout);
                } else{
                    enterReveal(mRevealLayout);
                }
                isReveal = !isReveal;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMainFrame.setAnimation(new AlphaAnimation(0f,1f));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void exitReveal(final View view){

        int dx = view.getMeasuredWidth();
        int dy = view.getMeasuredHeight();

        int initialRadius = view.getWidth();

        Animator animator = ViewAnimationUtils.createCircularReveal(view,dx,dy,initialRadius,0);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                mClickText.setText(getString(R.string.click_button));
                mClickText.setTextColor(ContextCompat.getColor(getActivity(),android.R.color.black));
                mNormalText.setVisibility(View.VISIBLE);
                mFab.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getActivity(),R.color.colorAccent)));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void enterReveal(View view){

        int dx = view.getMeasuredWidth();
        int dy = view.getMeasuredHeight();

        int finalRadius = Math.max(dx, dy);

        Animator animator = ViewAnimationUtils.createCircularReveal(view,dx,dy,0,finalRadius);
        view.setVisibility(View.VISIBLE);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mClickText.setText(getString(R.string.click_to_go_back));
                mClickText.setTextColor(ContextCompat.getColor(getActivity(),android.R.color.white));
                mNormalText.setVisibility(View.GONE);
                mFab.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getActivity(),R.color.colorPrimary)));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
