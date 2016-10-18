package com.ayd.rhcf;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;

/**
 * Created by yxd on 2016/4/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        umengShareConfig();

    }

    private void umengShareConfig() {
        //微信,朋友圈；
        PlatformConfig.setWeixin("wxf8aad4382df4b0d3", "b11f0c2c903bd5a4e229a8b442fd932a");
//        //sina新浪微博
        PlatformConfig.setSinaWeibo("4182989850", "2ec29c5458f29ba28b5eabcd151065aa");
//        // QQ,Qzone
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        // 关闭分享的Log和Toast
        Log.LOG = false;
        Config.IsToastTip = true;
        //关闭sina的日志输出；
//        com.sina.weibo.sdk.utils.LogUtil.disableLog();
        }
        }
