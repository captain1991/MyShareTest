package com.xiaodong.tu8me.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gqy on 2016/3/5.
 */
public class SpUtil {
    private static SharedPreferences sp;

    public static SharedPreferences getInstance(Context context) {
        synchronized (SpUtil.class) {
            if (null == sp) {
                sp = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
            }
        }

        return sp;
    }

    /**
     * 保存；
     *
     * @param context
     * @param key
     * @param value
     */
    public static void save2SpString(Context context, String key, String value) {
        SharedPreferences sp = getInstance(context);
        if (sp != null) {
            sp.edit().putString(key, value).commit();
        }
    }

    public static void save2SpBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getInstance(context);
        if (sp != null) {
            sp.edit().putBoolean(key, value).commit();
        }
    }

    public static void save2SpInt(Context context, String key, int value) {
        SharedPreferences sp = getInstance(context);
        if (sp != null) {
            sp.edit().putInt(key, value).commit();
        }
    }

    /**
     * read value by key；
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getSpStringValueByKey(Context context, String key, String defaultValue) {
        return getInstance(context).getString(key, defaultValue);
    }

    public static boolean getSpBooleanValueByKey(Context context, String key, boolean defaultValue) {
        return getInstance(context).getBoolean(key, defaultValue);
    }

    public static int getSpIntValueByKey(Context context, String key, int defaultValue) {
        return getInstance(context).getInt(key, defaultValue);
    }
}
