package com.ayd.rhcf;

import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.Process;
import java.util.Collections;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener {
    Button close;
    Button restart;
    Button recovery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        close= (Button) findViewById(R.id.close);
        restart = (Button) findViewById(R.id.restart);
        recovery = (Button) findViewById(R.id.recovery);
        close.setOnClickListener(this);
        restart.setOnClickListener(this);
        recovery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                try {
                    Process process = Runtime.getRuntime().exec("su");
                    DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
                    outputStream.writeBytes("reboot -p\n");
                    outputStream.writeBytes("exit\n");
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.restart:
                String cmd = "su -c reboot";
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.recovery:

                break;
            default:
                return;
        }
    }
}
