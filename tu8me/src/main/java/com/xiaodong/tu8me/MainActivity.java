package com.xiaodong.tu8me;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button shareBtn;
    private IWXAPI iwxapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this,"wx4bab1f458ac20eb8",true);
        iwxapi.registerApp("wx4bab1f458ac20eb8");
        setContentView(R.layout.activity_main);
        shareBtn = (Button) findViewById(R.id.sharebtn);
        shareBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ArrayList localArrayList = new ArrayList();
        Log.e("path==========",Environment.getExternalStorageDirectory().getAbsolutePath());
        localArrayList.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"equipment150827144924.jpg")));
        localArrayList.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"equipment150827152830.jpg")));
        Intent paramVarArgs = new Intent();
        paramVarArgs.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        paramVarArgs.setAction("android.intent.action.SEND_MULTIPLE");
        paramVarArgs.setType("image/*");
        paramVarArgs.putParcelableArrayListExtra("android.intent.extra.STREAM", localArrayList);
        startActivity(paramVarArgs);

    }
}
