package com.ayd.rhcf.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ayd.rhcf.R;
import com.ayd.rhcf.view.XBWView;

public class XBWTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xbwtest);
        View view  = LayoutInflater.from(this).inflate(R.layout.activity_xbwtest,null);
        XBWView xbwView = new XBWView(this);
        xbwView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(xbwView);
    }
}
