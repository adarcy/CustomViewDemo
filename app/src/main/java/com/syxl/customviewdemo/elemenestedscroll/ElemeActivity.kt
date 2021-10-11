package com.syxl.customviewdemo.elemenestedscroll

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.syxl.customviewdemo.R

class ElemeActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleme)
        StatusBarUtil.setTranslucentForImageView(this, 0, null)

    }
}