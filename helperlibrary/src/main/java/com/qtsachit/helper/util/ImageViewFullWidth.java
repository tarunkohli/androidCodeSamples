package com.qtsachit.helper.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * The class is Created by Sachit on 01/February/2017
 * <p>
 * Description- This class will provide the facility to show the image view with full width
 * <p>
 * Additional notes-
 */

public class ImageViewFullWidth extends ImageView {

    public ImageViewFullWidth(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Drawable d = getDrawable();
        if (d != null) {
            final float drawableWidth = d.getIntrinsicWidth();
            final float drawableHeight = d.getIntrinsicHeight();
            float proportion = drawableHeight / drawableWidth;

            final int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
            final int viewHeight = (int) (viewWidth * proportion);

            setMeasuredDimension(viewWidth, viewHeight);
        }
    }

}
