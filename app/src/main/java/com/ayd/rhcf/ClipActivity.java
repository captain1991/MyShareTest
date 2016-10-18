package com.ayd.rhcf;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayd.rhcf.view.ClipImageBorderView;
import com.ayd.rhcf.view.ClipImageLayout;

public class ClipActivity extends AppCompatActivity {
    ClipImageLayout clipImageLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        clipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
//        点击事件生成裁剪后的bitmap
//        Bitmap bitmap = clipImageLayout.clip();

    }
}
