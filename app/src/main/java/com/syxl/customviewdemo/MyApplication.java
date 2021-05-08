package com.syxl.customviewdemo;

import android.app.Application;

import com.syxl.customviewdemo.utils.ActivityThreadHelper;
import com.syxl.customviewdemo.utils.SafeToastService;

/**
 * @Description:
 * @Author: likun
 * @CreateDate: 2021/5/7
 */
public class MyApplication extends Application {

    @Override
    public Object getSystemService(String name) {
        return SafeToastService.getSystemService(name, super.getSystemService(name));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityThreadHelper.tryHackActivityThreadH();
    }
}
