package com.syxl.customviewdemo

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Printer
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.syxl.customviewdemo.flow_layout.TagAdapter
import com.syxl.customviewdemo.flow_layout.FlowLayout
import kotlinx.android.synthetic.main.activiry_test.*
import java.lang.Thread.sleep

class LooperPrinterActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_looper_printer)

//        var list = arrayOf("Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
//                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
//                    "Android", "Weclome Hello", "Button Text", "TextView","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
//                    "Android", "Weclome", "Button ImageView")
//
//        flow_layout.setAdapter(object : TagAdapter<String>(list) {
//            override fun getView(parent: FlowLayout, position: Int, s: String): View {
//                val tv = LayoutInflater.from(applicationContext).inflate(
//                        R.layout.tv,
//                        flow_layout, false
//                ) as TextView
//                tv.text = s
//                return tv
//            }
//        })

        view1.setOnClickListener {
            var scaleX = ObjectAnimator.ofFloat(it, "scaleX", 0f, 1f).setDuration(4000)
            var scaleY = ObjectAnimator.ofFloat(it, "scaleY", 0f, 1f).setDuration(4000)
//            view_test.pivotX = (ai_wheel.left + ai_wheel.width/2).toFloat()
//            view_test.pivotY = (ai_wheel.top + ai_wheel.height/2).toFloat()
            sleep(500)
            scaleX.start()
            scaleY.start()
        }

        view2.setOnClickListener {
            var scaleX = ObjectAnimator.ofFloat(it, "scaleX", 0f, 1f).setDuration(4000)
            var scaleY = ObjectAnimator.ofFloat(it, "scaleY", 0f, 1f).setDuration(4000)
//            view_test.pivotX = (ai_wheel.left + ai_wheel.width/2).toFloat()
//            view_test.pivotY = (ai_wheel.top + ai_wheel.height/2).toFloat()
            view2.pivotX = 400f
            view2.pivotY = 400f
            view2.invalidate()

            sleep(500)
            scaleX.start()
            scaleY.start()
        }

//        Looper.getMainLooper().setMessageLogging(object : Printer {
//            private val START = ">>>>> Dispatching"
//            private val END = "<<<<< Finished"
//            private var time = -1L
//
//            override fun println(x: String?) {
//                if (x?.startsWith(START) == true) {
//                    //从这里开启一个定时任务来打印方法的堆栈信息
//                    LooperLog.instance.startPrintLog()
//                }
//                if (x?.startsWith(END) == true) {
//                    //从这里取消定时任务
//                    LooperLog.instance.canclePrintLog()
//
////                    if ((System.currentTimeMillis() - time) > 200){
////                        if (x?.contains("FrameHandler") != true){
////                            Log.e("printer", "==println==$x");
////                        }
////                    }
//                }
//            }
//        })
    }

}

class LooperLog private constructor() {
    private val mLogThread = HandlerThread("log")
    private val mIoHandler: Handler

    fun startPrintLog() {
        time = System.currentTimeMillis()
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK)
    }

    fun canclePrintLog() {
        mIoHandler.removeCallbacks(mLogRunnable)
    }

    companion object {
        val instance = LooperLog()
        private const val TIME_BLOCK = 100L
        var time = 0L
        private val mLogRunnable = Runnable {
            val sb = StringBuilder()
            val stackTrace = Looper.getMainLooper().thread.stackTrace
            for (s in stackTrace) {
                sb.append("$s \n")
            }
            Log.i("LogPrinter--", ((System.currentTimeMillis() - time).toString()))
            Log.i("LogPrinter--", sb.toString())
        }
    }

    init {
        mLogThread.start()
        mIoHandler = Handler(mLogThread.looper)
    }
}
