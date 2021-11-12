package com.syxl.customviewdemo

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Printer
import android.view.View
import android.view.ViewConfiguration
import android.webkit.ConsoleMessage
import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.bytedance.tailor.Tailor
import com.syxl.customviewdemo.ChaosCompass.ChaosCompassActivity
import com.syxl.customviewdemo.DragScrollDetailsLayout.DragScrollDetailsLayoutActivity
import com.syxl.customviewdemo.GALeafLoading.LeafLoadingActivity
import com.syxl.customviewdemo.activity.RxJava2Activity
import com.syxl.customviewdemo.activity.ScrollerViewPagerActivity
import com.syxl.customviewdemo.base.MenuAdapter
import com.syxl.customviewdemo.base.MenuBean
import com.syxl.customviewdemo.captcha.CaptchaActivity
import com.syxl.customviewdemo.dragfillquestion.DragActivity
import com.syxl.customviewdemo.elemenestedscroll.ElemeActivity
import com.syxl.customviewdemo.fragmentAnimation.FragmentTransactionActivity
import com.syxl.customviewdemo.itemDecorator.ItemDecorationActivity
import com.syxl.customviewdemo.rvimageads.RvimageadsActivity
import com.syxl.customviewdemo.span.SpanActivity
import com.syxl.customviewdemo.views.ballview.BallViewActivity
import hugo.weaving.DebugLog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.security.Permissions
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val list = ArrayList<MenuBean>()
    private val flist: ArrayList<User> = ArrayList()
    private val TAG = "MainActivity"

    @DebugLog
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
        list.add(MenuBean("ScrollerViewPager", ScrollerViewPagerActivity::class.java))
        list.add(MenuBean("test", TestActivity::class.java))
        list.add(MenuBean("web", WebViewActivity::class.java))
        list.add(MenuBean("ItemDecoration", ItemDecorationActivity::class.java))
        list.add(MenuBean("Eleme", ElemeActivity::class.java))
        list.add(MenuBean("BallView", BallViewActivity::class.java))
        list.add(MenuBean("LooperPrinter", LooperPrinterActivity::class.java))
        list.add(MenuBean("RxJava2", RxJava2Activity::class.java))

        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list.adapter = MenuAdapter(this@MainActivity, list)


        val scaledDoubleTapSlop = ViewConfiguration.get(this).scaledDoubleTapSlop
        LogUtils.e("scaledDoubleTapSlop=$scaledDoubleTapSlop")


        btn_kotlin.setOnClickListener {
//            KotlinActivity.actionStart(this, "Kotlin", "hello")
            Toast.makeText(this,"MyService oncreate",Toast.LENGTH_LONG).show()

            var intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        btn_tailor.setOnClickListener {

            if (!PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                var list = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this@MainActivity.requestPermissions(list,100)
                }
            }else{
                for (i in 0..49) {
                    val fragment = User()
                    flist.add(fragment)
                }

                val path: String = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator.toString() + "demo.hprof"
                Log.e(TAG,"path=$path")
                try {
                    Tailor.dumpHprofData(path, true)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }


        }

        var webview = WebView(this)

        webview.webChromeClient = object : WebChromeClient(){
            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                return super.onConsoleMessage(consoleMessage)
            }
        }



        var p = Printer {
//            LogUtils.e(it)
            Log.e("Printer",it)
        };
        Looper.getMainLooper().setMessageLogging(p);
    }

}
