package com.syxl.customviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewConfiguration

import com.blankj.utilcode.util.LogUtils
import com.syxl.customviewdemo.ChaosCompass.ChaosCompassActivity
import com.syxl.customviewdemo.DragScrollDetailsLayout.DragScrollDetailsLayoutActivity
import com.syxl.customviewdemo.GALeafLoading.LeafLoadingActivity
import com.syxl.customviewdemo.base.MenuAdapter
import com.syxl.customviewdemo.base.MenuBean
import com.syxl.customviewdemo.captcha.CaptchaActivity
import com.syxl.customviewdemo.dragfillquestion.DragActivity
import com.syxl.customviewdemo.fragmentAnimation.FragmentTransactionActivity
import com.syxl.customviewdemo.rvimageads.RvimageadsActivity
import com.syxl.customviewdemo.span.SpanActivity
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<MenuBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv_list = findViewById<View>(R.id.rv_list) as RecyclerView
        list.add(MenuBean("拼图滑块", CaptchaActivity::class.java))
        list.add(MenuBean("商品详情页拖拽控件", DragScrollDetailsLayoutActivity::class.java))
        list.add(MenuBean("仿知乎创意广告", RvimageadsActivity::class.java))
        list.add(MenuBean("小米指南针和时钟", ChaosCompassActivity::class.java))
        list.add(MenuBean("树叶loading效果", LeafLoadingActivity::class.java))
        list.add(MenuBean("TextView 图文混排 & 炫酷的段落级Span解析", SpanActivity::class.java))
        list.add(MenuBean("拖拽完成选词", DragActivity::class.java))
        list.add(MenuBean("fragment切换动画", FragmentTransactionActivity::class.java))
        list.add(MenuBean("test", TestActivity::class.java))

        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list.adapter = MenuAdapter(this@MainActivity, list)


        val scaledDoubleTapSlop = ViewConfiguration.get(this).scaledDoubleTapSlop
        LogUtils.e("scaledDoubleTapSlop=$scaledDoubleTapSlop")


    }
}
