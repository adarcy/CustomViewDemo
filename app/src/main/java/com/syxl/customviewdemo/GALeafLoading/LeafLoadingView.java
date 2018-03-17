package com.syxl.customviewdemo.GALeafLoading;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.syxl.customviewdemo.R;
import com.syxl.customviewdemo.utils.UiUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by likun on 2018/3/13.
 */

public class LeafLoadingView extends View {
    private static final String TAG = "LeafLoadingView";

    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;
    // 中等振幅大小
    private static final int MIDDLE_AMPLITUDE = 13;
    // 不同类型之间的振幅差距
    private static final int AMPLITUDE_DISPARITY = 5;

    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 叶子飘动一个周期所花的时间
    private static final long LEAF_FLOAT_TIME = 3000;
    // 叶子旋转一周需要的时间
    private static final long LEAF_ROTATE_TIME = 2000;

    // 用于控制绘制的进度条距离左／上／下的距离
    private static final int LEFT_MARGIN = 9;
    // 用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 25;
    private int mLeftMargin, mRightMargin;
    // 中等振幅大小
    private int mMiddleAmplitude = MIDDLE_AMPLITUDE;
    // 振幅差
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;

    // 叶子飘动一个周期所花的时间
    private long mLeafFloatTime = LEAF_FLOAT_TIME;
    // 叶子旋转一周需要的时间
    private long mLeafRotateTime = LEAF_ROTATE_TIME;
    private Resources mResources;
    private Bitmap mLeafBitmap;
    private int mLeafWidth, mLeafHeight;

    private Bitmap mOuterBitmap;
    private Rect mOuterSrcRect, mOuterDestRect;
    private int mOuterWidth, mOuterHeight;

    private int mTotalWidth, mTotalHeight;

    private Paint mBitmapPaint, mWhitePaint, mOrangePaint;
    private RectF mWhiteRectF, mOrangeRectF, mArcRectF;
    // 当前进度
    private int mProgress;
    // 所绘制的进度条部分的宽度
    private int mProgressWidth;
    // 当前所在的绘制的进度条的位置
    private int mCurrentProgressPosition=0;
    // 弧形的半径
    private int mArcRadius;

    // arc的右上角的x坐标，也是矩形x坐标的起始点
    private int mArcRightLocation;
    // 用于产生叶子信息
    private LeafFactory mLeafFactory;
//     产生出的叶子信息
    private List<Leaf> mLeafInfos;
    // 用于控制随机增加的时间不抱团
    private int mAddTime;


    public LeafLoadingView(Context context) {
        this(context,null);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLeftMargin = UiUtils.dipToPx(context, LEFT_MARGIN);
        mRightMargin = UiUtils.dipToPx(context, RIGHT_MARGIN);
        mLeafFloatTime = LEAF_FLOAT_TIME;
        mLeafRotateTime = LEAF_ROTATE_TIME;
        initPaint();
        initBitmap();

        mLeafFactory = new LeafFactory();
        mLeafInfos = mLeafFactory.generateLeafs(6);
    }

