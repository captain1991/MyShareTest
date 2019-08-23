package com.xiaodong.tu8me.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

/**
 * Created by yxd on 2016/9/28.
 */
public class CommonUtils {

    public static String getCachePath(Context context){
        String cachePath = null;
        if (context!=null && isSdCardAvailable()) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        }
        return cachePath;
    }

    public static boolean isSdCardAvailable(){
        if(Environment.MEDIA_UNMOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除某个目录下的所有文件；
     *
     * @param cacehDir
     */
    public static void clearCacheDir(File cacehDir) {
        if (cacehDir != null) {

            if (cacehDir.isDirectory()) {
                File[] files = cacehDir.listFiles();

                if (files != null && files.length > 0) {

                    for (File itemFile : files) {

                        //是文件，则删除；
                        if (itemFile.isFile()) {
                            itemFile.delete();
                        } else {
                            clearCacheDir(itemFile);
                        }
                    }
                }

            } else {
                cacehDir.delete();
            }
        }
    }


    /**
     * 获取制定包名应用的版本的versionCode
     * @param context
     * @param
     * @return
     */
    public static int getVersionCode(Context context,String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
