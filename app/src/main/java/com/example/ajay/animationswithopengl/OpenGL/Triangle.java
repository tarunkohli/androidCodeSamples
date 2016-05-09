package com.example.ajay.animationswithopengl.OpenGL;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by ajay on 28/1/16.
 */
public class Triangle {

    private FloatBuffer mVertexBuffer;
    static final int COORDS_PER_VERTEX = 3;
    static float mTriangleCoordinates[] = {
            0.0f,0.622008459f,0.0f, // top
            -0.5f, -0.311004243f, 0.0f,// bottom left
            0.5f, -0.311004243f, 0.0f// bottom right
    };

    private int mPositionHandle;
    private int mColorHandle;

    private final int mVertexCount = mTriangleCoordinates.length / COORDS_PER_VERTEX;
    private final int mVertexStride = COORDS_PER_VERTEX * 4;

    float mColor[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final String mVertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String mFragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private final int mProgram;

    public Triangle(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
                mTriangleCoordinates.length * 4
        );

        byteBuffer.order(ByteOrder.nativeOrder());

        mVertexBuffer = byteBuffer.asFloatBuffer();
        mVertexBuffer.put(mTriangleCoordinates);
        mVertexBuffer.position(0);

        int lVertexShader = CustomRenderer
                .loadShader(GLES20.GL_VERTEX_SHADER, mVertexShaderCode);
        int lFragmentShader = CustomRenderer
                .loadShader(GLES20.GL_FRAGMENT_SHADER,mFragmentShaderCode);

        // create empty program
        mProgram = GLES20.glCreateProgram();

        // attach vertex shader
        GLES20.glAttachShader(mProgram,lVertexShader);

        // attach fragment shader
        GLES20.glAttachShader(mProgram,lFragmentShader);

        // create program executables
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(){

        //Add program to OpenGLES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram,"vPosition");

        //Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        //Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle,COORDS_PER_VERTEX,
                                GLES20.GL_FLOAT,false,
                                mVertexStride,mVertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram,"vColor");

        // set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle,1, mColor,0);

        // draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,mVertexCount);

        // disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
