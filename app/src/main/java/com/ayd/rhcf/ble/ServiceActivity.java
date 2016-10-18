package com.ayd.rhcf.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ayd.rhcf.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Set;
import java.util.UUID;

public class ServiceActivity extends Activity implements View.OnClickListener {
    BluetoothAdapter bleadapter;
    BluetoothDevice device;
    String address;
    StringBuffer sdevice= new StringBuffer();
    StringBuffer pdsdevice= new StringBuffer();
    Button button;
    Button btnsearch;
    BluetoothSocket socket;
    BluetoothServerSocket serverSocket;
    PrintStream bos;
    BufferedReader in;
    boolean isReceiver = true;
    TextView textView;
    EditText editText;
    Button btnsend;

    private static final int SERVER_SUCCESS=0;
    private static final int RECEIVE_SUCCESS=1;
    private static final int CLIENT_SUCCESS=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        btnsearch = (Button) findViewById(R.id.buttonsearch);
        btnsearch.setOnClickListener(this);
        bleadapter = BluetoothAdapter.getDefaultAdapter();
        textView = (TextView) findViewById(R.id.clienttext);
        textView.setText("server");
        editText = (EditText) findViewById(R.id.clientedit);
        btnsend = (Button) findViewById(R.id.buttonsend);
        btnsend.setOnClickListener(this);
    }

    private void getbleAdp(){

        if(bleadapter==null){
            return;
        }

        if(bleadapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
            startActivity(intent);
        }

        Set<BluetoothDevice> bluetoothDevices = bleadapter.getBondedDevices();
        for (BluetoothDevice bluetoothDevice:bluetoothDevices){
            address = bluetoothDevice.getAddress();
        }
        init();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getbleAdp();
                break;
            case R.id.buttonsend:
                new Thread(){
                    @Override
                    public void run() {
                        Log.e("=====","===2222===");
                        if(bos!=null) {
                            Log.e("=====","======");
                            bos.println(editText.getText().toString());
                        }
                    }
                }.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bleadapter.disable();
    }

    private void init(){
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("Name",bleadapter.getName());
                    serverSocket = bleadapter.listenUsingRfcommWithServiceRecord(bleadapter.getName(), UUID
                            .fromString("00000000-2527-eef3-ffff-ffffe3160865"));
                    while (true) {
                        socket = serverSocket.accept();
                        if (socket != null) {
                            bos = new PrintStream(socket.getOutputStream());
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                textView.setText(info);
            }else if(msg.what==0){
                Log.e("success","success");
                Thread thread = new Thread(new ReceiverInfoThread());
                thread.start();
            }
        }
    };
    String info = null;
    class ReceiverInfoThread implements Runnable {

        @Override
        public void run() {

            while (isReceiver) {
                try {
                    //没数据时处于阻塞状态
                    info = in.readLine();
                    mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
