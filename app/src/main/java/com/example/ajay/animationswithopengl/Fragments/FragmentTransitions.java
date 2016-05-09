package com.example.ajay.animationswithopengl.Fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 12/4/16.
 */
public class FragmentTransitions extends Fragment{

    private FrameLayout mFrame;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_transitions,container,false);
        mFrame = (FrameLayout) view.findViewById(R.id.content_frame);

        addDefaultFragment();

        return view;
    }

    private void addDefaultFragment(){

        Fragment fragment = new TransitionsFragmentTwo();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(mFrame.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}