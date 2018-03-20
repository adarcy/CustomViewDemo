package com.syxl.customviewdemo.dragfillquestion;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by likun on 2018/3/20.
 */

public class DragFillBlankView extends RelativeLayout {

    public DragFillBlankView(Context context) {
        this(context, null);
    }

    public DragFillBlankView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragFillBlankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
