package com.ayd.rhcf.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by yxd on 2016/5/12.
 */
public class ConnectThread extends Thread {
    private final BluetoothDevice device;
    private final BluetoothSocket socket;

    public ConnectThread( BluetoothDevice device) {
        this.device = device;
        BluetoothSocket tmp = null;
        try {
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00000000-2527-eef3-ffff-ffffe3160865"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = tmp;
    }


    @Override
    public void run() {
        try {
            socket.connect();
            new ConnectedThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void cancle(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
