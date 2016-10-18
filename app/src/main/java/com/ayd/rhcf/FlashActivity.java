package com.ayd.rhcf;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class FlashActivity extends Activity {
    private Boolean isFirststart;
    private List<ImageView> imageViewsList;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        viewPager = (ViewPager) findViewById(R.id.pager);
        SharedPreferences preferences = this.getSharedPreferences("share", MODE_PRIVATE);
        isFirststart = preferences.getBoolean("isFirst", true);
        SharedPreferences.Editor editor = preferences.edit();
        if (isFirststart) {
            Log.i("==isFirst1==",""+isFirststart);
            imageViewsList = new ArrayList<ImageView>();
            loadImages();
            editor.putBoolean("isFirst", false);
            editor.commit();
            doGuide();
        }else{
            Log.i("==isFirst2==",""+isFirststart);
            startAc();
        }

    }

    private void loadImages() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.czmm);
        imageViewsList.add(imageView);
        ImageView imageView1 = new ImageView(this);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView1.setImageResource(R.drawable.dl);
        imageViewsList.add(imageView1);
        ImageView imageView2 = new ImageView(this);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView2.setImageResource(R.drawable.dl_spec);
        imageViewsList.add(imageView2);
        ImageView imageView3 = new ImageView(this);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView3.setImageResource(R.drawable.zhmm);
        imageViewsList.add(imageView3);
    }

    public void doGuide() {
        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {

            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void transformPage(View page, float position) {

                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    page.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        Log.i("vertMargin<0", "" + vertMargin);
                        page.setTranslationX(horzMargin - vertMargin / 2);
//                        page.setTranslationX(-pageWidth*(1-position));
                    } else {
                        Log.i("vertMargin>0", "" + vertMargin);
                        page.setTranslationX(-horzMargin + vertMargin / 2);
//                        page.setTranslationX(pageWidth*(1-position));
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    page.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);
                }
            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("onPageSvrolled>>p==",""+position);

//                最后一张图片左划进入应用
                if(position==imageViewsList.size()-1){
                    viewPager.setOnTouchListener(new View.OnTouchListener() {
                        float startX;
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()){
                                case MotionEvent.ACTION_DOWN:
                                    startX = event.getRawX();
                                    Log.i("ACTION_DOWN==",""+startX);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    float moveX = event.getRawX()-startX;
                                    Log.i("ACTION_MOVE==",""+moveX);
                                    if(moveX<-10){
                                        startAc();
                                    }
                                    break;
                                default:
                                    return false;
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void startAc(){
        Intent intent = new Intent(FlashActivity.this,GifTest.class);
        startActivity(intent);
        finish();
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
