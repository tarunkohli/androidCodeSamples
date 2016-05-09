package com.example.ajay.animationswithopengl.Fragments;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ajay.animationswithopengl.Interpolators.CustomInterpolator;
import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 28/2/16.
 */
public class ObjectAnimatorFragment extends Fragment implements View.OnClickListener{

    private LinearLayout mBallLayout;
    private ImageView mBall;
    private Button mAlpha;
    private Button mScale;
    private Button mTransform;
    private ObjectAnimator mAnimator;
    private Point mSize;
    private int mInterpolatorFlag = 0;
    private Button mLinearInterpolator;
    private Button mAccelerateInterpolator;
    private Button mCustomInterpolator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.object_animator_fragment,null);

        initializeViews(view);
        addListeners();

        return view;
    }

    /**
     * Bind views to objects
     * @param view
     */
    private void initializeViews(View view){

        mSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(mSize);
        mBall = (ImageView) view.findViewById(R.id.ball);
        mBallLayout = (LinearLayout) view.findViewById(R.id.ball_layout);
        mLinearInterpolator = (Button) view.findViewById(R.id.linear_interpolator);
        mAccelerateInterpolator = (Button) view
                .findViewById(R.id.accelerate_decelerate_interpolator);
        mCustomInterpolator = (Button) view.findViewById(R.id.custom_interpolator);
        mAlpha = (Button) view.findViewById(R.id.alpha);
        mScale = (Button) view.findViewById(R.id.scale);
        mTransform = (Button) view.findViewById(R.id.transform);
    }

    /**
     * Add listeners to clickable views
     */
    private void addListeners(){

        mAlpha.setOnClickListener(this);
        mScale.setOnClickListener(this);
        mTransform.setOnClickListener(this);
        mLinearInterpolator.setOnClickListener(this);
        mAccelerateInterpolator.setOnClickListener(this);
        mCustomInterpolator.setOnClickListener(this);
    }

    /**
     * OnClickListener implementation
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.alpha:
                animateAlpha(mBall);
                break;

            case R.id.scale:
                animateScale(mBallLayout);
                break;

            case R.id.transform:
                animateTransform(mBallLayout);
                break;

            case R.id.linear_interpolator:
                mInterpolatorFlag = 0;
                Toast.makeText(getActivity(),"Linear Interpolator set",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.accelerate_decelerate_interpolator:
                mInterpolatorFlag = 1;
                Toast.makeText(getActivity(),"Accelerate/Decelerate Interpolator set",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.custom_interpolator:
                mInterpolatorFlag = 2;
                Toast.makeText(getActivity(),"Custom Interpolator set",
                        Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /**
     * Method to animate alpha of view
     * @param view
     */
    private void animateAlpha(final View view){

        mAnimator = ObjectAnimator.ofFloat(view,"alpha",1,0,1)
                .setDuration(2000);
        setInterpolator();
        mAnimator.start();
    }

    /**
     * Method to animate scale of view
     * @param view
     */
    private void animateScale(final View view){

        mAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f,0.5f,1f)
                .setDuration(2000);
        setInterpolator();
        mAnimator.start();
    }

    /**
     * Method to animate transform of view
     * @param view
     */
    private void animateTransform(View view){

        mAnimator = ObjectAnimator.ofInt(view, "left", 100,
                ((mSize.x)/2) - (int) getResources().getDimension(R.dimen.dp_35))
                .setDuration(2000);
        setInterpolator();
        mAnimator.start();
    }

    /**
     * Method to set the interpolator
     */
    private void setInterpolator(){
        switch(mInterpolatorFlag){

            case 0:
                mAnimator.setInterpolator(new LinearInterpolator());
                break;

            case 1:
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                break;

            case 2:
                mAnimator.setInterpolator(new CustomInterpolator());
                break;

            default:
                break;
        }
    }
}