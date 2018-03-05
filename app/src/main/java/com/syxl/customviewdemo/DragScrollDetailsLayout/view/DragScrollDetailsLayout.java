package com.syxl.customviewdemo.DragScrollDetailsLayout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.syxl.customviewdemo.R;

/**
 * Created by likun on 2018/3/3.
 */

public class DragScrollDetailsLayout extends LinearLayout {
    private static final String TAG = "DragScrollDetailsLayout";
    private static final float DEFAULT_PERCENT = 0.3f;
    private static final int DEFAULT_DURATION = 300;

    private View upView;
    private View downView;
    private View targetView;
    private boolean mChildHasScrolled;
    private float mTouchSlop;

    public enum CurrentTargetIndex{
        UP,
        DOWN;
        public static CurrentTargetIndex valueOf(int index) {
            return index == 1? UP : DOWN;
        }
    }
    private CurrentTargetIndex currentTargetIndex = CurrentTargetIndex.UP;

    private int mMaxFlingVelocity;
    private int mMiniFlingVelocity;
    private float mPercent = DEFAULT_PERCENT;
    private int mDuration = DEFAULT_DURATION;
    private Scroller mScroller;

    private float mDownX,mDownY;
    private float mInitialInterceptY;
    private VelocityTracker velocityTracker;

    public DragScrollDetailsLayout(Context context) {
        this(context,null);
    }


    public DragScrollDetailsLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragScrollDetailsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DragScrollDetailsLayout,defStyleAttr,0);
        mPercent = ta.getFloat(R.styleable.DragScrollDetailsLayout_percent, DEFAULT_PERCENT);
        mDuration = ta.getInt(R.styleable.DragScrollDetailsLayout_duration, DEFAULT_DURATION);
        ta.recycle();
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMaxFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mMiniFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        setOrientation(VERTICAL);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount <= 1) {
            throw new RuntimeException("SlideDetailsLayout only accept childs more than 1!!");
        }
        upView = getChildAt(0);
        downView = getChildAt(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //滑动没结束时 分发到当前的view
        if (!mScroller.isFinished()) {
            resetDownPosition(ev);
            return true;
        }
        requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                resetDownPosition(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                adjustValidDownPoint(ev);
                return checkCanInterceptTouchEvent(ev);
        }
        return false;
    }

    private void adjustValidDownPoint(MotionEvent ev) {
        if (currentTargetIndex == CurrentTargetIndex.UP && ev.getY() > mDownY
                || currentTargetIndex == CurrentTargetIndex.DOWN && ev.getY() < mDownY) {
            mDownX = ev.getX();
            mDownY = ev.getY();
        }
    }

    private boolean checkCanInterceptTouchEvent(MotionEvent ev){
        float diffX = ev.getX() - mDownX;
        float diffY = ev.getY() - mDownY;
        if (!canChildScrollVertically((int) diffY, ev)) {
            mInitialInterceptY = ev.getY();
            if (Math.abs(diffY)>mTouchSlop && Math.abs(diffY)> Math.abs(diffX)
                    && !(currentTargetIndex == CurrentTargetIndex.UP && diffY>0
                    || currentTargetIndex == CurrentTargetIndex.DOWN && diffY<0)){
                return true;
            }
        }
        return false;
    }

    /**
     * 更新down位置信息
     * @param ev
     */
    private void resetDownPosition(MotionEvent ev) {
        mDownX = ev.getX();
        mDownY = ev.getY();
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.clear();
        mChildHasScrolled = false;
        mInitialInterceptY = ev.getY();
    }


    /***
     * 判断MotionEvent是否处于View上面
     */
    protected boolean isTransformedTouchPointInView(MotionEvent ev, View view){
        float rawX = ev.getRawX();
        float rawY = ev.getRawY();
        int[] rect = new int[2];
        view.getLocationInWindow(rect);
        float offsetX = rawX - rect[0];
        float offsetY = rawY - rect[1];
        return (offsetX > 0 && offsetX < (view.getRight() - view.getLeft())
                && (offsetY > 0 && (offsetY < (view.getBottom() - view.getTop()))));
    }


    /**
     * 遍历判断哪层可以滑动
     * @param view
     * @param offSet  offset的正负值用来判断向上还是向下
     * @param ev
     * @return
     */
    private boolean canScrollVertically(View view, int offSet, MotionEvent ev){
        if (!mChildHasScrolled && !isTransformedTouchPointInView(ev, view)) {
            return false;
        }
        if (ViewCompat.canScrollVertically(view, offSet)) {
            mChildHasScrolled = true;
            return true;
        }
        if (view instanceof ViewPager) {
//            return canViewPagerScrollVertically((ViewPager) view, offSet, ev);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (canScrollVertically(viewGroup.getChildAt(i),offSet,ev)) {
                    mChildHasScrolled = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void scrollToTop() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                flingToFinishScroll();
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_MOVE:
                scroll(ev);
                break;
            default:
                break;
        }
        return true;
    }

    private void flingToFinishScroll() {

    }

    private void recycleVelocityTracker() {

    }

    /**
     * 拦截之后的拖动
     */
    private void scroll(MotionEvent event) {
        if (currentTargetIndex == CurrentTargetIndex.UP) {
            if (getScrollY() <= 0 && event.getY() >= mInitialInterceptY) {
                mInitialInterceptY = (int) event.getY();
            }
            int distance = mInitialInterceptY - event.getY() >= 0 ? (int) (mInitialInterceptY - event.getY()) : 0;
            scrollTo(0, distance);
        } else {
            if (getScrollY() >= upView.getMeasuredHeight() && event.getY() <= mInitialInterceptY) {
                mInitialInterceptY = (int) event.getY();
            }
            int distance = event.getY() <= mInitialInterceptY ? upView.getMeasuredHeight()
                    : (int) (mInitialInterceptY - event.getY() + upView.getMeasuredHeight());
            scrollTo(0, distance);
        }
        velocityTracker.addMovement(event);
    }

    /***
     * 复用已经实现的View，省却了测量布局之类的麻烦
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    protected boolean canChildScrollVertically(int offSet, MotionEvent ev) {
        targetView = getCurrentTargetView();
        return canScrollVertically(targetView, -offSet, ev);
    }

    private View getCurrentTargetView() {
        return currentTargetIndex == CurrentTargetIndex.UP
                ? upView : downView;
    }
}
