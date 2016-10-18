package com.ayd.rhcf;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by yxd on 2016/5/10.
 */
public class AppBean {
    public Drawable drawable;
    public String name;
    public String pac;

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPac(String pac) {
        this.pac = pac;
    }

    public Drawable getDrawable() {

        return drawable;
    }

    public String getName() {
        return name;
    }

    public String getPac() {
        return pac;
    }
}
