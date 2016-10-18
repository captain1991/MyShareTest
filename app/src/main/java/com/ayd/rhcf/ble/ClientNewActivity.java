package com.ayd.rhcf.ble;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.AcceptThread;
import com.ayd.rhcf.utils.ConnectThread;

public class ClientNewActivity extends Activity {
    ConnectThread connectThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }
}
