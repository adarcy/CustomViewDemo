package com.syxl.customviewdemo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Scroller
import com.blankj.utilcode.util.CrashUtils.init

class ScrollerViewPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    val mScroller = Scroller(context)
    var mTouchSlop = 0
    var mDownX = 0f
    var mMoveX = 0f
    var mLastMoveX = 0f
    var mLeftBorder = 0
    var mRightBorder = 0

    constructor(context: Context?) : this(context, null) {

    }

    init {
        init11()
    }

    private fun init11() {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        println("mTouchSlop=="+mTouchSlop)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var childCount = childCount
        for (index in 0..childCount - 1) {
            var childView = getChildAt(index)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //layout
        var childCount = childCount
        for (index in 0..childCount - 1) {
            var childView = getChildAt(index)
            childView.layout(index * childView.measuredWidth, 0, (index + 1) * childView.measuredWidth,childView.measuredHeight)
        }
        //border
        mLeftBorder = getChildAt(0).left
        mRightBorder = getChildAt(childCount-1).right
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.rawX
                mLastMoveX = mDownX
            }
            MotionEvent.ACTION_MOVE -> {
                mMoveX = ev.rawX
                var diff = Math.abs(mLastMoveX - mMoveX)
                mLastMoveX = mMoveX
                if (diff > mTouchSlop){
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //move
        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                mMoveX = event.rawX
                //bianjie
                var scrolledX = (mLastMoveX - mMoveX).toInt()
                if (scrollX + scrolledX < mLeftBorder){
                    scrollTo(mLeftBorder,0)
                }else if (scrollX + scrolledX + width > mRightBorder){
                    scrollTo(mRightBorder-width,0)
                }
                scrollBy(scrolledX,0)
                mLastMoveX = mMoveX
            }
            MotionEvent.ACTION_UP -> {
                var index = (scrollX + width/2)/width
                var dx = index * width -scrollX
                mScroller.startScroll(scrollX,0,dx,0)
                postInvalidate()
            }
        }
        //up
        return super.onTouchEvent(event)
    }


    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.currX,mScroller.currY)
            postInvalidate()
        }
    }

}