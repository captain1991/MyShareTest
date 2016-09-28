package com.xiaodong.tu8me;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaodong.tu8me.utils.CommonUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yxd on 2016/9/28.
 */
public class BaseWebViewActivity extends FragmentActivity implements View.OnClickListener {
    private WebView webView;
    private ProgressBar progressBar;
    private MyTask task;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yqhy);
        textView = (TextView) findViewById(R.id.shuoming);
        textView.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        WvSettings.setting(webView);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                dealResponse = true;
                workOnAndroid(url, view);
                return true;
            }
        });

        webView.loadUrl("http://a.mjcydb.com/");
    }

    /**
     * 本地处理webview
     **/
    private String index;
    private Map<String, String> map = new HashMap<String, String>();//url携带的参数

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void workOnAndroid(String url, WebView view) {
        Log.d("over====" , url);
        String action;
        String param;
        if (url.startsWith("myapp")) {
            Log.d("myapp==========","myapp==========");
            view.stopLoading();
            int start = url.indexOf("r/");
            String commond = url.substring(start + 2);
            int end = commond.indexOf("/");
            action = commond.substring(0, end);
            if (commond.contains("?")) {
                index = commond.substring(end + 1, commond.indexOf("?"));
            } else {
                index = commond.substring(end + 1);
            }

            if (commond.contains("?")) {
                param = commond.substring(commond.indexOf("?") + 1);
                Log.d("param==========:" , param);

                if (param.contains("&")) {
                    String[] params = param.split("&");

                    if (params.length > 0) {
                        for (int i = 0; i < params.length; i++) {
                            if (params[i].contains("=")) {
                                String[] keyvalue = params[i].split("=");
                                if (keyvalue.length >= 2) {
                                    try {
                                        String decKey = URLDecoder.decode(keyvalue[0], "utf-8");
                                        String decValu = URLDecoder.decode(keyvalue[1], "utf-8");
                                        map.put(decKey, decValu);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        String decKey = URLDecoder.decode(keyvalue[0], "utf-8");
                                        map.put(decKey, "");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (param.contains("=")) {
                        String[] keyvalue = param.split("=");
                        if (keyvalue.length >= 2) {
                            try {
                                String decKey = URLDecoder.decode(keyvalue[0], "utf-8");
                                String decValu = URLDecoder.decode(keyvalue[1], "utf-8");
                                map.put(decKey, decValu);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                String decKey = URLDecoder.decode(keyvalue[0], "utf-8");
                                map.put(decKey, "");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }


            if (action.equals("appshare")) {
//                String title = "";
//                if (map.containsKey("title")) {
//                    title = map.get("title");
//                }
                String count = "";
                if (map.containsKey("count")) {
                    count = map.get("des");
                }

                String des = "";
                if (map.containsKey("des")) {
                    des = map.get("des");
                }
                String imgurl = "";
                if (map.containsKey("imgurl")) {
                    imgurl = map.get("imgurl");
                }

                ClipData mClipData = ClipData.newPlainText("Label", des);
                ((ClipboardManager)getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(mClipData);
                Log.e("content========", des);
                Toast.makeText(BaseWebViewActivity.this, "标题已复制到剪贴板，可在分享页面直接粘贴", Toast.LENGTH_LONG).show();
                task = new MyTask();
                       task.execute(imgurl);

//                ArrayList localArrayList = new ArrayList();
//                Log.e("path==========", Environment.getExternalStorageDirectory().getAbsolutePath());
//                localArrayList.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "equipment150827144924.jpg")));
//                localArrayList.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"equipment150827152830.jpg")));
//                Intent paramVarArgs = new Intent();
//                paramVarArgs.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
//                paramVarArgs.setAction("android.intent.action.SEND_MULTIPLE");
//                paramVarArgs.setType("image/*");
//                paramVarArgs.putParcelableArrayListExtra("android.intent.extra.STREAM", localArrayList);
//                startActivity(paramVarArgs);

            }
        } else if (url.startsWith("http")) {
            view.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ShuomingActivity.class);
        startActivity(intent);
    }

    class MyTask extends AsyncTask<String,Void,ArrayList>{

        @Override
        protected ArrayList doInBackground(String... params) {
            showOrDismissDialog(0);
            ArrayList localArrayList = new ArrayList();

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"immg");
            if(!file.exists()){
                file.mkdir();
            }
            List<String> imgNames = new ArrayList<>();
            CommonUtils.clearCacheDir(file);
            String[] urls = params[0].split(";");
            int i = 0;
            for(String u:urls){
                if(i<8)
                try {
                    i++;
                    Log.e("url====",u);
                    String name = ""+System.currentTimeMillis()+".jpg";
                    imgNames.add(name);
                    File file1 = new File(file,name);
                    FileOutputStream fos = new FileOutputStream(file1);
                    URL url = new URL(u);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.connect();
                    InputStream in = urlConnection.getInputStream();

//                    BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                    int len;
                    byte[] bytes = new byte[1024];

                    while ((len = in.read(bytes))>0) {
                        fos.write(bytes,0,len);
                    }
                    fos.flush();
                    in.close();
                    fos.close();
                    Log.e("file1path====",file1.getAbsolutePath());
                    localArrayList.add(Uri.fromFile(file1));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return localArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            showOrDismissDialog(1);
            Intent paramVarArgs = new Intent();
            paramVarArgs.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
            paramVarArgs.setAction("android.intent.action.SEND_MULTIPLE");
            paramVarArgs.setType("image/*");
            paramVarArgs.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
            startActivity(paramVarArgs);
        }
    }

    private void showOrDismissDialog(int i){
        WaitingDialog waitingDialog = WaitingDialog.getInstance().setMsg("请稍等");
        if(i==0){
            waitingDialog.show(getSupportFragmentManager(),"wating");
        }else {
            waitingDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(task!=null && !task.isCancelled()){
            task.cancel(true);
        }
    }
}
