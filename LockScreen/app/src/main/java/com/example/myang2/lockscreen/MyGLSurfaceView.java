/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.myang2.lockscreen;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.myang2.lockscreen.R.id.button;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private Button button;
    private MainActivity mActivity;
    private String fileName;
    private float[] coor;
    ArrayList<ArrayList<Float>> points;
    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

//        button.findViewById(R.id.button);
//        button.setText("asd");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                unlockScreen(v);
//            }
//        });
    }

    public void setmActivity(MainActivity mActivity) {
        this.mActivity = mActivity;
        mRenderer.setmActivity(mActivity);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        mRenderer.setFileName(fileName);
    }

    public void setCoor(float[] coor) {
        this.coor = coor;
        mRenderer.setCoor(coor);
    }
    public void setPoints(ArrayList<ArrayList<Float>> points) {
        this.points = points;
        mRenderer.setPoints(points);
    }



    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public void unlockScreen(View view) {
        //Instead of using finish(), this totally destroys the process
        android.os.Process.killProcess(android.os.Process.myPid());
    }




}