    private void initBitmap() {
        mLeafBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.leaf)).getBitmap();
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();

        mOuterBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.leaf_kuang)).getBitmap();
        mOuterWidth = mOuterBitmap.getWidth();
        mOuterHeight = mOuterBitmap.getHeight();
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(WHITE_COLOR);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin;
        mArcRadius = (mTotalHeight - 2*mLeftMargin)/2;

        mOuterSrcRect = new Rect(0,0,mOuterWidth,mOuterHeight);
        mOuterDestRect = new Rect(0,0,mTotalWidth,mTotalHeight);

        mWhiteRectF = new RectF(mLeftMargin + mCurrentProgressPosition, mLeftMargin, mTotalWidth - mRightMargin, mTotalHeight - mLeftMargin);
        mOrangeRectF = new RectF(mLeftMargin + mArcRadius, mLeftMargin, mLeftMargin+mCurrentProgressPosition, mTotalHeight - mLeftMargin);
        mArcRectF = new RectF(mLeftMargin, mLeftMargin, mLeftMargin+2*mArcRadius, mTotalHeight - mLeftMargin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        drawProgressAndLeafs(canvas);
        canvas.drawBitmap(mOuterBitmap,mOuterSrcRect,mOuterDestRect,mBitmapPaint);
        postInvalidate();
    }

    private void drawProgressAndLeafs(Canvas canvas) {
        if (mProgress > TOTAL_PROGRESS) {
            mProgress = 0;
        }
        mCurrentProgressPosition = mProgressWidth * mProgress/TOTAL_PROGRESS;
        Log.e(TAG,"mCurrentProgressPosition ="+mCurrentProgressPosition);
        Log.e(TAG,"mArcRadius ="+mArcRadius);
        if (mCurrentProgressPosition < mArcRadius) {
            canvas.drawArc(mArcRectF,90,180,false,mWhitePaint);
            mWhiteRectF.left = mLeftMargin+mArcRadius;
            canvas.drawRect(mWhiteRectF,mWhitePaint);

            //leafs
            drawLeafs(canvas);

            //orange
            double acos = Math.acos((mArcRadius - mCurrentProgressPosition) / (float)mArcRadius);
            int degrees = (int) Math.toDegrees(acos);//弧度转角度
            int angle = 180 - degrees;
            int sweep = 2*degrees;
            Log.i(TAG, "startAngle = " + angle);

            canvas.drawArc(mArcRectF,angle,sweep,false,mOrangePaint);
        }else {
            mWhiteRectF.left = mLeftMargin + mCurrentProgressPosition;
            canvas.drawRect(mWhiteRectF,mWhitePaint);
            drawLeafs(canvas);

            canvas.drawArc(mArcRectF,90,180,false,mOrangePaint);
            mOrangeRectF.left = mLeftMargin + mArcRadius;
            mOrangeRectF.right = mLeftMargin + mCurrentProgressPosition;
            canvas.drawRect(mOrangeRectF,mOrangePaint);
        }
    }

    private void drawLeafs(Canvas canvas) {
        mLeafRotateTime = mLeafRotateTime <= 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < mLeafInfos.size(); i++) {
            Leaf leaf = mLeafInfos.get(i);
            if (currentTime > leaf.startTime && leaf.startTime != 0) {
                getLeafLocation(leaf,currentTime);
                canvas.save();
                float transX = mLeftMargin + leaf.x;
                float transY = mLeftMargin + leaf.y;
                Matrix matrix = new Matrix();
                matrix.postTranslate(transX, transY);

                float rotateFraction = ((currentTime-leaf.startTime)%LEAF_ROTATE_TIME)/(float)LEAF_ROTATE_TIME;
                int angle = (int) (rotateFraction*360);
                int rotate = leaf.rotateDirection == 0? angle+leaf.rotateAngle : -angle+leaf.rotateAngle;
                matrix.postRotate(rotate,transX+mLeafWidth/2,transY+mLeafHeight/2);
                canvas.drawBitmap(mLeafBitmap,matrix,mBitmapPaint);
                canvas.restore();
            }else {
                continue;
            }
        }
    }

    private void getLeafLocation(Leaf leaf, long currentTime) {
        long intervalTime = currentTime - leaf.startTime;
        mLeafFloatTime = mLeafRotateTime<=0?LEAF_FLOAT_TIME:mLeafFloatTime;
        if (intervalTime < 0) {
            return;
        } else if (intervalTime > mLeafFloatTime) {
            leaf.startTime = System.currentTimeMillis() + new Random().nextInt((int) mLeafFloatTime);
        }
        float fraction = intervalTime/(float)mLeafFloatTime;
        leaf.x = mProgressWidth - mProgressWidth*fraction;
        Log.e(TAG, "leaf.x = " + leaf.x);
        Log.e(TAG, "fraction = " + fraction);
        leaf.y = getLocationY(leaf);
    }

    // 通过叶子信息获取当前叶子的Y值
    private int getLocationY(Leaf leaf) {
        // y = A(wx+Q)+h
        float w = (float) (Math.PI*2/(float)mProgressWidth);
        float a  = mMiddleAmplitude;
        switch (leaf.type) {
            case MIDDLE:
                break;
            case BIG:
                a = mMiddleAmplitude + mAmplitudeDisparity;
                break;
            case LITTLE:
                a = mMiddleAmplitude - mAmplitudeDisparity;
                break;
        }
        return (int) (a*Math.sin(w*leaf.x)+mArcRadius*2/3);
    }

    //区分不同的振幅
    enum StartType{
        MIDDLE,LITTLE,BIG
    }

    private class Leaf {

        // 在绘制部分的位置
        float x, y;
        // 控制叶子飘动的幅度
        StartType type;
        // 旋转角度
        int rotateAngle;
        // 旋转方向--0代表顺时针，1代表逆时针
        int rotateDirection;
        // 起始时间(ms)
        long startTime;
    }

    private class LeafFactory{
        private static final int MAX_LEAFS = 6;
        Random random = new Random();

        // 生成一个叶子信息
        public Leaf generateLeaf(){
            Leaf leaf = new Leaf();
            StartType type = StartType.MIDDLE;
            int randomtype = random.nextInt(3);
            switch (randomtype) {
                case 0:
                break;
                case 1:
                    type = StartType.LITTLE;
                    break;
                case 2:
                    type = StartType.BIG;
                    break;
            }
            leaf.type = type;
            leaf.rotateDirection = random.nextInt(2);
            leaf.rotateAngle = random.nextInt(360);
            // 为了产生交错的感觉，让开始的时间有一定的随机性
            mLeafFloatTime = mLeafFloatTime <= 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
            mAddTime += random.nextInt((int) (mLeafFloatTime * 2));
            leaf.startTime = System.currentTimeMillis() + mAddTime;
            return leaf;
        }

        // 根据传入的叶子数量产生叶子信息
        public List<Leaf> generateLeafs(int leafSize){
            List<Leaf> leafs = new LinkedList();
            for (int i = 0; i < leafSize; i++) {
                leafs.add(generateLeaf());
            }
            return leafs;
        }
    }

    /**
     * 设置中等振幅
     *
     * @param amplitude
     */
    public void setMiddleAmplitude(int amplitude) {
        this.mMiddleAmplitude = amplitude;
    }

    /**
     * 设置振幅差
     *
     * @param disparity
     */
    public void setMplitudeDisparity(int disparity) {
        this.mAmplitudeDisparity = disparity;
    }

    /**
     * 获取中等振幅
     *
     * @param amplitude
     */
    public int getMiddleAmplitude() {
        return mMiddleAmplitude;
    }

    /**
     * 获取振幅差
     *
     * @param disparity
     */
    public int getMplitudeDisparity() {
        return mAmplitudeDisparity;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    /**
     * 设置叶子飘完一个周期所花的时间
     *
     * @param time
     */
    public void setLeafFloatTime(long time) {
        this.mLeafFloatTime = time;
    }

    /**
     * 设置叶子旋转一周所花的时间
     *
     * @param time
     */
    public void setLeafRotateTime(long time) {
        this.mLeafRotateTime = time;
    }

    /**
     * 获取叶子飘完一个周期所花的时间
     */
    public long getLeafFloatTime() {
        mLeafFloatTime = mLeafFloatTime == 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
        return mLeafFloatTime;
    }

    /**
     * 获取叶子旋转一周所花的时间
     */
    public long getLeafRotateTime() {
        mLeafRotateTime = mLeafRotateTime == 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        return mLeafRotateTime;
    }
}
