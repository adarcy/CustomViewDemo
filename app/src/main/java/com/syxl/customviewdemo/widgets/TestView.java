package com.syxl.customviewdemo.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

public class TestView extends View {

    private Paint p;
    private Matrix matrix;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLUE);

        matrix = new Matrix();
//        matrix.setRectToRect(new RectF(0, 0, 200, 400),
//                new RectF(0, 0, 400, 800), Matrix.ScaleToFit.CENTER);
        LogUtils.e("matrix="+ matrix);
        Matrix invert = new Matrix();
        matrix.invert(invert);
        LogUtils.e("invert="+invert);
//        matrix.setConcat(matrix,invert);
//        LogUtils.e("matrix="+ matrix);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.e("onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtils.e("onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.e("onDraw");
        canvas.setMatrix(matrix);
        canvas.drawCircle(100,100, 50,p);
        matrix.preTranslate(100,200);
        canvas.setMatrix(matrix);
        p.setColor(Color.RED);
        canvas.drawCircle(100,100, 50,p);
        matrix.postTranslate(200,300);
        canvas.setMatrix(matrix);
        p.setColor(Color.GRAY);
        canvas.drawCircle(100,100, 50,p);
    }
}
