package xiaodong.com.bspathtest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cundong.utils.PatchUtils;

import java.io.File;

import xiaodong.com.bspathtest.utils.ApkExtract;
import xiaodong.com.bspathtest.utils.SignUtils;

public class MainActivity extends Activity {

    private Button mBtnPatch;
    private Button activity2;
    private Button checkmd5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnPatch = (Button) findViewById(R.id.id_btn_patch);
        activity2 = (Button) findViewById(R.id.activity2);
        checkmd5 = (Button) findViewById(R.id.checkmd5);
        checkmd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        mBtnPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    new MyTask().execute();
                }

            }
        });

    }

    private void doBspatch() {
        final File destApk = new File(Environment.getExternalStorageDirectory(), "dest.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "PATCH.patch");

        Log.e("hongyang", "patch = " + patch.exists() + " , " + patch.getAbsolutePath());

        if(patch.exists())
        PatchUtils.patch(ApkExtract.extract(this),
                destApk.getAbsolutePath(),
                patch.getAbsolutePath());

        Log.e("hongyang", new File(Environment.getExternalStorageDirectory(), "old").getAbsolutePath() + " , " + destApk.exists());

        if (destApk.exists()) {
            SignUtils.checkMd5(destApk, "1111");
            ApkExtract.install(this, destApk.getAbsolutePath());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new MyTask().execute();
            }
        }
    }

    class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            doBspatch();
            return null;
        }
    }

    static {
        System.loadLibrary("ApkPatchLibrary");
    }

}
