package com.ayd.rhcf;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxd on 2016/5/10.
 */
public class AllAppActivity extends Activity {
    ListView listView;
    MyAdapter adapter;
    PackageManager mPackageManager;
    List<AppBean> list = new ArrayList<AppBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allapp);
        mPackageManager = getPackageManager();
        listView = (ListView) findViewById(R.id.allapp_listview);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        getAllApps();
        adapter.setList(list);
    }

    public void getAllApps(){
        List<ApplicationInfo> applicationInfos = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(ApplicationInfo applicationInfo:applicationInfos){
            AppBean appBean = new AppBean();
            appBean.setDrawable(applicationInfo.loadIcon(mPackageManager));
            appBean.setName(applicationInfo.loadLabel(mPackageManager).toString());
            appBean.setPac(applicationInfo.packageName);
            list.add(appBean);
        }
    }
}
