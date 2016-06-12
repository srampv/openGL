package com.example.shashank.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shashank on 6/5/16.
 */
public class MyGLSurfaceView extends GLSurfaceView implements View.OnTouchListener {

    private final MyGLRenderer myGLRenderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(1);
        myGLRenderer=new MyGLRenderer();
        setRenderer(myGLRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setOnTouchListener(this);


}

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();
        System.out.println(x+"<------------:-------------->"+y+(e.getAction()==MotionEvent.ACTION_MOVE));

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }
                myGLRenderer.setX(x);
                myGLRenderer.setY(y);
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("<---------------onTouchCalled------------->");
        onTouchEvent(event);
        return true;
    }
}
