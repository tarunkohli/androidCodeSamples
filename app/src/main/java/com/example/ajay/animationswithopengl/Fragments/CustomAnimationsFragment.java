package com.example.ajay.animationswithopengl.Fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.example.ajay.animationswithopengl.Adapter.CustomAnimationsFragmentAdapter;
import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 30/3/16.
 */
public class CustomAnimationsFragment extends Fragment {

    private ListView mList;
    private String[] mDataList;
    private AnimationSet mSet;
    private int mScreenWidth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_animations_fragment,container,false);
        mList = (ListView) view.findViewById(R.id.custom_animations);
        mDataList = getResources().getStringArray(R.array.custom_animations_fragment_elements);

        CustomAnimationsFragmentAdapter adapter =
                new CustomAnimationsFragmentAdapter(getActivity(),mDataList);
        mList.setAdapter(adapter);

        //get screen height
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        mScreenWidth = size.x;

        //animation set on list view
        mSet = new AnimationSet(false);
        mSet.addAnimation(new AlphaAnimation(0f,1f));
        mSet.addAnimation(new TranslateAnimation(-mScreenWidth, 0f, 0f, 0f));
        mSet.setDuration(1500);
        mList.startAnimation(mSet);

        return view;
    }
}