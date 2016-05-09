package com.example.ajay.animationswithopengl.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajay.animationswithopengl.Interpolators.CustomInterpolator;
import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 27/2/16.
 */
public class ValueAnimatorFragment extends Fragment implements View.OnClickListener{

    private LinearLayout mBallLayout;
    private FrameLayout mFrameLayout;
    private ImageView mBall;
    private Button mAlpha;
    private Button mScale;
    private Button mTransform;
    private int mInterpolatorFlag = 0;
    private Button mLinearInterpolator;
    private Button mAccelerateDecelerateInterpolator;
    private Button mCustomInterpolator;
    private ValueAnimator mAnimator;
    private final int DURATION = 3000;
    private TextView mSelectBallLayout;
    private TextView mSelectFrameLayout;
    private LinearLayout mBottomLayout;
    private CheckBox mCheckBox;
    private TextView mHiddenText;
    private LinearLayout mBottomBar;
    private Button mReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.value_animator_fragment,null);

        initializeViews(view);
        addListeners();

        return view;
    }

    /**
     * Bind views to objects
     * @param view
     */
    private void initializeViews(View view){

        mInterpolatorFlag = 0;
        mSelectBallLayout = (TextView) view.findViewById(R.id.ball_layout_text);
        mSelectFrameLayout = (TextView) view.findViewById(R.id.frame_layout_text);
        mBallLayout = (LinearLayout) view.findViewById(R.id.ball_layout);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
        mHiddenText = (TextView) view.findViewById(R.id.hidden_text);
        mBall = (ImageView) view.findViewById(R.id.ball);
        mAlpha = (Button) view.findViewById(R.id.alpha);
        mScale = (Button) view.findViewById(R.id.scale);
        mTransform = (Button) view.findViewById(R.id.transform);
        mBottomLayout = (LinearLayout) view.findViewById(R.id.bottom_layout);
        mBottomBar = (LinearLayout) view.findViewById(R.id.bottom_border);
        mReset = (Button) view.findViewById(R.id.reset);
        mLinearInterpolator = (Button) view.findViewById(R.id.linear_interpolator);
        mAccelerateDecelerateInterpolator = (Button) view
                .findViewById(R.id.accelerate_decelerate_interpolator);
        mCustomInterpolator = (Button) view.findViewById(R.id.custom_interpolator);
    }

    /**
     * Add listeners to clickable views
     */
    private void addListeners(){

        mAlpha.setOnClickListener(this);
        mScale.setOnClickListener(this);
        mTransform.setOnClickListener(this);
        mLinearInterpolator.setOnClickListener(this);
        mAccelerateDecelerateInterpolator.setOnClickListener(this);
        mCustomInterpolator.setOnClickListener(this);
        mSelectBallLayout.setOnClickListener(this);
        mSelectFrameLayout.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final RelativeLayout.LayoutParams params =
                        (RelativeLayout.LayoutParams) mBottomBar.getLayoutParams();

                ValueAnimator bottomBarAnimator = ValueAnimator.ofInt(params.topMargin,
                        params.topMargin + (int) getResources().getDimension(R.dimen.dp_20))
                        .setDuration(DURATION - 1500);
                bottomBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        params.topMargin = (int) animation.getAnimatedValue();
                        mBottomBar.requestLayout();
                    }
                });
                bottomBarAnimator.start();

                if (isChecked)
                    mHiddenText.setText("Checked");
                else
                    mHiddenText.setText("Not Checked");

                ObjectAnimator hiddenTextAnimator = ObjectAnimator.ofFloat(mHiddenText, "alpha", 0, 1)
                        .setDuration(DURATION - 500);
                hiddenTextAnimator.start();
            }
        });
        mReset.setOnClickListener(this);
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

            case R.id.ball_layout_text:
                showBallLayout();
                break;

            case R.id.frame_layout_text:
                showFrameLayout();
                break;

            case R.id.reset:
                reset();
                break;

            default:
                break;
        }
    }

    /**
     * Method to reset the frame layout
     */
    public void reset(){
        mHiddenText.setAlpha(0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                mBottomBar.getLayoutParams();
        params.topMargin = (int) getResources().getDimension(R.dimen.dp_10);
        mBottomBar.setLayoutParams(params);
    }

    /**
     * Method to set visibility for frame layout
     */
    public void showFrameLayout(){
        mBallLayout.setVisibility(View.GONE);
        mBottomLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
        mReset.setVisibility(View.VISIBLE);
    }

    /**
     * Method to set visibility for ball layout
     */
    public void showBallLayout(){
        mBallLayout.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
        mReset.setVisibility(View.GONE);
    }

    /**
     * Method to animate alpha value of ball
     * @param view
     */
    private void animateAlpha(final View view){

        mAnimator = ValueAnimator.ofFloat(1, 0);
        mAnimator.setRepeatCount(0);
        setInterpolator();
        mAnimator.setDuration(DURATION);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                view.setAlpha((Float) animation.getAnimatedValue());
                view.requestLayout();
            }
        });
        setOnAnimationEnd("alpha",view);
        mAnimator.start();
    }

    /**
     * Method to animate scale of the view
     * @param view
     */
    private void animateScale(final View view){

        mAnimator = ValueAnimator.ofInt(0, 500);
        mAnimator.setRepeatCount(0);
        setInterpolator();
        mAnimator.setDuration(DURATION);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                view.getLayoutParams().height = (int) animation.getAnimatedValue();
                view.getLayoutParams().width = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });
        setOnAnimationEnd("scale", view);
        mAnimator.start();
    }

    /**
     * Method to animate position of view
     * @param view
     */
    private void animateTransform(final View view){

        final RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) view.getLayoutParams();
        mAnimator = ValueAnimator.ofInt(params.topMargin,1000);
        mAnimator.setRepeatCount(0);
        setInterpolator();
        mAnimator.setDuration(DURATION);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                params.topMargin = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });
        setOnAnimationEnd("transform", view);
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

    /**
     * Add listener on animation end
     */
    private void setOnAnimationEnd(final String pType,final View view){
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                switch(pType){
                    case "alpha":
                        view.setVisibility(View.VISIBLE);
                        view.setAlpha(1);
                        break;

                    case "scale":
                        view.setVisibility(View.VISIBLE);
                        view.getLayoutParams().height =
                                (int) getResources().getDimension(R.dimen.dp_70);
                        view.getLayoutParams().width =
                                (int) getResources().getDimension(R.dimen.dp_70);
                        break;

                    case "transform":
                        view.setVisibility(View.VISIBLE);
                        ((RelativeLayout.LayoutParams) view.getLayoutParams()).topMargin
                                = (int) getResources().getDimension(R.dimen.dp_100);
                        break;

                    default:
                        break;
                }

                super.onAnimationEnd(animation);
            }
        });
    }
}