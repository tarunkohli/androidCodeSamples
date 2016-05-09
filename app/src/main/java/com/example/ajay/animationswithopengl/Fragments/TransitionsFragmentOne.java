package com.example.ajay.animationswithopengl.Fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 12/4/16.
 */
public class TransitionsFragmentOne extends Fragment{

    private ImageView mImg;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transitions_fragment_1,container,false);
        mImg = (ImageView) view.findViewById(R.id.img);
        mImg.setTransitionName("image_transition");

        return view;
    }
}