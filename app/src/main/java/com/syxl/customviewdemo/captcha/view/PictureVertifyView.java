package com.syxl.customviewdemo.captcha.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.syxl.customviewdemo.captcha.PositionInfo;

import java.util.Random;

/**
 * Created by likun on 2018/3/2.
 */

public class PictureVertifyView extends AppCompatImageView {
    private Context mContext;
    private Paint shadowPaint;
    //最大尺寸
    private int blockSize = 30;
    private Path shadowShape;

    //展示图片



    //图形移动
    //边界检查
    //结果验证
    public PictureVertifyView(Context context) {
        this(context,null);
    }

    public PictureVertifyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PictureVertifyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(Color.parseColor("#333333"));
        shadowPaint.setAlpha(166);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //截取形状  //阴影覆盖
        shadowShape = getShadowShape(canvas);
        PositionInfo shadowPositionInfo = getShadowPositionInfo(getWidth(), getHeight());
        shadowShape.offset(shadowPositionInfo.left,shadowPositionInfo.top);
        canvas.drawPath(shadowShape,shadowPaint);

        //生成滑块
        createBlock();


    }

    private void createBlock() {
        Bitmap blockBitmap  = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blockBitmap);
        getDrawable().setBounds(0,0,getWidth(),getHeight());
        canvas.clipPath(shadowShape);
        getDrawable().draw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{20,20},10));
        canvas.drawPath(shadowShape,paint);

    }

    private PositionInfo getShadowPositionInfo(int width,int height) {
        Random random = new Random();
        int left = random.nextInt(width - blockSize);
        if (left < blockSize){
            left = blockSize;
        }
        int top = random.nextInt(height - blockSize);
        if (top < 0){
            left = 0;
        }

        return new PositionInfo(left, top);
    }

    private Path getShadowShape(Canvas canvas){
        Path path = new Path();
        path.moveTo(0,0);
        path.rLineTo(50,50);
        path.rLineTo(-50,50);
        path.rLineTo(-50,-50);
        path.close();
        return path;
    }
}
