package com.example.ajay.animationswithopengl;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;

/**
 * Created by ajay on 2/2/16.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyTransition extends Transition {

    private final String BACKGROUND = "com.example.ajay.animationswithopengl:MyTransition:background";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues){

        View view = transitionValues.view;
        transitionValues.values.put(BACKGROUND,view.getBackground());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }
}
