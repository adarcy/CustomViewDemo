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
import android.widget.LinearLayout;

import com.syxl.customviewdemo.R;

/**
 * Created by likun on 2018/3/3.
 */

public class DragScrollDetailsLayout extends LinearLayout {
    private static final String TAG = "DragScrollDetailsLayout";

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
    private CurrentTargetIndex currentTargetIndex;

    private int mMaxFlingVelocity;
    private int mMiniFlingVelocity;

    public DragScrollDetailsLayout(Context context) {
        this(context,null);
    }


    public DragScrollDetailsLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragScrollDetailsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DragScrollDetailsLayout,defStyleAttr,0);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMaxFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mMiniFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        setOrientation(VERTICAL);
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


    private float mDownX,mDownY;

    private float mInitialInterceptY;
    private VelocityTracker velocityTracker;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                }
                velocityTracker.clear();
                mChildHasScrolled = false;
                break;
            case MotionEvent.ACTION_MOVE:
//                adjustValidDownPoint(ev);
                return checkCanInterceptTouchEvent(ev);
        }
        return false;
    }

    private boolean checkCanInterceptTouchEvent(MotionEvent ev){
        float diffX = ev.getX() - mDownX;
        float diffY = ev.getY() - mDownY;
//        if (!canChildScrollVertically(diffY, ev)) {
//            mInitialInterceptY = ev.getY();
//            if (Math.abs(diffY)>mTouchSlop && Math.abs(diffY)> Math.abs(diffX)
//                    && !(currentTargetIndex == CurrentTargetIndex.UP && Math.abs(diffY)>0
//                    || currentTargetIndex == CurrentTargetIndex.DOWN && Math.abs(diffY)<0)){
//                return true;
//            }
//        }
        return false;
    }

    public void scrollToTop() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                flingToFinishScroll();
//                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_MOVE:
//                scroll(ev);
                break;
            default:
                break;
        }
        return true;
    }
}
