package com.ayd.rhcf.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ayd.rhcf.R;

/**
 * Created by yxd on 2016/5/31.
 */
public class XBWView extends View {
    Paint mStroke , mFill;
    Context mContext;
    private int px,py;
    public XBWView(Context context) {
        this(context,null);
    }

    public XBWView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XBWView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    public void initPaint(){
        mStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStroke.setStyle(Paint.Style.STROKE);
        mStroke.setStrokeWidth(5);
        mFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFill.setStyle(Paint.Style.FILL);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_logo);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mFill.setShader(shader);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            px = (int) event.getX();
            py = (int) event.getY();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(px, py, 30, mStroke);
        canvas.drawCircle(px,py,30,mFill);
    }
}
