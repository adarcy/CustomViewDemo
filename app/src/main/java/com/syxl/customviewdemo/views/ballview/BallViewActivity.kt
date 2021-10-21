package com.syxl.customviewdemo.views.ballview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.syxl.customviewdemo.R

class BallViewActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ballview)
        StatusBarUtil.setTranslucentForImageView(this, 0, null)

    }
}