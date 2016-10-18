package com.ayd.rhcf;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CarameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    SurfaceView surfaceview;
    SurfaceHolder holder;
    Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caramera);
        surfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        holder = surfaceview.getHolder();
        holder.addCallback(this);
        camera = getCamera();

    }


    private Camera getCamera(){
        Camera mCamera = null;
        mCamera = Camera.open();
        return mCamera;
    }

    private void stopCamera(){
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private void setCameraHolder(Camera camera,SurfaceHolder holder){
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setCameraHolder(camera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        camera.stopPreview();
        setCameraHolder(camera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }

    public void teckphoto(View v){
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File f = new File("/sdcard/picture.png");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(f);
                    fileOutputStream.write(data);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
