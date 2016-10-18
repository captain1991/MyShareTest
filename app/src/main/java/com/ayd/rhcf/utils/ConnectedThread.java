package com.ayd.rhcf.utils;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by yxd on 2016/5/12.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket msocket;
    private final InputStream in;
    private final OutputStream out;

    public ConnectedThread(BluetoothSocket msocket) {
        this.msocket = msocket;

        InputStream tempin = null;
        OutputStream tempout = null;
        try {
            tempin = msocket.getInputStream();
            tempout = msocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.in = tempin;
        this.out = tempout;
    }


    @Override
    public void run() {
        super.run();
        byte[] buff = new byte[1024];
        int len;
        while (true) {
            try {
                len = in.read(buff);
                String str = new String(buff, "ISO-8859-1");
                str = str.substring(0, len);
                Log.e("read", str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(byte[] bytes){
        try {
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancle(){
        try {
            msocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
