package com.syxl.customviewdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.syxl.customviewdemo.R
import kotlinx.android.synthetic.main.activity_view_list.*

class ViewListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)

        //流光效果
        flowing_light_view.visibility = View.VISIBLE
        shine_text_view.visibility = View.VISIBLE
    }
}