package com.example.ajay.animationswithopengl.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.ajay.animationswithopengl.OpenGL.CustomRenderer;

/**
 * Created by ajay on 28/1/16.
 */
public class CustomSurface extends GLSurfaceView {

    private CustomRenderer mRenderer;

    public CustomSurface(Context context) {
        super(context);

        // OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new CustomRenderer();

        // set the renderer
        setRenderer(mRenderer);
    }
}
