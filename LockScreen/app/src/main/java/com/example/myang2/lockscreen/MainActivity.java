package com.example.myang2.lockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myang2.lockscreen.LockScreenService;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private MyGLSurfaceView mGLView;
    public String path = Environment.getExternalStorageDirectory().getPath() + "/assets";
    BufferedReader reader;
    String line;
    float[][] coord;
    private ArrayList<ArrayList<Float>> point;
    private ArrayList<ArrayList<ArrayList<Float>>> points;
    boolean isPolylines;
    int countOfPolylines;
    int[][] countOfPoints;
    int indexOfPoints;
    int index;
    static String[] fileName;
    int size;
    private Button button;
    String[] fileNames = {
            "birdhead.dat",
            "dino.dat",
            "dragon.dat",
            "house.dat",
            "knight.dat",
            "rex.dat",
            "scene.dat",
            "usa.dat",
            "vinci.dat"
    };
    int fileIndex;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout layoutMain = (RelativeLayout) findViewById(R.id.layout_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        //LinearLayout layoutOfGL  = (LinearLayout) findViewById(R.id.layout).findViewById(R.id.layoutOfGl);

        mGLView = new MyGLSurfaceView(this);
        mGLView.setmActivity(this);

        //fileIndex = 1;
        //fileName[fileIndex] = fileNames[fileIndex];
        readFile(fileNames);
        layout.addView(mGLView);

        mGLView.setFileName(fileNames);
        mGLView.setCoor(coord);
        mGLView.setPoints(points);
        mGLView.setCountOfPoints(countOfPoints);

        button = (Button) findViewById(R.id.button);
        button.setText("UnLock");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockScreen(v);
            }
        });
        //layout.addView(button);


        makeFullScreen();
        startService(new Intent(this, LockScreenService.class));
//        mGLView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
//            public void onSwipeRight() {
//                if(fileIndex == 8) fileIndex = 0;
//                else fileIndex += 1;
//                fileName = fileNames[fileIndex];
//                readFile(fileName);
//                mGLView.setFileName(fileName);
//                mGLView.setCoor(coord);
//                mGLView.setPoints(points);
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeLeft() {
//                if(fileIndex == 0) fileIndex = 8;
//                else fileIndex -= 1;
//                fileName = fileNames[fileIndex];
//                readFile(fileName);
//                mGLView.setFileName(fileName);
//                mGLView.setCoor(coord);
//                mGLView.setPoints(points);
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * A simple method that sets the screen to fullscreen.  It removes the Notifications bar,
     *   the Actionbar and the virtual keys (if they are on the phone)
     */
    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    public void unlockScreen(View view) {
        //Instead of using finish(), this totally destroys the process
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onBackPressed() {
        return; //Do nothing!
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

    public void readFile(String[] fileName) {
        points = new ArrayList<>();
        coord = new float[9][];
        countOfPoints = new int[9][];
        for (int j = 0; j < 9; j++) {
            try {
                InputStream in = getAssets().open(fileName[j]);
                reader = new BufferedReader(new InputStreamReader(in));
                int i = 0;
                countOfPolylines = 0;
                indexOfPoints = 0;
                index = 0;

                do {
                    line = reader.readLine();
                    if (line == null) break;
                    String[] lines = line.split("\\s+");
                    if (isPolylines) {
                        if (lines.length == 1) {
                            countOfPoints[j][index] = Integer.parseInt(lines[0]);
                            ArrayList<Float> list = new ArrayList<>();
                            point.add(list);
                            index++;
                            i = 0;

                        }
                    }
                    if (index != 0 && lines.length > 1 && i < countOfPoints[j][index - 1]) {
                        point.get(index - 1).add(Float.parseFloat(lines[lines.length - 2]));
                        point.get(index - 1).add(Float.parseFloat(lines[lines.length - 1]));
                        point.get(index - 1).add(0.0f);
                        i++;

                    }
                    if (lines.length == 1 && !isPolylines) {
                        isPolylines = true;
                        countOfPolylines = Integer.parseInt(lines[0]);
                        countOfPoints[j] = new int[countOfPolylines];
                        point = new ArrayList<>();
                    }
                    // do something with the line
                } while (line != null);

                in.close();
                getCoord(j);
                points.add(point);
                isPolylines = false;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void getCoord(int k) {
        int total = 0;
        for (int i = 0; i < point.size(); i++) {
            total += point.get(i).size();
        }
        coord[k] = new float[total];
        int indexOfCoor = 0;
        for (int i = 0; i < point.size(); i++) {
            for (int j = 0; j < point.get(i).size(); j++) {
                coord[k][indexOfCoor++] = point.get(i).get(j);
            }
        }
    }
}
