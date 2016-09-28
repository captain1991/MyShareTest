package com.xiaodong.tu8me.utils;

import java.io.File;

/**
 * Created by yxd on 2016/9/28.
 */
public class CommonUtils {

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
}
