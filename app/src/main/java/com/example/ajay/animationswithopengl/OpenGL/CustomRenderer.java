package com.example.ajay.animationswithopengl.OpenGL;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ajay on 28/1/16.
 */
public class CustomRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // background frame color
        GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);

        mTriangle = new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // draw the triangle
        mTriangle.draw();
    }

    public static int loadShader(int pType, String pShaderCode){

        // Shader type (vertex or fragment)
        int lShader = GLES20.glCreateShader(pType);

        // Add source code to shader and compile it
        GLES20.glShaderSource(lShader, pShaderCode);
        GLES20.glCompileShader(lShader);

        return lShader;
    }
}
