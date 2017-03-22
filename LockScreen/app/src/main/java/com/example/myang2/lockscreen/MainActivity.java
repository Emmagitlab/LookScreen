package com.example.myang2.lockscreen;

import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private GLSurfaceView mGLView;
    public String path = Environment.getExternalStorageDirectory().getPath() + "/assets";
    BufferedReader reader;
    String line;
    static float[] coord;
    static ArrayList<ArrayList<Float>> points;
    boolean isPolylines;
    int countOfPolylines;
    int[] countOfPoints;
    int indexOfPoints ;
    int index;
    static String fileName;
    int size;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        fileName = "birdhead.dat";
        readFile(fileName);

        mGLView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
//                mGLView = new MyGLSurfaceView(getApplicationContext());
//                setContentView(mGLView);
//                fileName = "birdhead.dat";
//                readFile(fileName);
//                client = new GoogleApiClient.Builder(getApplicationContext()).addApi(AppIndex.API).build();
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void readFile(String fileName) {

        try {
            InputStream in = getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            int i = 0;
            countOfPolylines = 0;
            indexOfPoints = 0;
            index = 0;

            do {
                line = reader.readLine();
                if(line == null) break;
                String[] lines = line.split("\\s+");
                if (isPolylines) {
                    if (lines.length == 1) {
                        countOfPoints[index] = Integer.parseInt(lines[0]);
                        ArrayList<Float> list = new ArrayList<>();
                        points.add(list);
                        index++;
                        i = 0;

                    }
                }
                if (index != 0 && lines.length > 1 && i < countOfPoints[index-1]) {
                    points.get(index-1).add(Float.parseFloat(lines[lines.length-2]));
                    points.get(index-1).add(Float.parseFloat(lines[lines.length-1]));
                    points.get(index-1).add(0.0f);
                    i++;

                }
                if (lines.length == 1 && !isPolylines) {
                    isPolylines = true;
                    countOfPolylines = Integer.parseInt(lines[0]);
                    countOfPoints = new int[countOfPolylines];
                    points = new ArrayList<>();
                }
                // do something with the line
            } while (line != null);

            in.close();
            getCoord();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getCoord(){
        int total = 0;
        for(int i = 0; i < points.size();i++){
            total += points.get(i).size();
        }
        coord = new float[total];
        int indexOfCoor = 0;
        for(int i = 0; i < points.size();i++){
            for(int j = 0; j < points.get(i).size();j++){
                coord[indexOfCoor++] = points.get(i).get(j);
            }
        }

    }
}
