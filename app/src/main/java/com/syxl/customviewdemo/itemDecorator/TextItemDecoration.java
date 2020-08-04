package com.syxl.customviewdemo.itemDecorator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.syxl.customviewdemo.R;

public class TextItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Paint mTextPaint;
    private Bitmap bitmap;

    public TextItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(40f);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_view_as_list);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position % 5 == 0) {
            outRect.set(0, 50, 0, 0);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);

            if (index % 5 == 0) {
                c.drawRect(child.getLeft(), child.getTop() - 50, child.getRight(), child.getTop(), mPaint);
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();//屏幕内可兼得item数量
        c.drawText("childCount=" + childCount, 300, 350, mTextPaint);

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            c.drawText("getChildAdapterPosition=" + index, 300, 550, mTextPaint);
            if (index % 5 == 0) {
                int item = index / 5;
                if (i < 5) {
                    if (i == 1 && child.getTop() < 100){
                        int left = 0;
                        int top = child.getTop() - 100;
                        int right = child.getRight();
                        int bottom = child.getTop() - 50;
                        c.drawRect(left, top, right, bottom, mPaint);
                        c.drawText("这是条目" + item + "视图i是+" + i + "顶部的高低" + child.getTop() + "index++" + index, left, top + 50, mTextPaint);
                    }else {
                        int left = 0;
                        int top = 0;
                        int right = child.getRight();
                        int bottom = 50;
                        c.drawRect(left, top, right, bottom, mPaint);
                        if (i == 0) {
                            c.drawText("这是条目" + (item + 1) + "视图i是+" + i + "顶部的高低" + child.getTop() + "index++" + index, left, top + 50, mTextPaint);
                        } else {
                            c.drawText("这是条目" + (item) + "视图i是+" + i + "顶部的高低" + child.getTop() + "index++" + index, left, top + 50, mTextPaint);
                        }
                    }
                }
                if (i != 0){
//                    c.drawRect(child.getLeft(),child.getTop()-50, child.getRight(),child.getTop(),mPaint);
                    c.drawText("这是条目" + item + "视图i是+" + i + "顶部的高低" + child.getTop() + "index++" + index, child.getLeft(), child.getTop(), mTextPaint);
                }
            }
        }
    }
}
