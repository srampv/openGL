package com.example.shashank.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

/*

/**
* Created by shashank on 6/5/16.
*/
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private float x;
    private float y;

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {

        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    private float vertices[] = {
            -1.0f,  1.0f, 0.0f,  // 0, Top Left
            -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
            1.0f, -1.0f, 0.0f,  // 2, Bottom Right
            1.0f,  1.0f, 0.0f,  // 3, Top Right
    };
    FloatBuffer vertexBuffer=null;

    private short[] indices = { 0, 1, 2, 0, 2, 3 };


    public MyGLRenderer(){
        // a float is 4 bytes, therefore we multiply the number if vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
         vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

    }


        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set the background color to black ( rgba ).
            gl.glClearColor(1.0f, 1.0f, 0.0f, 0.5f);
            // Enable Smooth Shading, default not really needed.
            gl.glShadeModel(GL10.GL_SMOOTH);
            // Depth buffer setup.
            gl.glClearDepthf(1.0f);
            // Enables depth testing.
            gl.glEnable(GL10.GL_DEPTH_TEST);
            // The type of depth testing to do.
            gl.glDepthFunc(GL10.GL_LEQUAL);
            // Really nice perspective calculations.
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                    GL10.GL_NICEST);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.
             * microedition.khronos.opengles.GL10)
         */
        public void onDrawFrame(GL10 gl) {
            gl.glLineWidth(2.5f);
            gl.glColor4f(1.0f,0.0f,0.0f,0.0f);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                    GL10.GL_DEPTH_BUFFER_BIT);
            gl.glTranslatef(0, 0, -4);
            Square s=new Square(getX(),getY());
            s.draw(gl);
            gl.glLoadIdentity();
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.
             * microedition.khronos.opengles.GL10, int, int)
         */
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // Sets the current view port to the new size.
            gl.glViewport(0, 0, width, height);
            // Select the projection matrix
            gl.glMatrixMode(GL10.GL_PROJECTION);
            // Reset the projection matrix
            gl.glLoadIdentity();
            // Calculate the aspect ratio of the window
            GLU.gluPerspective(gl, 45.0f,
                    (float) width / (float) height,
                    0.1f, 100.0f);
            // Select the modelview matrix
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            // Reset the modelview matrix
            gl.glLoadIdentity();
        }



}
