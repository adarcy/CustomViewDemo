package com.syxl.customviewdemo.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @Description:
 * @Author: likun
 * @CreateDate: 2021/5/7
 */
public class SafeToastService {
    private static final String TAG = "SafeToastService";
    private static WindowManager mWindowManager;

    public static Object getSystemService(String name, Object baseService){
        if (Build.VERSION.SDK_INT >= 24 && Build.VERSION.SDK_INT <= 25) {// 兼容android 7.1.1 toast崩溃问题
            if (name.equals(Context.WINDOW_SERVICE) && callFromToast()) {
                if (mWindowManager == null) {
                    mWindowManager = new WindowManagerWrapper((WindowManager) baseService);
                }
                return mWindowManager;
            }
        }
        return baseService;
    }

    private static boolean callFromToast(){
        boolean fromToast = false;
        try {
            // android.widget.Toast$TN.handleShow
            StackTraceElement[] traces = Thread.currentThread().getStackTrace();
            if (traces != null) {
                for (StackTraceElement trace : traces) {
                    Log.e(TAG,"trace.getClassName(: " + trace.getClassName());

                    if ("android.widget.Toast$TN".equals(trace.getClassName()) ) {
                        if ("handleShow".equals(trace.getMethodName())) {
                            fromToast = true;
                            break;
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return fromToast;
    }

    static class WindowManagerWrapper implements WindowManager {
        private WindowManager mBaseManager;

        public WindowManagerWrapper(WindowManager baseManager){
            mBaseManager = baseManager;
        }

        @Override
        public Display getDefaultDisplay() {
            return mBaseManager.getDefaultDisplay();
        }

        @Override
        public void removeViewImmediate(View view) {
            mBaseManager.removeViewImmediate(view);
        }

        @Override
        public void addView(View view, ViewGroup.LayoutParams layoutParams) {
            try {
                mBaseManager.addView(view, layoutParams);
            }catch (Exception e){
                Log.e(TAG,"add window failed: " + e.toString());
            }
        }

        @Override
        public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
            mBaseManager.updateViewLayout(view,layoutParams);
        }

        @Override
        public void removeView(View view) {
            mBaseManager.removeView(view);
        }
    }
}


