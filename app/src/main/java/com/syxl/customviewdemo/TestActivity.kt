package com.syxl.customviewdemo

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.syxl.customviewdemo.flow_layout.TagAdapter
import com.syxl.customviewdemo.flow_layout.FlowLayout
import kotlinx.android.synthetic.main.activiry_test.*

class TestActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_test)

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
            scaleX.start()
            scaleY.start()
        }

    }

}
