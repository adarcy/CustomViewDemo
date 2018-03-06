package com.syxl.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.syxl.customviewdemo.DragScrollDetailsLayout.DragScrollDetailsLayoutActivity;
import com.syxl.customviewdemo.base.MenuAdapter;
import com.syxl.customviewdemo.base.MenuBean;
import com.syxl.customviewdemo.captcha.CaptchaActivity;
import com.syxl.customviewdemo.rvimageads.RvimageadsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MenuBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv_list = (RecyclerView) findViewById(R.id.rv_list);
        list.add(new MenuBean("拼图滑块",CaptchaActivity.class));
        list.add(new MenuBean("商品详情页拖拽控件",DragScrollDetailsLayoutActivity.class));
        list.add(new MenuBean("仿知乎创意广告",RvimageadsActivity.class));

        rv_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_list.setAdapter(new MenuAdapter(MainActivity.this,list));
    }
}
