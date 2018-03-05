package com.syxl.customviewdemo.DragScrollDetailsLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.syxl.customviewdemo.DragScrollDetailsLayout.view.DragScrollDetailsLayout;
import com.syxl.customviewdemo.R;

/**
 * Created by likun on 2018/3/5.
 */

public class FragmentTabHostSlidingLayoutActivity extends AppCompatActivity{
    FragmentTabHost fragmentTabHost;
    private Class[] mHomeFragments={FragmentItem2.class,FragmentItem1.class,FragmentItem2.class};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tabhost_sliding);
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tablayout);
        fragmentTabHost.setup(FragmentTabHostSlidingLayoutActivity.this,getSupportFragmentManager(),R.id.content);
        for (int i = 0; i < mHomeFragments.length; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(""+i).setIndicator(""+i);
            fragmentTabHost.addTab(tabSpec,mHomeFragments[i],null);
        }


        Button up =  findViewById(R.id.up);
        final DragScrollDetailsLayout dragScrollDetailsLayout = findViewById(R.id.detail);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragScrollDetailsLayout.scrollToTop();
            }
        });
    }
}
