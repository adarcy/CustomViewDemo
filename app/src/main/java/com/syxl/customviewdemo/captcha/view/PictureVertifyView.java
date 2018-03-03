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
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.syxl.customviewdemo.captcha.PositionInfo;

import java.util.Random;

/**
 * Created by likun on 2018/3/2.
 */

public class PictureVertifyView extends AppCompatImageView {
    private static final String TAG = "PictureVertifyView";

    private static final int STATE_DOWN = 1;
    private static final int STATE_MOVE = 2;
    private static final int STATE_LOOSEN = 3;
    private static final int STATE_IDEL = 4;
    private static final int STATE_ACCESS = 5;
    private static final int STATE_UNACCESS = 6;

    private static final int TOLERANCE = 10;

    private int state = STATE_IDEL;
    private Context mContext;
    private Paint shadowPaint;
    //最大尺寸
    private int blockSize = 50;
    private Path shadowShape;
    private PositionInfo shadowPositionInfo;
    private PositionInfo blockPositionInfo;
    private Bitmap verfitybitmap;
    private Paint bitmapPaint;

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

        bitmapPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //截取形状  //阴影覆盖
        if (shadowShape == null) {
            shadowShape = getShadowShape(canvas);
        }
        if (shadowPositionInfo == null) {
            shadowPositionInfo = getShadowPositionInfo(getWidth(), getHeight());
            blockPositionInfo = getShadowPositionInfo(getWidth(), getHeight());
            shadowShape.offset(shadowPositionInfo.left, shadowPositionInfo.top);
        }
        if (state != STATE_ACCESS) {
            canvas.drawPath(shadowShape,shadowPaint);
        }

        //生成滑块
        if (verfitybitmap == null) {
            verfitybitmap = createBlock();
        }
        //y轴随机
        if (state == STATE_DOWN || state == STATE_MOVE || state == STATE_IDEL) {
            canvas.drawBitmap(verfitybitmap,blockPositionInfo.left,blockPositionInfo.top,bitmapPaint);
        }


    }

    private Bitmap createBlock() {
        //画出shadowshape形状哪的bitmap
        Bitmap blockBitmap  = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blockBitmap);
        getDrawable().setBounds(0,0,getWidth(),getHeight());
        canvas.clipPath(shadowShape);
        getDrawable().draw(canvas);
        //装饰
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{20,20},10));
        canvas.drawPath(shadowShape,paint);
        //剪裁 crop
        Bitmap crop = Bitmap.createBitmap(blockBitmap, shadowPositionInfo.left-blockSize, shadowPositionInfo.top, blockSize*2, blockSize*2);
        blockBitmap.recycle();
        return crop;
    }

    private PositionInfo getShadowPositionInfo(int width,int height) {
        Random random = new Random();
        int left = random.nextInt(width - blockSize);
        if (left < blockSize){
            left = blockSize;
        }
        int top = random.nextInt(height - blockSize);
        if (top < 0){
            top = 0;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getX()<blockPositionInfo.left || event.getX()>blockPositionInfo.left+blockSize*2
                || event.getY()<blockPositionInfo.top || event.getY()>blockPositionInfo.top+blockSize*2){
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    private float tempX, tempY, downX, downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                state = STATE_DOWN;
//                blockPositionInfo.left = (int) (x - blockSize/2);
//                blockPositionInfo.top = (int) (y - blockSize/2);
//                 invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                state = STATE_MOVE;
                float offsetX = x - tempX;
                float offsetY = y - tempY;
                blockPositionInfo.left = ((int) (blockPositionInfo.left + offsetX));
                blockPositionInfo.top = ((int) (blockPositionInfo.top + offsetY));
                Log.e(TAG,"left==="+Math.abs(blockPositionInfo.left - shadowPositionInfo.left+ blockSize)+"");
                Log.e(TAG,"top==="+Math.abs(blockPositionInfo.top - shadowPositionInfo.top)+"");
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                state = STATE_LOOSEN;
                if (Math.abs(blockPositionInfo.left - shadowPositionInfo.left + blockSize)<TOLERANCE &&
                        Math.abs(blockPositionInfo.top - shadowPositionInfo.top) <TOLERANCE){
                    state = STATE_ACCESS;
                    invalidate();
                    Toast.makeText(getContext(),"匹配成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),"匹配失败", Toast.LENGTH_SHORT).show();
                    reset();
                }
                break;
        }
        tempX = x;
        tempY = y;
        return true;
    }

    private void reset() {
        state = STATE_IDEL;
        verfitybitmap.recycle();
        verfitybitmap = null;
        shadowPositionInfo = null;
        blockPositionInfo = null;
        shadowShape = null;
        invalidate();
    }
}
