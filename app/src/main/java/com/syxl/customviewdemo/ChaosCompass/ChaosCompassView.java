package com.syxl.customviewdemo.ChaosCompass;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.syxl.customviewdemo.R;

/**
 * Created by likun on 2018/3/7.
 */

public class ChaosCompassView extends View {
    private static final String TAG = "ChaosCompassView";

    private Canvas mCanvas;
    private Context mContext;
    //View矩形的宽度
    private int width;
    //指南针圆心点坐标
    private int mCenterX;
    private int mCenterY;
    //外圆半径
    private int mOutSideRadius;
    //外接圆半径
    private int mCircumRadius;
    //指南针文字大小空间高度
    private int mTextHeight;
    //暗红色 外圈笔
    private Paint mDarkRedPaint;
    //深灰 外圈笔
    private Paint mDeepGrayPaint;
    //外三角笔
    private Paint mOutSideCircumPaint;
    //浅灰 外圈笔
    private Paint mLightGrayPaint;
    //指南针上面 文字笔
    private Paint mTextPaint;
    //外接圆，三角形笔
    private Paint mCircumPaint;
    //指南针上面文字的外接矩形,用来测文字大小让文字居中
    private Rect mTextRect;
    //外圈小三角形的Path
    private Path mOutsideTriangle;
    //外接圆小三角形的Path
    private Path mCircumTriangle;

    //NESW 文字笔 和文字外接矩形
    private Paint mNorthPaint;
    private Paint mOthersPaint;
    private Rect  mPositionRect;
    //小刻度文字大小矩形和画笔
    private Paint mSamllDegreePaint;
    //两位数的
    private Rect mSencondRect;
    //三位数的
    private Rect mThirdRect;
    //圆心数字矩形
    private Rect mCenterTextRect;

    //中心文字笔
    private Paint mCenterPaint;

    //内心圆是一个颜色辐射渐变的圆
    private Shader mInnerShader;
    private Paint mInnerPaint;

    //定义个点击属性动画
    private ValueAnimator mValueAnimator;
    // camera绕X轴旋转的角度
    private float mCameraRotateX;
    // camera绕Y轴旋转的角度
    private float mCameraRotateY;
    //camera最大旋转角度
    private float mMaxCameraRotate = 10;

    // camera绕X轴旋转的角度
    private float mCameraTranslateX;
    // camera绕Y轴旋转的角度
    private float mCameraTranslateY;
    //camera最大旋转角度
    private float mMaxCameraTranslate;
    //camera矩阵
    private Matrix mCameraMatrix;
    //设置camera
    private Camera mCamera;

    private float val=50f;
    private float valCompare;
    //偏转角度红线笔
    private Paint mAnglePaint;

    //方位文字
    private String text="北";

    public float getVal() {
        return val;
    }

    public void setVal(float val) {
        this.val = val;
        invalidate();
    }

    public ChaosCompassView(Context context) {
        this(context,null);
    }

    public ChaosCompassView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChaosCompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mDarkRedPaint = new Paint();
        mDarkRedPaint.setStyle(Paint.Style.STROKE);
        mDarkRedPaint.setAntiAlias(true);
        mDarkRedPaint.setColor(context.getResources().getColor(R.color.darkRed));


        mDeepGrayPaint = new Paint();
        mDeepGrayPaint.setStyle(Paint.Style.STROKE);
        mDeepGrayPaint.setAntiAlias(true);
        mDeepGrayPaint.setColor(context.getResources().getColor(R.color.deepGray));


        mLightGrayPaint = new Paint();
        mLightGrayPaint.setStyle(Paint.Style.FILL);
        mLightGrayPaint.setAntiAlias(true);
        mLightGrayPaint.setColor(context.getResources().getColor(R.color.lightGray));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(80);
        mTextPaint.setColor(context.getResources().getColor(R.color.white));

        mCircumPaint = new Paint();
        mCircumPaint.setStyle(Paint.Style.FILL);
        mCircumPaint.setAntiAlias(true);
        mCircumPaint.setColor(context.getResources().getColor(R.color.red));

        mOutSideCircumPaint = new Paint();
        mOutSideCircumPaint.setStyle(Paint.Style.FILL);
        mOutSideCircumPaint.setAntiAlias(true);
        mOutSideCircumPaint.setColor(context.getResources().getColor(R.color.lightGray));

        mTextRect = new Rect();
        mOutsideTriangle = new Path();
        mCircumTriangle = new Path();

        mNorthPaint = new Paint();
        mNorthPaint.setStyle(Paint.Style.STROKE);
        mNorthPaint.setAntiAlias(true);
        mNorthPaint.setTextSize(40);
        mNorthPaint.setColor(context.getResources().getColor(R.color.red));

        mOthersPaint = new Paint();
        mOthersPaint.setStyle(Paint.Style.STROKE);
        mOthersPaint.setAntiAlias(true);
        mOthersPaint.setTextSize(40);
        mOthersPaint.setColor(context.getResources().getColor(R.color.white));

