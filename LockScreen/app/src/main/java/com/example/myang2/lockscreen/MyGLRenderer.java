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

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.example.myang2.lockscreen.MainActivity.fileName;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer  implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;

   // private Square   mSquare;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private float[][] mMVPMatrix = new float[9][16];
    private float[][] mProjectionMatrix = new float[9][16];
    private float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private MainActivity mActivity;
    private String[] fileName = new String[9];
    private float[][] coor = new float[9][];
    private ArrayList<ArrayList<ArrayList<Float>>> points = new ArrayList<>();

    private float mAngle;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mTriangle.setmActivity(mActivity);


       // mSquare   = new Square();
    }
    public void setmActivity(MainActivity mActivity) {
        this.mActivity = mActivity;

    }
    public void setFileName(String[] fileName) {
        for (int i = 0; i < 9; i++) {
            this.fileName[i] = fileName[i];
        }
    }

    public void setCoor(float[][] coor) {
        for (int i = 0; i < 9; i++) {
            this.coor[i] = coor[i];
        }
    }
    public void setPoints(ArrayList<ArrayList<ArrayList<Float>>> points) {
        for (int i = 0; i < 9; i++) {
            this.points.add(points.get(i));
        }
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[][] scratch = new float[9][16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        //Matrix.scaleM(mProjectionMatrix, 0, 0.05f, 0.05f, 1.0f);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        //Matrix.orthoM(mProjectionMatrix, 0, -1.3f, 1.3f, -1, 1, -1, 1);
        // Calculate the projection and view transformation
        //Matrix.multiplyMM(mMVPMatrix[1], 0, mProjectionMatrix[1], 0, mViewMatrix, 0);

        // Draw square
      //  mSquare.draw(mMVPMatrix);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch[0], 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle

        for (int i = 0; i < 9; i++) {
            Matrix.multiplyMM(mMVPMatrix[i], 0, mProjectionMatrix[i], 0, mViewMatrix, 0);
            mTriangle.setCoor(coor[i]);
            mTriangle.setPoints(points.get(i));
            mTriangle.draw(mMVPMatrix[i]);
        }
        //mTriangle.draw(mMVPMatrix[0]);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);
        mProjectionMatrix = new float[9][16];

        float ratio = (float) width / height;


        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //birdhead.dat
        Matrix.frustumM(mProjectionMatrix[0], 0, -ratio*1.1F, ratio/0.8F, -3.0f, 0.8f, 2, 7);

        //(fileName[1].equals("dino.dat")) {
        Matrix.frustumM(mProjectionMatrix[1], 0, -ratio*8, ratio/1.8F, -6.3f, 1.4f, 2, 7);
        Matrix.scaleM(mProjectionMatrix[1], 0, 0.004f, 0.004f, 1.0f);

        //dragon.dat
        Matrix.frustumM(mProjectionMatrix[2], 0, -ratio*1.3F, ratio/0.6F, -3.0f, 2.0f, 2, 7);

        //} else if(fileName.equals("house.dat")){
        Matrix.frustumM(mProjectionMatrix[3], 0, -ratio*2F, ratio/1.5F, -1.4F, 0.8F, 3, 7);
        Matrix.scaleM(mProjectionMatrix[3], 0, 0.3f, 0.3f, 1.0f);

        //} else if(fileName.equals("knight.dat")){
        Matrix.frustumM(mProjectionMatrix[4], 0, -ratio*0.9F, ratio, -0.6F, 1.6F, 3, 7);
        Matrix.scaleM(mProjectionMatrix[4], 0, 0.5f, 0.5f, 1.0f);

        //fileName.equals("rex.dat")){
        Matrix.frustumM(mProjectionMatrix[5], 0, -ratio*50, ratio/10, -7.6F, 31F, 3, 7);
        Matrix.scaleM(mProjectionMatrix[5], 0, 20f, 20f, 1.0f);

        //fileName.equals("scene.dat")){
        Matrix.frustumM(mProjectionMatrix[6], 0, -ratio*1.9f, ratio/0.4f, -0.2F, 5F, 3, 7);
//        Matrix.scaleM(mProjectionMatrix[6], 0, 20f, 20f, 1.0f);

        //} else if(fileName.equals("usa.dat")){
        Matrix.frustumM(mProjectionMatrix[7], 0, -ratio*5.1f, ratio/1.1f, -0.5F, 6F, 3, 7);
        Matrix.scaleM(mProjectionMatrix[7], 0, 0.8f, 0.8f, 1.0f);
//
//        //fileName.equals("vinci.dat")){
//        Matrix.frustumM(mProjectionMatrix[8], 0, -ratio*20, ratio/20, -0.0F, 14F, 3, 7);
//        Matrix.scaleM(mProjectionMatrix[8], 0, 20f, 20f, 1.0f);
    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }

}