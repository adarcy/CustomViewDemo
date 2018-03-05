package com.syxl.customviewdemo.DragScrollDetailsLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.syxl.customviewdemo.R;
import com.syxl.customviewdemo.base.MenuAdapter;
import com.syxl.customviewdemo.base.MenuBean;

import java.util.ArrayList;

/**
 * Created by likun on 2018/3/5.
 */

public class DragScrollDetailsLayoutActivity extends AppCompatActivity {
    private static final String TAG = "DragScrollDetailsLayout";
    private ArrayList<MenuBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv_list = (RecyclerView) findViewById(R.id.rv_list);
        list.add(new MenuBean("FragmentTabHostSlidingLayout",FragmentTabHostSlidingLayoutActivity.class));

        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(new MenuAdapter(DragScrollDetailsLayoutActivity.this,list));
    }
}