        mPositionRect = new Rect();
        mCenterTextRect = new Rect();

        mCenterPaint = new Paint();
        mCenterPaint.setStyle(Paint.Style.STROKE);
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setTextSize(120);
        mCenterPaint.setColor(context.getResources().getColor(R.color.white));

        mSamllDegreePaint = new Paint();
        mSamllDegreePaint.setStyle(Paint.Style.STROKE);
        mSamllDegreePaint.setAntiAlias(true);
        mSamllDegreePaint.setTextSize(30);
        mSamllDegreePaint.setColor(context.getResources().getColor(R.color.lightGray));

        mSencondRect = new Rect();
        mThirdRect = new Rect();

        mInnerPaint = new Paint();
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setAntiAlias(true);

        mAnglePaint = new Paint();
        mAnglePaint.setStyle(Paint.Style.STROKE);
        mAnglePaint.setAntiAlias(true);
        mAnglePaint.setColor(context.getResources().getColor(R.color.red));

        mCameraMatrix = new Matrix();
        mCamera = new Camera();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        //为指南针上面的文字预留空间，定为1/3边张
        mTextHeight = width/3;
        //设置圆心点坐标
        mCenterX = width/2;
        mCenterY = width/2+mTextHeight;
        //外部圆的外径
        mOutSideRadius = width*3/8;
        //外接圆的半径
        mCircumRadius = mOutSideRadius*4/5;
        //camera最大平移距离
        mMaxCameraTranslate = 0.02f*mOutSideRadius;
        //View布局大小
        setMeasuredDimension(width, width+width/3 );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;

