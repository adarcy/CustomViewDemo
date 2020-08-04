package com.syxl.customviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activiry_web.*

class WebViewActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_web)

        webview.loadUrl("http://test.weather.sohu.com?navigationBarHidden=1&statusBarStyle=2")
    }
}
