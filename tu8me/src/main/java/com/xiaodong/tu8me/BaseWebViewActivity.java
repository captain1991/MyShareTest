package com.xiaodong.tu8me;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaodong.tu8me.utils.CommonUtils;
import com.xiaodong.tu8me.utils.SpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yxd on 2016/9/28.
 */
public class BaseWebViewActivity extends FragmentActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private WebView webView;
    private ProgressBar progressBar;
    private MyTask task;
    private TextView textView;
    private SwipeRefreshLayout refreshLayout;
    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    String des = "";//发布的文字
    /**
     * 微信7.0版本号，兼容处理微信7.0版本分享到朋友圈不支持多图片的问题
     */
    private static final int VERSION_CODE_FOR_WEI_XIN_VER7 = 1380;
    /**
     * 微信包名
     */
    public static final String PACKAGE_NAME_WEI_XIN = "com.tencent.mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yqhy);
        Log.d("onCreate","BRAND==="+Build.BOARD);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
        textView = (TextView) findViewById(R.id.shuoming);
        textView.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        WvSettings.setting(webView,this);
        webView.addJavascriptInterface(new MainJieInterface(),"cydb");
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
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:" +
                                "function setSpan(){ " +
                                "var mspans = document.getElementsByTagName(\"span\"); " +
                                "for(var i=0;i<mspans.length;i++){" +
                                "var hhr;" +
                                "hhr = $(mspans[i]).attr(\"id\");" +
                                "if(hhr!=null){" +
                                "var isshare = window.cydb.isShared(hhr);" +
                                "if(isshare){" +
                                "mspans[i].style.color = \"#D45656\";" +
                                "}}" +
//                        "mspans[2].innerHTML = \"已分享\"" +
//                        "mspans[i].href= hhr;" +
                                "} " +
                                "var mp = document.getElementsByTagName(\"p\");" +
                                "for(var i=0;i<mp.length;i++){" +
                                "  var ppp;" +
                                "  ppp =  $(mp[i]).attr(\"id\");" +
                                "  if(ppp != null){" +
                                "  var isshare = window.cydb.isShared(ppp);" +
                                "  if(isshare){" +
                                "    mp[i].style.color = \"#D45656\";" +
                                "}" +
                                "}" +
                                "}" +
                                "}" +
                                "" +
                                " function changeText(id){" +
                                "var mspans = document.getElementsByTagName(\"span\");" +
                                "mspans[id].style.color = \"#D45656\"; " +
                                "}" +
                                "" +
//                                "function changeSpanColor(id){" +
//                                " " +
//                                "}" +
                                "" +
                                "function changeColor(data){" +
//                                "var ids = data.bean.ids;" +
                                "for(var i=0;i<data.length;i++){" +
//                                "alert(data[i]);" +
                                   "var food = document.getElementById(data[i]);" +
                                "if(food != null){" +
                                "food.style.color = \"#D45656\";" +
                                "}" +
                                "" +
//                                "var pa = food.parentElement;" +
//                                "var child = pa.childNodes;" +
//                                "child[1].style.color = \" #45656\";" +
//                                "var mspans = document.getElementsByTagName(\"span\");" +
//                                "for(var i=0;i<mspans.length;i++){" +
//                                "mspans[i]" +
//                                "}" +
//                                "var sp = food.getElementsByTagName(\"span\");
//
//                                   "food.style.color = \"#D45656\";" +
//                                "if(food !=null && food.length!=0){" +
//                                "for(var j=0;j<food.length;j++){" +
//                                "food[j].style.color = \"#D45656\";" +
//                                "}" +
//                                "}" +
                                "}" +
                                "}" +
                                "setSpan();"
