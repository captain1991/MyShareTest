package com.ayd.rhcf.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientActivity extends Activity implements View.OnClickListener {
    BluetoothAdapter bleadapter;
    BluetoothDevice device;
    String address;
    StringBuffer sdevice= new StringBuffer();
    StringBuffer pdsdevice= new StringBuffer();
    Button button;
    Button btnsearch;
    BluetoothSocket socket;
    PrintStream bos;
    BufferedReader in;
    boolean isReceiver = true;
    TextView textView;
    EditText editText;
    Button btnsend;
    //所有链接的设备
    Set<BluetoothDevice> bluetoothDevices;
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
        textView.setText("client");
        editText = (EditText) findViewById(R.id.clientedit);
        btnsend = (Button) findViewById(R.id.buttonsend);
        btnsend.setOnClickListener(this);
        registReceiver();
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
        bluetoothDevices = bleadapter.getBondedDevices();
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
//                EncodingHandler.createQRCode("www",300);
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

    private void init(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (final BluetoothDevice bluetoothDevice:bluetoothDevices) {
            Thread connectT = new Thread() {
                @Override
                public void run() {
                    try {
//                        UUID.randomUUID();
                        socket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID
                                .fromString("00000000-2527-eef3-ffff-ffffe3160865"));
                        if (socket != null) {
                            socket.connect();
                            bos = new PrintStream(socket.getOutputStream());
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            mHandler.sendEmptyMessage(0);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
           executorService.execute(connectT);
        }

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
                    info = in.readLine();
                    mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void registReceiver(){
        myBroadcast receiver = new myBroadcast();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        bleadapter.disable();
        super.onDestroy();
    }

    class myBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 判断是否配对过
//                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 添加到列表
                    sdevice.append(device.getName() + ":"
                            + device.getAddress() + "\n");
//                    }
                    address = device.getAddress();
//                    Log.e("address", address);
                    Log.e("配对设备",sdevice.toString());
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    setProgressBarIndeterminateVisibility(true);
                    setTitle("搜索完成...");
                    break;
            }
        }
    }

}
