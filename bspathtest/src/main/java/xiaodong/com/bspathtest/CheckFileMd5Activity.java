package xiaodong.com.bspathtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import xiaodong.com.bspathtest.utils.SignUtils;

public class CheckFileMd5Activity extends Activity {

    private TextView mdString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_file_md5);
        mdString = (TextView) findViewById(R.id.mdString);
    }

    public void check(View view){
        File file = new File(Environment.getExternalStorageDirectory(),"check.apk");
        if(file.exists())
            mdString.setText(SignUtils.getMd5ByFile(file));
    }
}
