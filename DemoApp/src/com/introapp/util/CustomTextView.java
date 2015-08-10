package com.introapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sachit on 6/14/2015.
 */
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        style(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context);
    }

    private void style(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto.ttf");
        setTypeface(tf);
    }

}