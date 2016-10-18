package com.ayd.rhcf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.ayd.rhcf.R;

/**
 * Created by yxd on 2016/5/10.
 */
public class CircleImageView extends ImageView {
    int width;
    int height;
    Paint paint;
    int circleColor = 0xFFFFFFFF;
    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        circleColor = a.getColor(R.styleable.CircleImageView_circleColor,0xFFFFFF);
        a.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = dp2px(200);
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            height = dp2px(200);
        }
        setMeasuredDimension(width, height);
    }

    public int dp2px(int dp){
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        return (int) (dp*density+0.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Drawable drawable = getResources().getDrawable(R.drawable.sina_web_default);
        Drawable drawable = getDrawable();
        if (drawable.getClass() == NinePatchDrawable.class)
            return;
        drawCircle(canvas);
        Bitmap drawableBitmap = ((BitmapDrawable)drawable).getBitmap();
        drawableBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap bitmap = getCroppedBitmap(drawableBitmap,1);
        canvas.drawBitmap(bitmap, 0, 0, paint);
//        drawCircle(canvas);
        setImageBitmap(bitmap);
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap,int radius){

        Bitmap output = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.save();
        canvas.drawCircle(width / 2, height / 2, height / 2-10, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, width, height), paint);
        canvas.restore();
        paint.setXfermode(null);
        return output;
    }

    public void drawCircle(Canvas canvas){
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.save();
        paint1.setColor(circleColor);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(10);
        canvas.drawCircle(width / 2, width / 2, width / 2 - 6, paint1);
        canvas.restore();
    }

}
