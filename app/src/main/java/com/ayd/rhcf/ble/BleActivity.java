package com.ayd.rhcf.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ayd.rhcf.R;

import java.util.Set;

public class BleActivity extends Activity implements View.OnClickListener {
    BluetoothAdapter mBtadapter;
    StringBuffer sdevice= new StringBuffer();
    StringBuffer pdsdevice= new StringBuffer();
    Button button;
    Button btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        btnsearch = (Button) findViewById(R.id.buttonsearch);
        btnsearch.setOnClickListener(this);
        registReceiver();
    }

    public void initBlueTooth(){
        mBtadapter = BluetoothAdapter.getDefaultAdapter();
        if(mBtadapter == null){
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
           return;
        }
        if(!mBtadapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
            startActivity(intent);
//              强制开启蓝牙
//            mBtadapter.enable();
        }
        Set<BluetoothDevice> devices = mBtadapter.getBondedDevices();
        for(BluetoothDevice device:devices){
            pdsdevice.append(device.getName() + ":"
                    + device.getAddress() + "\n");
        }
        Log.e("以配对设备",pdsdevice.toString());

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                initBlueTooth();
                break;
            case R.id.buttonsearch:
                setProgressBarIndeterminateVisibility(true);
                setTitle("正在搜索...");
                if(mBtadapter.isDiscovering()){
                    mBtadapter.cancelDiscovery();
                }
                mBtadapter.startDiscovery();
                break;
            default:
                return;
        }

    }

    public void registReceiver(){
        myBroadcast receiver = new myBroadcast();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver,filter);
    }

    class myBroadcast extends BroadcastReceiver{

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
