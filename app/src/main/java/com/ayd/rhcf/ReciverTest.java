package com.ayd.rhcf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yxd on 2016/5/9.
 */
public class ReciverTest extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNum = getResultData();
        String newPhoneNum ="+86"+phoneNum;
        setResultData(newPhoneNum);
    }
}
