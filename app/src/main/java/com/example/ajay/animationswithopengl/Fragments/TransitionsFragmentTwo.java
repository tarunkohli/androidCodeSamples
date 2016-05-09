package com.example.ajay.animationswithopengl.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 18/4/16.
 */
public class TransitionsFragmentTwo extends Fragment {

    private Button mBtn;
    private ImageView mImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transitions_fragment_2,container,false);
        mBtn = (Button) view.findViewById(R.id.btn);
        mImg = (ImageView) view.findViewById(R.id.img);

        final Fragment fragment = new TransitionsFragmentOne();

        fragment.setSharedElementEnterTransition(TransitionInflater
                .from(getActivity()).inflateTransition(R.transition.change_transform));
        fragment.setSharedElementReturnTransition(TransitionInflater
                .from(getActivity()).inflateTransition(R.transition.change_transform));

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(container.getId(), fragment)
                        .addSharedElement(mImg, "image_transition")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