//                                "var oldPage = _pagenow;" +
////                                "var oldheight = $(document).height();" +
//                                "$(this).scroll(function () {\n" +
////                                "var nheight = $(document).height();" +
//                                "if(oldPage<_pagenow)" +
//                                "        {" +
//                                "oldPage = _pagenow;" +
////                                "setSpan();" +
//                                "window.cydb.onLoadmoreLisn();" +
////                                "oldheight=nheight;" +
////                                "setSpan();oldheight=nheight;" +
//                                "}\n" +
//                                "    }); "
                );
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                dealResponse = true;
                workOnAndroid(url, view);
                return true;
            }
        });

        webView.loadUrl("http://a.mjcydb.com/");
    }

    class Bean implements Serializable {
        final int[] ids = new int[]{143,142,141};
    }

    final class MainJieInterface{

        @JavascriptInterface
        public void onLoadmoreLisn(){
            final int[] ids = new int[]{143,142,141};
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Map<String,int[]> map = new HashMap<String, int[]>();
//                    map.put("ids",ids);
//                    JSONArray jsonObject = null;
//                    try {
//                        jsonObject = new JSONArray(ids);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    webView.loadUrl("javascript:changeColor("+jsonObject+")");
                    webView.loadUrl("javascript:setSpan()");
                }
            },500);

            Log.d("succeed==========","onLoadmore============");
        }

        @JavascriptInterface
        public boolean isShared(String id){
            if(SpUtil.getSpBooleanValueByKey(BaseWebViewActivity.this,id,false)){

                return true;
            }

            return false;
        }
    }

    /**
     * 本地处理webview
     **/
    private String index;
    private Map<String, String> map = new HashMap<String, String>();//url携带的参数

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void workOnAndroid(String url, WebView view) {

        Log.d("over====", url);
        showOrDismissDialog(0);
        String action;
        String param;
        if (url.startsWith("myapp")) {
            Log.d("myapp==========", "myapp==========");
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
                Log.d("param==========:", param);

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
                if (ContextCompat.checkSelfPermission(BaseWebViewActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BaseWebViewActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                    //Manifest.permission.READ_PHONE_STATE,
                } else {
                    clickShare();
                }
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
            showOrDismissDialog(1);
            view.loadUrl(url);
        }
    }

    private void clickShare(){
        String count = "";
        if (map.containsKey("count")) {
            count = map.get("des");
        }


        if (map.containsKey("des")) {
            des = map.get("des").replace("<br>","");
        }
        String imgurl = "";
        if (map.containsKey("imgurl")) {
            imgurl = map.get("imgurl");
        }

        ClipData mClipData = ClipData.newPlainText("Label", des);
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(mClipData);
        Log.e("content========", des);
//                Log.e("id========", map.get("id"));
        Toast.makeText(BaseWebViewActivity.this, "标题已复制到剪贴板，可在分享页面直接粘贴", Toast.LENGTH_LONG).show();
        task = new MyTask();
        task.execute(imgurl);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ShuomingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        webView.loadUrl("http://a.mjcydb.com/");
        refreshLayout.setRefreshing(false);
    }

    class MyTask extends AsyncTask<String, Void, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<Uri> localArrayList = new ArrayList<>();
            copyImg(localArrayList, params);
            return localArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            showOrDismissDialog(1);
            if (arrayList != null&&arrayList.size()>0) {
                SpUtil.save2SpBoolean(BaseWebViewActivity.this, map.get("id"), true);
                webView.loadUrl("javascript:setSpan()");
                openWeChat();
//                try {
//                    Intent paramVarArgs = new Intent();
//                    paramVarArgs.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
//                    paramVarArgs.setAction("android.intent.action.SEND_MULTIPLE");
//                    paramVarArgs.setType("image/*");
////                    paramVarArgs.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
//                    paramVarArgs.putExtra("Kdescription", des);
//                    if (CommonUtils.getVersionCode(BaseWebViewActivity.this,PACKAGE_NAME_WEI_XIN) < VERSION_CODE_FOR_WEI_XIN_VER7) {
//                        // 微信7.0以下版本
//                        paramVarArgs.setAction(Intent.ACTION_SEND_MULTIPLE);
//                        paramVarArgs.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
//                    } else {
//                        // 微信7.0及以上版本
//                        paramVarArgs.setAction(Intent.ACTION_SEND);
//                        paramVarArgs.putExtra(Intent.EXTRA_STREAM, (Uri)arrayList.get(0));
//                    }
//                     startActivity(paramVarArgs);
//                } catch (Exception e) {
//                    Toast.makeText(BaseWebViewActivity.this, "未找到微信", Toast.LENGTH_SHORT).show();
//                }
            }
        }
    }

    //打开微信启动页
    private void openWeChat(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            for(int result:grantResults){
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showDialog();
                    return;
                }
            }
            clickShare();
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                uuid = CommonUtil.getUUID(LoginActivityNew.this);
//                Toast.makeText(BaseWebViewActivity.this, "已获取相关权限，请重新分享", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(BaseWebViewActivity.this, "请同意相关权限", Toast.LENGTH_SHORT).show();
//            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showDialog(){
           AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

                // 拒绝, 退出应用
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
                                dialog.dismiss();
                            }
                        });

                builder.setPositiveButton("设置",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        });
                builder.setCancelable(false);
                builder.show();
    }

    private void copyImg(ArrayList localArrayList, String... params) {
        Log.d("copyImg","BRAND==="+Build.BOARD);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file;
            if(Build.BRAND.equals("Xiaomi")){
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(),"Camera");
//            file = new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/");
            }else {
                file = new File(Environment.getExternalStorageDirectory().getPath()+"/tencent/MicroMsg/WeiXin");
//                 file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "immg");
            }
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures");
            if (!file.exists()) {
                file.mkdir();
            }
            List<String> imgNames = new ArrayList<>();
//            CommonUtils.clearCacheDir(file);
            String[] urls = params[0].split(";");
            int i = 0;
            for (String u : urls) {
//                if (i < 8)
                try {
                    i++;
                    Log.e("url====", u);
                    String name = "wx_camera_" + System.currentTimeMillis() + ".jpg";
                    imgNames.add(name);
                    File file1 = new File(file, name);
                    FileOutputStream fos = new FileOutputStream(file1);
                    URL url = new URL(u);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.connect();
                    InputStream in = urlConnection.getInputStream();

//                    BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                    int len;
                    byte[] bytes = new byte[1024];

                    while ((len = in.read(bytes)) > 0) {
                        fos.write(bytes, 0, len);
                    }
                    fos.flush();
                    in.close();
                    fos.close();
                    Log.e("file1path====", file1.getAbsolutePath());
//                    localArrayList.add(Uri.fromFile(file1));

//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        localArrayList.add(Uri.fromFile(file1));
//                    }else {
//                        //修复微信在7.0崩溃的问题
//                        Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), file1.getAbsolutePath(), name, null));
//                        localArrayList.add(uri);
//                    }
//                    if(Build.BRAND.equals("Xiaomi")) {
                        // 把文件插入到系统图库
                        MediaStore.Images.Media.insertImage(getApplication().getContentResolver(), file1.getAbsolutePath(), name, null);
                        getApplication().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "")));
//                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    WaitingDialog waitingDialog;

    private void showOrDismissDialog(int i) {
        if (waitingDialog == null) {
            waitingDialog = WaitingDialog.getInstance().setMsg("请稍等");

            waitingDialog.setOnCancleCallback(new MyEventCallBack() {
                @Override
                public void adapterEventCallBack(Object... args) {
                    if (task != null && !task.isCancelled()) {
                        task.cancel(true);
                    }
                }
            });
        }
        if (i == 0) {
            waitingDialog.show(getSupportFragmentManager(), "wating");
        } else {
            waitingDialog.dismiss();
        }
    }
}
