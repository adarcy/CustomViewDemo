package com.syxl.customviewdemo.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by dafengluo on 2018/1/11.
 */

public class BaseWebView extends WebView {

    protected Context context;
    private OnScrollChangeListener mOnScrollChangeListener;

    public BaseWebView(Context context) {
        this(getFixedContext(context), null);
        this.context = context;
        setClickable(true);
        initWebViewSettings();
    }

    public BaseWebView(Context context, AttributeSet set) {
        super(getFixedContext(context), set);
        this.context = context;
        setClickable(true);
        initWebViewSettings();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
        this.context = context;
        setClickable(true);
        initWebViewSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setLoadWithOverviewMode(true);//搜狐号pc页面展示过大
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

//        if (AppUtils.isOldHomeShow) {
//            if ("HUAWEI".equals(Build.BRAND.toUpperCase()) || "SAMSUNG".equals(Build.BRAND.toUpperCase())) {
//                webSetting.setUserAgentString(webSetting.getUserAgentString() + " " + "MSOHU/1.1.3");
//            }
//        } else {
//            webSetting.setUserAgentString(webSetting.getUserAgentString() + " " + Consts.APP_UA);
//        }

        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener == null)
            return;
        // webview的高度
        float webcontent = getContentHeight() * getScale();
        // 当前webview的高度
        float webnow = getHeight() + getScrollY();
//        if (Math.abs(webcontent - webnow) < 1) {
//            //处于底端
//            mOnScrollChangeListener.onPageEnd(l, t, oldl, oldt);
//        } else if (getScrollY() == 0) {
//            //处于顶端
//            mOnScrollChangeListener.onPageTop(l, t, oldl, oldt);
//        } else {
//            mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
//        }
        mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mOnScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {
//
//        void onPageEnd(int l, int t, int oldl, int oldt);
//
//        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23) {// Android Lollipop 5.0 & 5.1
            return context.createConfigurationContext(new Configuration());
        }else {
            return context;
        }
    }
}
