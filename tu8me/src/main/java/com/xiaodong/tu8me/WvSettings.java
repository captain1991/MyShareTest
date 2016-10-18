package com.xiaodong.tu8me;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xiaodong.tu8me.utils.CommonUtils;

/**
 * Created by gqy on 2016/3/14.
 */
public class WvSettings {

    public static abstract class IWvCallBack {
        public abstract void onProgressChanged(int newProgress);
    }

    public static void setting(final WebView mWebView,Context context) {
        mWebView.setInitialScale(100);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置WebView可触摸放大缩小
        webSettings.setBuiltInZoomControls(true);
        //支持缩放
        webSettings.setSupportZoom(true);

        webSettings.setDefaultTextEncodingName("utf-8");
      webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕：

        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(CommonUtils.getCachePath(context));
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }
    }
}
