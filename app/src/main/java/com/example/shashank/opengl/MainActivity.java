package com.example.shashank.opengl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class MainActivity extends Activity {
    private GLSurfaceView mGView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("<-------------OnActivityResult--------->");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent=new Intent(this,AudioRecordActivity.class);
        intent.putExtra("link","http://www.google.com");
        startActivityForResult(intent,1);
//        Runtime r=Runtime.getRuntime();
//        try {
//            r.exec(" su cd /system");
//            System.out.println("Files_DIR:--->"+this.getFilesDir());
//            File f=new File(this.getFilesDir(),"default.prop");
//            System.out.println("File_NAME:--->"+f.getName());
//           OutputStream outputStream = openFileOutput("default.prop", Context.MODE_APPEND);
//            outputStream.write( "ro.secure=0".getBytes());
//            outputStream.write( "ro.allow.mock.location=0".getBytes());
//            outputStream.write( "ro.debuggable=1".getBytes());
//            outputStream.write( "persist.service.adb.enable=1".getBytes());
//            outputStream.close();
//
//            Process ps1=r.exec("su cat /system/default.prop");
//            InputStream is=ps1.getInputStream();
//            DataInputStream ds=new DataInputStream(is);
//            String DATA="";
//            while(DATA!=null){
//                DATA=ds.readLine();
//                if(DATA==null)
//                    DATA="";
//                System.out.println("DATA:--->"+DATA);
//                if(DATA==null || DATA.length()==0)
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Welcome", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        MyGLSurfaceView view = new MyGLSurfaceView(this);
        //setContentView(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
