package com.ayd.rhcf.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.IOException;

public class PhoneRecordService extends Service {
    MediaRecorder recorder;
    PhoneRecordService phoneRecordService;

    public PhoneRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        getTelphoneManager();
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public PhoneRecordService getService() {
            if (phoneRecordService == null) {
                synchronized (PhoneRecordService.class) {
                    if (phoneRecordService == null) {
                        phoneRecordService = new PhoneRecordService();
                    }
                }
            }
            return phoneRecordService;
        }
    }

    public void getTelphoneManager() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tm.listen(new MyListen(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    class MyListen extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (recorder != null) {
                        recorder.stop();
                        recorder.release();
                        recorder = null;
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    recorder.start();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    getRecorder();
                    break;
                default:
                    return;
            }
        }
    }

    public void getRecorder() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile("sdcard/audio.3gp");

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