        drawText();
        drawCompassOutSide();
        drawCompassCircum();
        drawInnerCricle();
        drawCompassDegreeScale();
        drawCenterText();
    }

    private void drawCenterText() {
        String centerText = String.valueOf((int)val)+"°";
        mCenterPaint.getTextBounds(centerText,0,centerText.length(),mCenterTextRect);
        int centerTextWidth = mCenterTextRect.width();
        int centerTextHeight = mCenterTextRect.height();
        mCanvas.drawText(centerText,width/2-centerTextWidth/2,mTextHeight+mOutSideRadius+centerTextHeight/5,mCenterPaint);
    }

    private void drawCompassDegreeScale() {
        mCanvas.save();
        //获取N文字的宽度
        mNorthPaint.getTextBounds("N",0,1,mPositionRect);
        int mPositionTextWidth = mPositionRect.width();
        int mPositionTextHeight = mPositionRect.height();
        //获取W文字宽度,因为W比较宽 所以要单独获取
        mNorthPaint.getTextBounds("W",0,1,mPositionRect);
        int mWPositionTextWidth = mPositionRect.width();
        int mWPositionTextHeight = mPositionRect.height();
        //获取小刻度，两位数的宽度
        mSamllDegreePaint.getTextBounds("30",0,1,mSencondRect);
        int mSencondTextWidth = mSencondRect.width();
        int mSencondTextHeight = mSencondRect.height();
        //获取小刻度，3位数的宽度
        mSamllDegreePaint.getTextBounds("30",0,1,mThirdRect);
        int mThirdTextWidth = mThirdRect.width();
        int mThirdTextHeight = mThirdRect.height();

        mCanvas.rotate(-val,width/2,mOutSideRadius+mTextHeight);

        for (int i = 0; i < 240; i++) {
            if (i==0 || i==60 || i==120 || i==180){
                mCanvas.drawLine(width/2,mTextHeight+mOutSideRadius-mCircumRadius+10,
                        width/2,mTextHeight+mOutSideRadius-mCircumRadius+30,mDeepGrayPaint);
            }else {
                mCanvas.drawLine(width/2,mTextHeight+mOutSideRadius-mCircumRadius+10,
                        width/2,mTextHeight+mOutSideRadius-mCircumRadius+30,mLightGrayPaint);
            }

            if (i==0){
                mCanvas.drawText("N",width/2-mPositionTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mPositionTextHeight,mNorthPaint);
            } else if (i==60) {
                mCanvas.drawText("E",width/2-mPositionTextHeight/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mPositionTextHeight,mOthersPaint);
            }else if (i==120) {
                mCanvas.drawText("S",width/2-mPositionTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mPositionTextHeight,mOthersPaint);
            }else if (i==180) {
                mCanvas.drawText("W",width/2-mWPositionTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mWPositionTextHeight,mOthersPaint);
            }else if (i==20){
                mCanvas.drawText("30", this.width /2-mSencondTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mSencondTextHeight,mSamllDegreePaint);
            }else if (i==40){
                mCanvas.drawText("60", this.width /2-mSencondTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mSencondTextHeight,mSamllDegreePaint);
            }else if (i==80){
                mCanvas.drawText("120", this.width /2-mThirdTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mThirdTextHeight,mSamllDegreePaint);
            }else if (i==100){
                mCanvas.drawText("150", this.width /2-mThirdTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mThirdTextHeight,mSamllDegreePaint);
            }else if (i==140){
                mCanvas.drawText("210", this.width /2-mThirdTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mThirdTextHeight,mSamllDegreePaint);
            }else if (i==160){
                mCanvas.drawText("240", this.width /2-mThirdTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mThirdTextHeight,mSamllDegreePaint);
            }else if (i==200){
                mCanvas.drawText("300", this.width /2-mThirdTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mThirdTextHeight,mSamllDegreePaint);
            }else if (i==220){
                mCanvas.drawText("330", this.width /2-mThirdTextWidth/2,mTextHeight+mOutSideRadius-mCircumRadius+40+mThirdTextHeight,mSamllDegreePaint);
            }

            mCanvas.rotate(1.5f,width/2,mOutSideRadius+mTextHeight);
        }

        mCanvas.restore();
    }

    private void drawInnerCricle() {
        RadialGradient innerShader = new RadialGradient(width/2,mTextHeight+mOutSideRadius,mCircumRadius-40,
                Color.parseColor("#666666"),Color.parseColor("#000000"), Shader.TileMode.CLAMP);
        mInnerPaint.setShader(innerShader);
        mCanvas.drawCircle(width/2,mTextHeight+mOutSideRadius,mCircumRadius-50,mInnerPaint);
    }

    /**
     * 指南针外接圆，和外部圆换道理差不多
     */
    private void drawCompassCircum() {
        mCanvas.save();
        mCanvas.rotate(-val,width/2,mTextHeight+mOutSideRadius);
        int triangleHeight = (mOutSideRadius - mCircumRadius)/2;
        float trianglrSide = (float) (triangleHeight*2/Math.sqrt(3));
        mCircumTriangle.moveTo(width/2,mTextHeight+triangleHeight);
        mCircumTriangle.lineTo(width/2-trianglrSide/2,mTextHeight+triangleHeight*2);
        mCircumTriangle.lineTo(width/2+trianglrSide/2,mTextHeight+triangleHeight*2);
        mCircumTriangle.close();
        mCanvas.drawPath(mCircumTriangle,mCircumPaint);
        RectF rectF = new RectF(width/2-mCircumRadius,mTextHeight+triangleHeight*2,
                width/2+mCircumRadius,mTextHeight+triangleHeight*2+mCircumRadius*2);
        mCanvas.drawArc(rectF,-85,350,false,mDeepGrayPaint);
        mAnglePaint.setStrokeWidth(5f);
        if (val <= 180) {
            mCanvas.drawArc(rectF,-85,val,false,mAnglePaint);
        }else {
            mCanvas.drawArc(rectF,-95,val-360,false,mAnglePaint);
        }
        mCanvas.restore();
    }

    private void drawCompassOutSide() {
        mCanvas.save();
        int triangleHeight = 40;
        float trianglrSide = 46.18f;
        mOutsideTriangle.moveTo(width/2,mTextHeight-triangleHeight);
        mOutsideTriangle.lineTo(width/2-trianglrSide/2,mTextHeight);
        mOutsideTriangle.lineTo(width/2+trianglrSide/2,mTextHeight);
        mOutsideTriangle.close();
        mCanvas.drawPath(mOutsideTriangle,mOutSideCircumPaint);

        //画圆弧
        mDarkRedPaint.setStrokeWidth((float) 5);
        mLightGrayPaint.setStrokeWidth((float)5);
        mDeepGrayPaint.setStrokeWidth((float)3);
        mLightGrayPaint.setStyle(Paint.Style.STROKE);
        RectF outsideRect = new RectF(width/2-mOutSideRadius,mTextHeight,width/2+mOutSideRadius,mTextHeight+mOutSideRadius*2);
        mCanvas.drawArc(outsideRect,-80,120,false,mLightGrayPaint);
        mCanvas.drawArc(outsideRect,40,20,false,mDeepGrayPaint);
        mCanvas.drawArc(outsideRect,-100,-20,false,mLightGrayPaint);
        mCanvas.drawArc(outsideRect,-120,-120,false,mDarkRedPaint);
        mCanvas.restore();
    }

    private void drawText() {
        if (val<=15 || val>=345){
            text = "北";
        } else if (val>15 && val<=75) {
            text = "东北";
        }else if (val>75 && val<=105) {
            text = "东";
        }else if (val>105 && val<=165) {
            text = "东南";
        }else if (val>165 && val<=195) {
            text = "南";
        }else if (val>195 && val<=255) {
            text = "西南";
        }else if (val>255 && val<=285) {
            text = "西";
        }else if (val>285 && val<345) {
            text = "西北";
        }
        mTextPaint.getTextBounds(text,0,text.length(),mTextRect);
        int textWidth = mTextRect.width();
        mCanvas.drawText(text,(width-textWidth)/2,mTextHeight/2, mTextPaint);
    }
}
