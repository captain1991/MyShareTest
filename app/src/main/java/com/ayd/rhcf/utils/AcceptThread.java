package com.ayd.rhcf.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by yxd on 2016/5/12.
 */
public class AcceptThread extends Thread {
    private final BluetoothServerSocket serverSocket;

    public AcceptThread(BluetoothAdapter mBluetoothAdapter) {
        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("blue", UUID
                    .fromString("00000000-2527-eef3-ffff-ffffe3160865"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = tmp;
    }

    @Override
    public void run() {
        BluetoothSocket socket=null;

        while (true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (socket != null){
                new ConnectedThread(socket).start();
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                break;
            }
        }
    }

    public void cancle( BluetoothSocket socket){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
