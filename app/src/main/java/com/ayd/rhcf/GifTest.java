package com.ayd.rhcf;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayd.rhcf.view.GifImage;

import java.io.InputStream;

/**
 * Created by yxd on 2016/4/19.
 */
public class GifTest extends Activity {

    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftest);
        imageView = (ImageView) findViewById(R.id.gifimage);
        showGifImage();

    }

    public void showGifImage() {

        InputStream is = getResources().openRawResource(R.raw.catcc);
        GifImage gifImage = new GifImage();
        int code = gifImage.read(is);
        if (code == GifImage.STATUS_OK) {

            GifImage.GifFrame[] frameList = gifImage.getFrames();
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (GifImage.GifFrame frame : frameList) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), frame.image);
                animationDrawable.addFrame(bitmapDrawable, frame.delay);
            }
            imageView.setImageDrawable(animationDrawable);
            animationDrawable.setOneShot(false);
            animationDrawable.start();
        } else if (code == GifImage.STATUS_FORMAT_ERROR) {
            Toast.makeText(this, "该图片不是gif格式", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "gif图片读取失败:" + code, Toast.LENGTH_LONG).show();
        }
    }
}
