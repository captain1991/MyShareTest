package com.ayd.rhcf;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CopyFlashActivity extends Activity {
    private Boolean isFirststart;
    private List<ImageView> imageViewsList = new ArrayList<ImageView>();
    public ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        viewPager = (ViewPager) findViewById(R.id.pager);

//        SharedPreferences preferences = this.getSharedPreferences("share",MODE_PRIVATE);
//        isFirststart = preferences.getBoolean("isFirst",true);
//        SharedPreferences.Editor editor = preferences.edit();
//        if(isFirststart){

        loadImages();
//            editor.putBoolean("isFirst",false);
            doGuide();
//        }

    }

    private void loadImages(){

        for(int i=0;i<4;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.czmm);
            imageViewsList.add(imageView);
        }

    }

    public void doGuide(){
        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);

    }
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList == null ? 0 : imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
