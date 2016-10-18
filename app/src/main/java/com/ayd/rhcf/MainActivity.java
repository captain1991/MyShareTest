package com.ayd.rhcf;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ayd.rhcf.services.PhoneRecordService;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    PhoneRecordService phoneRecordService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShareAPI = UMShareAPI.get(MainActivity.this);
    }


    public void doShare(View v){
//        goShare();
        Intent intent = new Intent(MainActivity.this,PhoneRecordService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PhoneRecordService.MyBinder binder = (PhoneRecordService.MyBinder) service;
                phoneRecordService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },BIND_AUTO_CREATE);
    }

    public void doShouquan(View v){
        goShouquan(v.getId());
    }

    public void doSCShouquan(View v){
        switch(v.getId()) {
            case R.id.scqqsq:
                mShareAPI.deleteOauth(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.scwxsq:
                mShareAPI.deleteOauth(MainActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.scwbsq:
                mShareAPI.deleteOauth(MainActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            default:
                return;
        }
    }

    public UMShareAPI mShareAPI;
    public void goShouquan(int i){

        SHARE_MEDIA share_media = null ;
        switch(i){
            case R.id.qqsq:
                share_media = SHARE_MEDIA.QQ;
                break;
            case  R.id.wxsq:
                share_media = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.wbsq:
                share_media = SHARE_MEDIA.SINA;
                break;
            default:
                return;
        }


        mShareAPI.doOauthVerify(MainActivity.this, share_media, umAuthListener);

//        mShareAPI.getPlatformInfo(MainActivity.this, share_media, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//            Toast.makeText(MainActivity.this, " 授权成功啦", Toast.LENGTH_SHORT).show();
//            StringBuilder stringBuilder = new StringBuilder();
//            Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()){
//                Map.Entry<String, String> entry = iterator.next();
//                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
//                if (iterator.hasNext()) {
//                    stringBuilder.append("\n");
//                }
//            }
//            Log.e("==stringBuilder==", stringBuilder.toString());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    public void goShare(){
        UMImage umImage = new UMImage(MainActivity.this,R.drawable.ic_logo);
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction(MainActivity.this).setDisplayList(displaylist)
                .setContentList(new ShareContent(), new ShareContent())

                .withText("呵呵")
                .withTitle("title")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(umImage)

                .setListenerList(umShareListener)
                .open();
    }


    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(MainActivity.this," 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(MainActivity.this," 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(MainActivity.this," 分享取消啦", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(MainActivity.this).onActivityResult(requestCode, resultCode, data);
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
